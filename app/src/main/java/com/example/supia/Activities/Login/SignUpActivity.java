package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity {

    final static String TAG = "회원가입";
    EditText userinfoId, userinfoPw, userinfoPwCheck, userinfoAddrDetail, userinfoTel;

    TextView pwCheckAlert, userinfoAddr;
    ArrayList<UserDto> userDtos;
    private String urlAddr;
    private String macIp;
    private String userIdCheck;
    private int totalCheck = 0;
    CheckBox agreementCheckBox;
    ImageView buttonPassword;
    Button buttonIdCheck, buttonSignUp;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //button
        buttonSignUp = findViewById(R.id.signUpButton_signup);
        buttonIdCheck = findViewById(R.id.btnIdCheck_signup);
        buttonPassword = findViewById(R.id.btn_password);
        //textview
        pwCheckAlert = findViewById(R.id.pwCheckAlert);
        //editText
        userinfoAddr = findViewById(R.id.et_address);
        userinfoAddrDetail = findViewById(R.id.et_address_detail);
        userinfoTel = findViewById(R.id.textTelNumber);
        userinfoId = findViewById(R.id.textId);
        userinfoPw = findViewById(R.id.textPassword);
        userinfoPwCheck = findViewById(R.id.textPasswordCheck);
        //checkbox
        agreementCheckBox = findViewById(R.id.Agreement);


        buttonSignUp.setOnClickListener(mOnclickListener);


//------------------------------------------------중복 아이디 체크------------------------------------------//

        buttonIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strId = userinfoId.getText().toString().trim();
                Log.d(TAG, "strid : " + strId);
                if (strId.length() != 0) {
                    connectIdCheck();//idCheck 메소드 발동.

                } else {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("이메일을 입력하세요!")

                            .setMessage("이메일을 입력하세요!.")
                            .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                            .setPositiveButton("닫기", null)
                            .show();
                }

            }
        });
        //---------------------------------------------------전화번호 입력--------------------------------------------//

        userinfoTel.addTextChangedListener(new TextWatcher() {//자동으로 "-" 생성해서 카드번호에 붙여주기
            private int beforeLenght = 0;
            private int afterLenght = 0;

            //입력 혹은 삭제 전의 길이와 지금 길이를 비교하기 위해 beforeTextChanged에 저장
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //아무글자도 없는데 지우려고 하면 로그띄우기 에러방지
                if (s.length() <= 0) {
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Length)");
                    return;
                }
                //특수문자 입력 방지
                char inputChar = s.charAt(s.length() - 1);
                if (inputChar != '-' && (inputChar < '0' || inputChar > '9')) {
                    userinfoTel.getText().delete(s.length() - 1, s.length());
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Number)");
                    return;
                }
                afterLenght = s.length();
                String tel = String.valueOf(userinfoTel.getText());
                tel.substring(0, 1);
                if (beforeLenght < afterLenght) {// 타자를 입력 중이면
                    if (afterLenght == 3) { //subSequence로 지정된 문자열을 반환해서 "-"폰을 붙여주고 substring
                        userinfoTel.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                    } else if (afterLenght == 8) {
                        userinfoTel.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                    }
                }
                userinfoTel.setSelection(userinfoTel.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });


//------------------------------------------------눈 보이기------------------------------------------//
        buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPassword.getTag().equals("0")) {//비밀번호 안보이고 있는 상황
                    buttonPassword.setTag("1");
                    buttonPassword.setImageResource(R.drawable.openeye);
                    userinfoPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    userinfoPwCheck.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    buttonPassword.setTag("0");
                    buttonPassword.setImageResource(R.drawable.closedeye);
                    userinfoPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userinfoPwCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

//---------------------------------------------------주소API--------------------------------------------//
        userinfoAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }//onCreate

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        userinfoAddr.setText(data);
                    }
                }
                break;
        }


