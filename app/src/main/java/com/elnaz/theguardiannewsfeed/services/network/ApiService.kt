package com.elnaz.theguardiannewsfeed.services.network

import com.elnaz.theguardiannewsfeed.data.ArticlesResponse
import com.elnaz.theguardiannewsfeed.data.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getArticles(
        @Query("page") pageNumber: Int
    ): Response<ArticlesResponse>
}