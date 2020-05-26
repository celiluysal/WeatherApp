package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.weatherapp.DashBoard.DashBoardFragment;
import com.example.weatherapp.Details.DetailsFragment;
import com.example.weatherapp.Retrofit.PlaceApiUtils;
import com.example.weatherapp.Retrofit.PlaceDaoInterface;
import com.example.weatherapp.Retrofit.WeatherApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.Search.SearchFragment;

public class MainActivity extends AppCompatActivity{
    private static MainActivity instance;

    private Toolbar toolbar;
    private TextView textViewToolbarTitle;
    private FrameLayout frameLayout;
    private Fragment tempFragment;

    private Double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        toolbar = findViewById(R.id.toolbar);
        textViewToolbarTitle = findViewById(R.id.textViewToolbarTitle);
        frameLayout = findViewById(R.id.fragmentHolder);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        SharedPreferences sh = getSharedPreferences("coord", 0);
        String sLat = sh.getString("lat", getString(R.string.deffault_lat));
        lat = Double.valueOf(sLat);
        String sLon = sh.getString("lon", getString(R.string.deffault_lon));
        lon = Double.valueOf(sLon);

        tempFragment = DashBoardFragment.newInstance(lat,lon);
        DashBoardFragment  tmp = DashBoardFragment.newInstance(lat,lon);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentHolder, tmp,"dashboard").commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_item){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("details");
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            fragment = getSupportFragmentManager().findFragmentByTag("dashboard");
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit);
            ft.add(R.id.fragmentHolder, new SearchFragment(),"search").commit();
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
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("search");
            ft.remove(fragment).commit();
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
    }

    public static MainActivity getInstance(){
        return instance;
    }

    public void setToolbarTile(String tile){
        textViewToolbarTitle.setText(tile);
    }

    public void refreshDashBoard(){
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

        SharedPreferences sharedPreferences = getSharedPreferences("coord",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lat",Double.toString(lat));
        editor.putString("lon",Double.toString(lon));
        editor.apply();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("search");
        ft.remove(fragment).commit();

        getSupportActionBar().show();
        refreshDashBoard();
    }

}
