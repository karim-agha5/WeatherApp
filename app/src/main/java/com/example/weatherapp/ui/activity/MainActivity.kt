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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.ViewPagerAdapter
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.remote.response.HourlyForecastResponse
import com.example.weatherapp.data.source.remote.service.RetrofitWeatherNetwork

import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG: String = "Exception"

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,false)

        initMainActivityBinding()
        loadCollapsingToolbarImage()
        setupSupportActionBar()
        setUpTabLayoutFunctionality()
        mainActivityBinding.tvWeatherDegree.append("\u00B0");


       /* val weatherService: WeatherService = RetrofitHelper.weatherService
        lifecycleScope.launch(Dispatchers.IO) {
            val hourlyForecastResponse: HourlyForecastResponse = weatherService.hourlyForecast(
                "26.8206",
               "30.8025",
                apikey
            )
            Log.i(TAG, hourlyForecastResponse.city.country)
        }*/

       /* lifecycleScope.launch(Dispatchers.IO){
            val currentWeather : CurrentWeatherResponse = weatherService.currentWeatherForecast(
                "26.8206",
                "30.8025",
                apikey
            )
            Log.i(TAG, currentWeather.city.country)
        }*/

/*
        lifecycleScope.launch(Dispatchers.IO){
            val retrofit =
                Retrofit
                    .Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val weatherOneCall = retrofit.create(WeatherService::class.java)
            val weatherOneCallResponse = weatherOneCall.weatherOneCall(
                "26.8206",
                "30.8025",
                apikey
            )

            Log.i(
                TAG, "${weatherOneCallResponse.dailyForecast.size} |" +
                    " ${weatherOneCallResponse.twoDaysHourlyForecast.size}")

        }*/


        val repo = WeatherRepository(Dispatchers.IO,RetrofitWeatherNetwork)
        lifecycleScope.launch(Dispatchers.Main){
            val response = repo.weatherOneCall("26.8206","30.8025")
            Log.i(TAG, "${response.currentWeatherDetailedInfo.temp}")
        }
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