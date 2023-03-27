package com.example.weatherapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.weatherapp.ui.fragment.DailyForecastFragment
import com.example.weatherapp.ui.fragment.DetailsFragment
import com.example.weatherapp.ui.fragment.ThirdFragment

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm,behavior) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> DailyForecastFragment()
            1 -> DetailsFragment()
            else -> ThirdFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Daily Forecast"
            1 -> "Details"
            else -> "Third"
        }
        return super.getPageTitle(position)
    }
}