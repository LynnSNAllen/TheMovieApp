package com.example.themovieapp.MVP.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.MVP.views.MainView
import com.example.themovieapp.activities.MainActivity
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.interactors.MovieInteractor
import com.example.themovieapp.interactors.MovieInteractorImpl

class MainPresenterImpl : ViewModel() , MainPresenter {

    //View
    var mView : MainView? = null

//    //Model
//    private val mMovieModel : MovieModel = MovieModelImpl

    //Interactor
    private val mMovieInteractor : MovieInteractor = MovieInteractorImpl

    //States
    private var mGenres : List<GenreVO>? = listOf()

    override fun initView(view: MainView) {
       mView = view
    }

    override fun onUiReady(owner: LifecycleOwner) {
        //Now Playing
        mMovieInteractor.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        //Popular Movies
        mMovieInteractor.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        //Top Rated Movies
       mMovieInteractor.getTopRatedMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        //Genre and Get Movies For First Genre
        mMovieInteractor.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let {firstGenreId ->
                    onTapGenre(firstGenreId)
                }
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        //Actors
        mMovieInteractor.getActors(
            onSuccess = {
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGenre(genrePosition: Int) {
       mGenres?.getOrNull(genrePosition)?.id?.let {  genreId ->
           mMovieInteractor.getMoviesByGenre(genreId = genreId.toString(), onSuccess = {
               mView?.showMoviesByGenre(it)
           }, onFailure = {
               mView?.showError(it)
           })
       }
    }



    override fun onTapMovieFromBanner(movieId: Int) {
       mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovie(movieId: Int) {
       mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun showError(showErrorMessage: String) {

    }
}