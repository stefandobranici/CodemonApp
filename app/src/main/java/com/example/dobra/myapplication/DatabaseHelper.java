package com.example.dobra.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "game.db";
    public static final String TABLE_NAME = "map_table";
    public static final String COL_MAP_1 = "mission_id";
    public static final String COL__MAP_2 = "color_id";
    public static final String COL__MAP_3 = "chapter_id";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table "+ TABLE_NAME + " (MISSION_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLOR_ID INTEGER, CHAPTER_ID INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
