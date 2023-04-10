package com.example.weatherapp.ui.fragment.mainapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.*
import com.example.weatherapp.Notification
import com.example.weatherapp.databinding.FragmentAlertsBinding
import com.example.weatherapp.ui.activity.MainActivity
import com.example.weatherapp.util.TAG
import java.util.Calendar


class AlertsFragment : Fragment(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    private lateinit var binding: FragmentAlertsBinding
    private var dateYear: Int = 0
    private var dateMonth: Int = 0
    private var dateDayOfMonth: Int = 0
    private var dateHourOfDay: Int = 0
    private var dateMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_alerts,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationConfig()
       // createNotification()

        binding.fab.setOnClickListener {
            //scheduleNotification()

           /* val datePickerDialog = setupDatePicker()
            val timePickerDialog = setupTimePicker()*/
            createNotification()
            val datePickerDialog = setupDatePicker()
        }
    }

    private fun setupTimePicker() : TimePickerDialog{
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val timePickerDialog =
            TimePickerDialog(
                requireActivity(),
                this@AlertsFragment,
                hour,
                min,
                false
            )
        timePickerDialog.show()
        return timePickerDialog
    }

    private fun setupDatePicker() : DatePickerDialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(
                requireActivity(),
                this@AlertsFragment,
                year,
                month,
                day
            )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        return datePickerDialog
    }

    private fun scheduleNotification(){
        val intent = Intent(requireActivity(),Notification::class.java)
        val title = "Weather Alert!"
        val message = "Everything is fine"
        intent.putExtra(INTENT_TITLE_KEY,title)
        intent.putExtra(INTENT_MESSAGE_KEY,message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity(),
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val time = getTime()
        Log.i(TAG, "$time")

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun getTime() : Long{
        var time: Long = 0
        val c = Calendar.getInstance()

        if(dateYear != 0){
            c.set(Calendar.YEAR,dateYear)
        }


        if(dateMonth != 0){
            c.set(Calendar.MONTH,dateMonth)
        }


        if(dateDayOfMonth != 0){
            c.set(Calendar.DAY_OF_MONTH,dateDayOfMonth)
        }


        if(dateHourOfDay != 0){
            c.set(Calendar.HOUR_OF_DAY,dateHourOfDay)
        }


        if(dateMinute != 0){
            c.set(Calendar.MINUTE,dateMinute)
        }

        time = c.timeInMillis

        return time
    }

    private fun createNotification(){
        val channelName = "Weather Alerts"
        val description = "Scheduled Weather Alerts"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID,channelName,importance)
        channel.description = description
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    private fun setupNavigationConfig() {
        val appBarConfiguration =
            AppBarConfiguration(
                findNavController().graph,
                (activity as MainActivity).mainActivityBinding.drawerLayout
            )

        /*
        * This should transform the hamburger icon to the back button
        * if a non top-level destination is on top of the stack
        * */

        binding.alertsFragmentToolbar.setupWithNavController(findNavController(), appBarConfiguration)

        /*
        * Attaches the nav controller to the navigation view to enable the navigation among fragments
        * through the menu items
        * */
        (activity as MainActivity).mainActivityBinding.navigationView.setupWithNavController(
            findNavController()
        )
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        dateYear = p1
        dateMonth = p2
        dateDayOfMonth = p3
        setupTimePicker()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        dateHourOfDay = p1
        dateMinute = p2
        scheduleNotification()
    }
}