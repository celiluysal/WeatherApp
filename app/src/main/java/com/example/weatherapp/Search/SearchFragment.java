package com.example.weatherapp.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.weatherapp.Database.SearchDao;
import com.example.weatherapp.Database.SearchDatabaseHelper;
import com.example.weatherapp.PlaceApi.Hit;
import com.example.weatherapp.PlaceApi.PlaceResponse;
import com.example.weatherapp.R;
import com.example.weatherapp.Retrofit.PlaceApiUtils;
import com.example.weatherapp.Retrofit.PlaceDaoInterface;
import com.example.weatherapp.SearchCardData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ItemTouchHelper itemTouchHelper;
    private SearchDatabaseHelper searchDatabaseHelper;
    private List<SearchCardData> searchCardDataList;

    private PlaceDaoInterface placeDaoInterface;
    private View rootView;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchRecyclerVivewAdapter searchRecyclerVivewAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager)   rootView.getContext().getSystemService(rootView.getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        placeDaoInterface = PlaceApiUtils.getPlaceDaoInterface();

        searchView = rootView.findViewById(R.id.searchView);
        recyclerView = rootView.findViewById(R.id.recyclerViewSearchResults);
        recyclerView.setHasFixedSize(true);

        fillHistoryFromDatabase();

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        InputMethodManager imm = (InputMethodManager) rootView.getContext().getSystemService(rootView.getContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeData(newText);
                return true;
            }
        });

        return rootView;
    }

    private void fillHistoryFromDatabase(){
        searchDatabaseHelper = new SearchDatabaseHelper(getContext());
        searchCardDataList = new ArrayList<>();
        searchCardDataList = new SearchDao().allSearches(searchDatabaseHelper);
        searchRecyclerVivewAdapter = new SearchRecyclerVivewAdapter(getContext(), searchCardDataList);
        recyclerView.setAdapter(searchRecyclerVivewAdapter);
    }


    private Paint p = new Paint();

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {


        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.e("m", "onswipe");
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            new SearchDao().deleteSearch(searchDatabaseHelper,
                    searchCardDataList.get(position).getLat(),
                    searchCardDataList.get(position).getLon());

            searchCardDataList.remove(position);
            searchRecyclerVivewAdapter.notifyDataSetChanged();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Bitmap icon;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;
                    if(dX > 0){
                        p.setColor(Color.parseColor("#ffbfbf"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = getBitmapFromVectorDrawable(getContext(),R.drawable.ic_delete_white);

                        RectF icon_dest = new RectF((float) itemView.getLeft() + width/2,
                                (float) itemView.getTop() + width/2,
                                (float) ((float) itemView.getLeft()+ 2.5*width),
                                (float)itemView.getBottom() - width/2);

                        c.drawBitmap(icon,null,icon_dest,p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

    };

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private List<SearchCardData> fillData(PlaceResponse pr){
        List<SearchCardData> searchCardDataList =new ArrayList<>();

        for (Hit hit:pr.getHits()){
            SearchCardData data = new SearchCardData();

            data.setLocaleName(hit.getLocaleNames().getDefault().get(0));
            if (hit.getCounty() != null && hit.getCounty().getDefault() != null) {
                data.setCounty(hit.getCounty().getDefault().get(0));
            }
            else {
                data.setCounty("");
            }

            if (hit.getAdministrative() != null) {
                data.setAdministrative(hit.getAdministrative().get(0));
            }
            else {
                data.setAdministrative("");
            }
            data.setCountry(hit.getCountry().getDefault());

            data.setLat(hit.getGeoloc().getLat());
            data.setLon(hit.getGeoloc().getLng());

            searchCardDataList.add(data);
        }

        return searchCardDataList;
    }

    public void placeData(String query){
        placeDaoInterface.getPlaceData(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.body().getHits() != null) {
                    searchCardDataList = fillData(response.body());
                    searchRecyclerVivewAdapter = new SearchRecyclerVivewAdapter(getContext(), searchCardDataList);
                    recyclerView.setAdapter(searchRecyclerVivewAdapter);
                    itemTouchHelper.attachToRecyclerView(null);
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Log.e("onfailure",t.getLocalizedMessage());
            }
        });
    }


}
