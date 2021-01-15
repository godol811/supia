package com.example.supia.Activities.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


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
    int year, monthOfYear, dayOfMonth;


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
        monthOfYear = datePicker.getMonth();
        dayOfMonth = datePicker.getDayOfMonth();//데이트피커에서 날짜를 가져옴

        checkDay(year, monthOfYear, dayOfMonth);//가져온 날짜에 하이픈을 첨가하여 "yyyy-mm-dd" 형태로 만듬

        myDBHelper = new myDBHelper(datePicker.getContext());

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            checkDay(year, monthOfYear, dayOfMonth);


            Log.v(TAG, "ㅇㅇㅇ"+menstruationStart+menstruationEnd);
        });//날짜 클릭시 데이터값 변경

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menstruationStart = pickDate;
                Log.v(TAG, "fdd"+pickDate);
                Toast.makeText(context,"시작일"+menstruationStart, Toast.LENGTH_SHORT).show();
            }

        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("아래의 날짜가 맞습니까?");
                builder.setMessage(menstruationStart+"~"+menstruationEnd);
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sqlDB = myDBHelper.getWritableDatabase();
                                sqlDB.execSQL("INSERT INTO supiamensterm (mStart, mEnd)VALUES ( '" + menstruationStart + "' , '" + menstruationEnd + "');");
                               // sqlDB.execSQL("INSERT INTO supiamensterm (mStart, mEnd)VALUES ( '2021-01-01', '2021-01-02');");
                                sqlDB.close();
                                Toast.makeText(context,"입력되었습니다.", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "입력이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                ShareVar.Updatemensfinishdate = null;
                                ShareVar.updatemensstartdate = null;
                                dismiss();
                            }
                        });
                builder.show();


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menstruationEnd = pickDate;
                Toast.makeText(context,"종료일:"+menstruationEnd, Toast.LENGTH_SHORT).show();

            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public String checkDay(int Year, int Month, int Day) {
        pickDate = Year + "-" + (Month + 1) + "" + "-" + Day;
        Log.v("TAG", "오오오" + pickDate);
        return pickDate;
    }

}


