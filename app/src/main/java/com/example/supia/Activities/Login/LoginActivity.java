package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.Adapter.UserAdapter;
import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;


import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class LoginActivity extends Activity {

    final static String TAG = "LoginActivity";

    private View loginButton, kakaoLoginButton, signUpButton, findButton;
    private SignInButton googleLoginButton;
    private EditText userId, userPw;
    private String urlAddr = null;
    ArrayList<UserDto> userDtos;
    UserAdapter adapter;
    String macIp;
    String userinfoId, userinfoPw;
    String kakaoNickName = "";
    String userIdCheck, userPwCheck;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;
    private Context mContext;
    CheckBox cbAutoLogin;

    //카카오 수정 0113 -종찬


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;


        SharedPreferences sf = getSharedPreferences("auto", MODE_PRIVATE);
        userinfoId = sf.getString("userId", "");
        userinfoPw = sf.getString("userPw", "");
        Log.d(TAG, userinfoId);

        if (userinfoId.trim().

                length() != 0) {//자동 로그인 되어있을경우에는 MainCalendar 액티비티로이동
            ShareVar.sharvarUserId = userinfoId;
            Intent intent2 = new Intent(LoginActivity.this, MainCalendar.class);//
            startActivity(intent2);
        }

        //구글 SignInOptions 유도///////////////////////////////////////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        ///// findViewID 유도///////////////////////////////////////////////////////////////////////
        cbAutoLogin = findViewById(R.id.cb_autologin_login);
        kakaoLoginButton = findViewById(R.id.kakaologin);
        loginButton = findViewById(R.id.login);
        findButton = findViewById(R.id.btn_find);
        signUpButton = findViewById(R.id.btnsignUp);
        googleLoginButton = findViewById(R.id.loginGoogle);
        userId = findViewById(R.id.userid);
        userPw = findViewById(R.id.userpw);

        //// 회원가입에서 이동해서 아이디 값 가져오기///////////////////////////////////////////////
        Intent intent = getIntent();
        userId.setText(intent.getStringExtra("USERID"));

        //// 클릭 리스너 할당 //////////////////////////////////////////////////////////////////////
        signUpButton.setOnClickListener(mOnclickListener);
        findButton.setOnClickListener(mOnclickListener);
        kakaoLoginButton.setOnClickListener(mOnclickListener);
        loginButton.setOnClickListener(mOnclickListener);
        googleLoginButton.setOnClickListener(mOnclickListener);
        googleLoginButton.setSize(SignInButton.SIZE_WIDE);


    }//oncreate


    /////// 클릭 리스너 활성////////////////////////////////////////////////////////////////////////
    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kakaologin://카카오 로그인
                    if (LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) { //기기를 통한 카카오톡 로그인 가능한지
                        LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);


                    } else {
                        LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);//아니면 카카오톡 온라인
                    }
                    updateKakaoLoginUi();
