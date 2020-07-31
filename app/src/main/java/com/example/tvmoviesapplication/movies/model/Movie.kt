package com.example.tvmoviesapplication.movies.model

import com.google.gson.annotations.SerializedName

class Movie{
    var vote_count: Int = 0
    var id: Int = 0
    var isVideo: Boolean = false
    var vote_average: Double = 0.toDouble()
    var title: String? = null
    var popularity: Double = 0.toDouble()
    var poster_path: String? = null
    var original_language: String? = null
    var original_title: String? = null
    var backdrop_path: String? = null
    var isAdult: Boolean = false
    var overview: String? = null
    var release_date: String? = null
    var genre_ids: List<Int>? = null
}