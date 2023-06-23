package com.example.themovieapp.MVP.views

import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

interface MainView : BaseView {
     fun showNowPlayingMovies(nowPlayingMovies : List<MovieVO>)
     fun showPopularMovies(popularMovies : List<MovieVO>)
     fun showTopRatedMovies(topRatedMovies : List<MovieVO>)
     fun showGenres(genreList: List<GenreVO>)
     fun showMoviesByGenre(moviesByGenre: List<MovieVO>)
     fun showActors(actors : List<ActorVO>)
     fun navigateToMovieDetailsScreen(movieId: Int)
}