package com.elnaz.theguardiannewsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.databinding.ItemArticleBinding

class FavouriteAdapter(
    private val list: List<Article>,
    private val onFavClick: (Article) -> Unit,
    private val onClick: (Article, TextView, TextView) -> Unit
) : RecyclerView.Adapter<FavouriteAdapter.FavArticleViewHolder>() {

    inner class FavArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            val context = binding.root.context

            binding.favIcon.text = binding.root.context.getString(R.string.remove_from_fav)
            binding.articleTitle.text = article.webTitle
            binding.articleCategory.text = article.sectionName

            binding.root.setOnClickListener {
                onClick(article, binding.articleTitle, binding.articleCategory)
            }

            binding.favIcon.icon =
                ResourcesCompat.getDrawable(context.resources, R.drawable.ic_star, null)

            binding.favIcon.setOnClickListener {
                Toast.makeText(context, context.getString(R.string.article_removed), Toast.LENGTH_LONG).show()
                onFavClick(article)
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return FavArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavArticleViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}