package com.example.weatherapp.util

private const val WIND_SPEED_CONVERSION_FACTOR = 2.2369362912

class Converter private constructor() {
    companion object{
        fun celsiusToKelvin(celsius: Double) = celsius + 273.15

        fun celsiusToFahrenheit(celsius: Double) = (celsius * 9.0/5.0) + 32

        fun kelvinToCelsius(kelvin: Double) = kelvin - 273.15

        fun kelvinToFahrenheit(kelvin: Double) = celsiusToFahrenheit(kelvinToCelsius(kelvin))

        fun fahrenheitToCelsius(fahrenheit: Double) = (fahrenheit - 32) * 5.0/9.0

        fun fahrenheitToKelvin(fahrenheit: Double) = celsiusToKelvin(fahrenheitToCelsius(fahrenheit))

        fun meterPerSecToMilePerHour(windSpeed: Double) = windSpeed * WIND_SPEED_CONVERSION_FACTOR

        fun milePerHourToMeterPerSec(windSpeed: Double) = windSpeed / WIND_SPEED_CONVERSION_FACTOR
    }
}