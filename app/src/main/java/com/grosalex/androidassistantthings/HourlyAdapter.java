package com.grosalex.androidassistantthings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by grosalex on 07/05/17.
 */

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder>  {

    private Context mContext;
    private ArrayList<HourlyWeather> datas;

    public HourlyAdapter(Context context, ArrayList<HourlyWeather> input){
        datas=input;
        mContext=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView weatherTextView;
        public  TextView temperatureTextView;
        // each data item is just a string in this case
        public TextView hoursTextView;
        public ImageView icon;
        public ViewHolder(View v) {
            super(v);
            hoursTextView = (TextView)v.findViewById(R.id.hours);
            weatherTextView =(TextView)v.findViewById(R.id.hourly_weather);
            temperatureTextView = (TextView)v.findViewById(R.id.hourly_temperature);
            icon=(ImageView)v.findViewById(R.id.hourly_icon);
        }
    }
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HourlyAdapter.ViewHolder holder, int position) {
        holder.weatherTextView.setText(datas.get(position).getWeather());
        holder.temperatureTextView.setText(datas.get(position).getTemp()+"C°");
        Picasso.with(mContext).load("http://openweathermap.org/img/w/"+datas.get(position).getIcon()+".png").into(holder.icon);

        Date d = new Date(datas.get(position).getDt()*1000);
        SimpleDateFormat sdfd = new SimpleDateFormat(""); // the format of your date
        SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM HH:mm"); // the format of your date
        sdfh.setTimeZone(TimeZone.getTimeZone("GMT+4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdfh.format(d);
        holder.hoursTextView.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
