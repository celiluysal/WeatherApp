package com.example.weatherapp.DashBoard;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Details.DetailsFragment;
import com.example.weatherapp.ForecastCardData;
import com.example.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastCardHolder> {
    private java.util.List<ForecastCardData> forecastCardDataList;
    private java.util.List<ForecastCardData> forecastFiveDayList;
    private FragmentActivity fragmentActivity;

    public ForecastRecyclerViewAdapter(FragmentActivity fragmentActivity, java.util.List<ForecastCardData> forecastCardDataList) {
        this.forecastCardDataList = forecastCardDataList;
        this.fragmentActivity = fragmentActivity;

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
        holder.textViewTemp.setText(forecastFiveDayList.get(position).getTemperature() + "°");
        holder.textViewTempMin.setText(forecastFiveDayList.get(position).getMinTemperature() + "°");
        holder.textViewTempMax.setText(forecastFiveDayList.get(position).getMaxTemperature() + "°");

        String uri = "@drawable/a"+ forecastFiveDayList.get(position).getIcon() +"_svg";
        int imageResource = holder.view.getResources().getIdentifier(uri, null, "com.example.weatherapp");
        holder.imageViewForecastIcon.setImageResource(imageResource);
        holder.imageViewFivedaysBackground.setImageResource(imageResource);
        holder.imageViewFivedaysBackground.setAlpha(30);

        holder.cardViewOtherdays.setCardBackgroundColor(forecastFiveDayList.get(position).getColorDay());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            holder.cardViewOtherdays.setOutlineSpotShadowColor(forecastFiveDayList.get(position).getColorDay());
        }

        holder.cardViewOtherdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentActivity.getSupportFragmentManager()
                        .beginTransaction().add(R.id.fragmentHolder,
                        DetailsFragment.newInstance(selectOneDay((String) holder.textViewDayOfWeek.getText())),"details")
                        .commit();
            }
        });
    }


    private void selectFiveDay(){
        for (ForecastCardData data:forecastCardDataList){
            if (data.getTime().equals("12:00")){
                forecastFiveDayList.add(data);
            }
        }
    }

    private List<ForecastCardData> selectOneDay(String day){
        List<ForecastCardData> tempList = new ArrayList<>();
        for (ForecastCardData data:forecastCardDataList){
            if (data.getDay().equals(day)){
                tempList.add(data);
            }
        }
        return tempList;
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



