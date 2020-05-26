package com.example.weatherapp.Search;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.PlaceApi.Hit;
import com.example.weatherapp.R;

import java.util.List;

public class SearchRecyclerVivewAdapter extends RecyclerView.Adapter<SearchRecyclerVivewAdapter.SearchResultHolder>{
    private List<Hit> hitList;

    public SearchRecyclerVivewAdapter(List<Hit> hitList) {
        this.hitList = hitList;
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result,parent,false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        String text = "";
        text += hitList.get(position).getLocaleNames().getDefault().get(0);
        text += ", ";
        if (hitList.get(position).getCounty() != null && hitList.get(position).getCounty().getDefault() != null) {
            text += hitList.get(position).getCounty().getDefault().get(0);
            text += ", ";
        }
        SpannableString s1 = new SpannableString(text);

        text = "";
        if (hitList.get(position).getAdministrative() != null) {
            text += hitList.get(position).getAdministrative().get(0);
            text += ", ";
        }
        text += hitList.get(position).getCountry().getDefault();
        SpannableString s2 = new SpannableString(text);

        int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        s1.setSpan(new StyleSpan(Typeface.BOLD), 0, s1.length(), flag);
        s2.setSpan(new StyleSpan(Typeface.ITALIC), 0, s2.length(), flag);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(s1);
        builder.append(s2);

        holder.textViewCityName.setText(builder);

        holder.textViewCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double lat,lon;
                lat = hitList.get(position).getGeoloc().getLat();
                lon = hitList.get(position).getGeoloc().getLng();
                Log.e("coord", String.valueOf(lat) + " - " + String.valueOf(lon));
                Log.e("name", String.valueOf(builder));

                MainActivity.getInstance().setCoordAndShow(lat,lon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hitList.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {
        private TextView textViewCityName;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
        }
    }
}
