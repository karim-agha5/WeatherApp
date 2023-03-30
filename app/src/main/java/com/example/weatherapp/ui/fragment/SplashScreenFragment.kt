package com.example.weatherapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.helper.ONBOARDING_SHARED_PREFERENCE_KEY
import com.example.weatherapp.helper.ONBOARDING_SHARED_PREFERENCE_NAME
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.ui.activity.SplashScreenActivity


class SplashScreenFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({

            if(isOnBoardingFinished()){
                (activity as SplashScreenActivity)?.startMainActivity()
            }
            else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }

        },2000)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun isOnBoardingFinished() : Boolean{
        return requireActivity()
            .getSharedPreferences(ONBOARDING_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
            .getBoolean(ONBOARDING_SHARED_PREFERENCE_KEY,false)
    }
}