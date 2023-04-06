package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG

class UnableToFindALocationFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var btnOpenUpTheMap: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unable_to_find_your_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.unable_to_find_a_location_toolbar)
        setupNavigationConfig()
        btnOpenUpTheMap = view.findViewById(R.id.btn_open_up_the_map)
        btnOpenUpTheMap.setOnClickListener {
            findNavController().navigate(R.id.addLocationFragment)
        }
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner){
                Log.i(TAG, "${this@UnableToFindALocationFragment.javaClass.simpleName} : $it")
            }
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