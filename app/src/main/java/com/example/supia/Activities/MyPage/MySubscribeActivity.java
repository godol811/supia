package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.R;

public class MySubscribeActivity extends AppCompatActivity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);

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
        //------------------------------------------//




    }//----------------onCreate


    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MySubscribeActivity.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.fadein, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //----------------------------------뒤로가기 버튼 이벤트----------------------------------//
    View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            overridePendingTransition(R.anim.fadein, R.anim.hold);
            onBackPressed();

        }
    };
    //-----------------------------------------------------------------------------------//



    //-----------------------------------header OrderList 이동--------------------------------//
    View.OnClickListener orderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerforOrder = new Intent(MySubscribeActivity.this, MyOrderActivity.class);
            tvOrder.setTypeface(tvOrder.getTypeface(), Typeface.BOLD); // 클릭시 글씨 두꺼워짐
            startActivity(headerforOrder);
            overridePendingTransition(R.anim.fadein, R.anim.hold);

        }
    };
    //------------------------------------------------------------------------------------//

    //-----------------------------------header Subscribe 이동--------------------------------//
    View.OnClickListener subscribeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerSubscribe = new Intent(MySubscribeActivity.this, MySubscribeActivity.class);
            tvSubscribe.setTypeface(tvSubscribe.getTypeface(), Typeface.BOLD);
            startActivity(headerSubscribe);
            overridePendingTransition(R.anim.fadein, R.anim.hold);

        }
    };
    //------------------------------------------------------------------------------------//

    //-----------------------------------header Mypage 이동--------------------------------//
    View.OnClickListener myPageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent forMypage = new Intent(MySubscribeActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.fadein, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//


}//--------------------끝