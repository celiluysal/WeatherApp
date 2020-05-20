package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.weatherapp.DashBoard.DashBoardFragment;
import com.example.weatherapp.PlaceApi.PlaceResponse;
import com.example.weatherapp.Retrofit.PlaceApiUtils;
import com.example.weatherapp.Retrofit.PlaceDaoInterface;
import com.example.weatherapp.Retrofit.WeatherApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.Search.SearchFragment;

import com.example.weatherapp.WeatherApi.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private WeatherDaoInterface weatherDaoInterface;
    private PlaceDaoInterface placeDaoInterface;

    private String isim;


    private Toolbar toolbar;
    private Fragment tempFragment;

    private WeatherResponse wr;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);


        weatherDaoInterface = WeatherApiUtils.getWeatherDaoInterface();
        placeDaoInterface = PlaceApiUtils.getPlaceDaoInterface();


        toolbar.setTitle("konum");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        tempFragment = new DashBoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentHolder, tempFragment).commit();

        Log.e("a",getPackageName());
        //weatherData(38.0787,26.9584);
        //Log.e("isim",isim);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_item){
            tempFragment = new SearchFragment();
            getSupportActionBar().hide();
        }
        else {
            Log.e("mesaj",item.toString());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, tempFragment).commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentHolder);

        if (f instanceof SearchFragment)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new DashBoardFragment()).commit();
            getSupportActionBar().show();
        }
        else {
            Log.e("mesaj","diğer");
            Log.e("isim",isim);
        }

        //super.onBackPressed();
    }

    public void placeData(String query){
        placeDaoInterface.getPlaceData(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                String localeNames = response.body().getHits().get(0).getLocaleNames().getDefault().get(0);

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.e("onfailure",t.getLocalizedMessage());
            }
        });
    }


    public void setIsim(WeatherResponse r){
        isim = r.getCity().getName();
        Log.e("isim",isim);

    }

    public void weatherData(double lat, double lon){
        /*lat,lon,getString(R.string.apiKey)*/
        weatherDaoInterface.getWeatherData(lat,lon,getString(R.string.apiKey)).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                //if ()

                //isim = response.body().getCity().getName();
                setIsim(response.body());
                //tempFragment = DashBoardFragment.newInstance(response.body());
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, tempFragment).commit();
                //City city = response.body().getCity();
                //Toast.makeText(getApplicationContext(),"calisti",Toast.LENGTH_LONG).show();
                //Log.e("cit name",city.getName());

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"onFailure",Toast.LENGTH_LONG).show();
                Log.e("onfailure",t.getLocalizedMessage());


            }
        });

    }

}
