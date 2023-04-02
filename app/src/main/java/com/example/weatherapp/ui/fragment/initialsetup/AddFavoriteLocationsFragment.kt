package com.example.weatherapp.ui.fragment.initialsetup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.util.TAG

class AddFavoriteLocationsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_favorite_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext: Button = view.findViewById(R.id.btn_onbaording_lets_get_started)
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_initialUserSettingsFragment)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished(){
        val settingsManager = SettingsManager.getInstance(requireContext().applicationContext)
        settingsManager.setOnBoardingFinished(true)
    //    Log.i(TAG, "onBoardingFinished: ${settingsManager.hashCode()}")
    }
}