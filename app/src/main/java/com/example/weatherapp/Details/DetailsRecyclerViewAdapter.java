package com.example.weatherapp.Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsHolder> {



    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_hours,parent,false);
        return new DetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {
        //holder.textViewHourOfDay.setText("aa");

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class DetailsHolder extends RecyclerView.ViewHolder {
        private TextView textViewHourOfDay, textViewTemp;
        private ImageView imageViewForecastIcon;
        public DetailsHolder(@NonNull View itemView) {
            super(itemView);
            textViewHourOfDay = itemView.findViewById(R.id.textViewHourOfDay);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
            imageViewForecastIcon = itemView.findViewById(R.id.imageViewForecastIcon);





        }
    }
}
