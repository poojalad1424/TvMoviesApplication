package com.example.tvmoviesapplication

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmoviesapplication.databinding.ActivityMainBinding
import com.example.tvmoviesapplication.movies.viewmodel.MovieListViewModel

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.popularRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.topRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.topRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.upcomingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.upcomingRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.error.observe(
            this,
            Observer { value -> Toast.makeText(this, value, Toast.LENGTH_LONG).show() })
    }

}
