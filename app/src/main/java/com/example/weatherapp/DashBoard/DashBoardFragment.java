package com.example.weatherapp.DashBoard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.Retrofit.WeatherApiUtils;
import com.example.weatherapp.Retrofit.WeatherDaoInterface;
import com.example.weatherapp.WeatherApi.City;
import com.example.weatherapp.WeatherApi.List;
import com.example.weatherapp.WeatherApi.WeatherResponse;

import java.util.ArrayList;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private RecyclerView recyclerView;
    private ForecastRecyclerViewAdapter forecastRecyclerViewAdapter;
    private static java.util.List<List> list;
    private TextView textViewTemperature;

    private WeatherDaoInterface weatherDaoInterface;


    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        weatherDaoInterface = WeatherApiUtils.getWeatherDaoInterface();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);

        textViewTemperature =  rootView.findViewById(R.id.textViewTemperature);
        textViewTemperature.setText("34°");

        recyclerView = rootView.findViewById(R.id.recyclerForecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        //weatherData(38.0787,26.9584);

        java.util.List<String> temps = new ArrayList<>();
        temps.add("1");
        temps.add("2");
        temps.add("3");
        temps.add("4");
        temps.add("5");

        forecastRecyclerViewAdapter = new ForecastRecyclerViewAdapter(temps);
        recyclerView.setAdapter(forecastRecyclerViewAdapter);

        // Inflate the layout for this fragment
        return rootView;




    }

    public void weatherData(double lat, double lon){
        /*lat,lon,getString(R.string.apiKey)*/
        weatherDaoInterface.getWeatherData(lat,lon,getString(R.string.apiKey)).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                list = response.body().getList();


            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("onfailure",t.getLocalizedMessage());


            }
        });

    }
}
