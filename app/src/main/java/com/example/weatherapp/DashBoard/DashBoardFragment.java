package com.example.weatherapp.DashBoard;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapp.ForecastCardData;
import com.example.weatherapp.R;
import com.example.weatherapp.Retrofit.WeatherApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.Search.SearchFragment;
import com.example.weatherapp.WeatherApi.City;
import com.example.weatherapp.WeatherApi.List;
import com.example.weatherapp.WeatherApi.WeatherResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String WEATHER_RESPONSE_KEY = "weatherResponse_key";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private WeatherResponse weatherResponse;
    private String mParam2;

    private View rootView;

    private java.util.List<ForecastCardData> forecastCardDataList;

    private RecyclerView recyclerView;
    private ForecastRecyclerViewAdapter forecastRecyclerViewAdapter;

    private TextView textViewTemperature,textViewWeatherInfo,textViewHumidity;
    private ImageView imageViewIcon,imageViewTodayBackground;
    private CardView cardViewToday;
    private ProgressBar progressBarToday,progressBarOtherdays;

    private WeatherDaoInterface weatherDaoInterface;
    private City city;
    private String cityName;


    public DashBoardFragment() {

        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(WeatherResponse param_weatherResponse) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(WEATHER_RESPONSE_KEY, param_weatherResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            weatherResponse = (WeatherResponse) getArguments().getSerializable(WEATHER_RESPONSE_KEY);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //weatherResponse = (WeatherResponse) getArguments().getSerializable(WEATHER_RESPONSE_KEY);
        rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);
        weatherDaoInterface = WeatherApiUtils.getWeatherDaoInterface();

        textViewTemperature =  rootView.findViewById(R.id.textViewTemperature);
        imageViewIcon = rootView.findViewById(R.id.imageViewIcon);
        imageViewTodayBackground = rootView.findViewById(R.id.imageViewTodayBackground);
        textViewHumidity = rootView.findViewById(R.id.textViewHumidity);
        textViewWeatherInfo = rootView.findViewById(R.id.textViewWeatherInfo);
        cardViewToday = rootView.findViewById(R.id.cardViewToday);
        progressBarToday = rootView.findViewById(R.id.progressBarToday);
        progressBarToday.setVisibility(ProgressBar.VISIBLE);

        recyclerView = rootView.findViewById(R.id.recyclerForecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        weatherData(38.0787,26.9584);

        return rootView;
    }

    private void setTodayCard(ForecastCardData data){

        progressBarToday.setVisibility(ProgressBar.INVISIBLE);
        textViewTemperature.setText(data.getTemperature() + "°");

        String uri = "@drawable/a"+ data.getIcon() +"_svg"; //imname without extension
        int imageResource = getResources().getIdentifier(uri, null, "com.example.weatherapp");
        imageViewIcon.setImageResource(imageResource);
        imageViewTodayBackground.setImageResource(imageResource);
        imageViewTodayBackground.setAlpha(10);

        textViewWeatherInfo.setText(data.getWeatherInfo());
        textViewHumidity.setText("%"+data.getHumidity());
        cardViewToday.setCardBackgroundColor(data.getColor());



    }

    private void setMinMax(java.util.List<ForecastCardData> dataList){
        java.util.List<Integer> temps = new ArrayList<>();
        java.util.List<java.util.List<Integer>> tempsOfDays = new ArrayList<>();
        int[][] daysMinMax = new int[6][2];

        int counter = 0;
        String tmpDay = dataList.get(0).getDay();
        for (int i=0;i<dataList.size();i++){
            if (dataList.get(i).getDay().equals(tmpDay)) {
                temps.add(dataList.get(i).getTemperature());
            }
            else {
                //tempsOfDays.add(temps);
                if (temps != null) {
                    daysMinMax[counter][0] = Collections.min(temps);
                    daysMinMax[counter][1] = Collections.max(temps);
                }
                temps.clear();
                counter++;
                tmpDay = dataList.get(i).getDay();
            }

            if (i == dataList.size()-1){
                if (temps.size() != 0) {
                    daysMinMax[counter][0] = Collections.min(temps);
                    daysMinMax[counter][1] = Collections.max(temps);
                }
            }
        }
        counter = 0;
        tmpDay = dataList.get(0).getDay();
        for (ForecastCardData data:dataList) {
            if (!data.getDay().equals(tmpDay)) {
                counter++;
                tmpDay = data.getDay();
            }
            else{
                data.setMinTemperature(daysMinMax[counter][0]);
                data.setMaxTemperature(daysMinMax[counter][1]);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillData(WeatherResponse wr){
        SimpleDateFormat sd;
        Date time;
        int unixTime;

        for (com.example.weatherapp.WeatherApi.List list:wr.getList()) {
            ForecastCardData data = new ForecastCardData();
            unixTime = list.getDt();
            time = new java.util.Date((long) unixTime * 1000);

            sd = new SimpleDateFormat("EEEE", new Locale("tr"));
            data.setDay(sd.format(time));

            switch (data.getDay()){
                case "Pazartesi":
                    data.setColor(getResources().getColor(R.color.color_day1));
                    break;
                case "Salı":
                    data.setColor(getResources().getColor(R.color.color_day2));
                    break;
                case "Çarşamba":
                    data.setColor(getResources().getColor(R.color.color_day3));
                    break;
                case "Perşembe":
                    data.setColor(getResources().getColor(R.color.color_day4));
                    break;
                case "Cuma":
                    data.setColor(getResources().getColor(R.color.color_day5));
                    break;
                case "Cumartesi":
                    data.setColor(getResources().getColor(R.color.color_day6));
                    break;
                case "Pazar":
                    data.setColor(getResources().getColor(R.color.color_day7));
                    break;
                default:
                    data.setColor(getResources().getColor(R.color.color_day1));
                    break;
            }

            sd = new SimpleDateFormat("HH:mm", new Locale("tr"));
            data.setTime(sd.format(time));

            data.setTemperature((int) Math.round(list.getMain().getTemp()));

            data.setHumidity((int) Math.round(list.getMain().getHumidity()));
            data.setIcon(list.getWeather().get(0).getIcon());
            data.setWeatherInfo(list.getWeather().get(0).getDescription());

            forecastCardDataList.add(data);

        }
        setMinMax(forecastCardDataList);
    }


    public void weatherData(double lat, double lon) {
        weatherDaoInterface.getWeatherData(lat, lon, getString(R.string.apiKey)).enqueue(new Callback<WeatherResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                forecastCardDataList = new ArrayList<>();
                fillData(response.body());
                setTodayCard(forecastCardDataList.get(0));



                forecastRecyclerViewAdapter = new ForecastRecyclerViewAdapter(getActivity(), response.body(),forecastCardDataList);
                recyclerView.setAdapter(forecastRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("onfailure", "on failure");
            }
        });
    }
}
