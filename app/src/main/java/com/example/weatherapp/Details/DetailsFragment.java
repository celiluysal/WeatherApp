package com.example.weatherapp.Details;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.ForecastCardData;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<ForecastCardData> mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private DetailsRecyclerViewAdapter detailsRecyclerViewAdapter;
    private View rootView;

    private CardView cardViewDetails;
    private FloatingActionButton floatingActionButtonClose;
    private TextView textViewDayOfWeek, textViewTemp, textViewTempMin, textViewTempMax;
    private ImageView imageViewForecastIcon;
    private CardView cardViewHourOfDayContainer;
    private FrameLayout frameLayout;
    private ConstraintLayout constraintLayoutDetails;

    private List<ForecastCardData> forecastCardDataList;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(List<ForecastCardData> param1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forecastCardDataList = (List<ForecastCardData>) getArguments().getSerializable(ARG_PARAM1);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        constraintLayoutDetails.setFocusableInTouchMode(true);
        constraintLayoutDetails.requestFocus();
        constraintLayoutDetails.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    RemoveAndAnimation();
                    return true;
                }
                return false;
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        floatingActionButtonClose = rootView.findViewById(R.id.floatingActionButtonClose);
        cardViewDetails = rootView.findViewById(R.id.cardViewDetails);
        textViewDayOfWeek = rootView.findViewById(R.id.textViewDayOfWeek);
        textViewTemp = rootView.findViewById(R.id.textViewTemp);
        textViewTempMin = rootView.findViewById(R.id.textViewTempMin);
        textViewTempMax = rootView.findViewById(R.id.textViewTempMax);
        imageViewForecastIcon = rootView.findViewById(R.id.imageViewForecastIcon);
        cardViewHourOfDayContainer = rootView.findViewById(R.id.cardViewHourOfDayContainer);
        frameLayout = rootView.findViewById(R.id.frameLayout);
        constraintLayoutDetails = rootView.findViewById(R.id.constraintLayoutDetails);

        recyclerView = rootView.findViewById(R.id.recyclerViewHourOfDay);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        detailsRecyclerViewAdapter = new DetailsRecyclerViewAdapter(getActivity(),forecastCardDataList);
        recyclerView.setAdapter(detailsRecyclerViewAdapter);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.enter);
        constraintLayoutDetails.startAnimation(animation);

        setDetailsCard();

        return rootView;
    }


    public int getHourPosition(String hour){
        int position = 0;
        for (int i=0;i<forecastCardDataList.size();i++){
            if (forecastCardDataList.get(i).getTime().equals(hour)) {
                position = i;
            }
        }
        return position;
    }

    private void RemoveAndAnimation(){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.exit);
        constraintLayoutDetails.startAnimation(animation);
        constraintLayoutDetails.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("details");
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        },animation.getDuration());
    }

    @SuppressLint("Range")
    public void setDetailsCard(){

        int position = getHourPosition("12:00");
        textViewDayOfWeek.setText(forecastCardDataList.get(position).getDay());
        textViewTemp.setText(forecastCardDataList.get(position).getTemperature() + "°");
        textViewTempMin.setText(forecastCardDataList.get(position).getMinTemperature() + "°");
        textViewTempMin.setAlpha(30);
        textViewTempMax.setText(forecastCardDataList.get(position).getMaxTemperature() + "°");
        textViewTempMax.setAlpha(30);

        String uri = "@drawable/a"+ forecastCardDataList.get(position).getIcon() +"_svg"; //imname without extension
        int imageResource = getResources().getIdentifier(uri, null, "com.example.weatherapp");
        imageViewForecastIcon.setImageResource(imageResource);

        cardViewDetails.setCardBackgroundColor(forecastCardDataList.get(0).getColorDay());


        floatingActionButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveAndAnimation();
            }
        });
    }
}
