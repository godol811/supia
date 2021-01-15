package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.HashSet;

public class MensEdit extends Dialog {
    final static String TAG = "캘린더_SQLite 선택";
    private final Context context;
    String userId;
    MaterialCalendarView materialCalendarView;
    TextView tvToday;
    Button btnInsert, btnUpdate, btnDelete;
    ImageButton btnCancle;
    String menstruationStart, menstruationEnd;
    public String CurrentStartDay, LastFinishDay;
    int year, month, dayOfMonth;
    ArrayList pickDates;
    String pickDate;

    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    private HashSet<CalendarDay> dates;


    public MensEdit(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_mens_edit);
        userId = ShareVar.sharvarUserId;

        btnInsert = findViewById(R.id.btn_insert_mensedit_calendar);
        btnUpdate = findViewById(R.id.btn_update_mensedit_calendar);
        btnDelete = findViewById(R.id.btn_delete_mensedit_calendar);
        btnCancle = findViewById(R.id.btn_cancle_mensedit_calendar);
        tvToday = findViewById(R.id.tv_today_mensedit_calendar);

        tvToday.setText("TODAY");

        //year = Integer.parseInt(String.valueOf(CalendarDay.today()));
        year = 2021;
        month = 01;
        dayOfMonth = 15;//데이트피커에서 날짜를 가져옴
        checkDay(year, month, dayOfMonth);//가져온 날짜에 하이픈을 첨가하여 "yyyy-mm-dd" 형태로 만듬

//        materialCalendarView.setOnDateChangedListener((view, year1, monthOfYear, dayOfMonth1) -> {
//            checkDay( year1, monthOfYear, dayOfMonth1);
//        });

        menstruationStart = checkDay(year, month, dayOfMonth);
        Log.v(TAG, "오오오" + menstruationStart);
        menstruationEnd = checkDay(year, month, dayOfMonth);
        Log.v(TAG, "오오오" + menstruationEnd);

        //myDBHelper = new myDBHelper(materialCalendarView.getContext());

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO supiamensterm (mStart, mEnd)VALUES ( '" + menstruationStart + "' , '" + menstruationEnd + "');");
                sqlDB.close();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE INTO supiamensterm (mStart, mEnd)VALUES ( '" + menstruationStart + "' , '" + menstruationEnd + "');");
                sqlDB.close();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE INTO supiamensterm (mStart, mEnd)VALUES ( '" + menstruationStart + "' , '" + menstruationEnd + "');");
                sqlDB.close();

            }
        });




        materialCalendarView = findViewById(R.id.mensedit_calendar);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        materialCalendarView.getCurrentDate();




    }

    public String checkDay(int cYear, int cMonth, int cDay) {
        pickDate = cYear + "-" + (cMonth + 1) + "" + "-" + cDay;
        Log.v("TAG", "오오오" + pickDate);
        return pickDate;
    }

    public class myDBHelper extends SQLiteOpenHelper {
//        public static final String DATABASE_NAME = "reminders.db";
//        public static final String T_1 = "tbl_users";
//        public static final String T1_COL_1 = "ID";
//        public static final String T1_COL_2 = "FIRST_NAME";
//        public static final String T1_COL_3 = "MIDDLE_INITIAL";
//        public static final String T1_COL_4 = "LAST_NAME";
//        public static final String T1_COL_5 = "PHONE";
//        public static final String T1_COL_6 = "EMAIL";
//        public static final String T1_COL_7 = "USERNAME";
//        public static final String T1_COL_8 = "PASSWORD";
        public myDBHelper(Context context) {
            super(context, "supia", null, 1);
        }
//
//
//        Object obj = myDBHelper..get();
//        dates = (HashSet<CalendarDay>) obj;
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE supiamensterm (mStart VAR(20), mEnd VAR(20));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS supiamensterm");
            onCreate(db);

        }

    }
}