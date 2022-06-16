package com.elnaz.theguardiannewsfeed

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.pagingsources.ArticleDBPagingSource
import com.elnaz.theguardiannewsfeed.pagingsources.ArticleAPIPagingSource
import com.elnaz.theguardiannewsfeed.pagingsources.PAGE_SIZE
import com.elnaz.theguardiannewsfeed.services.network.ApiService
import com.elnaz.theguardiannewsfeed.services.room.AppDataBase
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDataBase
) {

    suspend fun insertArticle(article: Article){
        db.articleDao.insert(article)
    }

    suspend fun deleteArticle(article: Article){
        db.articleDao.delete(article)
    }

    fun getArticles() : LiveData<PagingData<Article>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticleAPIPagingSource(apiService,db.articleDao) }
        ).liveData
    }

    //for pageble database
//    fun getSavedArticles(): LiveData<PagingData<Article>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = PAGE_SIZE,
//                enablePlaceholders = false,
//                initialLoadSize = PAGE_SIZE * 2
//            ),
//            pagingSourceFactory = { ArticleDBPagingSource(dao = db.articleDao) }
//        ).liveData
//    }

     fun getFavourites() =
        db.articleDao.getAllSavedArticlesFlow()
}