package com.elnaz.theguardiannewsfeed.di

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalHttpUrl  = request.url()

        val urlBuilder = originalHttpUrl .newBuilder()
            .addQueryParameter("api-key","test")
            .addQueryParameter("format","json")

        val newRequestBuilder = request.newBuilder()
            .url(urlBuilder.build())

        return chain.proceed(newRequestBuilder.build())
    }
}