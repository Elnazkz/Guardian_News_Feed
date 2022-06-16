package com.elnaz.theguardiannewsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.databinding.ItemArticleBinding
import org.w3c.dom.Text

class ArticlesPagingAdapter(
    private val onFavClick: (Article) -> Unit,
    private val onClick: (Article, TextView, TextView) -> Unit
) :
    PagingDataAdapter<Article, ArticlesPagingAdapter.NewsViewHolder>(DiffUtilCallBack) {

    inner class NewsViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.articleTitle.text = article.webTitle
            binding.articleCategory.text = article.sectionName

            binding.root.setOnClickListener {
                onClick(article, binding.articleTitle, binding.articleCategory)
            }

            binding.favIcon.setOnClickListener {
                onFavClick(article)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
}