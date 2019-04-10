package com.android.workmanagerexample

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.workmanagerexample.database.Joke
import com.android.workmanagerexample.databinding.ItemJokeBinding

class JokeAdapter:RecyclerView.Adapter<JokeHolder>() {
    private var dataList= listOf<Joke>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): JokeHolder {
        val binding=DataBindingUtil.inflate<ItemJokeBinding>(LayoutInflater.from(parent.context),R.layout.item_joke,parent,false)
        return JokeHolder(binding)
    }

    override fun getItemCount(): Int =dataList.size

    override fun onBindViewHolder(holder: JokeHolder, position: Int) {
        holder.bind(dataList[position])
    }
    fun setJokeData(jokeList:List<Joke>){
        dataList=jokeList
    }
}