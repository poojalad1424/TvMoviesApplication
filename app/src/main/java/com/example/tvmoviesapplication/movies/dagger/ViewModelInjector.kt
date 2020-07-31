package com.example.tvmoviesapplication.movies.dagger

import com.example.tvmoviesapplication.movies.utils.ServiceGenerator
import com.example.tvmoviesapplication.movies.viewmodel.MovieListViewModel
import dagger.Component
import javax.inject.Singleton

/**

 * Component providing inject() methods for presenters.

 */

@Singleton
@Component(modules = [(ServiceGenerator::class)])
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(movieListViewModel: MovieListViewModel)

    @Component.Builder
    interface Builder {

        fun build(): ViewModelInjector

        fun serviceGenerator(serviceGenerator: ServiceGenerator) : Builder

    }

}