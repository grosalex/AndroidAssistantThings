package com.grosalex.androidassistantthings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by grosalex on 07/05/17.
 */

public class HourlyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> datas;

    public HourlyAdapter(Context context, ArrayList<String> input){
        mContext=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
