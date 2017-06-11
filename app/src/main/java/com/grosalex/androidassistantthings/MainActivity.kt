package com.grosalex.androidassistantthings

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class MainActivity : Activity() {
    private var title: TextView? = null
    private var weather: TextView? = null
    private var temperature: TextView? = null
    private var hourlyWeatherArrayList: ArrayList<HourlyWeather>? = null
    private var hourlyRecycler: RecyclerView? = null
    private var mAdapter: HourlyAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var icon: ImageView? = null
    private var mHandler: Handler? = null
    private var textClock: TextClock? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Hello World")

        title = findViewById(R.id.title) as TextView
        weather = findViewById(R.id.weather) as TextView
        temperature = findViewById(R.id.temperature) as TextView
        icon = findViewById(R.id.weather_icon) as ImageView
        hourlyRecycler = findViewById(R.id.hourly_recycler) as RecyclerView
        hourlyRecycler!!.setHasFixedSize(true)

        textClock = findViewById(R.id.textClock) as TextClock
        textClock!!.format24Hour = "E d MMM HH:mm"
        textClock!!.format12Hour = null

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        hourlyRecycler!!.layoutManager = mLayoutManager

        // specify an adapter (see also next example)
        hourlyWeatherArrayList = ArrayList<HourlyWeather>()
        mHandler = Handler()

        mAdapter = HourlyAdapter(this, hourlyWeatherArrayList!!)
        hourlyRecycler!!.adapter = mAdapter
        fetchTodaysWeather()
        fetchNextDaysWeather()
        update()
    }


    private fun update() {
        mHandler!!.postDelayed({
            hourlyWeatherArrayList!!.clear()
            fetchTodaysWeather()
            fetchNextDaysWeather()
            update()
        }, 3600000)
    }

    private fun fetchNextDaysWeather() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&units=metric&lang=fr&APPID=" + getString(R.string.weather_api_key)
        val jsObjRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->
            try {
                for (i in 0..response.getJSONArray("list").length() - 1) {
                    hourlyWeatherArrayList!!.add(HourlyWeather(response.getJSONArray("list").getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mAdapter!!.notifyDataSetChanged()
            //response.get
        }, Response.ErrorListener {
            // TODO Auto-generated method stub
        })

        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest)
    }

    private fun fetchTodaysWeather() {

        val queue = Volley.newRequestQueue(this)
        val url = "http://api.openweathermap.org/data/2.5/weather?q=Paris,fr&units=metric&lang=fr&APPID=" + getString(R.string.weather_api_key)
        val jsObjRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->
            Log.d(TAG, "onResponse: " + response.toString())
            try {
                weather!!.text = response.getJSONArray("weather").getJSONObject(0).getString("description")
                temperature!!.text = response.getJSONObject("main").getDouble("temp_min").toString() + " C° - " + response.getJSONObject("main").getDouble("temp_max") + " C°"
                Picasso.with(applicationContext).load("http://openweathermap.org/img/w/" + response.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png").into(icon)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            //response.get
        }, Response.ErrorListener {
            // TODO Auto-generated method stub
        })

        // Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest)
    }

    companion object {

        private val TAG = "MainActivity"
    }
}
