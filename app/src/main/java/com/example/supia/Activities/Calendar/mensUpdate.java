package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.supia.Activities.Login.UserDataQuestion2;
import com.example.supia.Activities.Login.UserDataQuestion3;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class mensUpdate extends Dialog {

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

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShareVar.updatemensstartdate = datePicker.get;

                mensUpdate2 dialog = new mensUpdate2(getContext());
                dialog.show();
                dismiss();
            }
        });

    }
}
