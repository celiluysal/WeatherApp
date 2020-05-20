package com.example.weatherapp.DashBoard;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.ForecastCardData;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.WeatherApi.WeatherResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastCardHolder> {
    private java.util.List<ForecastCardData> forecastCardDataList;
    private java.util.List<ForecastCardData> forecastFiveDayList;
    private WeatherResponse weatherResponse;



    public ForecastRecyclerViewAdapter(WeatherResponse weatherResponse, java.util.List<ForecastCardData> forecastCardDataList) {
        this.weatherResponse = weatherResponse;
        this.forecastCardDataList = forecastCardDataList;

        forecastFiveDayList = new ArrayList<>();
        selectFiveDay();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public ForecastCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otherdays_forecast,parent,false);
        return new ForecastCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastCardHolder holder, int position) {


        holder.textViewTimeOfDay.setText(forecastFiveDayList.get(position).getTime());
        holder.textViewDayOfWeek.setText(forecastFiveDayList.get(position).getDay());
        holder.textViewTemp.setText(forecastFiveDayList.get(position).getTemperature());
        holder.textViewTempMin.setText(forecastFiveDayList.get(position).getMinTemperature());
        holder.textViewTempMax.setText(forecastFiveDayList.get(position).getMaxTemperature());

        String uri = "@drawable/a"+ forecastFiveDayList.get(position).getIcon() +"_svg"; //imname without extension
        int imageResource = holder.view.getResources().getIdentifier(uri, null, "com.example.weatherapp");
        holder.imageViewForecastIcon.setImageResource(imageResource);
        holder.imageViewFivedaysBackground.setImageResource(imageResource);
        holder.imageViewFivedaysBackground.setAlpha(30);


        holder.cardViewOtherdays.setCardBackgroundColor(forecastFiveDayList.get(position).getColor());

    }

    private void selectFiveDay(){
        for (ForecastCardData data:forecastCardDataList){
            if (data.getTime().equals("12:00")){
                forecastFiveDayList.add(data);
                Log.e("saat",data.getDay()+" - "+data.getTime());
            }
        }
    }

    @Override
    public int getItemCount() {

        return forecastFiveDayList.size();
    }
    public class ForecastCardHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textViewTimeOfDay,textViewDayOfWeek,textViewTemp,textViewTempMin,textViewTempMax;
        private ImageView imageViewForecastIcon,imageViewFivedaysBackground;
        private CardView cardViewOtherdays;


        @RequiresApi(api = Build.VERSION_CODES.N)
        public ForecastCardHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            textViewTimeOfDay = itemView.findViewById(R.id.textViewTimeOfDay);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
            textViewTempMin = itemView.findViewById(R.id.textViewTempMin);
            textViewTempMax = itemView.findViewById(R.id.textViewTempMax);
            imageViewForecastIcon = itemView.findViewById(R.id.imageViewForecastIcon);
            imageViewFivedaysBackground = itemView.findViewById(R.id.imageViewFivedaysBackground);
            cardViewOtherdays = itemView.findViewById(R.id.cardViewOtherdays);
        }
    }
}



