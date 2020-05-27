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
import com.example.weatherapp.R;
import com.example.weatherapp.SearchCardData;

import java.util.List;

public class SearchRecyclerVivewAdapter extends RecyclerView.Adapter<SearchRecyclerVivewAdapter.SearchResultHolder>{
    private List<SearchCardData> searchCardData;

    public SearchRecyclerVivewAdapter(List<SearchCardData> searchCardData) {
        this.searchCardData = searchCardData;
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
        text += searchCardData.get(position).getLocaleName();
        text += ", ";
        if (!searchCardData.get(position).getCounty().equals("")) {
            text += searchCardData.get(position).getCounty();
            text += ", ";
        }
        SpannableString s1 = new SpannableString(text);

        text = "";
        if (!searchCardData.get(position).getAdministrative().equals("")) {
            text += searchCardData.get(position).getAdministrative();
            text += ", ";
        }
        text += searchCardData.get(position).getCountry();
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
                MainActivity.getInstance().setCoordAndShow(
                        searchCardData.get(position).getLat()
                        ,searchCardData.get(position).getLon());
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchCardData.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {
        private TextView textViewCityName;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
        }
    }
}
