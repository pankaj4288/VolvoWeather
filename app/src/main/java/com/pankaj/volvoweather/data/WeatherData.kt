package com.pankaj.volvoweather.data

data class WeatherData(
    val consolidated_weather: List<ConsolidatedWeather>,
    val sun_rise: String,
    val sun_set: String,
    val title: String
)
