package com.example.themovieapp.network.responses

import com.example.themovieapp.data.vos.GenreVO
import com.google.gson.annotations.SerializedName

data class GetGenreResponse (
    @SerializedName("genres")
    val genres: List<GenreVO>?
        )