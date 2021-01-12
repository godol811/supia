package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.R;


public class UserDataQuestion6 extends Activity {

    Button btnstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question6);

        btnstart = findViewById(R.id.btn_next_question6);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDataQuestion6.this, MainCalendar.class);
                startActivity(intent);
            }
        });



    }
}