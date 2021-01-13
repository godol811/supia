package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;
import java.util.HashSet;

public class MensEdit extends Dialog {
    final static String TAG = "캘린더_월경일선택_material";
    private final Context context;
    MaterialCalendarView materialCalendarView;
    TextView textView;
    ImageButton cancle;
    Button btnInsert, btnUpdate, btnDelete;
    ImageButton btnCancle;
    String menstruationStart, menstruationEnd;

    Date today,date1, date2;

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

        btnInsert = findViewById(R.id.btn_insert_datepicker_calendar);
        btnUpdate = findViewById(R.id.btn_update_datepicker_calendar);
        btnDelete = findViewById(R.id.btn_delete_datepicker_calendar);
        btnCancle = findViewById(R.id.btn_cancle_datepicker_calendar);

        cancle = findViewById(R.id.btn_cancle_mensedit_calendar);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        materialCalendarView = findViewById(R.id.mensedit_calendar);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        materialCalendarView.getCurrentDate();
        materialCalendarView.getSelectedDates();



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