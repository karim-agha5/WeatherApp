package com.example.weatherapp.ui.fragment.mainapp

import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.room.WeatherDatabase
import com.example.weatherapp.data.source.local.room.WeatherOneCallResponseDao
import com.example.weatherapp.databinding.FragmentFavoriteBinding
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.ui.adapter.FavoriteLocationsAdapter
import com.example.weatherapp.util.TAG
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var weatherOneCallResponseDao: WeatherOneCallResponseDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            weatherDatabase = WeatherDatabase.getInstance(requireContext())
            weatherOneCallResponseDao = weatherDatabase.getWeatherOneCallResponseDao()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // toolbar = view.findViewById(R.id.favorite_fragment_toolbar)
        setupNavigationConfig()
        val locationsList: MutableList<LatLng> = mutableListOf()
        var addresses: MutableList<Address?> = mutableListOf()



        // Observe over the list of favorite locations
        weatherInfoViewModel.favoriteWeatherOneCallResponseList.observe(viewLifecycleOwner){

            for (i in it.indices){
                locationsList.add(LatLng(it[i].lat,it[i].lon))
            }

            // Get the addresses of these locations
            // and assign both the locations and their addresses lists to the adapter
            lifecycleScope.launch(Dispatchers.IO){
                try{
                    addresses = weatherInfoViewModel.getAddresses(locationsList)
                    for(i in addresses.indices){
                        if(it[i].customAddress.subAdminArea == "Failed to get Address"){
                            if(addresses?.get(i)?.countryName?.isNotBlank() == true &&
                                    addresses?.get(i)?.countryName?.isNotEmpty() == true){
                                it[i].customAddress.subAdminArea = addresses?.get(i)?.subAdminArea ?: ""
                                it[i].customAddress.adminArea = addresses?.get(i)?.adminArea ?: ""
                                it[i].customAddress.subAdminArea = addresses?.get(i)?.countryName ?: ""
                            }
                        }
                    }
                }catch (ex: Exception){
                    Log.i(TAG, "cannot fetch addresses")
                }
                val adapter =
                    FavoriteLocationsAdapter(
                        it.toMutableList(),
                        requireContext(),
                        weatherInfoViewModel,
                        addresses,
                        this@FavoriteFragment
                    )
                binding.adapter = adapter
            }
        }

        // Restore the list of favorite locations from the local database
        weatherInfoViewModel.getAllFavoriteLocations()


        binding.fab.setOnClickListener {
            // Launch the Map telling its fragment that FavoriteFragment is the one who launched it
            // to display the proper bottom navigation sheet *workaround*
            // TODO fix this workaround later
            val action =
                FavoriteFragmentDirections
                .actionFavoriteFragmentToAddLocationFragment(this::class.java.simpleName)
            findNavController().navigate(action)

        }

    }

    fun navigateToMainFragment(){
        val action =
            FavoriteFragmentDirections.actionFavoriteFragmentToMainFragment(FavoriteFragment::class.java.simpleName)
        findNavController().navigate(action)
        findNavController().graph.setStartDestination(R.id.mainFragment)
    }

    private fun setupNavigationConfig() {
        val appBarConfiguration =
            AppBarConfiguration(
                findNavController().graph,
                (activity as MainActivity).mainActivityBinding.drawerLayout
            )

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */

        binding.favoriteFragmentToolbar.setupWithNavController(findNavController(), appBarConfiguration)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(
            findNavController()
        )
    }

}