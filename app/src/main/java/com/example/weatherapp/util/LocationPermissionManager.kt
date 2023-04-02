package com.example.weatherapp.util


import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * This class no longer uses ActivityResultLauncher due to lifecycle bugs and is REWORKED to use
 * regular permission checks that can be used globally around the app.
 *
 *  You'll have to use the onPermissionResult callback in your activity now.
 * */

// TODO fix the launcher lifecycle bugs later.

class LocationPermissionManager(
    private val activity: ComponentActivity,
    view: View,
    private val onLocationPermissionGrantedAction: () -> Unit,
    private val onLocationPermissionDismissedAction: () -> Unit,
    private val onGoToAppSettingsClickAction: () -> Unit, // Can be replace by a function that displays rationale
    private val LOCATION_REQUEST_CODE: Int
) {

    //var isPermanentlyDeclined = false
    var counter = 0
    private val locationPermissionRationaleManager = LocationPermissionRationaleManager(view)


    /*fun showLocationPermissionRationale(){
        locationPermissionRationaleManager.showPermissionRationale()
    }

    private fun onLocationPermissionDismissed() {
        onLocationPermissionDismissedAction.invoke()
    }*/

    private fun onGotoAppSettingsClick() {
        onGoToAppSettingsClickAction.invoke()
    }


   /* private var launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
            isGranted -> onPermissionResult(isGranted,onLocationPermissionGrantedAction,onLocationPermissionDismissedAction)
                 Log.i(TAG, "inside launcher: $isGranted ")
    }

    fun setLauncher(launcher: ActivityResultLauncher<String>){
        this.launcher = launcher
    }
*/
    fun requestLocationPermission(){
        when{
            ContextCompat
                .checkSelfPermission(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is granted -- Do something
                onPermissionResult(true,onLocationPermissionGrantedAction,onLocationPermissionDismissedAction)
                // TODO probably should set the counter to 0 here
            }

            ActivityCompat
                .shouldShowRequestPermissionRationale(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                // additional rationale should be displayed

                // onLocationPermissionDismissed()
                // onPermissionResult(false,null,onLocationPermissionDismissedAction)
                locationPermissionRationaleManager.showPermissionRationale()
            }

            else -> {

                // permission has not been asked yet
             //   launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                showLocationPermissionDialog()
            }
        }
    }


    private fun showLocationPermissionDialog(){
        ActivityCompat
            .requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
    }

/*

    companion object{
        // TODO a hack because not being able to register the activity when it's resumed -- FIX LATER!
        fun requestLocationPermissionWithoutLauncher(activity: ComponentActivity,view: View){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                val snackbar = Snackbar
                    .make(view, "Permission is not granted", Snackbar.LENGTH_LONG)
                    .setAction("Enable") {
                       ActivityCompat.requestPermissions(activity, arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION), 5)
                    }

                val snackBarView: View = snackbar.view
                val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams

                params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    params.bottomMargin + 20
                )

                snackBarView.layoutParams = params
                snackbar.show()
            }

            else{
                ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_GRANTED_REQUEST_CODE)
                */
/*
                    The request code will allow you to know whether the permission was accepted or denied to make
                    a decision whether it is pertaining to this request code
                *//*

            }
        }
    }
*/



    fun onPermissionResult(
        isGranted: Boolean,
        onPermissionGrantedAction: (() -> Unit)?,
        onPermissionDismissedAction: (() -> Unit)?
    ) {
        if (!isGranted) {
            // Do something if not granted -- this one is possibly not called becaose of the counter.
            if(counter >= 2){
                // locationPermissionRationaleManager.showPermissionRationale()
                onGotoAppSettingsClick()
            }
            else{
                onPermissionDismissedAction?.invoke()
                counter++
                Log.i(TAG, "permission denied : counter is -> $counter ")
            }

        } else {
            // Do something else
            onPermissionGrantedAction?.invoke()
            Log.i(TAG, "onPermissionResult: onPermissionGrantedAction() should be invoked!")
        }
    }






    private inner class LocationPermissionRationaleManager(
        private val view: View,
        // private val enablePermissionAction: () -> Unit
    ) {

        fun showPermissionRationale(){
            val snackbar = Snackbar
                .make(view, "Permission is not granted", Snackbar.LENGTH_LONG)
                .setAction("Enable") {
                    //  enablePermissionAction.invoke()
                   // launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    showLocationPermissionDialog()
                }

            val snackBarView: View = snackbar.view
            val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams

            params.setMargins(
                params.leftMargin,
                params.topMargin,
                params.rightMargin,
                params.bottomMargin + 60
            )

            snackBarView.layoutParams = params
            snackbar.show()

        }
    }
}