//---------------------------------------------------암호체크용--------------------------------------------//
        userinfoPwCheck.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strUserinfoPw = userinfoPw.getText().toString();
                Log.d("하하", s + "," + count + "," + strUserinfoPw);
                if (s != null) {
                    if (s.toString().equals(strUserinfoPw)) {//암호 확인 체크 하기

                        pwCheckAlert.setText("암호가 일치합니다.");
                    } else {

                        pwCheckAlert.setText("암호가 일치하지 않습니다.");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//---------------------------------------------------암호체크용--------------------------------------------//
    }//onCreate

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String strTel = userinfoTel.getText().toString().trim();
            String strAddr = userinfoAddr.getText().toString().trim() + userinfoAddrDetail.getText().toString().trim();
            String strPw = userinfoPw.getText().toString().trim();
            String strPwCheck = userinfoPwCheck.getText().toString().trim();

            if (strTel.length() != 0 && strAddr.length() != 0 && strPw.equals(strPwCheck) && agreementCheckBox.isChecked() && isValidEmail(userinfoId.getText().toString()) == true && isValidPassword(strPw) == true) {

                Log.d("입력체크", "토탈체크 " + totalCheck);
                buttonEnable();

            } else if (!agreementCheckBox.isChecked()) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("약관에 동의해주세요")
                        .setMessage("약관에 동의해주세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();

            } else if (strAddr.length() == 0) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("주소를 입력하세요")
                        .setMessage("주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoAddr.setFocusable(true);

            } else if (userinfoAddrDetail.getText().toString().trim().length() == 0) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("상세주소를 입력하세요")
                        .setMessage("상세주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoAddrDetail.setFocusable(true);

            } else if (strTel.length() == 0) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("전화번호를 입력하세요")
                        .setMessage("전화번호를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoTel.setFocusable(true);
            } else if (!strPw.equals(strPwCheck)) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("같은 암호를 입력하세요")
                        .setMessage("같은 암호를 입력하세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoPw.setFocusable(true);
            } else if (strPwCheck.length() == 0) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("암호 확인을 채워주세요")
                        .setMessage("암호 확인을 채워주세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoPwCheck.setFocusable(true);

            } else if (strPw.length() == 0) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("암호를 입력해주세요")
                        .setMessage("암호를 입력해주세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoPw.setFocusable(true);
            } else if (isValidEmail(userinfoId.getText().toString()) == false) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일을 입력해주세요")
                        .setMessage("이메일의 양식에 맞게 입력해주세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoId.setFocusable(true);
            } else if (isValidPassword(strPw) == false) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("암호형식 확인")
                        .setMessage("숫자와 문자 포함하여 6~12 자리의 암호를 입력해주세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoId.setFocusable(true);

            } else {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("전화번호와 주소를 입력하세요")
                        .setMessage("전화번호와 주소를 입력하세요.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoTel.setFocusable(true);
            }
        }
    };
   /*
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
   METHOD
    */
    //-------------------------------아이디 체크 메소드 ----------------------------------------------//


    private void connectIdCheck() {
        Log.v(TAG, "connectGetData()");
        try {
            String strId = userinfoId.getText().toString();
            macIp = "192.168.35.147";
            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserIdCheck.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
            urlAddr = urlAddr + "userId=" + strId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            UserInfoNetworkTask networkTask = new UserInfoNetworkTask(SignUpActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userDtos = (ArrayList<UserDto>) obj; //userInfoDtos 다시 풀기


            if (isValidEmail(strId) == false) {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고 (없는 값이기 때문에 불러와지지 않으므로
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일을 입력해주세요")
                        .setMessage("이메일의 양식에 맞게 입력해주세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                totalCheck = 1;
            } else if (userDtos.isEmpty()) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일 중복확인 결과!")
                        .setMessage("사용가능한 이메일 입니다.")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoId.setFocusable(true);
            } else {
                userIdCheck = userDtos.get(0).getUserId();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.
                Log.d(TAG, userIdCheck);
//            Log.d(TAG,userPwCheck);
                totalCheck = 0;
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("이메일 중복확인 결과!")
                        .setMessage("다른 이메일을 입력하세요")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();
                userinfoId.setFocusable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//-----------------------------------아이디 체크끝 ---------------------------------------------------------//

    //버튼 먹히
    private void buttonEnable() {
        if (totalCheck == 1) {
            String strId = userinfoId.getText().toString().trim();
            String strPw = userinfoPw.getText().toString().trim();
            String strTel = userinfoTel.getText().toString().trim();
            String strAddr = userinfoAddr.getText().toString().trim();
            String strAddrDetail = userinfoAddrDetail.getText().toString().trim();

            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
            urlAddr = urlAddr + "userId=" + strId + "&userPw=" + strPw + "&userTel=" + strTel + "&userAddr=" + strAddr + "&userAddrDetail=" + strAddrDetail + "&userPlatform=normal";


            try {
                UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(SignUpActivity.this, urlAddr, "insert");
                insertworkTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaDeliveryAddrInsert.jsp?";//배송지 주소록에도 넣기
            urlAddr = urlAddr + "userId=" + strId + "&deliveryTel=" + strTel + "&deliveryAddr=" + strAddr + "&deliveryAddrDetail=" + strAddrDetail;


            try {
                UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(SignUpActivity.this, urlAddr, "insert");
                insertworkTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }


            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("가입완료!")
                    .setMessage("로그인 페이지로 넘어갑니다!")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.putExtra("USERID", strId);
                            startActivity(intent);

                        }
                    })
                    .show();

        } else {
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("이메일 중복확인!")
                    .setMessage("이메일 중복확인을 해주세요")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", null)
                    .show();

        }


    }

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

    /**
     * Comment : 정상적인 이메일 인지 검증.
     */
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isValidPassword(String pw) {//숫자와 문자 포함 형태의 6~12자리 이내의 암호 정규식
        boolean err = false;
        String regex = "/^[A-Za-z0-9]{6,12}$/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pw);
        if (m.matches()) {
            err = true;
        }
        return err;
    }


}