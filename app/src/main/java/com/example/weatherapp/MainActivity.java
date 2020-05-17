package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.PlaceApi.Hit;
import com.example.weatherapp.PlaceApi.PlaceResponse;
import com.example.weatherapp.Retrofit.PlaceApiUtils;
import com.example.weatherapp.Retrofit.PlaceDaoInterface;
import com.example.weatherapp.Retrofit.WeatherApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.WeatherApi.City;

import com.example.weatherapp.WeatherApi.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private WeatherDaoInterface weatherDaoInterface;
    private PlaceDaoInterface placeDaoInterface;

    private TextView textViewShow;
    private Button buttonCalistir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalistir = findViewById(R.id.buttonCalistir);
        textViewShow = findViewById(R.id.textViewShow);

        weatherDaoInterface = WeatherApiUtils.getWeatherDaoInterface();
        placeDaoInterface = PlaceApiUtils.getPlaceDaoInterface();

        buttonCalistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //weatherData(38.0787,26.9584);
                placeData("serdi");
            }
        });





    }

    public void placeData(String query){
        placeDaoInterface.getPlaceData(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                String localeNames = response.body().getHits().get(0).getLocaleNames().getDefault().get(0);
                textViewShow.setText(localeNames);

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.e("onfailure",t.getLocalizedMessage());
            }
        });
    }

    public void weatherData(double lat, double lon){
        /*lat,lon,getString(R.string.apiKey)*/
        weatherDaoInterface.getWeatherData(lat,lon,getString(R.string.apiKey)).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                City city = response.body().getCity();
                textViewShow.setText(city.getName());


                Toast.makeText(getApplicationContext(),"calisti",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"onFailure",Toast.LENGTH_LONG).show();
                Log.e("onfailure",t.getLocalizedMessage());


            }
        });

    }

}
