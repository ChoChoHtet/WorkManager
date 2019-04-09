package com.android.workmanagerexample.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import androidx.work.*
import com.android.workmanagerexample.MainActivity.Companion.TASK
import com.android.workmanagerexample.MainActivity.Companion.UNIQUE
import com.android.workmanagerexample.retrofit.RetrofitService
import com.android.workmanagerexample.workers.RefreshWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private  var workManager=WorkManager.getInstance()
    private lateinit var workInfo:LiveData<WorkInfo>
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest
    private lateinit var networkConstraint:Constraints
    private lateinit var apiService: RetrofitService
    private val outputData=Data.Builder()

    val obverseData:LiveData<WorkInfo>
    get() = workInfo

    fun fetchData() {
        Log.e("Fetch","fetch")
        networkConstraint=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(RefreshWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .addTag(UNIQUE)
            .build()
         workManager.enqueueUniquePeriodicWork(UNIQUE,ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
       /* oneTimeWorkRequest= OneTimeWorkRequest.Builder(RefreshWorker::class.java)
            .setInitialDelay(1,TimeUnit.SECONDS)
            .setConstraints(networkConstraint)
            .build()*/
        workInfo=workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
        //workManager.enqueue(oneTimeWorkRequest)
    }

    fun btnRefresh(){
       fetchData()
    }
}
