package com.example.tvmoviesapplication.movies.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.tvmoviesapplication.movies.adapter.MovieListAdapter
import com.example.tvmoviesapplication.movies.interfaces.CellClickListener
import com.example.tvmoviesapplication.movies.interfaces.MovieApi
import com.example.tvmoviesapplication.movies.model.Movie
import com.example.tvmoviesapplication.movies.model.MovieResponse
import com.example.tvmoviesapplication.movies.utils.API_KEY
import com.example.tvmoviesapplication.movies.utils.MovieType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MovieListViewModel : BaseViewModel(),
    CellClickListener {

    @Inject
    lateinit var movieApi: MovieApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val detailsVisibility: MutableLiveData<Int> = MutableLiveData()

    lateinit var popularMovieResponse: MovieResponse
    lateinit var topRatedMovieResponse: MovieResponse
    lateinit var upcomingMovieResponse: MovieResponse


    val error = MutableLiveData<String>()

    val movieTitle = MutableLiveData<String>()
    val movieBody = MutableLiveData<String>()
    val movieYear = MutableLiveData<String>()

    val popularMovieCount = MutableLiveData<String>()
    val topRatedMovieCount = MutableLiveData<String>()
    val upcomingMovieCount = MutableLiveData<String>()

    val popularMovieCountVisibility = MutableLiveData<Int>()
    val topRatedMovieCountVisibility = MutableLiveData<Int>()
    val upcomingMovieCountVisibility = MutableLiveData<Int>()

    val popularMovieFocused = MutableLiveData<String>()
    val topRatedMovieFocused = MutableLiveData<String>()
    val upcomingMovieFocused = MutableLiveData<String>()

    val popularMovieListAdapter: MovieListAdapter =
        MovieListAdapter(this)
    val upcomingMovieListAdapter: MovieListAdapter =
        MovieListAdapter(this)
    val topRatedMovieListAdapter: MovieListAdapter =
        MovieListAdapter(this)

    init {
        popularMovieCountVisibility.value = View.GONE
        topRatedMovieCountVisibility.value = View.GONE
        upcomingMovieCountVisibility.value = View.GONE
         loadMovies()
    }

    override fun onCellClickListener(data: Movie, type: MovieType, index : Int) {
        movieTitle.value = data.title
        movieBody.value = data.overview
        movieYear.value = data.release_date

        val focus = index + 1
        when(type) {
            MovieType.POPULAR -> {
                popularMovieFocused.value = "$focus of "
                popularMovieCountVisibility.value = View.VISIBLE
                topRatedMovieCountVisibility.value = View.GONE
                upcomingMovieCountVisibility.value = View.GONE
            }
            MovieType.TOPRATED -> {
                topRatedMovieFocused.value = "$focus of "
                topRatedMovieCountVisibility.value = View.VISIBLE
                popularMovieCountVisibility.value = View.GONE
                upcomingMovieCountVisibility.value = View.GONE
            }
            MovieType.UPCOMING -> {
                upcomingMovieFocused.value = "$focus of "
                upcomingMovieCountVisibility.value = View.VISIBLE
                popularMovieCountVisibility.value = View.GONE
                topRatedMovieCountVisibility.value = View.GONE
            }
        }
    }

    private fun loadMovies() {
        val request: ArrayList<Observable<MovieResponse>> = ArrayList()
        request.add(movieApi.getPopularMovies(API_KEY,"en-US",1).subscribeOn(Schedulers.io()))
        request.add(movieApi.getTopRatedMovies(API_KEY,"en-US",1).subscribeOn(Schedulers.io()))
        request.add(movieApi.getUpcomingMovies(API_KEY,"en-US",1).subscribeOn(Schedulers.io()))

        subscription = Observable.zip(request[0],request[1],request[2],
            Function3<MovieResponse, MovieResponse, MovieResponse, Unit> {
                    popularMovie, topRatedMovie, upcomingMovie ->
                this.popularMovieResponse = popularMovie
                this.topRatedMovieResponse = topRatedMovie
                this.upcomingMovieResponse = upcomingMovie
            })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { onRetrievePostListSuccess() },
                { error -> onRetrievePostListError(error.message) }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        detailsVisibility.value = View.GONE
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
        detailsVisibility.value = View.VISIBLE
    }

    private fun onRetrievePostListSuccess() {
        popularMovieListAdapter.updateMovieList(popularMovieResponse, MovieType.POPULAR)
        popularMovieCount.value = popularMovieResponse.results.size.toString()
        topRatedMovieListAdapter.updateMovieList(topRatedMovieResponse, MovieType.TOPRATED)
        topRatedMovieCount.value = topRatedMovieResponse.results.size.toString()
        upcomingMovieListAdapter.updateMovieList(upcomingMovieResponse, MovieType.UPCOMING)
        upcomingMovieCount.value = upcomingMovieResponse.results.size.toString()
    }

    private fun onRetrievePostListError(message: String?) {
        error.value = message
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}