package com.example.supia.Activities.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.supia.R;

public class mensUpdate2 extends Dialog {

    private Context context;
    Button btnback, btncomplite;

    public mensUpdate2(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_updata_finish);

        btnback = findViewById(R.id.btn_back_mensupdate);
        btncomplite = findViewById(R.id.btn_complite_mensupdate);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btncomplite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                dismiss();
                builder.setTitle("아래의 날짜가 맞습니까?");
                builder.setMessage("");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainCalendar.class);
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });
                builder.show();
            }
        });

    }
}