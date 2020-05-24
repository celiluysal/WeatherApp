package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.DashBoard.DashBoardFragment;
import com.example.weatherapp.Details.DetailsFragment;
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
    private static MainActivity instance;

    private WeatherDaoInterface weatherDaoInterface;
    private PlaceDaoInterface placeDaoInterface;


    private Toolbar toolbar;
    private TextView textViewToolbarTitle;
    private FrameLayout frameLayout;
    private Fragment tempFragment;

    private Double lat, lon;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;


        toolbar = findViewById(R.id.toolbar);
        textViewToolbarTitle = findViewById(R.id.textViewToolbarTitle);
        frameLayout = findViewById(R.id.fragmentHolder);

        weatherDaoInterface = WeatherApiUtils.getWeatherDaoInterface();
        placeDaoInterface = PlaceApiUtils.getPlaceDaoInterface();


        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        //tempFragment = new DashBoardFragment();


        lat = 38.0787;
        lon = 26.9584;
        tempFragment = DashBoardFragment.newInstance(lat,lon);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, tempFragment,"dashboard").commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_item){
            //detach dashboard
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("dashboard");
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentHolder, new SearchFragment(),"search").commit();
            getSupportActionBar().hide();
        }
        else {
            Log.e("mesaj",item.toString());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentHolder);

        if (f instanceof SearchFragment)
        {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("search");
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragment = getSupportFragmentManager().findFragmentByTag("dashboard");
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            }
            getSupportActionBar().show();
        }
        else if (f instanceof DetailsFragment){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("details");
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            fragment = getSupportFragmentManager().findFragmentByTag("dashboard");
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().attach(fragment).commit();
            }

        }
        else {
            super.onBackPressed();

        }

        //super.onBackPressed();
    }

    public static MainActivity getInstance(){
        return instance;
    }

    public void setToolbarTile(String tile){
        textViewToolbarTitle.setText(tile);
    }

    public void refreshDashBoard(){
        Log.e("asd","refresh");
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dashboard");
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        tempFragment = DashBoardFragment.newInstance(lat,lon);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, tempFragment,"dashboard").commit();
    }

    public void setCoordAndShow(Double lat, Double lon){
        this.lat = lat;
        this.lon = lon;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("search");
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        getSupportActionBar().show();
        refreshDashBoard();

    }


}
