package com.elnaz.theguardiannewsfeed.fragments

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.elnaz.theguardiannewsfeed.R
import com.elnaz.theguardiannewsfeed.adapters.ListFragmentPagerAdapter
import com.elnaz.theguardiannewsfeed.core.BaseFragment
import com.elnaz.theguardiannewsfeed.data.TabTitle
import com.elnaz.theguardiannewsfeed.databinding.FragmentHomeBinding
import com.elnaz.theguardiannewsfeed.utils.TabTitles
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var tabTitleList: List<TabTitle>

    override fun setupViews() {
        tabTitleList = TabTitles.getTabs()

        val tablayout = binding.tabLayout
        val viewPager = binding.viewPager
        viewPager.isSaveEnabled = false

        val newsPagerAdapter = ListFragmentPagerAdapter(this)
        viewPager.adapter = newsPagerAdapter

        TabLayoutMediator(tablayout,viewPager) { tab, position ->
            tab.customView = setTabCustomView(position)
        }.attach()

    }

    private fun setTabCustomView(position: Int): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab, null)
        val text = view.findViewById<TextView>(R.id.tab_title)
        val image = view.findViewById<ImageView>(R.id.tab_icon)
        text.text = tabTitleList[position].name
        image.setImageResource(tabTitleList[position].icon)
        return view
    }

}