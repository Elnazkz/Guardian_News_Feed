package com.elnaz.theguardiannewsfeed.fragments

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.activities.*
import com.elnaz.theguardiannewsfeed.adapters.ArticlesPagingAdapter
import com.elnaz.theguardiannewsfeed.adapters.LoaderStateAdapter
import com.elnaz.theguardiannewsfeed.core.BaseFragment
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.databinding.FragmentNewslistBinding
import com.elnaz.theguardiannewsfeed.utils.toast
import com.elnaz.theguardiannewsfeed.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesListFragment : BaseFragment<FragmentNewslistBinding>(R.layout.fragment_newslist),
    SwipeRefreshLayout.OnRefreshListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var newsListAdapter: ArticlesPagingAdapter
    private lateinit var articleToBeAdded: Article
    private var clickable = true

    override fun setupViews() {
        binding.refresher.setOnRefreshListener(this)

        setArticlesAdapter()
        observe()
        setOnLoadListener()
    }

    private fun observe() {
        if (newsListAdapter.itemCount != 0)
            binding.noDataFound.visibility = View.GONE

        lifecycleScope.launch {
            mainViewModel.getArticles().observe(viewLifecycleOwner) {
                newsListAdapter.submitData(lifecycle, it)
            }
        }

        mainViewModel.articleFound.observe(viewLifecycleOwner){
            if (it.isEmpty() && this::articleToBeAdded.isInitialized){
                articleToBeAdded.selected = true
                mainViewModel.addArticleToFav(articleToBeAdded)
                binding.progressBar.visibility = View.GONE
                requireContext().toast(getString(R.string.added_to_favorites), Toast.LENGTH_LONG).show()
                articleToBeAdded.selected = true
            }else {
                binding.progressBar.visibility = View.GONE
                requireContext().toast(getString(R.string.article_already_added), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setArticlesAdapter() {
        newsListAdapter =
            ArticlesPagingAdapter({
                if (clickable)
                    onFavIcClick(it)
            }, { article, textView1, textView2 ->
                if (clickable)
                    onArticleClick(article, textView1, textView2)
            })

        val concatAdapter = newsListAdapter.withLoadStateFooter(LoaderStateAdapter {
            newsListAdapter.retry()
        })

        binding.articleRv.adapter = concatAdapter

    }


    private fun onFavIcClick(article: Article) {
        binding.progressBar.visibility = View.VISIBLE
        articleToBeAdded = article
        mainViewModel.findArticle(article.id)
    }

    private fun onArticleClick(article: Article, textView1: TextView, textView2: TextView) {
        val detailIntent = Intent(requireContext(),ArticleDetailActivity::class.java)
        val articleNamePair = Pair.create<View,String>(textView1, article.webTitle)
        val articleCatPair = Pair.create<View,String>(textView2, article.sectionName)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),articleNamePair,articleCatPair)

        detailIntent.putExtra(ARTICLE,article)
        detailIntent.putExtra(ARTICLE_TITLE,article.webTitle)
        detailIntent.putExtra(ARTICLE_CAT,article.sectionName)
        startActivity(detailIntent,options.toBundle())
    }

    private fun setOnLoadListener(){
        newsListAdapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                if (newsListAdapter.itemCount == 0) {
                    binding.noDataFound.visibility = View.VISIBLE
                } else {
                    binding.noDataFound.visibility = View.GONE
                }
            }

            val errorState = when {
                it.append is LoadState.Error -> it.append as LoadState.Error
                it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                else -> null
            }

            if (errorState != null){
                clickable = false
                binding.progressBar.visibility = View.VISIBLE
                requireContext().toast(errorState.error.toString(), Toast.LENGTH_LONG).show()
            }else{
                clickable = true
                binding.progressBar.visibility = View.GONE
            }

        }
    }

    override fun onRefresh() {
        if (this::newsListAdapter.isInitialized)
            newsListAdapter.retry()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.refresher.isRefreshing = false
        }, 2000L)
    }

}