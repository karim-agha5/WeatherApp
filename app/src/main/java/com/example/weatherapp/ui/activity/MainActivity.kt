package com.example.weatherapp.ui.activity


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.util.LOCATION_PERMISSION_GRANTED_REQUEST_CODE
import com.example.weatherapp.util.LocationPermissionManager
import com.example.weatherapp.viewmodel.WeatherInfoViewModel


const val TAG: String = "Exception"

class MainActivity : AppCompatActivity() {

    private lateinit var locationPermissionManager: LocationPermissionManager

    lateinit var mainActivityBinding: ActivityMainBinding
    private val connectivityManager by lazy {
        this.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }

    private val weatherInfoViewModel: WeatherInfoViewModel by lazy{
         ViewModelProvider(this).get(WeatherInfoViewModel::class.java)
    }

    private lateinit var navController: NavController


    private val egLat: String by lazy{"24.0889"}
    private val egLon: String by lazy{"32.8998"}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,false)


        setupNetworkCheck()
        initMainActivityBinding()
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHost.navController
        navController.navigate(R.id.noLocationPermissionFragment)

        setupLocationPermissionManager(mainActivityBinding.root)

        mainActivityBinding.viewmodel = weatherInfoViewModel


        if (isConnected()){

        }
        else{
            startActivity(Intent(this,ErrorActivity::class.java))
            finish()
        }


    }




    private fun setupLocationPermissionManager(view: View){
        locationPermissionManager =
            LocationPermissionManager(
                this,
                view,
                this::onLocationPermissionGranted,
                this::onLocationPermissionDismissed,
                this::onGotoAppSettingsClick,
                LOCATION_PERMISSION_GRANTED_REQUEST_CODE
            )
       // locationPermissionManager.requestLocationPermission()
    }

    private fun onLocationPermissionGranted(){
        navController.popBackStack()
        navController.navigate(R.id.mainFragment)
    }

    private fun onLocationPermissionDismissed(){
        // Do nothing and stay as you are in NoLocationPermissionFragment
        Toast.makeText(this, "NOT GRANTED", Toast.LENGTH_SHORT).show()
      //  navController.navigate(R.id.noLocationPermissionFragment)
    }

    private fun onGotoAppSettingsClick(){
        startActivity( Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package",this.packageName,this.toString())
        )
        )
    }






   /* private fun fetchInitialData(){
        // Run it on the UI thread because it's not possible to observe on a background thread.
        runOnUiThread {
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
    }*/

    private fun onNetworkStatus(status: Boolean){
        // onAvailable
        if(status){
        //    fetchInitialData()
        }
        // onLost
        else{
            startActivity(Intent(this@MainActivity,ErrorActivity::class.java))
            finish()
        }

    }

    private fun setupNetworkCheck(){

        val networkRequest =
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                // .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()




        val networkCallback = object : ConnectivityManager.NetworkCallback(){

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                //Toast.makeText(this@MainActivity, "Available", Toast.LENGTH_SHORT).show() // works
              //  Log.i(TAG, "onAvailable() AVAILABLE!")
             //   fetchInitialData()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
               // Toast.makeText(this@MainActivity, "Lost Connection", Toast.LENGTH_SHORT).show() // works
            //    Log.i(TAG, "onLost() LOST!!!!")
                startActivity(Intent(this@MainActivity,ErrorActivity::class.java))
                finish()
            }

        }

        connectivityManager.requestNetwork(networkRequest,networkCallback)


        if(isConnected()){
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        //    Log.i(TAG, "isConnected(): CONNECTED")
        }
        else{
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
          //  Log.i(TAG, "isConnected(): DISCONNECTED")
        }

    }
    private fun isConnected() : Boolean{
        var connected = false
        val info = connectivityManager.activeNetworkInfo
        connected = info != null && info.isAvailable && info.isConnected

        return connected
    }

  /*  private fun loadCollapsingToolbarImage(){
        Glide
            .with(this)
            .load(ResourcesCompat.getDrawable(resources, R.drawable.ice_snowy_landscape,theme))
            .into(mainActivityBinding.ivCollapsingImage)
    }*/

    private fun initMainActivityBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityBinding.lifecycleOwner = this
    }

    /*
    * Sets the custom toolbar as the Appbar along with its components
    * */
   /* private fun setupSupportActionBar(){
        setSupportActionBar(mainActivityBinding.toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.round_menu_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }*/

   /* private fun setUpTabLayoutFunctionality(){
        mainActivityBinding.viewPager.adapter =
            ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        mainActivityBinding.tabLayout.setupWithViewPager(mainActivityBinding.viewPager)

    }*/

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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_GRANTED_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // handled by NoLocationPermissionFragment
              //  navController.navigate(R.id.mainFragment)
                Log.i(TAG, "MainActivity. onRequestPermissionResult() called." +
                        "Permission granted. Should navigate now")
                navController.popBackStack()
                navController.navigate(R.id.mainFragment)
            }
            else{
                /*locationPermissionManager
                    .onPermissionResult(
                        false,
                        null,
                        this::onLocationPermissionDismissed
                    )
                Log.i(TAG, "MainActivity. onRequestPermissionResult() called." +
                        "Permission denied. dismissed action should be called.")*/
                // The user checked "never ask again"
                if(!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    AlertDialog
                        .Builder(this)
                        .setTitle("Permission Denied")
                        .setMessage("This time, you will have to enable " +
                                "the location permission in your phone's settings.")
                        .setPositiveButton("Settings") { _, _ -> onGotoAppSettingsClick() }
                        .setNegativeButton("Not Now"){_,_-> /*Do Nothing*/}
                        .setCancelable(true)
                        .show()


                }
            }
        }
    }


}