package com.android.workmanagerexample.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import androidx.work.*
import com.android.workmanagerexample.MainActivity.Companion.TASK
import com.android.workmanagerexample.MainActivity.Companion.UNIQUE
import com.android.workmanagerexample.workers.RefreshWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private  var workManager=WorkManager.getInstance()
    private lateinit var workInfo:LiveData<WorkInfo>
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest
    private lateinit var networkConstraint:Constraints

    val obverseData:LiveData<WorkInfo>
    get() = workInfo

    fun fetchData() {
        Log.e("Fetch","fetch")
        val sendData=Data.Builder().putString(TASK,"data is passed from Main Activity").build()
        networkConstraint=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest=PeriodicWorkRequest.Builder(RefreshWorker::class.java,1, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .setInputData(sendData)
            .addTag(UNIQUE)
            .build()
       /* oneTimeWorkRequest= OneTimeWorkRequest.Builder(RefreshWorker::class.java)
            .setConstraints(networkConstraint)
            .setInputData(sendData)
            .build()*/
        workInfo=workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
        workManager.enqueueUniquePeriodicWork(UNIQUE,ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
    }
    fun btnRefresh(){
        //workManager.enqueue(periodicWorkRequest)
    }
}
