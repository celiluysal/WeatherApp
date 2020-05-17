package com.example.weatherapp.Retrofit;

public class ApiUtils {
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static WeatherDaoInterface getWeatherDaoInterface(){
        return RetrofitClient.getClient(BASE_URL).create(WeatherDaoInterface.class);
    }
}
