package com.example.weatherapp.Retrofit;

public class WeatherApiUtils {
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static WeatherDaoInterface getWeatherDaoInterface(){
        RetrofitClient.retrofit = null;
        return RetrofitClient.getClient(BASE_URL).create(WeatherDaoInterface.class);
    }
}
