package com.android.workmanagerexample.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCall {
    const val URL = "https://official-joke-api.appspot.com/"
    private var instance: Retrofit? = null
    private val retrofitInstance: Retrofit
        get() {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build()
            }
            return instance!!
        }

    val fetchData: RetrofitService
        get() = retrofitInstance.create(RetrofitService::class.java)

}