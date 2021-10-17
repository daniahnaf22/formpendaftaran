package com.daniahnaf.formpendaftan.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ujian.db";
    private static final int DATABASE_VERSION = 2;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NOHP = "nohp";
    private static final String COLUMN_JK = "jk";
    private String TABLE_SQLite = "sqllite";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_NOHP + " TEXT NOT NULL, " +
                COLUMN_JK + " TEXT NOT NULL " +
                ")";
        db.execSQL(SQL_CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> dataList;
        dataList = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAMA, cursor.getString(1));
                map.put(COLUMN_ADDRESS, cursor.getString(2));
                map.put(COLUMN_NOHP, cursor.getString(3));
                map.put(COLUMN_JK, cursor.getString(4));
                dataList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("select sqlite", "" + dataList);
        database.close();
        return dataList;
    }

    public void insert(String name, String address, String nohp, String jk) {
        SQLiteDatabase database = this.getWritableDatabase();
        String update = "INSERT INTO " + TABLE_SQLite + " (name, address, nohp, jk) " +
                "VALUES ('" + name + "', '" + address + "', '" + nohp + "', '" + jk + "')";
        Log.e("insert sqlite", "" + update);
        database.execSQL(update);
        database.close();
    }

    public void update(int id, String nohp, String name, String address, String jk) {
        SQLiteDatabase database = this.getWritableDatabase();
        String update = "UPDATE " + TABLE_SQLite + " SET " +
                COLUMN_NAMA + "='" + name + "', " +
                COLUMN_ADDRESS + "='" + address + "'" +
                COLUMN_NOHP + "='" + nohp + "'" +
                COLUMN_JK + "='" + jk + "'" +
                " WHERE " + COLUMN_ID + " ='" + id + "'";
        Log.e("update sqlite", "" + update);
        database.execSQL(update);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String delete = "DELETE FROM " + TABLE_SQLite +
                " WHERE " + COLUMN_ID + " ='" + id + "'";
        Log.e("update sqlite", "" + delete);
        database.execSQL(delete);
        database.close();

    }
}