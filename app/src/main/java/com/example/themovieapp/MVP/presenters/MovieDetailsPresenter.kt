package com.example.themovieapp.MVP.presenters

import androidx.lifecycle.LifecycleOwner
import com.example.themovieapp.MVP.views.MovieDetailsView

interface MovieDetailsPresenter : IBasePresenter {
    fun initView (view : MovieDetailsView)
    fun onUiReadyInMovieDetails(owner: LifecycleOwner , movieId : Int)
    fun onTapBack()
}