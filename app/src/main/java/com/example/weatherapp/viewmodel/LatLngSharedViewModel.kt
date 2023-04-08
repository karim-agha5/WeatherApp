package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavBackStackEntry
import com.google.android.gms.maps.model.LatLng

class LatLngSharedViewModel(
    application: Application,
    navBackStackEntry: NavBackStackEntry,
) : AndroidViewModel(application){

    private val _latLng: MutableLiveData<LatLng> = MutableLiveData()
    val latLngLiveData: LiveData<LatLng> = _latLng
}