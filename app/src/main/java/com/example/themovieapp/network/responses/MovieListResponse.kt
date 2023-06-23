package com.example.themovieapp.network.responses

import com.example.themovieapp.data.vos.DateVO
import com.example.themovieapp.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @SerializedName("page")
    val page : Int?,

    @SerializedName("dates")
    val dates : DateVO?,

    @SerializedName("results")
    val result: List<MovieVO>?
)