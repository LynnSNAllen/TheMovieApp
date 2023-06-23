package com.example.themovieapp.MVP.views

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.MovieVO

interface MovieDetailsView : BaseView {
    fun showMovieDetails(movie : MovieVO)
    fun showCreditsByMoive(cast : List<ActorVO> , crew : List<ActorVO>)
    fun navigateBack()
}