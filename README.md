# VolvoWeather

This app uses api from https://www.metaweather.com/api/ to fetch waether report.

First we use https://www.metaweather.com/api/location/search/?query={location} to fetch location code.
Using this location code an api https://www.metaweather.com/api/location/{location_code} is hit to fetch weather info

This app shows below data
-City Name
-Weather icon
-Sunrise time
-Sunset time
-maximum temp
-min temp

This project uses MVVM design pattern and retrofit library and parses response in WeatherData object using GsonConverterFactory. Also Glide library is usded to show weather icon


