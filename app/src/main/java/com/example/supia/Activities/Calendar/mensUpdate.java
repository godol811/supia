package com.example.supia.Activities.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.supia.R;

public class mensUpdate extends Dialog {

    private Context context;
    Button btnnext;

    public mensUpdate(Context context){
        super(context);
        this.context= context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_updata_start);

        btnnext = findViewById(R.id.btn_next_mensupdate);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensUpdate2 dialog = new mensUpdate2(getContext());
                dialog.show();
                dismiss();
            }
        });

    }
}
