package com.elnaz.theguardiannewsfeed.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.services.room.ArticleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

const val INITIAL_PAGE = 0

class ArticleDBPagingSource(
    private val dao: ArticleDao
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: INITIAL_PAGE

        return try {
            withContext(Dispatchers.IO) {
                val entities =
                    dao.getPagedList(params.loadSize, page * params.loadSize)

                if (page != 0) delay(1000)

                LoadResult.Page(
                    data = entities,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (entities.isEmpty()) null else page + 1
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}