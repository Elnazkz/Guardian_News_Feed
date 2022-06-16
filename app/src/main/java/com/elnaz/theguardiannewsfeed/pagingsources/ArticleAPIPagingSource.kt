package com.elnaz.theguardiannewsfeed.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.services.network.ApiService
import com.elnaz.theguardiannewsfeed.services.room.ArticleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

const val INITIAL_KEY = 1
const val PAGE_SIZE = 10

class ArticleAPIPagingSource(
    private val apiService: ApiService,
    private val dao: ArticleDao
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: INITIAL_KEY

        return try {
            val result =
                apiService.getArticles(position)

            val dbData = dao.getAllSavedArticles()

            //TODO move to another scope
            val news = result.response.results.apply {

                dbData.onEach { dbArticle ->
                    this.find { dbArticle.id == it.id }?.apply {
                        this.selected = dbArticle.selected
                    }
                }
            }

            val nextKey = if (news.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }

            val prevKey = if (position == INITIAL_KEY) null else position - 1

            LoadResult.Page(
                data = news,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

}