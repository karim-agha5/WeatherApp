package com.example.weatherapp.ui.fragment.mainapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentBottomNavigationSheetBinding
import com.example.weatherapp.util.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomNavigationSheetFragment : BottomSheetDialogFragment() {


    private lateinit var btnPin: Button
    private lateinit var btnFavorite: Button
    private lateinit var btnDismiss: Button
    private lateinit var tvCountryName: TextView
    private lateinit var tvSubAdminArea: TextView
    private lateinit var binding: FragmentBottomNavigationSheetBinding
   // private val args: BottomNavigationSheetFragmentArgs by navArgs()
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private val liveLat: MutableLiveData<Double> = MutableLiveData(lat)
    private val liveLon: MutableLiveData<Double> = MutableLiveData(lon)
    private lateinit var addLocationFragment: AddLocationFragment

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
            R.layout.fragment_bottom_navigation_sheet,
            container,
            false
        )
        return binding.root
        // return inflater.inflate(R.layout.fragment_bottom_navigation_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         btnPin = view.findViewById(R.id.btn_bottom_navigation_sheet_pin)
         btnFavorite = view.findViewById(R.id.btn_bottom_navigation_sheet_favorite)
         btnDismiss = view.findViewById(R.id.btn_bottom_navigation_sheet_dismiss)



        /*   if(args.adminArea?.isBlank() == true){
               if(args.countryName?.isBlank() == true){
                   binding.tvBottomNavigationSheetCountryName.visibility = View.GONE
               }
               else{
                   binding.tvBottomNavigationSheetCountryName.text = args.countryName
               }
           }
           else if(args.adminArea?.isBlank() == false){
               binding.tvBottomNavigationSheetCountryName.text = args.adminArea
               binding.tvBottomNavigationSheetCountryName.append(", ${args.countryName}")
           }
           if(args.subadminArea?.isBlank() == true){
               binding.tvBottomNavigationSheetSubadminArea.visibility = View.GONE
           }
           else{
               binding.tvBottomNavigationSheetSubadminArea.text = args.subadminArea
           }

   */

        binding.btnBottomNavigationSheetPin.setOnClickListener {
            addLocationFragment.navigateToMainFragment()
            dismissNow()
        }
        binding.btnBottomNavigationSheetFavorite.setOnClickListener {
            Log.i(TAG, "Favorite Tapped! ")
        }

        binding.btnBottomNavigationSheetDismiss.setOnClickListener {
            dismissNow()
        }
    }


    // To navigate from the map to the MainFragment
    fun setAddLocationFragment(addLocationFragment: AddLocationFragment){
        this.addLocationFragment = addLocationFragment
    }

/*
    fun getLocationInfo(countryName: String?, adminArea: String?, subAdminArea: String?, lat: Double?, lon: Double?,addLocationFragment: AddLocationFragment) {
        this.addLocationFragment = addLocationFragment
        displayAddress(countryName, adminArea, subAdminArea)
    }

    private fun displayAddress(countryName: String?, adminArea: String?, subAdminArea: String?){
        if (adminArea?.isEmpty() == true || adminArea?.isBlank() == true || adminArea == "") {
            if (countryName?.isEmpty() == true || countryName?.isBlank() == true || countryName == "") {
                lifecycleScope.launch(Dispatchers.Main) {
                    tvCountryName.text = "Unknown"
                    tvSubAdminArea.text = "Unknown"
                }
            } else {
                lifecycleScope.launch(Dispatchers.Main) {
                    tvCountryName.text = countryName
                }
            }
        } else if (adminArea?.isEmpty() == false) {
            lifecycleScope.launch(Dispatchers.Main) {
                tvCountryName.text = adminArea
                tvCountryName.append(", $countryName")
            }
        }
        if (subAdminArea?.isBlank() == true || subAdminArea?.isEmpty() == true || subAdminArea == "") {
            lifecycleScope.launch(Dispatchers.Main) {
                tvSubAdminArea.text = "Unknown"
            }
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                tvSubAdminArea.text = subAdminArea
            }
        }
    }*/

}