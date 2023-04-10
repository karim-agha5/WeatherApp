package com.example.weatherapp.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConverterTest {


    @Test
    fun kelvinToCelsius_278point15_kelvin_5_celsius(){
        // Given -> temperature in kelvin that equals 278.15
        val kelvin = 278.15

        // Where -> call kelvinToCelsius() method
        val celsius = Converter.kelvinToCelsius(kelvin)

        // Then -> We should be getting 15 celsius
        assertThat(celsius, `is`(5.0))
    }


    @Test
    fun meterPerSecToMilePerHour_10meterPerSec_22point369362912milesPerHour(){
        // Given -> Wind speed in meter/sec that equals 10
        val meterPerSec = 10

        // Where -> call meterPerSecToMilePerHour() method
        val milesPerHour = Converter.meterPerSecToMilePerHour(meterPerSec.toDouble())

        // Then -> We should get 22.3694 as a value
        assertThat(milesPerHour, `is`(22.369362912))
    }
}