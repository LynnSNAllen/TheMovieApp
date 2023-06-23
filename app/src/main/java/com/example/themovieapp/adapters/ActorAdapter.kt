package com.example.themovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.R
import com.example.themovieapp.data.vos.ActorVO
import com.example.themovieapp.viewholders.ActorViewHolder

class ActorAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    private var mActors: List<ActorVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor,parent,false)
        return ActorViewHolder(view.rootView)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        if (mActors.isNotEmpty()) {
            holder.bindData(mActors[position])
        }
    }

    override fun getItemCount(): Int {
        return mActors.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(actors: List<ActorVO>) {
        mActors = actors
        notifyDataSetChanged()
    }
}