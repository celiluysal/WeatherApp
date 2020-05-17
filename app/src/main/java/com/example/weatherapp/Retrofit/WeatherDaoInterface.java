package com.example.weatherapp.Retrofit;

import com.example.weatherapp.WeatherApi.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherDaoInterface {
    //@GET("data/2.5/forecast")
    @GET("/data/2.5/forecast?lat=40.7848&lon=30.3947&appid=3f7d5d48bbd7ba82b28146325e18ff7a")
    //Call<WeatherResponse> getWeatherData(@Query("lat") double lat,@Query("lon") double lon,@Query("appid") String apiKey);
    Call<WeatherResponse> getWeatherData();
}
