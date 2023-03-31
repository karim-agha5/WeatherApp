package com.example.weatherapp.ui.activity

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.weatherapp.R
import com.example.weatherapp.helper.CustomProgressDialog
import com.example.weatherapp.ui.fragment.InitialUserSettingsFragment

class SplashScreenActivity : AppCompatActivity(),InitialUserSettingsFragment.OnDoneButtonClickListener {

    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }
     fun startMainActivity(){
         startActivity(Intent(this,MainActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
         customProgressDialog.dismiss()
         finish()
    }

    override fun onClick() {
        customProgressDialog = CustomProgressDialog.showProgressDialog(this)
        customProgressDialog.show()
        startMainActivity()
    }
}