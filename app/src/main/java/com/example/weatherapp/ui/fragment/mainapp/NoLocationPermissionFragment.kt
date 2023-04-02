package com.example.weatherapp.ui.fragment.mainapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.util.LOCATION_PERMISSION_GRANTED_REQUEST_CODE
import com.example.weatherapp.util.LocationPermissionManager
import com.example.weatherapp.util.TAG


class NoLocationPermissionFragment : Fragment() {

    private lateinit var btnRetry: Button
    private lateinit var locationPermissionManager: LocationPermissionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_location_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionManager = setupPermissionLocationManager(view)
        locationPermissionManager.requestLocationPermission()

        btnRetry = view.findViewById(R.id.btn_retry_fetch_location_data)
        btnRetry.setOnClickListener {
            locationPermissionManager.requestLocationPermission()
        }
    }

    private fun setupPermissionLocationManager(view: View) : LocationPermissionManager{
        return LocationPermissionManager(
                requireActivity(),
                view,
                this::onLocationPermissionGranted,
                this::onLocationPermissionDismissed,
                this::onGoToAppSettingsClick,
                LOCATION_PERMISSION_GRANTED_REQUEST_CODE
            )
    }

    private fun onLocationPermissionGranted(){
        // pops self of the stack and navigates to the MainFragment
        findNavController().popBackStack()
        findNavController().navigate(R.id.mainFragment)
    }

    private fun onLocationPermissionDismissed(){
        // Do nothing
    }

    private fun onGoToAppSettingsClick(){
        startActivity( Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package",requireActivity().packageName,this.toString())
        )
        )
    }
}