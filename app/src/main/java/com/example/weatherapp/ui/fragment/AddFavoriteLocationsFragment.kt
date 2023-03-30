package com.example.weatherapp.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.helper.ONBOARDING_SHARED_PREFERENCE_KEY
import com.example.weatherapp.helper.ONBOARDING_SHARED_PREFERENCE_NAME

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
            // TODO go to initial settings screen
            findNavController().navigate(R.id.action_viewPagerFragment_to_initialUserSettingsFragment)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished(){
        val sharedPreference =
            requireActivity()
                .getSharedPreferences(ONBOARDING_SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(ONBOARDING_SHARED_PREFERENCE_KEY,true)
        editor.commit()
    }
}