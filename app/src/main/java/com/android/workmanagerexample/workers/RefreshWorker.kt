package com.android.workmanagerexample.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.Build
import android.provider.SyncStateContract
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import androidx.work.*
import com.android.workmanagerexample.MainActivity
import com.android.workmanagerexample.MainActivity.Companion.TASK
import com.android.workmanagerexample.R
import com.android.workmanagerexample.database.JokeDatabase
import com.android.workmanagerexample.retrofit.RetrofitCall
import com.android.workmanagerexample.retrofit.RetrofitService

class RefreshWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private lateinit var apiService: RetrofitService
    private val db = JokeDatabase.provideDatabase(context).getJokeDao()
    override fun doWork(): Result {
        Log.e("Do Work ", "Worker is working")
        apiService = RetrofitCall.fetchData
        val response = apiService.getData().execute()
        if (response.isSuccessful) {
            db.addJoke(response.body()!!)
            showDataUpdateNotification("Update News", "New joke data is received")
            return Result.success()
        }
        return Result.retry()
    }

    private fun showDataUpdateNotification(title: String, body: String?) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //above API 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("app_id", "sample_app", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(applicationContext, "app_id")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(1, builder.build())

    }
}
