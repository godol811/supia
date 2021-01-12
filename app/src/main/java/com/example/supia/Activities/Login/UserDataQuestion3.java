package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


public class UserDataQuestion3 extends Activity {

    final static String TAG = "세번째 질문";
    String userId,menstruationStart,menstruationEnd,birthday;
    private String urlAddr;
    private String macIp;

    Button buttonNext;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question3);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        menstruationStart = intent.getStringExtra("menstruationStart");
        menstruationEnd = intent.getStringExtra("menstruationEnd");
        datePicker = findViewById(R.id.dp_question3);

        buttonNext = findViewById(R.id.btn_next_question3);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                birthday = Integer.toString(datePicker.getYear()) + "-" + Integer.toString(datePicker.getMonth() + 1) + "-" + Integer.toString(datePicker.getDayOfMonth());
                Log.d(TAG, birthday);
                calendarInsert();
                Intent intent1 = new Intent(UserDataQuestion3.this, UserDataQuestion4.class);
                intent1.putExtra("userId", userId);
                startActivity(intent1);

            }
        });


    }

    private void calendarInsert() {


        macIp = ShareVar.urlIp;
        urlAddr = "http:/" + macIp + ":8080/test/supiaCalendarInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
        urlAddr = urlAddr + "userId=" + userId + "&calendarStartDate=" + menstruationStart + "&calendarFinishDate=" + menstruationEnd + "&calendarBirthDate=" + birthday;


        try {
            UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(UserDataQuestion3.this, urlAddr,"insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}