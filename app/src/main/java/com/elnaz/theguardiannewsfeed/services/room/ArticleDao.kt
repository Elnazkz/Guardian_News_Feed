package com.elnaz.theguardiannewsfeed.services.room

import androidx.room.Dao
import androidx.room.Query
import com.elnaz.theguardiannewsfeed.data.Article
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao : BaseDao<Article>() {

    @Query("SELECT * FROM articles ORDER BY _id LIMIT :limit OFFSET :offset")
    abstract suspend fun getPagedList(limit: Int, offset: Int) : List<Article>

    @Query("SELECT * FROM articles")
    abstract suspend fun getAllSavedArticles(): List<Article>

    @Query("SELECT * FROM articles")
    abstract fun getAllSavedArticlesFlow(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE id LIKE '%' || :id || '%'")
    abstract suspend fun getArticleByID(id: String): List<Article>

}