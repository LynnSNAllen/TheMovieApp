package com.example.themovieapp.MVP.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.MVP.views.MovieDetailsView
import com.example.themovieapp.interactors.MovieInteractor
import com.example.themovieapp.interactors.MovieInteractorImpl

class MovieDetailsPresenterImpl : ViewModel() , MovieDetailsPresenter {

    //Interactor
    private val mMovieInteractor : MovieInteractor = MovieInteractorImpl

    //View
    private var mView : MovieDetailsView? = null

    override fun initView(view: MovieDetailsView) {
       mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        //Movie Details
        mMovieInteractor.getMovieDetails(movieId.toString()) {
            mView?.showError(it)
        }?.observe(owner){
            it?.let {
                mView?.showMovieDetails(it)
            }
        }
        //Credits
        mMovieInteractor.getCreditsByMovie(movieId = movieId.toString() , onSuccess = {
            mView?.showCreditsByMoive(cast = it.first , crew = it.second)
        }, onFailure = {
            mView?.showError(it)
        })
    }

    override fun onTapBack() {
       mView?.navigateBack()
    }

    override fun onUiReady(owner: LifecycleOwner) {

    }


}