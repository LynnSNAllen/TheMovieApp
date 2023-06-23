package com.example.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.R
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegate.ShowCaseViewHolderDelegate
import com.example.themovieapp.viewholders.ShowCaseViewHolder

class ShowCaseAdapter(private val mDelegate: ShowCaseViewHolderDelegate) :
    RecyclerView.Adapter<ShowCaseViewHolder>() {

    private var mMovieList : List<MovieVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_showcases, parent, false)
        return ShowCaseViewHolder(view.rootView, mDelegate)
    }


    override fun onBindViewHolder(holder: ShowCaseViewHolder, position: Int) {
          if (mMovieList.isNotEmpty()){
              holder.bindData(mMovieList[position])
          }
    }


    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieList: List<MovieVO>){
        mMovieList = movieList
        notifyDataSetChanged()
    }
}