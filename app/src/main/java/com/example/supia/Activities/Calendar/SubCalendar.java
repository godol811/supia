package com.example.supia.Activities.Calendar;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.supia.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class SubCalendar extends FragmentActivity {

    MaterialCalendarView materialCalendarView;
    Button btnedit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_subcalendar);

        materialCalendarView =findViewById(R.id.subcalendar_calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        btnedit = findViewById(R.id.btn_edit_subcalendar);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker dialog = new datePicker(SubCalendar.this);
                dialog.show();
            }
        });
    }

}
