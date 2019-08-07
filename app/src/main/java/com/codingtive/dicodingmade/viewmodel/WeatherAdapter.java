package com.codingtive.dicodingmade.viewmodel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<Weather> mData = new ArrayList<>();

    void setData(ArrayList<Weather> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_items, viewGroup, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder weatherViewHolder, int position) {
        weatherViewHolder.bindItem(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        TextView tvTemp;
        TextView tvDescription;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }

        void bindItem(Weather item) {
            tvCity.setText(item.getName());
            tvTemp.setText(item.getTemperature());
            tvDescription.setText(item.getDescription());
        }
    }
}
