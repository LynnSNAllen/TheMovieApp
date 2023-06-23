package com.example.themovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.MVP.presenters.MainPresenter
import com.example.themovieapp.MVP.presenters.MainPresenterImpl
import com.example.themovieapp.MVP.views.MainView
import com.example.themovieapp.MVVM.MainViewModel
import com.example.themovieapp.R
import com.example.themovieapp.adapters.BannerAdapter
import com.example.themovieapp.adapters.ShowCaseAdapter
import com.example.themovieapp.data.models.MovieModel
import com.example.themovieapp.data.models.MovieModelImpl
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegate.BannerViewHolderDelegate
import com.example.themovieapp.delegate.MovieViewHolderDelegate
import com.example.themovieapp.delegate.ShowCaseViewHolderDelegate
import com.example.themovieapp.dummy.dummyGenreList
import com.example.themovieapp.mvi.intents.MainIntent
import com.example.themovieapp.mvi.mvibase.MVIView
import com.example.themovieapp.mvi.states.MainState
import com.example.themovieapp.routers.navigateToMovieDetailsActivity
import com.example.themovieapp.routers.navigateToMovieSearchActivity
import com.example.themovieapp.viewpods.ActorListViewPod
import com.example.themovieapp.viewpods.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowCaseAdapter: ShowCaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMovieByGenreViewPod: MovieListViewPod
    lateinit var mActorListViewPod: ActorListViewPod

  //Presenter
    private lateinit var mPresenter: MainPresenter

    /// Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // Data
    private var mGenre: List<GenreVO>? = null

    // View Model
//    private lateinit var mViewModel : com.example.themovieapp.mvi.viewmodels.MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter() // MVP PRESENTER
//        setUpViewModel()  //MVVM VIEW MODEL

        setUpToolbar()
        setUpViewPod()
        setUpBannerViewPager()
        setUpShowCaseRecyclerView()
        setUpListeners()

        mPresenter.onUiReady(this) //MVP Demo



    }

    //MVP DEMO
    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)

    }
    private fun requestData() {
        // Now Playing Movies
        mMovieModel.getNowPlayingMovies{
            showErrorMessage()
        }?.observe(this){
            mBannerAdapter.setNewData(it)
        }

        // Popular Movies
        mMovieModel.getPopularMovies{
            showErrorMessage()
        }?.observe(this){
            mBestPopularMovieListViewPod.setData(it)
        }

        // Top Rated Movies
        mMovieModel.getTopRatedMovies{
            showErrorMessage()
        }?.observe(this){
            mShowCaseAdapter.setNewData(it)
        }

        // Get Genre
        mMovieModel.getGenres(
            onSuccess = {
                mGenre = it
                setUpGenreTabLayout(it)

                // Get Movies By Genre For First Genre
                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenre(genreId)
                }

            },
            onFailure = {
                showErrorMessage()
            }
        )

        // Get Actor
        mMovieModel.getActors(
            onSuccess = {
                mActorListViewPod.setData(it)
            },
            onFailure = {
                showErrorMessage()
            }
        )
    }
    private fun getMoviesByGenre(genreId: Int) {
        mMovieModel.getMoviesByGenre(genreId = genreId.toString(),
            onSuccess = {
                mMovieByGenreViewPod.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }
    private fun showErrorMessage() {
        Toast.makeText(this, "Failed",Toast.LENGTH_LONG).show()
    }

    private fun setUpViewPod() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMovieByGenreViewPod = vpMovieByGenre as MovieListViewPod
        mMovieByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mActorListViewPod = vpActorsList as ActorListViewPod
    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(mPresenter)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        //Genre Tab Layout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                mViewModel.getMovieByGenre(tab?.position ?: 0)
                mPresenter.onTapGenre(tab?.position ?: 0) // mvp
                Snackbar.make(window.decorView, tab?.text ?: "", Snackbar.LENGTH_LONG).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(mPresenter)
        viewPagerBanner.adapter = mBannerAdapter

        dotsBannerIndicator.attachTo(viewPagerBanner)
    }

    private fun setUpToolbar() {
        //App Bar Leading Icon
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

    }

    private fun setUpGenreTabLayout(genreList : List<GenreVO>) {
        genreList.forEach{
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun setUpGenreTabLayout() {
        dummyGenreList.forEach {
            val tab = tabLayoutGenre.newTab()
            tab.text = it
            tabLayoutGenre.addTab(tab)
        }
        //second form
//        dummyGenreList.forEach{
//            tabLayoutGenre.newTab().apply {
//                text = it
//                tabLayoutGenre.addTab(this)
//            }
//        }
    }

    //search in home screen
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.search){
            navigateToMovieSearchActivity()
        }
        return super.onOptionsItemSelected(item)
    }


    //implemented members
    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showPopularMovies(popularMovies: List<MovieVO>) {
        mBestPopularMovieListViewPod.setData(popularMovies)
    }

    override fun showTopRatedMovies(topRatedMovies: List<MovieVO>) {
        mShowCaseAdapter.setNewData(topRatedMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showMoviesByGenre(moviesByGenre: List<MovieVO>) {
        mMovieByGenreViewPod.setData(moviesByGenre)
    }

    override fun showActors(actors: List<ActorVO>) {
        mActorListViewPod.setData(actors)
    }

    override fun navigateToMovieDetailsScreen(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this,movieId = movieId))
    }

    override fun showError(errorString: String) {
        Toast.makeText(this, "", Toast.LENGTH_LONG).show()
    }

}