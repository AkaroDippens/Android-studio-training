package com.example.sqliteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "footballers.db";
    static String TABLE_NAME = "Footballers";
    static String COL_ID = "id";
    static String COL_FIRST_NAME = "first_name";
    static String COL_LAST_NAME = "last_name";
    static String COL_FOOTBALL_CLUB = "football_club";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FIRST_NAME + " TEXT, " + COL_LAST_NAME + " TEXT, " + COL_FOOTBALL_CLUB + " INTEGER);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addFootballer(Footballer footballer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_FIRST_NAME, footballer.getFootballer_first_name());
        cv.put(COL_LAST_NAME, footballer.getFootballer_last_name());
        cv.put(COL_FOOTBALL_CLUB, footballer.getFootball_club());

        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<Footballer> getFootballers() {
        ArrayList<Footballer> list = new ArrayList<Footballer>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor footballers = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (footballers.moveToFirst()) {
            do {
                int id = footballers.getInt(0);
                String first_name = footballers.getString(1);
                String last_name = footballers.getString(2);
                String football_club = footballers.getString(3);

                Footballer footballer = new Footballer(id, first_name, last_name, football_club);
                list.add(footballer);
            } while (footballers.moveToNext());
        }
        footballers.close();
        return list;
    }

    public void deleteFootballer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Footballer getFootballerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID, COL_FIRST_NAME, COL_LAST_NAME, COL_FOOTBALL_CLUB};
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int footballerId = cursor.getInt(0);
            String first_name = cursor.getString(1);
            String last_name = cursor.getString(2);
            String football_club = cursor.getString(3);
            cursor.close();
            return new Footballer(footballerId, first_name, last_name, football_club);
        }
        return null;
    }

    public void updateFootballer(Footballer footballer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_FIRST_NAME, footballer.getFootballer_first_name());
        cv.put(COL_LAST_NAME, footballer.getFootballer_last_name());
        cv.put(COL_FOOTBALL_CLUB, footballer.getFootball_club());

        db.update(TABLE_NAME, cv, COL_ID + " = ?", new String[]{String.valueOf(footballer.getID_Footballer())});
    }
}

