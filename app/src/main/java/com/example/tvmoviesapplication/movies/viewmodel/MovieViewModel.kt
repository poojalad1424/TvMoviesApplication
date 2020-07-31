package com.example.tvmoviesapplication.movies.viewmodel

import android.opengl.Visibility
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.tvmoviesapplication.movies.model.Movie

class MovieViewModel : BaseViewModel() {

    private val movieTitle = MutableLiveData<String>()

    private val movieProfile = MutableLiveData<String>()

    private val titleVisibility =  MutableLiveData<Int>()

    fun bind(movie: Movie) {
        movieTitle.value = movie.title
        movieProfile.value = movie.poster_path
        titleVisibility.value = View.GONE
    }

    fun getMovieTitle(): MutableLiveData<String> {
        return movieTitle
    }

    fun getMovieProfile(): MutableLiveData<String> {
        return movieProfile
    }

    fun getTitleVisibility() : MutableLiveData<Int> {
        return titleVisibility
    }

}