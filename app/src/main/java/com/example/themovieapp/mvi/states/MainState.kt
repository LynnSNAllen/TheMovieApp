package com.example.themovieapp.mvi.states

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.mvi.mvibase.MVIState

data class MainState(
    val isLoading : Boolean = false,
    val errorMessage : String = "",

    val nowPlayingMovies: List<MovieVO>,
    val popularMovies : List<MovieVO> ,
    val topRatedMovies : List<MovieVO> ,
    val genres : List<GenreVO> ,
    val moviesByGenre : List<MovieVO> ,
    val actors : List<ActorVO>
) : MVIState {
    companion object {
        fun idle() : MainState = MainState(
            isLoading = false,
            errorMessage = "",
            nowPlayingMovies = listOf(),
            popularMovies =  listOf(),
            topRatedMovies = listOf(),
            genres = listOf(),
            moviesByGenre = listOf(),
            actors = listOf(),
        )
    }
}
