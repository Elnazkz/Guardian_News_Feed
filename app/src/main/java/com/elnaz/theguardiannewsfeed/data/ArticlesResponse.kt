package com.elnaz.theguardiannewsfeed.data

data class ArticlesResponse(
    val status: String,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val orderBy: String,
    val results: List<Article>
)
