package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Login.LoginActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class MyInfoActivity extends Activity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar
    String userId = ShareVar.sharvarUserId;

    TextView myInfoId, myInfoTel, myInfoPw;
    TextView tvLogout;
    EditText etPw, etPwCh; //커스텀 다이얼로그 EditText
    String strMyInfoUserName, strMyInfoUserPw, strMyInfoUserTel, strMyInfoUserAddr; // 인텐트 받아오기위한 string
    Button changePwBtn;
    final String pwChange = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);

        //------------------Intent 받아오기---------------------//
        Intent intent = getIntent();
        strMyInfoUserName = intent.getStringExtra("userName");
        strMyInfoUserTel = intent.getStringExtra("userTel");
        strMyInfoUserPw = intent.getStringExtra("userPw");
        strMyInfoUserAddr = intent.getStringExtra("userAddr");
        //---------------------------------------------------//

        //------------아이디 받기---------------//
        myInfoId = findViewById(R.id.tv_userid_mypage_info);
        myInfoTel = findViewById(R.id.tv_usertel_mypage_info);
        myInfoPw = findViewById(R.id.tv_userpw_mypage_info);
        changePwBtn = findViewById(R.id.btn_changepw_mypage_info);
        etPw = findViewById(R.id.et_pwchange_mypage_info);
        etPwCh = findViewById(R.id.et_pwcheck_mypage_info);
        tvLogout = findViewById(R.id.info_logout);
        //----------------------------------//


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


        //-------setText-------//
        myInfoId.setText(userId);
        myInfoTel.setText(strMyInfoUserTel);
        myInfoPw.setText(strMyInfoUserPw);
        //---------------------//


        //---------------클릭이벤트--------------------//
        ibtnBack.setOnClickListener(backClickListener); //header 뒤로가기
        tvMypage.setOnClickListener(myPageClickListener); //header 마이페이지
        tvSubscribe.setOnClickListener(subscribeClickListener); //header 정기구독
        tvOrder.setOnClickListener(orderClickListener); //header 주문내역
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        changePwBtn.setOnClickListener(changePwClickListener); //비번 변경
        tvLogout.setOnClickListener(logoutClickListener);
        //------------------------------------------//


    }//-----------------onCreate

    //-----------------------------------logout 변경 클릭 이벤트-----------------------------------//
    View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            new AlertDialog.Builder(MyInfoActivity.this)
                    .setTitle("알림")
                    .setIcon(R.mipmap.supia)
                    .setMessage("확인을 누르시면 로그인 화면으로 돌아가게 됩니다.")
                    .setPositiveButton("취소",null)
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent logout = new Intent(MyInfoActivity.this, LoginActivity.class);
                                      userId= null;
                                      logout.putExtra("userId",userId);
                                      startActivity(logout);

                        }
                    }).show();



        }
    };
    //-----------------------------------------------------------------------------------------//

    //----------------------------------------비밀번호 변경 버튼 클릭 이벤트------------------------------------------//
    View.OnClickListener changePwClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            MypagePwChangeDialog pwDialog = new MypagePwChangeDialog(MyInfoActivity.this, userId, strMyInfoUserName, strMyInfoUserTel);
            pwDialog.callFunction(pwChange);


        }
    };
    //--------------------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MyInfoActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼핑몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MyInfoActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//





    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MyInfoActivity.this, MyPageMainActivity.class);
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
            Intent headerforOrder = new Intent(MyInfoActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyInfoActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyInfoActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//


}//---------------끝