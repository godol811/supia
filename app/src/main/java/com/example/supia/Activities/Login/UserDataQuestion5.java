package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.supia.R;


public class UserDataQuestion5 extends Activity {

    Button buttonIndex1, buttonIndex2, buttonIndex3,buttonIndex4,buttonNext;
    final static String TAG = "다섯번째 질문";
    String userId;
    String userStyle,strUserPurchaseStyle;
    String[] purchaseStyle = {"","","",""};
    private String urlAddr;
    private String macIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_question5);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userStyle = intent.getStringExtra("userStyle");




        buttonIndex1 = findViewById(R.id.btn_price_question5);
        buttonIndex2 = findViewById(R.id.btn_organic_question5);
        buttonIndex3 = findViewById(R.id.btn_cotton_question5);
        buttonIndex4 = findViewById(R.id.btn_comport_question5);
        buttonNext = findViewById(R.id.btn_next_question5);

        buttonIndex1.setOnClickListener(mOnclickListener);
        buttonIndex2.setOnClickListener(mOnclickListener);
        buttonIndex3.setOnClickListener(mOnclickListener);
        buttonIndex4.setOnClickListener(mOnclickListener);
        buttonNext.setOnClickListener(mOnclickListener);
    }


    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_price_question5:
                    if (buttonIndex1.getTag().equals("0")) {//흰색 버튼
                        buttonIndex1.setTag("1");
                        buttonIndex1.setBackgroundColor(Color.parseColor("#BAD23A"));
                    } else {
                        buttonIndex1.setTag("0");
                        buttonIndex1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }break;
                case R.id.btn_organic_question5:
                    if (buttonIndex2.getTag().equals("0")) {//흰색 버튼
                        buttonIndex2.setTag("1");
                        buttonIndex2.setBackgroundColor(Color.parseColor("#BAD23A"));
                    } else {
                        buttonIndex2.setTag("0");
                        buttonIndex2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }break;

                case R.id.btn_cotton_question5:
                    if (buttonIndex3.getTag().equals("0")) {//흰색 버튼
                        buttonIndex3.setTag("1");
                        buttonIndex3.setBackgroundColor(Color.parseColor("#BAD23A"));
                    } else {
                        buttonIndex3.setTag("0");
                        buttonIndex3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }break;

                case R.id.btn_comport_question5:
                    if (buttonIndex4.getTag().equals("0")) {//흰색 버튼
                        buttonIndex4.setTag("1");
                        buttonIndex4.setBackgroundColor(Color.parseColor("#BAD23A"));
                    } else {
                        buttonIndex4.setTag("0");
                        buttonIndex4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }break;


                case R.id.btn_next_question5:

                    if (buttonIndex1.getTag().equals("1")){
                        purchaseStyle[0] = "가격";
                    }else{
                        purchaseStyle[0] ="";
                    }
                    if(buttonIndex2.getTag().equals("1")){
                        purchaseStyle[1] = "유기농";
                    }else{
                        purchaseStyle[1] ="";

                    }
                    if(buttonIndex3.getTag().equals("1")){
                        purchaseStyle[2] = "순면";
                    }else{
                        purchaseStyle[2] ="";
                    }
                    if(buttonIndex4.getTag().equals("1")){
                        purchaseStyle[3] = "편리함";
                    }else{
                        purchaseStyle[3] ="";
                    }

                    for (int i=0;i<purchaseStyle.length;i++){
                        strUserPurchaseStyle += ","+purchaseStyle[i];
                    }

                    userPreferInsert();
                    Log.d(TAG,strUserPurchaseStyle);
                    Intent intent = new Intent(UserDataQuestion5.this,UserDataQuestion6.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);


                    break;


            }
        }
    };


    private void userPreferInsert() {


//        macIp = ShareVar.urlIp;
//        urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserStyleUpdate.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
//        urlAddr = urlAddr + "userId=" + userId + "&userStyle=" + userStyle + "&userPurchaseStyle=" + strUserPurchaseStyle;
//
//
//        try {
//            UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(UserDataQuestion5.this, urlAddr,"insert");
//            insertworkTask.execute().get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}