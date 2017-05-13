package com.grosalex.androidassistantthings;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by grosalex on 13/05/17.
 */

public class HourlyWeather {
    private long dt;
    private double temp;
    private double temp_max;
    private double temp_min;
    private String weather;
    private String icon;

    public HourlyWeather(JSONObject json) throws JSONException {
        dt=json.getInt("dt");
        temp=json.getJSONObject("main").getDouble("temp");
        temp_max=json.getJSONObject("main").getDouble("temp_max");
        temp_min=json.getJSONObject("main").getDouble("temp_min");
        weather=json.getJSONArray("weather").getJSONObject(0).getString("description");
        icon=json.getJSONArray("weather").getJSONObject(0).getString("icon");
    }

    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public String getWeather() {
        return weather;
    }

    public String getIcon() {
        return icon;
    }
}
