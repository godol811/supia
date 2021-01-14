package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class mensUpdate extends Dialog {

    final static String TAG = "캘린더정_마이에큐엘_월경일수_material";

    private Context context;
    Button btnnext;
    DatePicker datePicker;

    public mensUpdate(Context context){
        super(context);
        this.context= context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_updata_start);

        btnnext = findViewById(R.id.btn_next_mensupdate);
        datePicker = findViewById(R.id.maincalendar_mens_start_update);
        String date = Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());
        Log.v(TAG, date);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareVar.updatemensstartdate = date;
                Log.v(TAG, "dddd"+ShareVar.updatemensstartdate);

                mensUpdate2 dialog = new mensUpdate2(getContext());
                dialog.show();
                dismiss();
            }
        });

    }
}
