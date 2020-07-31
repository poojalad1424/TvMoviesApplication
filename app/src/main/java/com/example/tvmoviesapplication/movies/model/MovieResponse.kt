package com.example.tvmoviesapplication.movies.model

class MovieResponse {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: List<Movie> = listOf()

}