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


public class UserDataQuestion1 extends Activity {

    final static String TAG = "첫번째 질문";
    Button buttonNext;
    DatePicker datePicker;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question1);

        Intent intent = getIntent();
        userId = ShareVar.sharvarUserId;
        datePicker = findViewById(R.id.dp_question1);





        buttonNext = findViewById(R.id.btn_next_question1);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());
                Log.d(TAG,date);
                Intent intent1 = new Intent(UserDataQuestion1.this,UserDataQuestion2.class);
                intent1.putExtra("menstruationStart",date);
                intent1.putExtra("userId",userId);
                startActivity(intent1);

            }
        });



    }




}