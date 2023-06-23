package com.example.themovieapp.delegate

interface MovieViewHolderDelegate {
    fun onTapMovie(movieId: Int)
    fun showError(showErrorMessage : String)
}