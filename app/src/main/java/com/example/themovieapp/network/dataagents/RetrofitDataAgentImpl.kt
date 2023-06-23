package com.example.themovieapp.network.dataagents

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.TheMovieApi
import com.example.themovieapp.network.responses.GetActorsResponse
import com.example.themovieapp.network.responses.GetCreditsByMovieResponse
import com.example.themovieapp.network.responses.GetGenreResponse
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//object RetrofitDataAgentImpl : MovieDataAgent {
//
//    private var mTheMovieApi: TheMovieApi? = null
//
//    init {
//        val mOkHttpClient = OkHttpClient.Builder()
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .readTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(mOkHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        mTheMovieApi = retrofit.create(TheMovieApi::class.java)
//
//    }
//
//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getNowPlayingMovies()
//            ?.enqueue(object : Callback<MovieListResponse> {
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val movieList = response.body()?.result ?: listOf()
//                        onSuccess(movieList)
//                    } else {
//                        onFailure(response.message())
//
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                          onFailure(t.message ?: "")
//                }
//            })
//    }
//
//    override fun getPopularMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//         mTheMovieApi?.getPopularMovies()
//             ?.enqueue(object : Callback<MovieListResponse>{
//
//                 override fun onResponse(
//                     call: Call<MovieListResponse>,
//                     response: Response<MovieListResponse>
//                 ) {
//                     if (response.isSuccessful){
//                         val movieList = response.body()?.result?: listOf()
//                         onSuccess(movieList)
//                     }else {
//                         onFailure(response.message())
//                     }
//                 }
//
//                 override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                        onFailure(t.message?: "")
//                 }
//             })
//    }
//
//    override fun getTopRatedMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//       mTheMovieApi?.getTopRatedMovies()
//           ?.enqueue(object : Callback<MovieListResponse>{
//
//               override fun onResponse(
//                   call: Call<MovieListResponse>,
//                   response: Response<MovieListResponse>
//               ) {
//                  if (response.isSuccessful){
//                      val movieList = response.body()?.result?: listOf()
//                      onSuccess(movieList)
//                  }else{
//                      onFailure(response.message())
//                  }
//               }
//
//               override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                     onFailure(t.message ?: "")
//               }
//
//           })
//    }
//
//    override fun getGenres(
//        onSuccess: (List<GenreVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getGenres()
//            ?.enqueue(object : Callback<GetGenreResponse>{
//
//                override fun onResponse(
//                    call: Call<GetGenreResponse>,
//                    response: Response<GetGenreResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        onSuccess(response.body()?.genres ?: listOf())
//                    } else {
//                        onFailure(response.message())
//                    }
//                }
//
//                override fun onFailure(call: Call<GetGenreResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getMoviesByGenres(
//        genreId: String,
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getMoviesByGenre(genreId = genreId)
//            ?.enqueue(object : Callback<MovieListResponse>{
//                override fun onResponse(
//                    call: Call<MovieListResponse>,
//                    response: Response<MovieListResponse>
//                ) {
//                   if (response.isSuccessful){
//                       val movieList = response.body()?.result ?: listOf()
//                       onSuccess(movieList)
//                   }else{
//                       onFailure(response.message())
//                   }
//                }
//
//                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
//        mTheMovieApi?.getActors()
//            ?.enqueue(object : Callback<GetActorsResponse>{
//                override fun onResponse(
//                    call: Call<GetActorsResponse>,
//                    response: Response<GetActorsResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val actorList = response.body()?.results ?: listOf()
//                        onSuccess(actorList)
//                    }
//                    onFailure(response.message())
//                }
//
//                override fun onFailure(call: Call<GetActorsResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getMovieDetails(
//        movieId: String,
//        onSuccess: (MovieVO) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getMovieDetails(movieId = movieId)
//            ?.enqueue(object : Callback<MovieVO>{
//
//                override fun onResponse(call: Call<MovieVO>, response: Response<MovieVO>) {
//                   if (response.isSuccessful){
//                       response.body()?.let{
//                           onSuccess(it)
//                       }
//                   } else {
//                       onFailure(response.message())
//                   }
//                }
//                override fun onFailure(call: Call<MovieVO>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//
//    override fun getCreditsByMovie(
//        movieId: String,
//        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mTheMovieApi?.getCreditsByMovie(movieId = movieId)
//            ?.enqueue(object : Callback<GetCreditsByMovieResponse>{
//
//                override fun onResponse(
//                    call: Call<GetCreditsByMovieResponse>,
//                    response: Response<GetCreditsByMovieResponse>
//                ) {
//                    if (response.isSuccessful){
//                        response.body()?.let {
//                            onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
//                        }
//                    }else {
//                        onFailure(response.message())
//                    }
//                }
//
//
//                override fun onFailure(call: Call<GetCreditsByMovieResponse>, t: Throwable) {
//                    onFailure(t.message ?: "")
//                }
//
//            })
//    }
//}

