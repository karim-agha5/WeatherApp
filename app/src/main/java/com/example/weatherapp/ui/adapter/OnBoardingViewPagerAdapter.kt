package com.example.weatherapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(
    private val onBoardingFragmentsList: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment = onBoardingFragmentsList[position]

    override fun getItemCount(): Int = onBoardingFragmentsList.size
}