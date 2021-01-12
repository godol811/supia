package com.example.supia.Activities.Calendar;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashSet;

public class SubCalendar extends FragmentActivity {

    public String urlAddr,urlIp,userId;
    MaterialCalendarView materialCalendarView;
    Button btnedit;

    private HashSet<CalendarDay> dates;

    public static String TAG ="서브캘린더";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_subcalendar);

        userId = ShareVar.sharvarUserId;//사용자 아이디를 받아옴
        urlIp = ShareVar.urlIp;//아이피 받아옴
        urlAddr = "http://"+urlIp+":8080/test/supiaCalendarSelectMens.jsp";//jsp주소
        urlAddr = urlAddr + "?userId="+ userId;

        materialCalendarView =findViewById(R.id.subcalendar_calendarView);

        btnedit = findViewById(R.id.btn_edit_subcalendar);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker dialog = new datePicker(SubCalendar.this);
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {

            CalendarNetworkTask networkTask = new CalendarNetworkTask(SubCalendar.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            dates = (HashSet<CalendarDay>) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
