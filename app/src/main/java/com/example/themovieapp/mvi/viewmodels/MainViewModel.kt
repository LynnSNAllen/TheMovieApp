package com.example.themovieapp.mvi.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.mvi.intents.MainIntent
import com.example.themovieapp.mvi.mvibase.MVIViewModel
import com.example.themovieapp.mvi.processors.MainProcessor
import com.example.themovieapp.mvi.states.MainState

class MainViewModel(override var state: MutableLiveData<MainState> = MutableLiveData(MainState.idle())) : MVIViewModel<MainState,MainIntent> ,ViewModel() {

    private val mProcessor = MainProcessor

    override fun processIntent(intent: MainIntent, lifecycleOwner: LifecycleOwner) {
        when(intent) {
            //load Home Page Data
            MainIntent.LoadAllHomePageData -> {
                state.value?.let {
                    mProcessor.loadAllHomePageData(
                        previousState = it
                    ).observe(lifecycleOwner) { newState ->
                        state.postValue(newState)
                        if (newState.moviesByGenre.isEmpty()){
                            processIntent(MainIntent.LoadMoviesByGenreIntent(0),lifecycleOwner)
                        }
                    }
                }
            }
            // Load Movies By Genre
            is MainIntent.LoadMoviesByGenreIntent -> {
                state.value?.let {
                    val genreid = it.genres.getOrNull(intent.genrePosition)?.id ?: 0
                    mProcessor.loadMoviesByGenre(
                        genreId = genreid,
                        previousState = it
                    ).observe(lifecycleOwner , state::postValue)
                }
            }
        }
    }

}