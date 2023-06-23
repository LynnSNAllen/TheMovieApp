package com.example.themovieapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.themovieapp.MVP.presenters.MovieDetailsPresenter
import com.example.themovieapp.MVP.presenters.MovieDetailsPresenterImpl
import com.example.themovieapp.MVP.views.MovieDetailsView
import com.example.themovieapp.R
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.utils.IMAGE_BASE_URL
import com.example.themovieapp.viewpods.ActorListViewPod
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {

    //Presenter
    private lateinit var mPresenter: MovieDetailsPresenter

    //view pods
    lateinit var actorsViewPod: ActorListViewPod
    lateinit var creatorsViewPod: ActorListViewPod


    /// Model
    private val mMovieModel: MovieModel = MovieModelImpl

    companion object {

        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }
    // View Model
//    private lateinit var mViewModel: com.example.themovieapp.mvi.viewmodels.MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
        setUpPresenter()

        setUpViewPod()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let {
            mPresenter.onUiReadyInMovieDetails(this, movieId) // mvp
            requestData(it)
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MovieDetailsPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun requestData(movieId: Int) {
        //For Movie Details
        mMovieModel.getMovieDetails(
            movieId = movieId.toString(),
            onFailure = { showError(it) }
        )?.observe(this) {
            it?.let { movieDetails -> bindData(movieDetails) }
        }

        //For Credits by Movies
        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                actorsViewPod.setData(it.first)
                creatorsViewPod.setData(it.second)
            },
            onFailure = {
                showError(it)
            }
        )
    }
//    private fun observeLiveData() {
//        mViewModel.movieDetailsLiveData?.observe(this) {
//            it?.let { movie -> bindData(movie) }
//        }
//        mViewModel.castLiveData?.observe(this,actorsViewPod::setData)
//        mViewModel.crewLiveData?.observe(this,creatorsViewPod::setData)
//    }

    //    private fun setUpViewModel(movieId: Int) {
//         mViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
//        mViewModel.getInitialData(movieId)
//    }
    private fun bindData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMovieDetails)

        tvMovieName.text = movie.title ?: ""  //movie name

        collapsingToolbarName.title = movie.title ?: ""  //movie scroll up shown up name

        tvMovieReleaseYear.text = movie.releaseDate?.substring(0, 4)
        tvRateMovie.text = movie.voteAverage?.toString() ?: ""
        movie.voteCount?.let {
            tvNumberofVotes.text = "$it votes"
        }
        rbRatingBarMovieDetails.rating = movie.getRatingBasedOnFiveStars()

        bindGenres(movie, movie.genres ?: listOf())

        tvOverview.text = movie.overView ?: ""
        tvOriginalTitle.text = movie.title ?: ""
        tvType.text = movie.getGenresAsCommaSeparatedString()
        tvProducation.text = movie.getProductionCountriesAsCommaSeparatedString()
        tvPremiere.text = movie.releaseDate ?: ""
        tvDescription.text = movie.overView ?: ""

    }

    private fun bindGenres(movie: MovieVO, genres: List<GenreVO>) {
        movie.genres?.count()?.let {
            tvFirstGenre.text = genres.firstOrNull()?.name ?: ""
            tvSecondGenre.text = genres.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = genres.getOrNull(2)?.name ?: ""

            if (it < 3) {
                tvThirdGenre.visibility = View.GONE
            } else if (it < 2) {
                tvSecondGenre.visibility = View.GONE
            }
        }
    }



    private fun setUpListeners() {
        btnBack.setOnClickListener {
            mPresenter.onTapBack()
        }
    }

    private fun setUpViewPod() {
        actorsViewPod = vpActors as ActorListViewPod
        actorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )
        actorsViewPod.setUpActorListViewPod()

        creatorsViewPod = vpCreators as ActorListViewPod
        creatorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
        creatorsViewPod.setUpActorListViewPod()
    }
    override fun showMovieDetails(movie: MovieVO) {
        bindData(movie)
    }

    override fun showCreditsByMoive(cast: List<ActorVO>, crew: List<ActorVO>) {
        actorsViewPod.setData(cast)
        creatorsViewPod.setData(crew)
    }

    override fun navigateBack() {
        finish()
    }

    override fun showError(it: String) {
        Snackbar.make(window.decorView, "Check Your internet connection", Snackbar.LENGTH_SHORT)
            .show()
    }

}


