package com.example.themovieapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapters.MovieAdapter
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.delegate.MovieViewHolderDelegate
import com.jakewharton.rxbinding4.widget.textChanges
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity(), MovieViewHolderDelegate {

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context , MovieSearchActivity::class.java)
        }
    }

    //adapter
    private lateinit var mMovieAdapter: MovieAdapter

    //Models
    private val mMovieModel : MovieModel = MovieModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        setUpRecyclerView()

        setUpListeners()
    }

    @SuppressLint("CheckResult")
    private fun setUpListeners() {
        edittextSearch.textChanges()
            .debounce(500L , TimeUnit.MILLISECONDS)
            .flatMap {
                mMovieModel.SearchMovie(it.toString())
            }
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            },{
                showError(it.localizedMessage ?: "")
            })
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieAdapter(this)
        rvSearchMovies.adapter = mMovieAdapter
        rvSearchMovies.layoutManager = GridLayoutManager(this,2)

    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
    }

    override fun showError(showErrorMessage: String) {

    }
}