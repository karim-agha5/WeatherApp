package com.example.weatherapp.ui.fragment.mainapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.service.LocationService
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.util.getCDegreeFormat
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.ui.adapter.ViewPagerAdapter
import com.example.weatherapp.util.LOCATION_PERMISSION_GRANTED_REQUEST_CODE
import com.example.weatherapp.util.LocationPermissionManager
import com.example.weatherapp.util.TAG
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.google.android.gms.maps.model.LatLng


class MainFragment : Fragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var locationPermissionManager: LocationPermissionManager
    private val locationService by lazy {
        LocationService(requireActivity())
    }
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext())
    }
    private val egLat: String by lazy{"24.0889"}
    private val egLon: String by lazy{"32.8998"}
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if(settingsManager.isUserSettingsLocationSetToGps()){
            if(ActivityCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                findNavController().navigate(R.id.action_mainFragment_to_noLocationPermissionFragment2)
            }
        }*/


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
      //  binding.lifecycleOwner = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = weatherInfoViewModel
        loadCollapsingToolbarImage()
        locationPermissionManager = setupLocationPermissionManager(view)
       // locationPermissionManager.requestLocationPermission()


        /*
        * Check if the location permission is given or not.
        * If given, fetch the weather data from the API using either the GPS or google maps
        * depending on the current user settings.
        * */
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if(settingsManager.isUserSettingsLocationSetToGps()){
                    val futureLocationViaGps = locationService.startGettingLocationViaGps()
                    futureLocationViaGps.observe(viewLifecycleOwner) {
                        // Sometimes the location returned is null. So a nullability check is needed.
                        if(it != null){
                            fetchInitialData(it.latitude,it.longitude)
                            displayAddress(it.latitude,it.longitude)
                        }
                    }
                }

                if (settingsManager.isUserSettingsLocationSetToMap()){
                    val latLng = weatherInfoViewModel.getMapLatLng()
                    fetchInitialData(latLng.latitude, latLng.longitude)
                    displayAddress(latLng.latitude,latLng.longitude)

                }

            }

        setUpTabLayoutFunctionality()
        setupNavigationConfig()


    }


    private fun setupLocationPermissionManager(view: View) : LocationPermissionManager{
        return LocationPermissionManager(
                requireActivity(),
                view,
                this::onLocationPermissionGranted,
                this::onLocationPermissionDenied,
                this::onGoToSettingsClick,
                LOCATION_PERMISSION_GRANTED_REQUEST_CODE
            )
    }

    private fun setupNavigationConfig(){
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph,(activity as MainActivity).mainActivityBinding.drawerLayout)

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */
        binding.toolbar.setupWithNavController(findNavController(),appBarConfiguration)

        // Adds another Hamburger icon because the default one is black and it's not fully visible to the eye
        binding.toolbar.setNavigationIcon(R.drawable.round_menu_24)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(findNavController())

    }

    private fun fetchInitialData(lat: Double ,lon: Double){
        Log.i(TAG, "fetchInitialData: $lat \n $lon")
        // Run it on the UI thread because it's not possible to observe on a background thread.
        requireActivity().runOnUiThread {
            val weatherOneCallResponse = weatherInfoViewModel.weatherOneCall(lat.toString(),lon.toString())
            weatherOneCallResponse.observe(viewLifecycleOwner) {
                binding.tvWeatherDegree.text = getCDegreeFormat((it.currentWeatherDetailedInfo.temp - 273.15).toFloat())
                // set the selected weather to today's weather info so it can be displayed in the details fragment
                binding?.viewmodel?.setSelectedWeatherInfo(it.dailyForecast[0])
                binding?.viewmodel?.setSelectedListOfWeatherHourlyInfo(it.twoDaysHourlyForecast)
            }
        }
    }

    private fun displayAddress(lat: Double,lon: Double){
        val futureAddress = weatherInfoViewModel.getAddress(lat, lon)
        futureAddress.observe(viewLifecycleOwner, Observer {
            var address = ""
            if(it?.get(0)?.subAdminArea?.isNotEmpty() == true
                && it?.get(0)?.subAdminArea?.isNotBlank() == true){
                address = "${it?.get(0)?.subAdminArea ?: "Unknown Name"}\n"
            }

            address += "${it?.get(0)?.adminArea ?: "Unknown Name"}, "
            address += it?.get(0)?.countryName ?: "Unknown Name"

            binding.tvWeatherLocation.apply {
                this.text = address
            }

        })
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
       /* binding.viewPager.adapter =
            ViewPagerAdapter(requireActivity().supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)*/
        binding.viewPager.adapter =
            ViewPagerAdapter(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun onLocationPermissionGranted(){
      //  fetchInitialData()
    }

    private fun onLocationPermissionDenied(){
        findNavController().navigate(R.id.noLocationPermissionFragment)
    }

    private fun onGoToSettingsClick(){
        // Not really needed
    }


}