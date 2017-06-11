package com.grosalex.androidassistantthings

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by grosalex on 13/05/17.
 */

class HourlyWeather @Throws(JSONException::class)
constructor(json: JSONObject) {
    val dt: Long
    val temp: Double
    val temp_max: Double
    val temp_min: Double
    val weather: String
    val icon: String

    init {
        dt = json.getInt("dt").toLong()
        temp = json.getJSONObject("main").getDouble("temp")
        temp_max = json.getJSONObject("main").getDouble("temp_max")
        temp_min = json.getJSONObject("main").getDouble("temp_min")
        weather = json.getJSONArray("weather").getJSONObject(0).getString("description")
        icon = json.getJSONArray("weather").getJSONObject(0).getString("icon")
    }
}
