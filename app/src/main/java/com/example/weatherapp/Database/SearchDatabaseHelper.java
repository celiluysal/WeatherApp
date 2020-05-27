package com.example.weatherapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SearchDatabaseHelper extends SQLiteOpenHelper {
    public SearchDatabaseHelper(@Nullable Context context) {
        super(context, "search_history", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE places (place_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "localName TEXT,county TEXT,administrative TEXT,country TEXT," +
                "lat REAL, lon REAL);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS places");
        onCreate(db);
    }
}
