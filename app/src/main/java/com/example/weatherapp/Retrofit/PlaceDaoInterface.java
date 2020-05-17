package com.example.weatherapp.Retrofit;

import com.example.weatherapp.PlaceApi.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceDaoInterface {
    @GET("1/places/query")
    Call<PlaceResponse> getPlaceData(@Query("query") String query);
}
