package com.example.tweetapp.di

import okhttp3.Interceptor
import okhttp3.Response

class RapidAPIKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequestUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedRequestUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }
}
