package com.example.weatherapp.DashBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastCardHolder> {
    private java.util.List<String> maxTemps;




    public ForecastRecyclerViewAdapter(java.util.List<String> tmp) {
        maxTemps = tmp;
        /*for (List tmp:list){
            maxTemps.add(tmp.getMain().getTempMax().toString());
        }*/

    }

    @NonNull
    @Override
    public ForecastCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otherdays_forecast,parent,false);
        return new ForecastCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastCardHolder holder, int position) {
        String temp = maxTemps.get(position);

        holder.textViewTemp.setText(temp);

    }

    @Override
    public int getItemCount() {
        return maxTemps.size();
    }

    public class ForecastCardHolder extends RecyclerView.ViewHolder {

        private TextView textViewTemp;

        public ForecastCardHolder(@NonNull View itemView) {
            super(itemView);

            textViewTemp = itemView.findViewById(R.id.textViewTemp);
        }
    }
}



