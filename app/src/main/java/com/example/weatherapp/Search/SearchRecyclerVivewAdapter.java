package com.example.weatherapp.Search;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class SearchRecyclerVivewAdapter extends RecyclerView.Adapter<SearchRecyclerVivewAdapter.SearchResultHolder>{


    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {
        private TextView textViewCityName;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
        }
    }
}