//                    kakaoLoginActive();


                    break;

                case R.id.login://일반 로그인
                    userinfoId = userId.getText().toString().trim();
                    userinfoPw = userPw.getText().toString().trim();
                    if (userinfoId.length() != 0 && userinfoPw.length() != 0) {//빈칸이 아닐경우
                        connectloginCheck();//로그인 메소드 발동
                    } else {//빈칸이 있을경우
//                        Toast.makeText(LoginActivity.this, "빈칸을 채워주세요 ", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.loginGoogle://구글 로그인
                    signIn();
                    break;

                case R.id.btnsignUp://가입하기로 넘어가기
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_find://아이디 비번 찾기로 넘어가기
                    Intent intent1 = new Intent(LoginActivity.this, FindPWActivity.class);
                    startActivity(intent1);
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

    }
    //// 카카오 로그인 추가/////////////////////////////////////////////////////////////////////////

    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                //TBD
            }
            if (throwable != null) {
                //TBD
            }
            updateKakaoLoginUi();
            return null;
        }
    };

    //// 카카오 로그인 값 불러오기 /////////////////////////////////////////////////////////////////
    private void updateKakaoLoginUi() {//로그인 유무 확인
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if (user != null) {//로그인 상황일때
                    kakaoNickName = user.getKakaoAccount().getProfile().getNickname();
                    userId.setText(kakaoNickName);
                    Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(intent);



                } else {//로그아웃 상황일때.


                }
                return null;
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////
    //구글로그인 추가                                                         //
    ////////////////////////////////////////////////////////////////////////////


    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    } // [END on_start_sign_in]


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {// [START onActivityResult]
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {  // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    } // [END onActivityResult]

    //// 구글 로그인시 받아오는 값을 인텐트로 넘기기 ///////////////////////////////////////////////
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {// [START handleSignInResult]

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.


            if (account != null) {//google 인텐트 보내는 값 (유저 데이터)
                String personName = account.getDisplayName();
                String personEmail = account.getEmail();
//                String personGivenName = account.getGivenName();
//                String personFamilyName = account.getFamilyName();
//                String personId = account.getId();
//                Uri personPhoto = account.getPhotoUrl();
                if (cbAutoLogin.isChecked()) {
                    SharedPreferences sf = getSharedPreferences("auto", MODE_PRIVATE);//자동로그인 발동
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putString("userId", personEmail);
                    editor.putString("userPw", null);
                    editor.commit();//자동로그인

                    Intent intent = new Intent(LoginActivity.this, SocialLoginActivity.class);
                    ShareVar.sharvarUserId = personEmail;//유저 이메일 주소 넘기기
                    intent.putExtra("userName", personName);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(LoginActivity.this, SocialLoginActivity.class);
                    ShareVar.sharvarUserId = personEmail;//유저 이메일 주소 넘기기
                    intent.putExtra("userName", personName);
                    startActivity(intent);
                }

                macIp = "192.168.35.147";
                urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
                urlAddr = urlAddr + "userId=" + personEmail + "&userPlatform=google";
                socialUserInsert();
            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    ////구글로그인 끝  /////////////////////////////////////////////////////////////////////////////


    ////일반로그인 메소드 //////////////////////////////////////////////////////////////////////////
    private void connectloginCheck() {
        Log.v(TAG, "connectGetData()");
        try {

            macIp = ShareVar.urlIp;
            urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserIdCheck.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
            urlAddr = urlAddr + "userId=" + userinfoId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            UserInfoNetworkTask networkTask = new UserInfoNetworkTask(LoginActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            userDtos = (ArrayList<UserDto>) obj; //userInfoDtos 다시 풀기

            userIdCheck = userDtos.get(0).getUserId();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.
            userPwCheck = userDtos.get(0).getUserPw();
//            Log.d(TAG,userIdCheck);
//            Log.d(TAG,userPwCheck);

            if (userPwCheck.length() != 0) {//받아오는 암호값이 있으면 고고
                if (userPwCheck.equals(userinfoPw)) {//암호가 같으면
                    if (cbAutoLogin.isChecked()) {
                        SharedPreferences sf = getSharedPreferences("auto", MODE_PRIVATE);//자동로그인 발동
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putString("userId", userinfoId);
                        editor.putString("userPw", userinfoPw);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, UserDataQuestion1.class);
                        ShareVar.sharvarUserId = userinfoId;//sharevar에 값 넣어버리기.
                        intent.putExtra("userId", userinfoId);
                        Toast.makeText(this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, UserDataQuestion1.class);
                        ShareVar.sharvarUserId = userinfoId;//sharevar에 값 넣어버리기.
                        intent.putExtra("userId", userinfoId);
                        Toast.makeText(this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }


                } else {// 암호가 다르면 암호가 틀렸다고 메세지 띄우기
                    Toast.makeText(LoginActivity.this, "암호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }


            } else {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고
                Toast.makeText(this, "없는 아이디입니다.", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////구글이나 카카오로 로그인했을시 부족한테이터를 채우기 위한 인텐트////////////////////////

    private void socialUserInsert() {

        try {
            UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(LoginActivity.this, urlAddr, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
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
    private void kakaoLoginActive() {
        Log.d("닉네임",kakaoNickName);
        if (cbAutoLogin.isChecked()) {
            SharedPreferences sf = getSharedPreferences("auto", MODE_PRIVATE);//자동로그인 발동
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("userId", kakaoNickName);
            editor.putString("userPw", null);
            editor.commit();//자동로그인
        } else {
            Intent intent = new Intent(LoginActivity.this, SocialLoginActivity.class);
            intent.putExtra("userId", kakaoNickName);//유저 이메일 주소 넘기기
            ShareVar.sharvarUserId = kakaoNickName;//쉐어바에 값 넣어버리기
            startActivity(intent);
        }
        macIp = ShareVar.urlIp;
        urlAddr = "http:/" + macIp + ":8080/test/supiaUserinfoInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
        urlAddr = urlAddr + "userId=" + kakaoNickName + "&userPlatform=kakao";
        try {
            UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(LoginActivity.this, urlAddr, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}//--------