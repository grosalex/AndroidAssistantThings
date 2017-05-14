package com.grosalex.androidassistantthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView title;
    private TextView weather;
    private TextView temperature;
    private ArrayList<HourlyWeather> hourlyWeatherArrayList;
    private RecyclerView hourlyRecycler;
    private HourlyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ImageView icon;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Hello World");

        title = (TextView) findViewById(R.id.title);
        weather = (TextView)findViewById(R.id.weather);
        temperature = (TextView)findViewById(R.id.temperature);
        icon= (ImageView)findViewById(R.id.weather_icon);
        hourlyRecycler = (RecyclerView)findViewById(R.id.hourly_recycler);
        hourlyRecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        hourlyRecycler.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        hourlyWeatherArrayList= new ArrayList<HourlyWeather>();
        mHandler = new Handler();

        mAdapter = new HourlyAdapter(this,hourlyWeatherArrayList);
        hourlyRecycler.setAdapter(mAdapter);
        fetchTodaysWeather();
        fetchNextDaysWeather();
        update();
    }


    private void update(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchTodaysWeather();
                fetchNextDaysWeather();
                update();
            }
        },3600000);
    }
    private void fetchNextDaysWeather() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/forecast?q=Paris,fr&units=metric&lang=fr&APPID="+getString(R.string.weather_api_key);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for(int i=0;i<response.getJSONArray("list").length();i++){
                                hourlyWeatherArrayList.add(new HourlyWeather(response.getJSONArray("list").getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                        //response.get
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);
    }

    private void fetchTodaysWeather() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/weather?q=Paris,fr&units=metric&lang=fr&APPID="+getString(R.string.weather_api_key);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " +response.toString());
                        try {
                            weather.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                            temperature.setText(response.getJSONObject("main").getDouble("temp_min")+ " C° - " + response.getJSONObject("main").getDouble("temp_max")+ " C°");
                            Picasso.with(getApplicationContext()).load("http://openweathermap.org/img/w/"+response.getJSONArray("weather").getJSONObject(0).getString("icon")+".png").into(icon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //response.get
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);
    }
}
