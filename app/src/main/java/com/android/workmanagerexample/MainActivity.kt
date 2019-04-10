package com.android.workmanagerexample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.LayoutDirection
import android.util.Log
import com.android.workmanagerexample.database.JokeDao
import com.android.workmanagerexample.database.JokeDatabase
import com.android.workmanagerexample.databinding.ActivityMainBinding
import com.android.workmanagerexample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var jokeAdapter: JokeAdapter
    private lateinit var db: JokeDao

    companion object {
        const val TASK = "task"
        const val UNIQUE = "unique"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        setupRecyclerView()

        viewModel.fetchData()

        db = JokeDatabase.provideDatabase(this).getJokeDao()

        viewModel.obverseData.observe(this, Observer { workInfo ->
            viewModel.dbSize.set(db.sizeOfJoke())
            if (workInfo != null) {
                setDataToList()

            }else{
                if (db.sizeOfJoke()<=0){
                   setDataToList()
                }
            }
            //binding.tvState.append("\n"+workInfo!!.state.name)
        })

    }
    private fun setDataToList(){
        Log.e("Size", db.sizeOfJoke().toString())
        jokeAdapter.setJokeData(db.getAllJoke())
        jokeAdapter.notifyDataSetChanged()
    }

    private fun bindView() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupRecyclerView() {
        jokeAdapter = JokeAdapter()
        binding.jokeList.layoutManager = LinearLayoutManager(this)
        binding.jokeList.setHasFixedSize(true)
        binding.adapter = jokeAdapter
        val divider=DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        binding.jokeList.addItemDecoration(divider)

    }
}
