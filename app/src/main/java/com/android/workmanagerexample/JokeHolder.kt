package com.android.workmanagerexample

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.workmanagerexample.database.Joke
import com.android.workmanagerexample.databinding.ItemJokeBinding

class JokeHolder( private val binding:ItemJokeBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(data:Joke){
        binding.joke=data
    }
}