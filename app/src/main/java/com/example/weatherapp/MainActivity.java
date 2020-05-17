package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Retrofit.ApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.WeatherApi.City;
import com.example.weatherapp.WeatherApi.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private WeatherDaoInterface weatherDaoInterface;
    private TextView textViewShow;
    private Button buttonCalistir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalistir = findViewById(R.id.buttonCalistir);
        textViewShow = findViewById(R.id.textViewShow);

        weatherDaoInterface = ApiUtils.getWeatherDaoInterface();

        buttonCalistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherData();
            }
        });





    }

    public void weatherData(){
        /*lat,lon,getString(R.string.apiKey)*/
        weatherDaoInterface.getWeatherData().enqueue(new Callback<WeatherResponse>() {
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
