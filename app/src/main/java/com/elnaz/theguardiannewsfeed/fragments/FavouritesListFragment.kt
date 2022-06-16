package com.elnaz.theguardiannewsfeed.fragments

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.activities.ARTICLE
import com.elnaz.theguardiannewsfeed.activities.ARTICLE_CAT
import com.elnaz.theguardiannewsfeed.activities.ARTICLE_TITLE
import com.elnaz.theguardiannewsfeed.activities.ArticleDetailActivity
import com.elnaz.theguardiannewsfeed.adapters.FavouriteAdapter
import com.elnaz.theguardiannewsfeed.core.BaseFragment
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.databinding.FragmentNewslistBinding
import com.elnaz.theguardiannewsfeed.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesListFragment : BaseFragment<FragmentNewslistBinding>(R.layout.fragment_newslist),
    SwipeRefreshLayout.OnRefreshListener {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun setupViews() {
        mainViewModel.getFavouriteArticles()
        binding.refresher.setOnRefreshListener(this)
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            mainViewModel.savedArticle.observe(viewLifecycleOwner) {
                binding.progressBar.visibility = View.GONE
                if (it.isNotEmpty()) {
                    binding.articleRv.visibility = View.VISIBLE
                    binding.noDataFound.visibility = View.GONE
                    setFavAdapter(it)
                } else {
                    binding.noDataFound.visibility = View.VISIBLE
                    binding.articleRv.visibility = View.INVISIBLE
                }

            }
        }
    }

    private fun setFavAdapter(list: List<Article>) {
        favouriteAdapter = FavouriteAdapter(
            {
                onFavIcClick(it)

            }, { article , textView1, textView2 ->

                onArticleClick(article, textView1, textView2)

            })

        favouriteAdapter.setData(list)
        binding.articleRv.adapter = favouriteAdapter

    }

    private fun onFavIcClick(article: Article) {
        mainViewModel.removeArticleFromFav(article)
        article.selected = false
    }

    private fun onArticleClick(article: Article, textView1: TextView, textView2: TextView) {
        val detailIntent = Intent(requireContext(), ArticleDetailActivity::class.java)
        val articleNamePair = Pair.create<View,String>(textView1, article.webTitle)
        val articleCatPair = Pair.create<View,String>(textView2, article.sectionName)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),articleNamePair,articleCatPair)

        detailIntent.putExtra(ARTICLE,article)
        detailIntent.putExtra(ARTICLE_TITLE,article.webTitle)
        detailIntent.putExtra(ARTICLE_CAT,article.sectionName)
        startActivity(detailIntent,options.toBundle())
    }

    override fun onRefresh() {
        mainViewModel.getFavouriteArticles()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.refresher.isRefreshing = false
        }, 2000L)
    }

}