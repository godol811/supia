package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


public class UserDataQuestion4 extends Activity {

    Button buttonIndex1, buttonIndex2, buttonIndex3, buttonNext;
    final static String TAG = "네번째 질문";
    String userId, strStyle;
    String[] style = {"", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question4);


        Intent intent = getIntent();
        userId = ShareVar.sharvarUserId;

        buttonIndex1 = findViewById(R.id.btn_sanitarypad_question4);
        buttonIndex2 = findViewById(R.id.btn_tampon_question4);
        buttonIndex3 = findViewById(R.id.btn_menstrualcup_question4);
        buttonNext = findViewById(R.id.btn_next_question4);

        buttonIndex1.setOnClickListener(mOnclickListener);
        buttonIndex2.setOnClickListener(mOnclickListener);
        buttonIndex3.setOnClickListener(mOnclickListener);
        buttonNext.setOnClickListener(mOnclickListener);

    }


    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_sanitarypad_question4:

                    if (buttonIndex1.getTag().equals("0")) {//흰색 버튼
                        Log.d(TAG,buttonIndex1.getTag().toString());
                        buttonIndex1.setTag("1");
                        buttonIndex1.setBackgroundColor(Color.parseColor("#BAD23A"));
                        break;
                    } else {
                        Log.d(TAG,buttonIndex1.getTag().toString());
                        buttonIndex1.setTag("0");

                        buttonIndex1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    }

                case R.id.btn_tampon_question4:
                    if (buttonIndex2.getTag().equals("0")) {//흰색 버튼
                        buttonIndex2.setTag("1");
                        buttonIndex2.setBackgroundColor(Color.parseColor("#BAD23A"));
                        break;
                    } else {
                        buttonIndex2.setTag("0");
                        buttonIndex2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    }


                case R.id.btn_menstrualcup_question4:
                    if (buttonIndex3.getTag().equals("0")) {//흰색 버튼
                        buttonIndex3.setTag("1");
                        buttonIndex3.setBackgroundColor(Color.parseColor("#BAD23A"));
                        break;
                    } else {
                        buttonIndex3.setTag("0");
                        buttonIndex3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        break;
                    }



                case R.id.btn_next_question4:
                    if (buttonIndex1.getTag().equals("1")) {
                        style[0] = "생리대";
                    } else {
                        style[0] = "";
                    }
                    if (buttonIndex2.getTag().equals("1")) {
                        style[1] = "탐폰";
                    } else {
                        style[1] = "";
                    }
                    if (buttonIndex3.getTag().equals("1")) {
                        style[2] = "생리컵";
                    } else {
                        style[2] = "";
                    }

                    for (int i = 0; i < style.length; i++) {
                        strStyle += "," + style[i];
                    }
                    Log.d(TAG, strStyle);
                    Intent intent = new Intent(UserDataQuestion4.this, UserDataQuestion5.class);
                    intent.putExtra("userStyle", strStyle);
                    intent.putExtra("userId", userId);
                    startActivity(intent);

                    break;


            }
        }
    };
}