package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.data.source.remote.response.dataclass.Main
import com.example.weatherapp.ui.activity.MainActivity

class NoNetworkFragment : Fragment() {

    private lateinit var btnRetry: Button
    private lateinit var toolbar: Toolbar
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext().applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_network, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.no_network_toolbar)
        btnRetry = view.findViewById(R.id.btn_no_internet_retry)
        setupNavigationConfig()
        if(!isConnected()){
            (requireActivity() as MainActivity)
                .mainActivityBinding
                .navigationView
                .menu
                .findItem(R.id.addLocationFragment)
                .isEnabled = false
        }

        btnRetry.setOnClickListener {
            if(isConnected()){
                if(settingsManager.isUserSettingsLocationSetToGps()){
                    findNavController().navigate(R.id.action_noNetworkFragment_to_mainFragment)
                    findNavController().graph.setStartDestination(R.id.mainFragment)
                }
                else{
                    findNavController().navigate(R.id.action_noNetworkFragment_to_noLocationPermissionFragment)
                    findNavController().graph.setStartDestination(R.id.noLocationPermissionFragment)
                }
            }
            else{
                Toast.makeText(requireActivity(), "Not Connected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isConnected() : Boolean{
        var connected = false
        val info = (requireActivity() as MainActivity).connectivityManager.activeNetworkInfo
        connected = info != null && info.isAvailable && info.isConnected

        return connected
    }

    private fun setupNavigationConfig(){
        val appBarConfiguration =
            AppBarConfiguration(findNavController().graph,(activity as MainActivity).mainActivityBinding.drawerLayout)

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */
        toolbar.setupWithNavController(findNavController(),appBarConfiguration)

        // Adds another Hamburger icon because the default one is black and it's not fully visible to the eye
        toolbar.setNavigationIcon(R.drawable.round_menu_24_black)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(findNavController())

    }

}