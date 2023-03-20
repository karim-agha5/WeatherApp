package com.example.weatherapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm,behavior) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            else -> ThirdFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "First"
            1 -> "Second"
            else -> "Third"
        }
        return super.getPageTitle(position)
    }
}