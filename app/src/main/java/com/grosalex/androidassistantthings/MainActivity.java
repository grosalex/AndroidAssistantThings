package com.grosalex.androidassistantthings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TextView title;
    private TextView weather;
    private TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Hello World");

        title = (TextView) findViewById(R.id.title);
        weather = (TextView)findViewById(R.id.weather);
        temperature = (TextView)findViewById(R.id.temperature);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.openweathermap.org/data/2.5/weather?q=Paris,fr&units=metric&lang=fr&APPID="+getString(R.string.weather_api_key);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " +response.toString());
                        try {
                            weather.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                            temperature.setText(response.getJSONObject("main").getDouble("temp")+ " C°");
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
