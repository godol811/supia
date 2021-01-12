package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;

import com.example.supiatotal.R;

public class datePicker extends Dialog {
    final static String TAG = "캘린더_월경일선택_다이얼로그";
    private String urlAddr;
    private String macIp;
    private Context context;

    Button btnInsert, btnUpdate, btnDelete;
    ImageButton btnCancle;
    DatePicker datePicker;
    String pickDate;
    String menstruationStart, menstruationEnd;
    int year, month, dayOfMonth;


    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;


    public datePicker(Context context) {
        super(context);
        this.context = context;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_datepicker_calendarpage);


        btnInsert = findViewById(R.id.btn_insert_datepicker_calendar);
        btnUpdate = findViewById(R.id.btn_update_datepicker_calendar);
        btnDelete = findViewById(R.id.btn_delete_datepicker_calendar);
        btnCancle = findViewById(R.id.btn_cancle_datepicker_calendar);
        datePicker = findViewById(R.id.datepicker_calendar);


        year = datePicker.getYear();
        month = datePicker.getMonth();
        dayOfMonth = datePicker.getDayOfMonth();//데이트피커에서 날짜를 가져옴
        checkDay(year, month, dayOfMonth);//가져온 날짜에 하이픈을 첨가하여 "yyyy-mm-dd" 형태로 만듬

        menstruationStart = checkDay(year, month, dayOfMonth);
        Log.v(TAG, "aa" + menstruationStart);
        menstruationEnd = checkDay(year, month, dayOfMonth);

        myDBHelper = new myDBHelper(datePicker.getContext());

        datePicker.setOnDateChangedListener((view, year1, monthOfYear, dayOfMonth1) -> {
            checkDay(year1, monthOfYear, dayOfMonth1);
        });//날짜 클릭시 데이터값 변경


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

            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public String checkDay(int cYear, int cMonth, int cDay) {
        pickDate = cYear + "-" + (cMonth + 1) + "" + "-" + cDay;
        Log.v("TAG", "오오오" + pickDate);
        return pickDate;
    }


    public class myDBHelper extends SQLiteOpenHelper {
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


}

