package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.Activities.Login.LoginActivity;
import com.example.supia.Activities.Login.SocialLoginActivity;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MySnsPlatformActivity extends Activity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar
    Button snslistKakao, snslistGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sns_platform);
        overridePendingTransition(R.anim.hold, R.anim.hold);

        //구글 SignInOptions 유도///////////////////////////////////////////////////////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        snslistKakao = findViewById(R.id.snslist_kakao);
        snslistGoogle = findViewById(R.id.snslist_google);


        //----------header 아이디----------//
        ibtnBack = findViewById(R.id.ibtn_back_mypage_header); //뒤로가기
        tvMypage = findViewById(R.id.tv_mypage_mypage_header); //마이페이지
        tvSubscribe = findViewById(R.id.tv_subscribe_mypage_header); //정기구독
        tvOrder = findViewById(R.id.tv_order_mypage_header); //주문내역
        //-------------------------------------//


        //----------bottom 아이디----------//
        ibtnMall = findViewById(R.id.mall_bottom_bar);
        ibtnHome = findViewById(R.id.home_bottom_bar);
        ibtnMypage = findViewById(R.id.mypage_bottom_bar);
        //----------------------------------//

        //---------------클릭이벤트--------------------//
        ibtnBack.setOnClickListener(backClickListener); //header 뒤로가기
        tvMypage.setOnClickListener(myPageClickListener); //header 마이페이지
        tvSubscribe.setOnClickListener(subscribeClickListener); //header 정기구독
        tvOrder.setOnClickListener(orderClickListener); //header 주문내역
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        snslistKakao.setOnClickListener(kakaoClickListener);
        snslistGoogle.setOnClickListener(googleClickListener);


        //------------------------------------------//


    }//------onCreate

    //------------------------------구글로그인---------------------------//
    View.OnClickListener googleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(MySnsPlatformActivity.this)
                    .setTitle("알림")
                    .setIcon(R.mipmap.supia)
                    .setMessage("\n  확인을 누르시면 로그아웃이되며 \n 구글연동 로그인페이지로 가게됩니다.")
                    .setPositiveButton("취소",null)
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            signIn();
                        }
                    }).show();

        }
    };
    //----------------------------------------------------------------//

    //-----------------------------카카오 로그인-------------------------------//
    View.OnClickListener kakaoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new AlertDialog.Builder(MySnsPlatformActivity.this)
                    .setTitle("알림")
                    .setIcon(R.mipmap.supia)
                    .setMessage("\n  확인을 누르시면 로그아웃이되며 \n 카카오연동 로그인페이지로 가게됩니다.")
                    .setPositiveButton("취소",null)
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (LoginClient.getInstance().isKakaoTalkLoginAvailable(MySnsPlatformActivity.this)) { //기기를 통한 카카오톡 로그인 가능한지
                                LoginClient.getInstance().loginWithKakaoTalk(MySnsPlatformActivity.this, callback);
                            } else {
                                LoginClient.getInstance().loginWithKakaoAccount(MySnsPlatformActivity.this, callback);//아니면 카카오톡 온라인

                            }
                            updateKakaoLoginUi();

                        }
                    }).show();



        }
    };
    //---------------------------------------------------------------------//


    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MySnsPlatformActivity.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //----------------------------------뒤로가기 버튼 이벤트----------------------------------//
    View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.hold, R.anim.hold);
            onBackPressed();

        }
    };
    //-----------------------------------------------------------------------------------//


    //-----------------------------------header OrderList 이동--------------------------------//
    View.OnClickListener orderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerforOrder = new Intent(MySnsPlatformActivity.this, MyOrderActivity.class);
            tvOrder.setTypeface(tvOrder.getTypeface(), Typeface.BOLD); // 클릭시 글씨 두꺼워짐
            startActivity(headerforOrder);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //------------------------------------------------------------------------------------//

    //-----------------------------------header Subscribe 이동--------------------------------//
    View.OnClickListener subscribeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerSubscribe = new Intent(MySnsPlatformActivity.this, MySubscribeActivity.class);
            tvSubscribe.setTypeface(tvSubscribe.getTypeface(), Typeface.BOLD);
            startActivity(headerSubscribe);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //------------------------------------------------------------------------------------//

    //-----------------------------------header Mypage 이동--------------------------------//
    View.OnClickListener myPageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent forMypage = new Intent(MySnsPlatformActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//


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
//                    Log.d(TAG, "invoke: email = " + user.getKakaoAccount().getEmail());
                    user.getKakaoAccount().getProfile().getNickname();

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
//

            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

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


}//----------------끝


