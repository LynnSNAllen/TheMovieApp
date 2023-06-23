package com.example.themovieapp.network

import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.GetActorsResponse
import com.example.themovieapp.network.responses.GetCreditsByMovieResponse
import com.example.themovieapp.network.responses.GetGenreResponse
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieApi {
    @GET(APT_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ) : Observable<MovieListResponse>

    @GET(API_GET_POPULAR_MOVIES)
    fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    )  : Observable<MovieListResponse>

    @GET(API_GET_TOP_RATED_MOVIES)
    fun getTopRatedMovies(
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    )  : Observable<MovieListResponse>

    @GET(API_GET_GENRES)
    fun getGenres(
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY
    ) : Observable<GetGenreResponse>

    @GET(API_GET_MOVIES_BY_GENRE)
    fun getMoviesByGenre(
        @Query(PARAM_GENRE_ID) genreId:String,
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY,
    ) : Observable<MovieListResponse>

    @GET(API_GET_ACTORS)
    fun getActors(
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ) : Observable<GetActorsResponse>

    @GET("$API_GET_MOVIES_DETAILS/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId : String,
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY,
    ) : Observable<MovieVO>

    @GET("$API_GET_CREDITS_BY_MOVIE/{movie_id}/credits")
    fun getCreditsByMovie(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY)apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ) : Observable<GetCreditsByMovieResponse>

    @GET(API_SEARCH_MOVIE)
    fun searchMovie(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_QUERY) query: String
    ): Observable<MovieListResponse>
}