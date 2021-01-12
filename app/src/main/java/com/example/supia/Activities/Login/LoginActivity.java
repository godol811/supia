package com.example.supia.Activities.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.Adapter.UserAdapter;
import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
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
    String userIdCheck, userPwCheck;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;
    private Context mContext;
    CheckBox cbAutoLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;


        SharedPreferences sf = getSharedPreferences("auto",MODE_PRIVATE);
        userinfoId = sf.getString("userId","");
        userinfoPw =sf.getString("userPw","");
        Log.d(TAG,userinfoId);

        if(userinfoId.trim().length()!=0){
            ShareVar.sharvarUserId = userinfoId;
            Intent intent2 = new Intent(LoginActivity.this, ProductMainActivity.class);//추후에는 참치 쪽으로 이동
            startActivity(intent2);

        }



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        cbAutoLogin = findViewById(R.id.cb_autologin_login);




        kakaoLoginButton = findViewById(R.id.kakaologin);
        loginButton = findViewById(R.id.login);
        findButton = findViewById(R.id.btn_find);
        signUpButton = findViewById(R.id.btnsignUp);
        googleLoginButton = findViewById(R.id.loginGoogle);

        userId = findViewById(R.id.userid);
        userPw = findViewById(R.id.userpw);


        Intent intent = getIntent();
        userId.setText(intent.getStringExtra("USERID"));


        signUpButton.setOnClickListener(mOnclickListener);
        findButton.setOnClickListener(mOnclickListener);
        kakaoLoginButton.setOnClickListener(mOnclickListener);
        loginButton.setOnClickListener(mOnclickListener);
        googleLoginButton.setOnClickListener(mOnclickListener);
        googleLoginButton.setSize(SignInButton.SIZE_WIDE);


    }//oncreate


    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kakaologin:
                    if (LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) { //기기를 통한 카카오톡 로그인 가능한지
                        LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                    } else {
                        LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);//아니면 카카오톡 온라인

                    }
                    updateKakaoLoginUi();
                    break;

                case R.id.login:
                    userinfoId = userId.getText().toString().trim();
                    userinfoPw = userPw.getText().toString().trim();
                    if (userinfoId.length() != 0 && userinfoPw.length() != 0) {//빈칸이 아닐경우
                        connectloginCheck();//로그인 메소드 발동
                    } else {//빈칸이 있을경우
//                        Toast.makeText(LoginActivity.this, "빈칸을 채워주세요 ", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.loginGoogle:
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


    ////////////////////////////////////////////////////////////////////////////
    //일반 로그인                                                             //
    ////////////////////////////////////////////////////////////////////////////

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


    ////////////////////////////////////////////////////////////////////////////
    //카카오 로그인 추가                                                      //
    ////////////////////////////////////////////////////////////////////////////

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


    private void updateKakaoLoginUi() {//로그인 유무 확인
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {//로그인 상황일때
                    Log.d(TAG, "invoke: email = " + user.getKakaoAccount().getEmail());

                    if (cbAutoLogin.isChecked()) {
                        SharedPreferences sf = getSharedPreferences("auto", MODE_PRIVATE);//자동로그인 발동
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putString("userId", user.getKakaoAccount().getEmail());
                        editor.putString("userPw", null);
                        editor.commit();//자동로그인

                        Intent intent = new Intent(LoginActivity.this, SocialLoginActivity.class);
                        intent.putExtra("userId", user.getKakaoAccount().getEmail());//유저 이메일 주소 넘기기
                        ShareVar.sharvarUserId = user.getKakaoAccount().getEmail();//쉐어바에 값 넣어버리기
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(LoginActivity.this, SocialLoginActivity.class);
                        intent.putExtra("userId", user.getKakaoAccount().getEmail());//유저 이메일 주소 넘기기
                        ShareVar.sharvarUserId = user.getKakaoAccount().getEmail();//쉐어바에 값 넣어버리기
                        startActivity(intent);
                    }
                    macIp = ShareVar.urlIp;
                    urlAddr = "http:/" + macIp + ":8080/test/supiaUserinfoInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
                    urlAddr = urlAddr + "userId=" + user.getKakaoAccount().getEmail() + "&userPlatform=kakao";
                    try {
                        UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(LoginActivity.this, urlAddr, "insert");
                        insertworkTask.execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


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


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {// [START handleSignInResult]

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.


            if (account != null) {//google 인텐트 보내는 값 (유저 데이터)
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();
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
                urlAddr = "http:/" + ShareVar.urlIp + ":8080/test/supiaUserinfoInsert.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것
                urlAddr = urlAddr + "userId=" + personEmail + "&userPlatform=google";
                try {
                    UserInfoNetworkTask insertworkTask = new UserInfoNetworkTask(LoginActivity.this, urlAddr, "insert");
                    insertworkTask.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    ////////////////////////////////////////////////////////////////////////////
    //구글로그인 추가                                                         //
    ////////////////////////////////////////////////////////////////////////////


}