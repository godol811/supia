package com.example.supia.Activities.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class myDBHelper extends SQLiteOpenHelper {
    public static String TAG = "데이터베이스핼퍼_SQLite";

    public static final String DATABASE_NAME = "supia.db";
    public static final String T_1 = "supiamensterm";
    public static final String T1_COL_1 = "mStart";
    public static final String T1_COL_2 = "mEnd";
//    public myDBHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase database = this.getWritableDatabase();
//        Log.v(TAG, "생성자 호출");
//    }
//    public Cursor getAllDates() {
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery("select * from " + T_1, null);
//        database.close();
//        return cursor;
//    }
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
}
