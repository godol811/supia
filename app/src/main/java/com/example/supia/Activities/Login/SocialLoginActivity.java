package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import com.kakao.sdk.template.model.Social;

import java.util.ArrayList;

public class SocialLoginActivity extends Activity {

    final static String TAG = "소셜회원가입";
    EditText userAddrDetail, userTel;

    TextView userAddr, userId;
    ArrayList<UserDto> userDtos;
    private String urlAddr;
    private String macIp;
    private String userIdCheck;
    private int totalCheck = 0;
    CheckBox agreementCheckBox;
    Button buttonSocialSignUp;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        connectIdCheck();//전에 상세정보 등록했는지 파악하는 메소드


        buttonSocialSignUp = findViewById(R.id.signUpButton_signup_social);
        buttonSocialSignUp.setOnClickListener(mOnclickListener);
        userId = findViewById(R.id.userid_social);
        userTel = findViewById(R.id.et_telnumber_social);
        userAddr = findViewById(R.id.tv_address_social);
        userAddrDetail = findViewById(R.id.et_address_detail_social);
        agreementCheckBox = findViewById(R.id.Agreement_social);

        Intent intent = getIntent();
        userId.setText(ShareVar.sharvarUserId);


        //---------------------------------------------------주소API--------------------------------------------//
        userAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SocialLoginActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        userAddr.setText(data);
                    }
                }
                break;
        }
    }//end onCreate

    //----------------------------------------------------빈칸 체크--------------------------------------//
    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strAddr = userAddr.getText().toString().trim();
            String strAddrDetail = userAddrDetail.getText().toString().trim();
            String strTel = userTel.getText().toString().trim();

            if (strAddr.length() != 0 && strAddrDetail.length() != 0 && strTel.length() != 0 && agreementCheckBox.isChecked()) {
                moveToQuestion();


            } else if (!agreementCheckBox.isChecked()) {
                new AlertDialog.Builder(SocialLoginActivity.this)
                        .setTitle("약관에 동의해주세요")
                        .setMessage("약관에 동의해주세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();

            } else if (strAddr.length() == 0) {

                new AlertDialog.Builder(SocialLoginActivity.this)
                        .setTitle("주소를 입력하세요")
                        .setMessage("주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userAddr.setFocusable(true);

            } else if (userAddrDetail.getText().toString().trim().length() == 0) {

                new AlertDialog.Builder(SocialLoginActivity.this)
                        .setTitle("상세주소를 입력하세요")
                        .setMessage("상세주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userAddrDetail.setFocusable(true);

            } else if (strTel.length() == 0) {

                new AlertDialog.Builder(SocialLoginActivity.this)
                        .setTitle("전화번호를 입력하세요")
                        .setMessage("전화번호를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userTel.setFocusable(true);
            } else {

                new AlertDialog.Builder(SocialLoginActivity.this)
                        .setTitle("전화번호와 주소를 입력하세요")
                        .setMessage("전화번호와 주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userTel.setFocusable(true);
            }
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void moveToQuestion() {
        if (userAddr.getText() != null) {
            String strId = userId.getText().toString().trim();
            String strTel = userTel.getText().toString().trim();
            String strAddr = userAddr.getText().toString().trim();
            String strAddrDetail = userAddrDetail.getText().toString().trim();

            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserSocialUpdate.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
            urlAddr = urlAddr + "userId=" + strId + "&userTel=" + strTel + "&userAddr=" + strAddr + "&userAddrDetail=" + strAddrDetail ;

            Log.v(TAG, urlAddr);
            try {
                UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(SocialLoginActivity.this, urlAddr,"insert");
                insertworkTask.execute().get();


            } catch (Exception e) {
                e.printStackTrace();
            }
            new AlertDialog.Builder(SocialLoginActivity.this)
                    .setTitle("추가정보 등록 완료!")
                    .setMessage("취향 선택 페이지로 넘어갈게요!")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SocialLoginActivity.this, UserDataQuestion1.class);
                            intent.putExtra("userId", strId);
                            startActivity(intent);

                        }
                    })
                    .show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void connectIdCheck() {
        Log.v(TAG, "connectGetData()");
        try {
            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserIdCheck.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userId=" + ShareVar.sharvarUserId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            UserInfoNetworkTask networkTask = new UserInfoNetworkTask(SocialLoginActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userDtos = (ArrayList<UserDto>) obj; //userInfoDtos 다시 풀기


            String strUserAddr = userDtos.get(0).getUserAddr();
            Log.d(TAG,Integer.toString(strUserAddr.trim().length()));
            if(!strUserAddr.equals("null")){
                Intent intent = new Intent(SocialLoginActivity.this, UserDataQuestion1.class);
                startActivity(intent);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

