package com.example.tvmoviesapplication.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmoviesapplication.R
import com.example.tvmoviesapplication.databinding.MovieItemLayoutBinding
import com.example.tvmoviesapplication.movies.interfaces.CellClickListener
import com.example.tvmoviesapplication.movies.model.Movie
import com.example.tvmoviesapplication.movies.model.MovieResponse
import com.example.tvmoviesapplication.movies.utils.MovieType
import com.example.tvmoviesapplication.movies.utils.MovieType.POPULAR
import com.example.tvmoviesapplication.movies.viewmodel.MovieViewModel


class MovieListAdapter(listener1: CellClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var movieList: List<Movie>
    private lateinit var movieType: MovieType
    private var listener: CellClickListener

    init {
        this.listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: MovieItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                listener.onCellClickListener(movieList[position], movieType, position)
                holder.viewModel.getTitleVisibility().value = View.VISIBLE
            } else holder.viewModel.getTitleVisibility().value = View.GONE
        }
        if(movieType == POPULAR && position == 0) holder.itemView.requestFocus()
    }

    override fun getItemCount(): Int {
        return if (::movieList.isInitialized) movieList.size else 0
    }

    fun updateMovieList(movieResponse: MovieResponse, movieType: MovieType) {
        this.movieList = movieResponse.results
        this.movieType = movieType
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val viewModel = MovieViewModel()

        fun bind(movie: Movie) {
            viewModel.bind(movie)
            binding.viewModel = viewModel
        }
    }
}