package com.codingtive.dicodingmade.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

class MainViewModel extends ViewModel {
    private WeatherView view;
    private static final String API_KEY = "51de697f03533d2c8959a6872da563b4";
    private MutableLiveData<ArrayList<Weather>> weatherList = new MutableLiveData<>();

    void setView(WeatherView view) {
        this.view = view;
    }

    void setWeather(final String cities) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Weather> itemList = new ArrayList<>();
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + API_KEY;

        view.showLoading(true);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        Weather items = new Weather(weather);
                        itemList.add(items);
                    }
                    weatherList.postValue(itemList);
                    view.showLoading(false);
                } catch (Exception error) {
                    view.showLoading(false);
                    view.showToast(error.getMessage());
                    Log.e("Exception", error.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                view.showLoading(false);
                view.showToast(error.getMessage());
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Weather>> getWeathers() {
        return weatherList;
    }
}
