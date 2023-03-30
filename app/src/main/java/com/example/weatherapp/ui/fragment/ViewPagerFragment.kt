package com.example.weatherapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentViewPagerBinding
import com.example.weatherapp.ui.adapter.OnBoardingViewPagerAdapter

class ViewPagerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val onBoardingFragmentsList = arrayListOf<Fragment>(
            WeatherEverywhereFragment(),
            DetailedWeatherInfoFragment(),
            AddFavoriteLocationsFragment()
        )

        val adapter = OnBoardingViewPagerAdapter(
            onBoardingFragmentsList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val binding: FragmentViewPagerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_pager,container,false)
        binding.onBoardingViewPager.adapter = adapter
       // view.onBoardingViewPager.adapter = adapter
        return binding.root
    }


}