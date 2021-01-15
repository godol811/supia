package com.example.supia.Activities.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class FindPWActivity extends AppCompatActivity {

    final static String TAG = "암호찾기액티비티";
    String userId, userPw, code,userIdCheck;
    Button buttonSend, buttonCheck,buttonBack;
    EditText authEmail, authCheck;
    private String urlAddr;
    private String macIp;
    ArrayList<UserDto> userDtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        buttonCheck = findViewById(R.id.authCheckBtn);
        buttonSend = findViewById(R.id.authBtn);
        buttonBack = findViewById(R.id.btn_back_findpw);

        authEmail = findViewById(R.id.authEmail);
        authCheck = findViewById(R.id.authCheck);
        String auth = authCheck.getText().toString();


        buttonBack.setOnClickListener(new View.OnClickListener() {//뒤로 가기
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        buttonSend.setOnClickListener(new View.OnClickListener() {//이메일 보내기 버튼
            @Override
            public void onClick(View v) {
            userId = authEmail.getText().toString().trim();

                Log.d(TAG, "userId : " + userId);
                if (userId.length() != 0) {
                    connectIdCheck();//idCheck 메소드 발동.
                } else {
                    new AlertDialog.Builder(FindPWActivity.this)
                            .setTitle("이메일을 입력하세요!")
                            .setMessage("이메일을 입력하세요!.")
                            .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                            .setPositiveButton("닫기", null)
                            .show();
                }
            }
        });


      buttonCheck.setOnClickListener(new View.OnClickListener() {//인증번호 비교 대조 확인
          @Override
          public void onClick(View v) {
              Log.d("확인",code);
              Log.d("입력",authCheck.getText().toString());
              if(authCheck.getText().toString().trim().length() ==0) {

                  new AlertDialog.Builder(FindPWActivity.this)
                          .setTitle("인증번호 확인")
                          .setMessage("인증번호를 입력하셔야 암호를 바꾸실 수 있습니다.")
                          .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                          .setPositiveButton("닫기", null)
                          .show();
              }
             else if(authCheck.getText().toString().equals(code))//이메일 코드랑 맞는 경우에는 이메일 재설정 페이지로 이동
              {
                  Intent intent = new Intent(FindPWActivity.this,RefactorPassWordActivity.class);
                  intent.putExtra("userId",userId);
                  startActivity(intent);
              }else{
                  new AlertDialog.Builder(FindPWActivity.this)
                          .setTitle("인증번호 확인")
                          .setMessage("올바른 인증번호를 입력해주세요.")
                          .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                          .setPositiveButton("닫기", null)
                          .show();
              }

          }
      });

    }


    //-------------------------------아이디 체크 메소드 ----------------------------------------------//


    private void connectIdCheck() {
        Log.v(TAG, "connectGetData()");
        try {
            userId = authEmail.getText().toString().trim();
            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserIdCheck.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userId=" + userId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            UserInfoNetworkTask networkTask = new UserInfoNetworkTask(FindPWActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userDtos = (ArrayList<UserDto>) obj; //userInfoDtos 다시 풀기


            if (userDtos.isEmpty()) {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고 (없는 값이기 때문에 불러와지지 않으므로
                new AlertDialog.Builder(FindPWActivity.this)
                        .setTitle("이메일 없음")
                        .setMessage("올바른 이메일을 입력해주세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
            } else {
                userIdCheck = userDtos.get(0).getUserId();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.)
                Log.d(TAG, userIdCheck);
                new AlertDialog.Builder(FindPWActivity.this)
                        .setTitle("이메일을 보냈습니다.!")
                        .setMessage("인증번호를 확인해주세요!")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();

                SendMail sendMail = new SendMail();
                userId = authEmail.getText().toString().trim();
                code = sendMail.sendSecurityCode(FindPWActivity.this, userId);//ID 암호 보내는 메소드
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

//-----------------------------------아이디 체크끝 ---------------------------------------------------------//

    //--------------------------------------애정 추가  배경 터치 시 키보드 사라지게----------------------------------//
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm;
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    //---------------------------------------------------------------------------------------------//


}