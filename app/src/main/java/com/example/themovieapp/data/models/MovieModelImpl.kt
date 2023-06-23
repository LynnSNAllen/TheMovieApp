package com.example.themovieapp.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.example.themovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : BaseModel(), MovieModel {

    ///Data Agent
//    private val mMovieDataAgent : MovieDataAgent = RetrofitDataAgentImpl

    ///Database
//    private var mMovieDatabase : MovieDatabase? = null
//
//    fun initDatabase(context: Context){
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }

    @SuppressLint("CheckResult")
    override fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ) : LiveData<List<MovieVO>>? {
        //Data Base
//        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = NOW_PLAYING) ?: listOf())

        //Network
        mMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.result?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())


            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(type = NOW_PLAYING)
    }
        //Network Called by Agents Demo
//            onSuccess = {
//                  it.forEach{ movie -> movie.type = NOW_PLAYING}
//                mMovieDatabase?.movieDao()?.insertMovies(it)
//                onSuccess(it)
//            },
//            onFailure = onFailure
//        )


    @SuppressLint("CheckResult")
    override fun getPopularMovies(onFailure: (String) -> Unit) : LiveData<List<MovieVO>>? {

        //Data Base
//        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = POPULAR)?: listOf())

        //Network
        mMovieApi.getPopularMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.result?.forEach {movie -> movie.type = POPULAR}
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())

                },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
        return mMovieDatabase?.movieDao()?.getMoviesByType(type = POPULAR)
        //Network Called by Agents
//            onSuccess =
//            {
//                it.forEach { movie -> movie.type = POPULAR }
//                mMovieDatabase?.movieDao()?.insertMovies(it)
//                onSuccess(it)
//            }, onFailure = onFailure
//        )
    }

    @SuppressLint("CheckResult")
    override fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ) : LiveData<List<MovieVO>>? {
        //Data Base
//        onSuccess(mMovieDatabase?.movieDao()?.getMoviesByType(type = TOP_RATED)?: listOf())

        //Network Called by Agents
        mMovieApi.getTopRatedMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                it.result?.forEach { movie -> movie.type = TOP_RATED}
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())

            },{
                onFailure(it.localizedMessage ?: "")
            }
            )
        return mMovieDatabase?.movieDao()?.getMoviesByType(type = TOP_RATED)
    }

    @SuppressLint("CheckResult")
    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres ?: listOf())
            } , {
                onFailure(it.localizedMessage ?: "")
            })
    }

    @SuppressLint("CheckResult")
    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMoviesByGenre(genreId = genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess (it.result ?: listOf())
            },{
                onFailure(it.localizedMessage ?: "")
            })
    }

    @SuppressLint("CheckResult")
    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            } ,{
                onFailure(it.localizedMessage ?: "")
            })
    }

    @SuppressLint("CheckResult")
    override fun getMovieDetails(
        movieId: String,
        onFailure: (String) -> Unit
    ) : LiveData<MovieVO?>? {

        //Database
//       val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
//        movieFromDatabase?.let {
//            onSuccess(it)
//        }

        //Network Called by Agents
        mMovieApi.getMovieDetails( movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDatabaseToSync =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId = movieId.toInt())
                it.type = movieFromDatabaseToSync?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

            },{
                onFailure(it.localizedMessage ?: "")
            }
            )
        return mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
    }

    @SuppressLint("CheckResult")
    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
         mMovieApi.getCreditsByMovie(movieId = movieId)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({
                 onSuccess(Pair(it.cast ?: listOf() , it.crew ?: listOf()))
             } , {
                 onFailure(it.localizedMessage ?: "")
             })
    }

    @SuppressLint("CheckResult")
    override fun SearchMovie(query: String): Observable<List<MovieVO>> {
        return mMovieApi
            .searchMovie(query = query)
            .map { it.result ?: listOf() }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(Schedulers.io())
    }

    override fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.result?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())
            }

        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(type = NOW_PLAYING)
            ?.toObservable()
    }

    override fun getPopularMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getPopularMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.result?.forEach {movie -> movie.type = POPULAR}
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())

            }
        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(type = POPULAR)
            ?.toObservable()
    }

    override fun getTopRatedMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getTopRatedMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                it.result?.forEach { movie -> movie.type = TOP_RATED}
                mMovieDatabase?.movieDao()?.insertMovies(it.result ?: listOf())
            }
        return mMovieDatabase?.movieDao()
            ?.getMoviesByTypeFlowable(type = TOP_RATED)
            ?.toObservable()
    }

    override fun getGenresObservable(): Observable<List<GenreVO>>? {
        return mMovieApi.getGenres()
            .map { it.genres ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getActorsObservable(): Observable<List<ActorVO>>? {
        return mMovieApi.getActors()
            .map { it.results ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMoviesByGenresObservable(genreId: String): Observable<List<MovieVO>>? {
        return mMovieApi.getMoviesByGenre(genreId = genreId)
            .map { it.result ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMoviesByIdObservable(movieId: Int): Observable<MovieVO>? {
        mMovieApi.getMovieDetails( movieId = movieId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val movieFromDatabaseToSync =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId = movieId)
                it.type = movieFromDatabaseToSync?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

            }

        return mMovieDatabase?.movieDao()
            ?.getMoviesByIdFlowable(movieId)
            ?.toObservable()
    }

    override fun getCreditsByMovieObservable(movieId: Int): Observable<Pair<List<ActorVO>, List<ActorVO>>> {
      return mMovieApi.getCreditsByMovie(movieId = movieId.toString())
          .map {Pair<List<ActorVO>,List<ActorVO>>(it.cast ?: listOf() , it.crew ?: listOf()) }
          .subscribeOn(Schedulers.io())
    }


}