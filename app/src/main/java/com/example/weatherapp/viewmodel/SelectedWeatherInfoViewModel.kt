package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo

class SelectedWeatherInfoViewModel : ViewModel() {

    private val _selectedWeatherInfo : MutableLiveData<DailyWeatherInfo> = MutableLiveData()
    val selectedWeatherInfo : LiveData<DailyWeatherInfo> = _selectedWeatherInfo

}