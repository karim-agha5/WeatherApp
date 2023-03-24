package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.service.RetrofitWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel(){

    // Remove the WeatherRepository dependency and inject it later on
    private var weatherRepository = WeatherRepository(Dispatchers.IO,RetrofitWeatherNetwork)
    private var _weatherOneCallResponse: MutableLiveData<WeatherOneCallResponse> = MutableLiveData()
    var weatherOneCallResponse: LiveData<WeatherOneCallResponse> = _weatherOneCallResponse

    fun weatherOneCall(lat: String, lon: String) : LiveData<WeatherOneCallResponse> {
        viewModelScope.launch(Dispatchers.Main){
            _weatherOneCallResponse.value = weatherRepository.weatherOneCall(lat, lon)
        }
        return weatherOneCallResponse
    }
}