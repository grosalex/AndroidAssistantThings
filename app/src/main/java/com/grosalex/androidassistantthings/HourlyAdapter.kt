package com.grosalex.androidassistantthings

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.TimeZone

/**
 * Created by grosalex on 07/05/17.
 */

class HourlyAdapter(private val mContext: Context, private val datas: ArrayList<HourlyWeather>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var weatherTextView: TextView
        var temperatureTextView: TextView
        // each data item is just a string in this case
        var hoursTextView: TextView
        var icon: ImageView

        init {
            hoursTextView = v.findViewById(R.id.hours) as TextView
            weatherTextView = v.findViewById(R.id.hourly_weather) as TextView
            temperatureTextView = v.findViewById(R.id.hourly_temperature) as TextView
            icon = v.findViewById(R.id.hourly_icon) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HourlyAdapter.ViewHolder, position: Int) {
        holder.weatherTextView.text = datas[position].weather
        holder.temperatureTextView.text = datas[position].temp.toString() + "C°"
        Picasso.with(mContext).load("http://openweathermap.org/img/w/" + datas[position].icon + ".png").into(holder.icon)

        val d = Date(datas[position].dt * 1000)
        val sdfd = SimpleDateFormat("") // the format of your date
        val sdfh = SimpleDateFormat("dd/MM HH:mm") // the format of your date
        sdfh.timeZone = TimeZone.getTimeZone("GMT+4") // give a timezone reference for formating (see comment at the bottom
        val formattedDate = sdfh.format(d)
        holder.hoursTextView.text = formattedDate
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}
