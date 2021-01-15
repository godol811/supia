package com.example.supia.Activities.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class myDBHelper extends SQLiteOpenHelper {
    public static String TAG = "데이터베이스핼퍼_SQLite";

    SQLiteDatabase db;

    public static final String DATABASE_NAME = "supia.db";
    public static final String T_1 = "supiamensterm";
    public static final String T1_COL_1 = "mStart";
    public static final String T1_COL_2 = "mEnd";
//    public myDBHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase database = this.getWritableDatabase();
//        Log.v(TAG, "생성자 호출");
//    }

    void DBSearch(String tableName) {
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String age = cursor.getString(cursor.getColumnIndex("AGE"));
                    String phone = cursor.getString(cursor.getColumnIndex("PHONE"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", age: " + age + ", phone: " + phone);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // SELECT * FROM People WHERE age < "age" ORDER BY NAME
    void DBSearch(String tableName, Integer age) {
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, "AGE" + " < ?", new String[]{age.toString()}, null, null, "NAME");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String age2 = cursor.getString(cursor.getColumnIndex("AGE"));
                    String phone = cursor.getString(cursor.getColumnIndex("PHONE"));

                    Log.d(TAG, "id: " + id + ", name: " + name + ", age: " + age2 + ", phone: " + phone);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getAllDates() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + T_1, null);
        database.close();
        return cursor;
    }

    public myDBHelper(Context context) {
        super(context, "supia", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE supiamensterm (mStart DATE(20), mEnd DATE(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS supiamensterm");
        onCreate(db);

    }
//    public void displayUsers() {
//        Cursor cursor = myDBHelper.getAllDates(); //here's where the error keeps on happening
//        if(cursor.getCount() == 0) {
//            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//        }
//    }
}

