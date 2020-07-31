package com.example.tvmoviesapplication.movies.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tvmoviesapplication.movies.dagger.DaggerViewModelInjector
import com.example.tvmoviesapplication.movies.utils.ServiceGenerator
import com.example.tvmoviesapplication.movies.dagger.ViewModelInjector


abstract class BaseViewModel: ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector.builder()
        .serviceGenerator(ServiceGenerator)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MovieListViewModel -> injector.inject(this)
        }
    }
}