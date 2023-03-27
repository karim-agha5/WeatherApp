package com.example.weatherapp.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.example.weatherapp.ui.activity.TAG

//TODO Fix later
/**
 * If the connection is unavailable at the time of the app launch,
 * the client should provide their own implementation
 * */
class ConnectionManager (context: Context){

    private lateinit var executeWhenConnectionAvailable: () -> Unit
    private lateinit var executeWhenConnectionLost: () -> Unit


    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()


    /*
    * Actions to take when the network capabilities requested are
    * available, unavailable, or lost
    **/
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i(TAG, "onAvailable() AVAILABLE!")
            executeWhenConnectionAvailable.invoke()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i(TAG, "onLost() LOST!!!!")
            executeWhenConnectionLost.invoke()
        }

    }

    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager



    init {
        /*
     * Describes the app's connection needs.
     * The following requests a network that is connected to the internet, uses WiFi, and mobile data.
     * Uses mobile data when the device is in a non-metered connection.
     **/
        connectivityManager.requestNetwork(networkRequest,networkCallback)

    }


    /*
     * A replacement for the onUnavailable callback.
     */
     fun isConnected() : Boolean{
        var connected = false
        val info = connectivityManager.activeNetworkInfo
        connected = info != null && info.isAvailable && info.isConnected

        return connected
    }

    fun whenConnectionAvailable(executeWhenConnectionAvailable: () -> Unit){
        this.executeWhenConnectionAvailable = executeWhenConnectionAvailable
    }

    fun whenConnectionIsLost(executeWhenConnectionLost: () -> Unit){
        this.executeWhenConnectionLost = executeWhenConnectionLost
    }

}