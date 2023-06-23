package com.example.themovieapp.MVP.presenters

import com.example.themovieapp.MVP.views.MainView
import com.example.themovieapp.delegate.BannerViewHolderDelegate
import com.example.themovieapp.delegate.MovieViewHolderDelegate
import com.example.themovieapp.delegate.ShowCaseViewHolderDelegate

interface MainPresenter : IBasePresenter , BannerViewHolderDelegate , ShowCaseViewHolderDelegate , MovieViewHolderDelegate{
    fun initView(view : MainView)
    fun onTapGenre(genrePosition : Int)
}