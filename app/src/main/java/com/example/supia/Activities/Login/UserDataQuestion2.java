package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


public class UserDataQuestion2 extends Activity {
    final static String TAG = "두번째 질문";
    Button buttonNext;
    DatePicker datePicker;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question2);

        Intent intent = getIntent();
        userId = ShareVar.sharvarUserId;
        String menstruationStart = intent.getStringExtra("menstruationStart");
        datePicker = findViewById(R.id.dp_question2);

        buttonNext = findViewById(R.id.btn_next_question2);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());
                Log.d(TAG,date);
                Intent intent1 = new Intent(UserDataQuestion2.this, UserDataQuestion3.class);
                intent1.putExtra("menstruationEnd",date);
                intent1.putExtra("menstruationStart",menstruationStart);
                intent1.putExtra("userId",userId);
                startActivity(intent1);

            }
        });
    }
}