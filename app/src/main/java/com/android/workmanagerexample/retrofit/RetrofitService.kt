package com.android.workmanagerexample.retrofit

import com.android.workmanagerexample.Joke
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("random_joke")
    fun getData():Call<Joke>
}