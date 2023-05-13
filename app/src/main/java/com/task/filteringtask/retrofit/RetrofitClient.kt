package com.task.filteringtask.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BaseUrl = "http://edarh.inun.com/Inunion/index.php/"

    private val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient.build()
            .create(ApiInterface::class.java)
    }
}