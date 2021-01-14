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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class RefactorPassWordActivity extends Activity {

    final static String TAG = "암호재설정액티비티";
    String strUserId;
    EditText password, passwordCheck;
    TextView tvUserId, pwCheckAlert;
    ImageView buttonPassword;
    Button refactorPasswordButton,buttonBack;
    private String urlAddr;
    private String macIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refactor_pass_word);

        Intent intent = getIntent();
        strUserId = intent.getStringExtra("userId");
        tvUserId = findViewById(R.id.tv_id_refactor);
        tvUserId.setText(strUserId);

        password = findViewById(R.id.textPassword_refactor);
        passwordCheck = findViewById(R.id.textPasswordCheck_refactor);
        pwCheckAlert = findViewById(R.id.pwCheckAlert_refactor);
        buttonBack = findViewById(R.id.btn_back_refactor);

        buttonPassword = findViewById(R.id.btn_password_refactor);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        ///////////////////////////////////////눈 보이기////////////////////////////////////////////
        buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPassword.getTag().equals("0")) {//비밀번호 안보이고 있는 상황
                    buttonPassword.setTag("1");
                    buttonPassword.setImageResource(R.drawable.openeye);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordCheck.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    buttonPassword.setTag("0");
                    buttonPassword.setImageResource(R.drawable.closedeye);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        ////////////////////////////암호체크용 텍스트 와쳐//////////////////////////////////////////

        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strUserinfoPw = password.getText().toString();
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
        ////////////////////////////암호체크용 텍스트 와쳐//////////////////////////////////////////


        refactorPasswordButton = findViewById(R.id.btn_resetpw_Refactor);
        refactorPasswordButton.setOnClickListener(mOnclickListener);
    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strPassword = password.getText().toString().trim();
            String strPasswordCheck = passwordCheck.getText().toString().trim();

            if (strPasswordCheck.length() != 0 && strPassword.length() != 0) {
                buttonEnable();//암호설정 메소드로

            } else {

                new AlertDialog.Builder(RefactorPassWordActivity.this)
                        .setTitle("암호확인 체크!")
                        .setMessage("암호를 입력해주세요!")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("닫기", null)
                        .show();

            }

        }
    };

    ////////////////////////////암호 설정 메소드////////////////////////////////////////////////////
    private void buttonEnable() {

        String strPassword = password.getText().toString().trim();
        String strPasswordCheck = passwordCheck.getText().toString().trim();

        if (strPassword.equals(strPasswordCheck)) {

            macIp = "192.168.35.147";

            urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserPwRefactor.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userPw=" + strPassword + "&userId=" + strUserId;
            Log.d(TAG, urlAddr);

            try {
                UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(RefactorPassWordActivity.this, urlAddr,"insert");
                insertworkTask.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            new AlertDialog.Builder(RefactorPassWordActivity.this)
                    .setTitle("암호변경 완료!")
                    .setMessage("로그인 페이지로 넘어갑니다!")
                    .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                    .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RefactorPassWordActivity.this, LoginActivity.class);
                            intent.putExtra("암호변경 내용", strPassword);
                            startActivity(intent);

                        }
                    })
                    .show();


        } else {
            new AlertDialog.Builder(RefactorPassWordActivity.this)
                    .setTitle("암호확인 체크!")
                    .setMessage("같은 암호를 입력해주세요")
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
}