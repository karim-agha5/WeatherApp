package com.example.weatherapp.viewmodel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.remote.response.WeatherOneCallResponse
import com.example.weatherapp.data.source.remote.response.daily.DailyWeatherInfo
import com.example.weatherapp.data.source.remote.response.dataclass.CustomAddress
import com.example.weatherapp.data.source.remote.response.hourly.HourlyWeatherInfo
import com.example.weatherapp.data.source.remote.service.LocationService
import com.example.weatherapp.data.source.remote.service.RetrofitWeatherNetwork
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class WeatherInfoViewModel(application: Application) : AndroidViewModel(application) {

    // Remove the WeatherRepository dependency and inject it later on
    private val locationService: LocationService = LocationService(application.applicationContext)
    private var weatherRepository = WeatherRepository(Dispatchers.IO, RetrofitWeatherNetwork,application.applicationContext)
    private var _weatherOneCallResponse: MutableLiveData<WeatherOneCallResponse> = MutableLiveData()
    private var _favoriteWeatherOneCallResponseList: MutableLiveData<List<WeatherOneCallResponse>> =
        MutableLiveData()
    private val _selectedWeatherInfo: MutableLiveData<DailyWeatherInfo> = MutableLiveData()
    private val _selectedListOfWeatherHourlyInfo: MutableLiveData<List<HourlyWeatherInfo>> =
        MutableLiveData()
    private val _address: MutableLiveData<MutableList<Address>?> = MutableLiveData()
    private var mapLatLng: LatLng = LatLng(0.0, 0.0)
    private lateinit var weatherOneCallResponseToDisplay: WeatherOneCallResponse
    var weatherOneCallResponse: LiveData<WeatherOneCallResponse> = _weatherOneCallResponse
    val selectedWeatherInfo: LiveData<DailyWeatherInfo> = _selectedWeatherInfo
    val selectedListOfWeatherHourlyInfo: LiveData<List<HourlyWeatherInfo>> =
        _selectedListOfWeatherHourlyInfo
    val address: LiveData<MutableList<Address>?> = _address //TODO should it be private ???
    val favoriteWeatherOneCallResponseList = _favoriteWeatherOneCallResponseList

    fun weatherOneCall(lat: String, lon: String): LiveData<WeatherOneCallResponse> {
        viewModelScope.launch(Dispatchers.Main) {
            _weatherOneCallResponse.value = weatherRepository.weatherOneCall(lat, lon)
        }
        return weatherOneCallResponse
    }

    fun getAddress(lat: Double, lon: Double): LiveData<MutableList<Address>?> {
        viewModelScope.launch(Dispatchers.IO) {
            var list: MutableList<Address>? = locationService.getAddresses(lat, lon)
            if (list == null || list?.size == 0) list = null // Prevents ArrayIndexOutOfBoundsException
            _address.postValue(list)
        }
        return address
    }

    fun getAddresses(locationsList: MutableList<LatLng>) : MutableList<Address?> {
        var addresses: MutableList<Address?> = mutableListOf()
        val geocoder = Geocoder(getApplication<Application>().applicationContext, Locale.getDefault())
        for(i in locationsList.indices){
            addresses.add(
                geocoder
                    .getFromLocation(locationsList[i].latitude,locationsList[i].longitude,1)
                    ?.get(0)
            )
        }
        return addresses
    }

    fun setMapLatLng(latLng: LatLng) {
        mapLatLng = latLng
    }

    fun getMapLatLng() = mapLatLng

    fun setSelectedWeatherInfo(dailyWeatherInfo: DailyWeatherInfo) {
        _selectedWeatherInfo.value = dailyWeatherInfo
    }

    fun setSelectedListOfWeatherHourlyInfo(selectedListOfWeatherHourlyInfo: List<HourlyWeatherInfo>) {
        _selectedListOfWeatherHourlyInfo.value = selectedListOfWeatherHourlyInfo
    }

    fun getAllFavoriteLocations() {
        viewModelScope.launch(Dispatchers.Main) {
            val consumer = weatherRepository.getAllFavoriteLocations()
            consumer.collect {
                _favoriteWeatherOneCallResponseList.value = it
            }
        }
    }

    fun insertFavoriteLocation(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherOneCallResponse =
                weatherRepository.weatherOneCall(latLng.latitude.toString(),latLng.longitude.toString())

            val futureAddress = Geocoder(getApplication<Application>().applicationContext, Locale.getDefault())
                .getFromLocation(latLng.latitude,latLng.longitude,1)
            var customAddress = CustomAddress("Failed to get Address","","")
            try{
                val customAddress = CustomAddress(
                    futureAddress?.get(0)?.subAdminArea ?: "Unknown",
                    futureAddress?.get(0)?.adminArea ?: "Unknown",
                    futureAddress?.get(0)?.countryName ?: "Unknown"
                )
            }catch (e: IOException){
                Log.i(TAG, "${this::class.java.simpleName} - insertFavoriteLocation()")
            }
            weatherOneCallResponse.customAddress = customAddress
            weatherRepository.insertFavoriteLocation(weatherOneCallResponse)
        }
    }

    fun deleteFavoriteLocation(weatherOneCallResponse: WeatherOneCallResponse){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteFavoriteLocation(weatherOneCallResponse)
        }
    }

    fun setWeatherOneCallResponseToDisplay(weatherOneCallResponse: WeatherOneCallResponse){
        weatherOneCallResponseToDisplay = weatherOneCallResponse
    }

    fun getWeatherOneCallResponseToDisplay() = weatherOneCallResponseToDisplay
}