package com.elnaz.theguardiannewsfeed.activities

import android.transition.TransitionInflater
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.core.BaseActivity
import com.elnaz.theguardiannewsfeed.data.Article
import com.elnaz.theguardiannewsfeed.databinding.ActivityArticleDetailBinding

const val ARTICLE = "article"
const val ARTICLE_TITLE = "article_title"
const val ARTICLE_CAT = "article_cat"

class ArticleDetailActivity : BaseActivity<ActivityArticleDetailBinding>(R.layout.activity_article_detail) {

    private var article: Article? = null

    override fun setupViews() {
        article = intent?.extras?.takeIf {
            it.containsKey(ARTICLE)
        }?.getParcelable(ARTICLE)

        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)

        if (intent != null && intent.extras != null){
            setTransitionNames()
            setTexts()
            setFavIc()
        }
    }

    private fun setTransitionNames(){
        binding.articleWebTitle.transitionName = intent.extras?.getString(ARTICLE_TITLE)
        binding.articleCategory.transitionName = intent.extras?.getString(ARTICLE_CAT)
    }

    private fun setFavIc(){
        if (article!!.selected)
            binding.faveIcon.setImageResource(R.drawable.ic_star)
        else
            binding.faveIcon.setImageResource(R.drawable.ic_empty_start)
    }

    private fun setTexts(){
        binding.articleWebTitle.text = article!!.webTitle
        binding.articleCategory.text = article!!.sectionName
        binding.articleType.text = article!!.type
        binding.articleUrl.text = article!!.webUrl
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }
}