package com.android.workmanagerexample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.WorkManager
import androidx.work.Worker
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

        viewModel.obverseData.observe(this, Observer { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                binding.tvState.append("\n"+workInfo.state.name)
                val res = workInfo.outputData.getString(TASK)
                 val data = Utils.deserializeJson(res!!)
                binding.tvResult.text=data.type
            }
            binding.tvState.append("\n"+workInfo!!.state.name)
        })
    }
}
