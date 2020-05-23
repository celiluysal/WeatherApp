package com.example.weatherapp.Details;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.ForecastCardData;
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

    private List<ForecastCardData> forecastCardDataList;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(List<ForecastCardData> param1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forecastCardDataList = (List<ForecastCardData>) getArguments().getSerializable(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }


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

        recyclerView = rootView.findViewById(R.id.recyclerViewHourOfDay);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        detailsRecyclerViewAdapter = new DetailsRecyclerViewAdapter();
        recyclerView.setAdapter(detailsRecyclerViewAdapter);

        setDetailsCard();


         return rootView;
    }

    public void setDetailsCard(){



        String day = forecastCardDataList.get(0).getDay();
        textViewDayOfWeek.setText(day);


        floatingActionButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("q","close");
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("details");
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                fragment = getActivity().getSupportFragmentManager().findFragmentByTag("dashboard");
                if(fragment != null){
                    getActivity().getSupportFragmentManager().beginTransaction().attach(fragment).commit();
                }
            }
        });
    }
}
