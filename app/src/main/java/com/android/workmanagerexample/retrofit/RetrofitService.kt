package com.android.workmanagerexample.retrofit

import com.android.workmanagerexample.database.Joke
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("random_joke")
    fun getData():Call<Joke>
}