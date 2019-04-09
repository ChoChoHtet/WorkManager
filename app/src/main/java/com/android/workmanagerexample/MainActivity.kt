package com.android.workmanagerexample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.workmanagerexample.database.JokeDatabase
import com.android.workmanagerexample.databinding.ActivityMainBinding
import com.android.workmanagerexample.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TASK = "task"
        const val UNIQUE="unique"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        viewModel.fetchData()
        val jokeDao=JokeDatabase.provideDatabase(this).getJokeDao()
        viewModel.obverseData.observe(this, Observer { workInfo ->
            Log.e("Size",jokeDao.sizeOfJoke().toString())
            if (workInfo != null) {
                binding.tvState.append("\n"+workInfo.state.name)
                jokeDao.getAllJoke().forEach {
                    binding.tvResult.append("\n ${it.type}")
                }

            }
            binding.tvState.append("\n"+workInfo!!.state.name)
        })

    }
}
