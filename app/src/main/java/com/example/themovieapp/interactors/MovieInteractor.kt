package com.example.themovieapp.interactors

import androidx.lifecycle.LiveData
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Observable

interface MovieInteractor{

    fun getNowPlayingMovies(
        onFailure : (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?


    fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMoviesByGenre(
        genreId : String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieDetails(
        movieId : String,
        onFailure: (String) -> Unit
    ) : LiveData<MovieVO?>?

    fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO> , List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun SearchMovie(
        query : String
    ): Observable<List<MovieVO>>
}