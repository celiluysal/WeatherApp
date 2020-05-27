package com.example.weatherapp.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weatherapp.SearchCardData;

import java.util.ArrayList;
import java.util.List;

public class SearchDao {
    public void addSearch(SearchDatabaseHelper helper ,SearchCardData data){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM places WHERE " +
                "lat= '" + data.getLat() +"' AND " +
                "lon= '" + data.getLon() +"'",null);

        if (cursor.moveToNext()){
            Log.e("hata",data.getLocaleName()+", "+data.getCounty()+", "+
                    data.getAdministrative()+", "+data.getCountry()+" -- Bu kayıt mevcut");
        }
        else {
            ContentValues values = new ContentValues();
            values.put("localName", data.getLocaleName());
            values.put("county", data.getCounty());
            values.put("administrative", data.getAdministrative());
            values.put("country", data.getCountry());
            values.put("lat", data.getLat());
            values.put("lon", data.getLon());

            sqLiteDatabase.insertOrThrow("places", null, values);
            sqLiteDatabase.close();
        }
    }

    public List<SearchCardData> allSearches(SearchDatabaseHelper helper){
        List<SearchCardData> searchCardDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM places ORDER BY place_id DESC",null);

        while (cursor.moveToNext()){
            SearchCardData data = new SearchCardData();
            data.setLocaleName(cursor.getString(cursor.getColumnIndex("localName")));
            data.setCounty(cursor.getString(cursor.getColumnIndex("county")));
            data.setAdministrative(cursor.getString(cursor.getColumnIndex("administrative")));
            data.setCountry(cursor.getString(cursor.getColumnIndex("country")));
            data.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
            data.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));

            searchCardDataList.add(data);
        }
        return searchCardDataList;

    }

    public void deleteSearch(SearchDatabaseHelper helper,Double lat,Double lon){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        sqLiteDatabase.delete("places","lat=? and lon=?",
                new String[]{String.valueOf(lat), String.valueOf(lon)});
        sqLiteDatabase.close();
    }


}
