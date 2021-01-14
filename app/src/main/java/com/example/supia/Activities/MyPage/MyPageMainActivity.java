package com.example.supia.Activities.MyPage;


import android.app.Activity;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.CartActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Dto.UserDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

import static com.example.supia.ShareVar.ShareVar.*;

public class MyPageMainActivity extends Activity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar
    TextView tvUserIdMypage, tvMyInfoMypage;
    LinearLayout llLikeList, llCartList, llNoticeList, llSnsList, llLogout;
    ArrayList<UserDto> userinfo;
    String userId = ShareVar.sharvarUserId;
    String urlIp = ShareVar.urlIp;

    String url = "http://"+urlIp+":8080/test/supiaMypage.jsp?userId="+userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_main);


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


        //-----------가운데 레이아웃 아이디----------//
        tvUserIdMypage = findViewById(R.id.tv_userid_mypagemain);
        tvMyInfoMypage = findViewById(R.id.tv_myinfo_mypagemain);
        //-------------------------------------//


        //----------밑에 목록 리스트 아이디------------//
        llLikeList = findViewById(R.id.ll_likelist_mypagemain); //찜
        llCartList = findViewById(R.id.ll_cartlist_mypagemain); //장바구니
        llNoticeList = findViewById(R.id.ll_noticelist_mypagemain); //고객센터
        llSnsList = findViewById(R.id.ll_snslist_mypagemain); //소셜로그인
        llLogout = findViewById(R.id.ll_logout_mypagemain); //로그아웃
        //---------------------------------------//


        //---------------클릭이벤트--------------------//
        ibtnBack.setOnClickListener(backClickListener); //header 뒤로가기
        tvMypage.setOnClickListener(myPageClickListener); //header 마이페이지
        tvSubscribe.setOnClickListener(subscribeClickListener); //header 정기구독
        tvOrder.setOnClickListener(orderClickListener); //header 주문내역
        tvMyInfoMypage.setOnClickListener(myInfoClickListener); //내정보
        llLikeList.setOnClickListener(likeListClickListener); //찜목록
        llCartList.setOnClickListener(cartListClickListener); //장바구니
        llSnsList.setOnClickListener(snsListClickListener); // 소셜로그인
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //------------------------------------------//


        connectGetData();

        //--------------텍스트뷰 값띄워주기------------//
        tvUserIdMypage.setText(userinfo.get(0).getUserId());
        //----------------------------------------//

    } //---onCreate




    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MyPageMainActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼핑몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MyPageMainActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//




    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MyPageMainActivity.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //-------------------------------------소셜로그인 클릭 이벤트----------------------------------------//
    View.OnClickListener snsListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent snsPlatform = new Intent(MyPageMainActivity.this, MySnsPlatformActivity.class);
            startActivity(snsPlatform);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //----------------------------------------------------------------------------------------------//


    //----------------------------------장바구니 클릭 이벤트-----------------------------------//
    View.OnClickListener cartListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent cart = new Intent(MyPageMainActivity.this, CartActivity.class);
            startActivity(cart);
            overridePendingTransition(R.anim.hold, R.anim.hold);
        }
    };
    //-----------------------------------------------------------------------------------//


    //----------------------------------뒤로가기 버튼 이벤트----------------------------------//
    View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.hold, R.anim.hold);
            onBackPressed();

        }
    };
    //-----------------------------------------------------------------------------------//


    //----------------------------------찜목록 클릭이벤트----------------------------------//
    View.OnClickListener likeListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent like = new Intent(MyPageMainActivity.this, MyLikeListActivity.class);

            startActivity(like);
            overridePendingTransition(R.anim.hold, R.anim.hold);


        }
    };
    //--------------------------------------------------------------------------------//


    //----------------------------------내 정보 클릭이벤트----------------------------------//
    View.OnClickListener myInfoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infointent = new Intent(MyPageMainActivity.this, MyInfoActivity.class);
            infointent.putExtra("userId", userinfo.get(0).getUserId());
            infointent.putExtra("userName", userinfo.get(0).getUserName());
            infointent.putExtra("userTel", userinfo.get(0).getUserTel());
            infointent.putExtra("userPw", userinfo.get(0).getUserPw());
            infointent.putExtra("url", url);

            startActivity(infointent);
            overridePendingTransition(R.anim.hold, R.anim.hold);


        }
    };

    //------------------------------------------------------------------------------------//


    //-----------------------------------header OrderList 이동--------------------------------//
    View.OnClickListener orderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerforOrder = new Intent(MyPageMainActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyPageMainActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyPageMainActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//


    //----------------------------------connectGetData----------------------------------//
    private void connectGetData() {
        try {

            UserInfoNetworkTask networkTask = new UserInfoNetworkTask(MyPageMainActivity.this, url, "select");
            Object obj = networkTask.execute().get();
            userinfo = (ArrayList<UserDto>) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//


}//---------------------