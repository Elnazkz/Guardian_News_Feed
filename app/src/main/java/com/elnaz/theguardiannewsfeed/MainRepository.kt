package com.elnaz.theguardiannewsfeed

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.pagingsources.ArticleDBPagingSource
import com.elnaz.theguardiannewsfeed.pagingsources.ArticleAPIPagingSource
import com.elnaz.theguardiannewsfeed.pagingsources.PAGE_SIZE
import com.elnaz.theguardiannewsfeed.services.network.ApiService
import com.elnaz.theguardiannewsfeed.services.room.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDataBase
) {

    suspend fun insertArticle(article: Article){
        withContext(Dispatchers.IO){
            db.articleDao.insert(article)
        }
    }

    suspend fun deleteArticle(article: Article){
        withContext(Dispatchers.IO){
            db.articleDao.delete(article)
        }
    }

    suspend fun findArticle(id: String): List<Article> =
        db.articleDao.getArticleByID(id)

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