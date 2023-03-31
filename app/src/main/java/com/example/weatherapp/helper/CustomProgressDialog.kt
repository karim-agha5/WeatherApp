package com.example.weatherapp.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.weatherapp.R

class CustomProgressDialog {
    companion object{
        fun showProgressDialog(context: Context) : Dialog{
            val dialog = Dialog(context)
            val customProgressDialogLayout =
                LayoutInflater.from(context).inflate(R.layout.progress_dialog,null)
            dialog.setContentView(customProgressDialogLayout)
            dialog.setCancelable(false)
            return dialog
        }
    }
}