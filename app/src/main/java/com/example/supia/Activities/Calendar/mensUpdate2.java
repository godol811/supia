package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.supia.R;

public class mensUpdate2 extends Dialog {

    private Context context;
    Button btnnext;

    public mensUpdate2(Context context){
        super(context);
        this.context= context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_updata_finish);
    }
}
