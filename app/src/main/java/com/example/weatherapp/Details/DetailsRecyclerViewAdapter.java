package com.example.weatherapp.Details;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.ForecastCardData;
import com.example.weatherapp.R;

import java.util.List;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsHolder> {
    private java.util.List<ForecastCardData> forecastCardDataList;
    private FragmentActivity fragmentActivity;

    public DetailsRecyclerViewAdapter(FragmentActivity fragmentActivity, List<ForecastCardData> forecastCardDataList) {
        this.forecastCardDataList = forecastCardDataList;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_hours,parent,false);
        return new DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {

        holder.textViewHourOfDay.setText(forecastCardDataList.get(position).getTime());
        holder.textViewTemp.setText(forecastCardDataList.get(position).getTemperature()+"°");

        String uri = "@drawable/a"+ forecastCardDataList.get(position).getIcon() +"_svg"; //imname without extension
        int imageResource = holder.view.getResources().getIdentifier(uri, null, "com.example.weatherapp");
        holder.imageViewForecastIcon.setImageResource(imageResource);
        holder.imageViewForecastIcon.setColorFilter(forecastCardDataList.get(position).getColorHour());




    }

    @Override
    public int getItemCount() {
        return forecastCardDataList.size();
    }

    public class DetailsHolder extends RecyclerView.ViewHolder {
        private TextView textViewHourOfDay, textViewTemp;
        private ImageView imageViewForecastIcon;
        private View view;

        public DetailsHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            textViewHourOfDay = itemView.findViewById(R.id.textViewHourOfDay);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
            imageViewForecastIcon = itemView.findViewById(R.id.imageViewForecastIcon);





        }
    }
}
