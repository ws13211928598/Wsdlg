package com.example.wsdlg.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 *  created by ws
 *   on 2021/4/13
 *   describe:
 */
class WsViewPager2init() {
    var limit: Int = 0

    constructor(context: FragmentActivity,
                viewPager2: ViewPager2,
                tabLayout: TabLayout,
                fragmentMutableList: MutableList<Fragment>) : this() {
        viewPager2.adapter = object : FragmentStateAdapter(context) {
            override fun getItemCount(): Int {
                return fragmentMutableList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentMutableList[position]
            }
        }
        viewPager2.offscreenPageLimit = limit
        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->

        }).attach()

    }

    constructor(context: FragmentActivity,
                viewPager2: ViewPager2,
                tabLayout: TabLayout,
                fragmentMutableList: MutableList<Fragment>,
                tabName: MutableList<String>,
                tabIconID: MutableList<Int>) : this() {
        viewPager2.adapter = object : FragmentStateAdapter(context) {
            override fun getItemCount(): Int {
                return fragmentMutableList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentMutableList[position]
            }
        }
        viewPager2.offscreenPageLimit = limit
        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabName[position]
            tab.setIcon(tabIconID[position])
        }).attach()
    }

    constructor(
            context: FragmentActivity,
            viewPager2: ViewPager2,
            tabLayout: TabLayout,
            fragmentMutableList: MutableList<Fragment>,
            tabName: MutableList<String>) : this() {
        viewPager2.adapter = object : FragmentStateAdapter(context) {
            override fun getItemCount(): Int {
                return fragmentMutableList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentMutableList[position]
            }
        }
        viewPager2.offscreenPageLimit = limit
        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = tabName[position]
        }).attach()
    }
}