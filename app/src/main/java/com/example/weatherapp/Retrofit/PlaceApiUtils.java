package com.example.weatherapp.Retrofit;

public class PlaceApiUtils {
    public static final String BASE_URL = "https://places-dsn.algolia.net/";
    public static PlaceDaoInterface getPlaceDaoInterface(){
        RetrofitClient.retrofit = null;
        return RetrofitClient.getClient(BASE_URL).create(PlaceDaoInterface.class);
    }
}
