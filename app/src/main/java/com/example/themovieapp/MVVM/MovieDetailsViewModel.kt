package com.example.themovieapp.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.MovieVO

class MovieDetailsViewModel : ViewModel() {
    //Model
    private val mMovieModel = MovieModelImpl

    //Live Data
    var movieDetailsLiveData : LiveData<MovieVO?>? = null
    val castLiveData = MutableLiveData<List<ActorVO>>()
    val crewLiveData = MutableLiveData<List<ActorVO>>()
    val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId : Int){
        movieDetailsLiveData = mMovieModel.getMovieDetails(movieId = movieId.toString()) {mErrorLiveData.postValue(it)}

        mMovieModel.getCreditsByMovie(movieId.toString(), onSuccess = {
            castLiveData.postValue(it.first ?: listOf())
            crewLiveData.postValue(it.second ?: listOf())
        }, onFailure = {
            mErrorLiveData.postValue(it)
        })
    }
}