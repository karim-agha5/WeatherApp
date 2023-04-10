package com.example.weatherapp.ui.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.util.LOCATION_PERMISSION_GRANTED_REQUEST_CODE
import com.example.weatherapp.util.LocationPermissionManager
import com.example.weatherapp.viewmodel.WeatherInfoViewModel


const val TAG: String = "Exception"

class MainActivity : AppCompatActivity() {

    private lateinit var locationPermissionManager: LocationPermissionManager

    lateinit var mainActivityBinding: ActivityMainBinding
    val connectivityManager by lazy {
        this.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }

    private val weatherInfoViewModel: WeatherInfoViewModel by lazy{
         ViewModelProvider(this).get(WeatherInfoViewModel::class.java)
    }

    private lateinit var navController: NavController
    private val settingsManager by lazy {
        SettingsManager.getInstance(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar to be transparent
        WindowCompat.setDecorFitsSystemWindows(window,true)

        setupNetworkCheck()
        initMainActivityBinding()
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHost.navController

       /* // pop main fragment off the stack because NoLocationPermissionFragment navigates to it
        navController.popBackStack()
        navController.navigate(R.id.noLocationPermissionFragment)*/

        setupLocationPermissionManager(mainActivityBinding.root)

        mainActivityBinding.viewmodel = weatherInfoViewModel


        if (isConnected()){
            navController.popBackStack()
            navController.navigate(R.id.noLocationPermissionFragment)


            if(
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ){

                if(settingsManager.isUserSettingsLocationSetToGps()){
                    navController.popBackStack()
                    navController.navigate(R.id.mainFragment)
                    navController.graph.setStartDestination(R.id.mainFragment)
                }
                else{
                    navController.popBackStack()
                    navController.navigate(R.id.unableToFindALocationFragment)
                    navController.graph.setStartDestination(R.id.unableToFindALocationFragment)
                }



            }
        }
        else{

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
    }

    private fun onLocationPermissionGranted(){
        navController.popBackStack()
        navController.navigate(R.id.mainFragment)
    }

    private fun onLocationPermissionDismissed(){
        // Do nothing and stay as you are in NoLocationPermissionFragment
        Toast.makeText(this, "NOT GRANTED", Toast.LENGTH_SHORT).show()
    }

    private fun onGotoAppSettingsClick(){
        startActivity( Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package",this.packageName,this.toString())
        )
        )
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
                runOnUiThread {
                    mainActivityBinding.navigationView.menu.findItem(R.id.addLocationFragment).isEnabled =
                        true
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                runOnUiThread {
                    navController.popBackStack()
                    navController.navigate(R.id.noNetworkFragment)
                    navController.graph.setStartDestination(R.id.noNetworkFragment)
                }
            }

        }

        connectivityManager.requestNetwork(networkRequest,networkCallback)

    }
    private fun isConnected() : Boolean{
        var connected = false
        val info = connectivityManager.activeNetworkInfo
        connected = info != null && info.isAvailable && info.isConnected

        return connected
    }


    private fun initMainActivityBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityBinding.lifecycleOwner = this
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_GRANTED_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if(settingsManager.isUserSettingsLocationSetToGps()){
                    navController.popBackStack()
                    navController.navigate(R.id.mainFragment)
                }
                else{
                    navController.popBackStack()
                    navController.navigate(R.id.addLocationFragment)
                }



            }
            else{

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

   /* override fun onSupportNavigateUp(): Boolean {
       return navController.navigateUp() || super.onSupportNavigateUp()
    }
*/
    override fun onBackPressed() {
        if(navController.popBackStack().not()){
            finish()
        }
    }
}