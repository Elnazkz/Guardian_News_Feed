package com.elnaz.theguardiannewsfeed.utils

import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.data.TabTitle

object TabTitles {

    fun getTabs() = listOf(
        TabTitle(name = "Articles", R.drawable.ic_article),
        TabTitle(name = "My Favorites", R.drawable.ic_star)
    )

}