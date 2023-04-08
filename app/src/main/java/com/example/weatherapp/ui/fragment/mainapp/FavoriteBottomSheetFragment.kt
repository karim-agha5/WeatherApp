package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFavoriteBottomSheetBinding
import com.example.weatherapp.util.TAG
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FavoriteBottomSheetFragment : BottomSheetDialogFragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentFavoriteBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFavoriteBottomNavigationSheetFavorite.setOnClickListener {
            weatherInfoViewModel.insertFavoriteLocation(weatherInfoViewModel.getMapLatLng())
            dismiss()
        }


        binding.btnFavoriteBottomNavigationSheetDismiss.setOnClickListener {
            dismissNow()
        }
    }
}