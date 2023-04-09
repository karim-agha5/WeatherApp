package com.example.weatherapp.ui.fragment.mainapp

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.source.local.room.WeatherDatabase
import com.example.weatherapp.data.source.local.room.WeatherOneCallResponseDao
import com.example.weatherapp.databinding.FragmentBottomNavigationSheetBinding
import com.example.weatherapp.util.TAG
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomNavigationSheetFragment : BottomSheetDialogFragment() {

    private val weatherInfoViewModel: WeatherInfoViewModel by activityViewModels()
    private lateinit var btnPin: Button
    private lateinit var btnFavorite: Button
    private lateinit var btnDismiss: Button
    private lateinit var binding: FragmentBottomNavigationSheetBinding
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



        binding.btnBottomNavigationSheetPin.setOnClickListener {
            addLocationFragment.navigateToMainFragment()
            dismissNow()
        }
        binding.btnBottomNavigationSheetFavorite.setOnClickListener {
            try{
                weatherInfoViewModel.insertFavoriteLocation(addLocationFragment.getLatLng())

            }catch (ex: SQLiteConstraintException){
                Log.i(TAG, "constraint")
            }
            dismissNow()
        }

        binding.btnBottomNavigationSheetDismiss.setOnClickListener {
            dismissNow()
        }
    }


    // To navigate from the map to the MainFragment
    fun setAddLocationFragment(addLocationFragment: AddLocationFragment){
        this.addLocationFragment = addLocationFragment
    }
}