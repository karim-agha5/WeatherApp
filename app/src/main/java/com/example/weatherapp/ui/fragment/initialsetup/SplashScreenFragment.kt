package com.example.weatherapp.ui.fragment.initialsetup

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.sharedpreference.SettingsManager
import com.example.weatherapp.util.TAG
import com.example.weatherapp.ui.activity.SplashScreenActivity


class SplashScreenFragment : Fragment() {

    //TODO Inject later
    private val settingsManager by lazy {
        SettingsManager.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({

            if(!isOnBoardingFinished()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }
            else if(!isInitialUserSettingsFinished()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_initialUserSettingsFragment)
            }
            else{
                (activity as SplashScreenActivity)?.startMainActivity()
            }

        },2000)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun isOnBoardingFinished(): Boolean {
     //   Log.i(TAG, "splash fragment: ${settingsManager.hashCode()}")
        return settingsManager.isOnBoardingFinished()
    }


    private fun isInitialUserSettingsFinished(): Boolean {
       // Log.i(TAG, "splash fragment: ${settingsManager.hashCode()}")
        return settingsManager.isInitialUserSettingsFinished()
    }
}