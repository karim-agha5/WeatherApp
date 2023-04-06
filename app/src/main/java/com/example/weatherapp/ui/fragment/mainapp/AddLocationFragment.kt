package com.example.weatherapp.ui.fragment.mainapp

import android.graphics.drawable.Drawable
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.service.LocationService
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class AddLocationFragment : Fragment(),OnMapReadyCallback{

    private lateinit var toolbar: Toolbar
    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private var marker: Marker? = null
    private lateinit var navController: NavController
    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private val locationService: LocationService by lazy {
        LocationService(requireContext().applicationContext)
    }
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext().applicationContext)
    }

    private lateinit var currentLocationLatLng: LatLng
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_view)
        toolbar = view.findViewById(R.id.add_location_fragment_toolbar)
        setupNavigationConfig()
        var mapViewBundle: Bundle? = null
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(resources.getString(R.string.google_maps_key))
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

    }

    private fun setupNavigationConfig(){
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph,(activity as MainActivity).mainActivityBinding.drawerLayout)

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */

        toolbar.setupWithNavController(findNavController(),appBarConfiguration)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(findNavController())

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        navController = findNavController()
        // Set the initial position of the marker to iceland
        val iceland = LatLng(64.46207,-18.31371)
        marker = mMap.addMarker(MarkerOptions().position(iceland).title("Iceland").draggable(true))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iceland))
        mMap.uiSettings.isMapToolbarEnabled = false
        dragMarkerImpl(googleMap)

        mMap.setOnMapClickListener { latLng ->

            currentLocationLatLng = latLng
            marker?.position = latLng

            val sheet = BottomNavigationSheetFragment()
            sheet.setAddLocationFragment(this)
            sheet.show(childFragmentManager,"newPinTag")

        }
    }

    fun navigateToMainFragment(){
        weatherInfoViewModel.setMapLatLng(currentLocationLatLng)
        findNavController().graph.setStartDestination(R.id.mainFragment);
        findNavController().navigate(R.id.action_addLocationFragment_to_mainFragment)
    }
    fun getLatLng() : LatLng = currentLocationLatLng

    private fun dragMarkerImpl(googleMap: GoogleMap) {
        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {}

            override fun onMarkerDragEnd(p0: Marker) {


            }

            override fun onMarkerDragStart(p0: Marker) {}

        })
    }

    private fun getMarkerTitle(address: Address?) : String{
        val subAdminArea = address?.subAdminArea
        val adminArea = address?.adminArea
        val countryName = address?.countryName
        val stringBuilder = StringBuilder()
        if(subAdminArea?.isBlank() == false){
            stringBuilder.append("$subAdminArea, ")
        }
        if(adminArea?.isBlank() == false){
            stringBuilder.append("$adminArea, ")
        }
        if(countryName?.isNotEmpty() == true && countryName?.isNotBlank() == true){
            stringBuilder.append(countryName)
        }
        return stringBuilder.toString()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle: Bundle? = outState.getBundle(resources.getString(R.string.google_maps_key))
        if(mapViewBundle == null){
            mapViewBundle = Bundle()
            outState.putBundle(resources.getString(R.string.google_maps_key),mapViewBundle)
        }
        if(this::mapView.isInitialized){
            mapView.onSaveInstanceState(mapViewBundle)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}