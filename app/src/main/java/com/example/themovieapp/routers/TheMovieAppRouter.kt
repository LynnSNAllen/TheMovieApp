package com.example.themovieapp.routers

import android.app.Activity
import com.example.themovieapp.activities.MovieDetailsActivity
import com.example.themovieapp.activities.MovieSearchActivity

fun Activity.navigateToMovieDetailsActivity(movieId : Int) {
    startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
}

fun Activity.navigateToMovieSearchActivity() {
    startActivity(MovieSearchActivity.newIntent(this))
}