package com.example.localvibes.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(newRequest)
    }
}