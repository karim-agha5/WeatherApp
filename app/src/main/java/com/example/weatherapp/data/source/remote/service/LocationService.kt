package com.example.weatherapp.data.source.remote.service

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.util.TAG
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.*

class LocationService(context: Context) {

    private var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequestBuilder: LocationRequest.Builder
    private var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var geocoder: Geocoder
    private var _location: Location? = null
    private var _futureLocation: MutableLiveData<Location?> = MutableLiveData(_location)

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequestBuilder = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000)
        locationRequest = locationRequestBuilder.build()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                _location = p0.locations[0]
                _futureLocation.value = _location
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }
        geocoder = Geocoder(context, Locale.getDefault())
    }

    @SuppressWarnings("MissingPermission")
    fun startGettingLocationViaGps() : MutableLiveData<Location?> {
        fusedLocationProviderClient
            .requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
        return _futureLocation
    }


    /**
     * @return mutable list of addresses that may or may not contain any addresses.
     * There's no guarantee that his method returns accurate or any address at all.
     * */
    @Throws(IOException::class)
    suspend fun getAddresses(lat: Double,lon: Double) : MutableList<Address>? = withContext(Dispatchers.IO){
        //return@withContext geocoder.getFromLocation(lat,lon,1)
        try{
            return@withContext geocoder.getFromLocation(lat,lon,1)
        }catch (e: IOException){
            Log.i(TAG, "${this::class.java.simpleName} - getAddresses: ")
            return@withContext null
        }
    }

}