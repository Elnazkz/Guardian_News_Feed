package com.elnaz.theguardiannewsfeed.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elnaz.theguardiannewsfeed.fragments.ArticlesListFragment
import com.elnaz.theguardiannewsfeed.fragments.FavouritesListFragment

class ListFragmentPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            ArticlesListFragment()
        }else{
            FavouritesListFragment()
        }

    }
}