package com.codingtive.dicodingmade.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, WeatherView {

    private EditText edtCity;
    private ProgressBar progressBar;
    private WeatherAdapter adapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        viewModel = ViewModelProviders.of(WeatherActivity.this).get(MainViewModel.class);
        viewModel.setView(this);
        viewModel.getWeathers().observe(this, getWeather);

        bindView();
    }

    private void bindView() {
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();

        RecyclerView rvWeather = findViewById(R.id.rv_weather);
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setAdapter(adapter);

        progressBar = findViewById(R.id.progress_bar);
        edtCity = findViewById(R.id.edt_city);
        Button btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            String city = edtCity.getText().toString();

            if (TextUtils.isEmpty(city)) return;

            viewModel.setWeather(city);
        }
    }

    private Observer<ArrayList<Weather>> getWeather = new Observer<ArrayList<Weather>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Weather> weathers) {
            if (weathers != null) {
                adapter.setData(weathers);
            }
        }
    };

    @Override
    public void showLoading(Boolean state) {
        if (state) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
