package com.example.localvibes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val okHttpClient by lazy{
        OkHttpClient.Builder()
            //.addInterceptor(AuthInterceptor(API_KEY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val placeApi: PlaceApi by lazy {
        retrofit.create(PlaceApi::class.java)
    }
}