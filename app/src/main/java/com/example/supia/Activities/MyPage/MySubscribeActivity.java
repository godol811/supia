package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.NetworkTask.MyPage.MyPageSubscribeNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MySubscribeActivity extends Activity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar


    LinearLayout llSubscribeSetting;
    RadioGroup rgSubscribe;
    RadioButton rbSubscribeSetting, rbSubscribeDetail;

    TextView tvStartDate,tvState,tvAddr,tvPrice,tvPayment,tvNextPayDay;
    ArrayList<MySubscribeDto> members;
    String urlIp = ShareVar.urlIp;
    String userId = ShareVar.sharvarUserId;
    String url = "http://"+urlIp+":8080/test/supiaSubscribe.jsp?userId="+userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);

        rgSubscribe = findViewById(R.id.toggle);
        rbSubscribeDetail = findViewById(R.id.rb_detail_my_subscribe);
        rbSubscribeSetting = findViewById(R.id.rb_setting_my_subscribe);
        llSubscribeSetting = findViewById(R.id.ll_subscribe_setting); //구독관리 LinearLayout

        tvStartDate = findViewById(R.id.tv_startdate_my_subscribe);
        tvState = findViewById(R.id.tv_state_my_subscribe);
        tvAddr = findViewById(R.id.tv_address_my_subscibe);
        tvPrice = findViewById(R.id.tv_price_my_subscribe);
        tvPayment = findViewById(R.id.tv_payment_my_subscribe);
        tvNextPayDay = findViewById(R.id.tv_next_payday_my_subscribe);

        connectGetData();

        tvStartDate.setText(members.get(0).getSubscribeOrderDate());
        if(members.get(0).getProductId() == members.get(0).getProductNo()){
            tvState.setText("구독중");
        }else {
            tvState.setText("구독중이 아닙니다.");
        }
        tvAddr.setText(members.get(0).getSubscribeOrderAddr());
        tvPrice.setText(Integer.toString(members.get(0).getProductPrice()*members.get(0).getSubscribeOrderQuantity())+"원");
        tvPayment.setText(members.get(0).getSubscribeOrderPayment());
        tvNextPayDay.setText(members.get(0).getSubscribeOrderDate());


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
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        rgSubscribe.setOnCheckedChangeListener(radioGroupClickListener);
        //------------------------------------------//

        //------------------------------------사진 불러오기---------------------------------------//
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(MySubscribeActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)
        //------------------------------------------------------------------------------------//


    }//----------------onCreate


    //-------------------------------------------라디오 버튼 클릭이벤트----------------------------------------//
    RadioGroup.OnCheckedChangeListener radioGroupClickListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int i) {
            if (rbSubscribeDetail.isChecked()) {
                llSubscribeSetting.setVisibility(View.GONE);
            }
            if (rbSubscribeSetting.isChecked()) {
                llSubscribeSetting.setVisibility(View.VISIBLE);

            }
        }
    };
    //----------------------------------------------------------------------------------------------------//


    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MySubscribeActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼핑몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MySubscribeActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//


    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MySubscribeActivity.this, MyPageMainActivity.class);
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
            Intent headerforOrder = new Intent(MySubscribeActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MySubscribeActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MySubscribeActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//


    //----------------------------------connectGetData----------------------------------//
    private void connectGetData() {
        try {

            MyPageSubscribeNetworkTask networkTask = new MyPageSubscribeNetworkTask(MySubscribeActivity.this, url, "select");
            Object obj = networkTask.execute().get();
            members = (ArrayList<MySubscribeDto>) obj;




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//

}//--------------------끝