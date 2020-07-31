package com.example.tvmoviesapplication.movies.interfaces

import com.example.tvmoviesapplication.movies.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("popular?")
    fun getPopularMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                  @Query("page") page: Int): Observable<MovieResponse>

    @GET("upcoming?")
    fun getUpcomingMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                  @Query("page") page: Int): Observable<MovieResponse>

    @GET("top_rated?")
    fun getTopRatedMovies(@Query("api_key") api_key: String,
                  @Query("language") language: String,
                        @Query("page") page: Int): Observable<MovieResponse>
}