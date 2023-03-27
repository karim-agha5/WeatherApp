package com.example.weatherapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.ui.adapter.ViewPagerAdapter

import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.SelectedWeatherInfoViewModel
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import java.util.*

const val TAG: String = "Exception"

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,false)

        initMainActivityBinding()
        loadCollapsingToolbarImage()
        setupSupportActionBar()
        setUpTabLayoutFunctionality()
        mainActivityBinding.tvWeatherDegree.append("\u00B0");



        val weatherInfoViewModel = ViewModelProvider(this).get(WeatherInfoViewModel::class.java)
        val selectedWeatherInfoViewModel = ViewModelProvider(this).get(SelectedWeatherInfoViewModel::class.java)
        mainActivityBinding.viewmodel = weatherInfoViewModel

        val egLat = "24.0889"
        val egLon = "32.8998"

        val weatherOneCallResponse = weatherInfoViewModel.weatherOneCall(egLat,egLon)
        weatherOneCallResponse.observe(this, Observer {
            mainActivityBinding.tvWeatherDegree.text = (it.currentWeatherDetailedInfo.temp - 273.15).toInt().toString() + "\u00B0"
            // Set the selected weather to today's weather info so it can be displayed in the details fragment
            mainActivityBinding.viewmodel?.setSelectedWeatherInfo(it.dailyForecast[0])
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.twoDaysHourlyForecast[0].dt)
            mainActivityBinding.viewmodel?.setSelectedListOfWeatherHourlyInfo(it.twoDaysHourlyForecast)
        })


    }

    private fun loadCollapsingToolbarImage(){
        Glide
            .with(this)
            .load(ResourcesCompat.getDrawable(resources, R.drawable.ice_snowy_landscape,theme))
            .into(mainActivityBinding.ivCollapsingImage)
    }

    private fun initMainActivityBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityBinding.lifecycleOwner = this
    }

    /*
    * Sets the custom toolbar as the Appbar along with its components
    * */
    private fun setupSupportActionBar(){
        setSupportActionBar(mainActivityBinding.toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpTabLayoutFunctionality(){
        mainActivityBinding.viewPager.adapter =
            ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        mainActivityBinding.tabLayout.setupWithViewPager(mainActivityBinding.viewPager)

    }

    /*
    * Handles the hamburger icon clicks
    * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            if(mainActivityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                mainActivityBinding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }


        return super.onOptionsItemSelected(item)
    }
}