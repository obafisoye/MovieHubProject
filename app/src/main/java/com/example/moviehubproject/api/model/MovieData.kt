package com.example.moviehubproject.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieData(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Movie>,
)