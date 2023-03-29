package com.example.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.TAG
import com.example.weatherapp.data.source.remote.response.dataclass.Main
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.getCDegreeFormat
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.ui.adapter.ViewPagerAdapter
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import java.util.*


class MainFragment : Fragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private val egLat: String by lazy{"24.0889"}
    private val egLon: String by lazy{"32.8998"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = weatherInfoViewModel
        loadCollapsingToolbarImage()
        //setupSupportActionBar()

        setUpTabLayoutFunctionality()
        setupNavigationConfig()
        fetchInitialData()
        Log.i(TAG, "MainFragment ${weatherInfoViewModel.hashCode()} ")

    }


    private fun setupNavigationConfig(){
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph,(activity as MainActivity).mainActivityBinding.drawerLayout)

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */
      //  NavigationUI.setupActionBarWithNavController(activity as MainActivity,findNavController(),appBarConfiguration)
     //   (activity as MainActivity).setupActionBarWithNavController(findNavController(),appBarConfiguration)

        binding.toolbar.setupWithNavController(findNavController(),appBarConfiguration)

        // Adds another Hamburger icon because the default one is black and it's not fully visible to the eye
        binding.toolbar.setNavigationIcon(R.drawable.round_menu_24)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(findNavController())

        // Adds another Hamburger icon because the default one is black and it's not fully visible to the eye
       // (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
    }

    private fun fetchInitialData(){
        // Run it on the UI thread because it's not possible to observe on a background thread.
        requireActivity().runOnUiThread {
            val weatherOneCallResponse = weatherInfoViewModel.weatherOneCall(egLat,egLon)
            weatherOneCallResponse.observe(viewLifecycleOwner) {
                binding.tvWeatherDegree.text = getCDegreeFormat((it.currentWeatherDetailedInfo.temp - 273.15).toFloat())
                // set the selected weather to today's weather info so it can be displayed in the details fragment
                binding?.viewmodel?.setSelectedWeatherInfo(it.dailyForecast[0])
                binding?.viewmodel?.setSelectedListOfWeatherHourlyInfo(it.twoDaysHourlyForecast)

            }
        }
    }


    private fun loadCollapsingToolbarImage(){
        Glide
            .with(this)
            .load(ResourcesCompat.getDrawable(resources, R.drawable.ice_snowy_landscape,activity?.theme))
            .into(binding.ivCollapsingImage)
    }


    /*
    * Sets the custom toolbar as the Appbar along with its components
    * */
    private fun setupSupportActionBar(){
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        val supportActionBar = (activity as MainActivity).supportActionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpTabLayoutFunctionality(){
        binding.viewPager.adapter =
            ViewPagerAdapter(requireActivity().supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}