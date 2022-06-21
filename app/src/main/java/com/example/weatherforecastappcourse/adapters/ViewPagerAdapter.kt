package com.example.weatherforecastappcourse.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherforecastappcourse.fragments.MainFragment

class ViewPagerAdapter(fragment: MainFragment, private val list: List<Fragment>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}