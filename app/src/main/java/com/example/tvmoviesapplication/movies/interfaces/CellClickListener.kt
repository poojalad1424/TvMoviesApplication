package com.example.tvmoviesapplication.movies.interfaces

import com.example.tvmoviesapplication.movies.model.Movie
import com.example.tvmoviesapplication.movies.utils.MovieType

interface CellClickListener {
    fun onCellClickListener(data: Movie, type: MovieType, index : Int)
}