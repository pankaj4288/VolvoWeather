package com.pankaj.volvoweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pankaj.volvoweather.R
import com.pankaj.volvoweather.data.WeatherData

class WeatherAdapter(var context: Context, private val weatherDataList: List<WeatherData>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.row_layot, parent, false)
        return WeatherHolder(v)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val data = weatherDataList.get(position)
        // we get an array in consolidated weather. I was not sure what all array
        // means, so I just took first element in array and showed icon and temp from that
        val consolidatedData = data.consolidated_weather.get(0)
        holder.title.text = data.title
        holder.sunrise.text =
            context.getString(R.string.sunrise, data.sun_rise)
        holder.sunset.text =
            context.getString(R.string.sunset, data.sun_set)
        holder.maxTemp.text = context.getString(
            R.string.max_temp,
            consolidatedData.max_temp
        )
        holder.minTem.text = context.getString(
            R.string.min_temp,
            consolidatedData.min_temp
        )
        Glide.with(context)
            .load(makeIconURL(consolidatedData.weather_state_abbr))
            .into(holder.icon)
    }

    private fun makeIconURL(weatherStateAbbr: String): String {
        return "https://www.metaweather.com/static/img/weather/png/64/" + weatherStateAbbr + ".png"
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.name_city)
        var sunrise: TextView = itemView.findViewById(R.id.sunrise)
        var sunset: TextView = itemView.findViewById(R.id.sunset)
        var maxTemp: TextView = itemView.findViewById(R.id.maxt_temp)
        var minTem: TextView = itemView.findViewById(R.id.min_temp)
        var icon: ImageView = itemView.findViewById(R.id.weather_icon)
    }
}