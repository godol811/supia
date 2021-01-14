package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supia.Adapter.MyPage.MyOrderListAdapter;
import com.example.supia.Adapter.MyPage.MyReviewListAdapter;
import com.example.supia.Dto.MyPage.MyDeliveryOrderDto;
import com.example.supia.Dto.MyPage.MyOrderListDto;
import com.example.supia.Dto.MyPage.MyReviewDto;
import com.example.supia.NetworkTask.MyPage.MyOrderListNetworkTask;
import com.example.supia.NetworkTask.MyPage.MyPageDeliveryOrderNetworkTask;
import com.example.supia.NetworkTask.MyPage.MyReviewInsertNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyOrderActivity extends Activity {

    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar
    String urlIp = ShareVar.urlIp;
    String userId = ShareVar.sharvarUserId;

    TextView tvOrderName, tvOrderAddr, tvOrderTel;
    RecyclerView.LayoutManager reviewLayoutManager = null;
    RecyclerView.LayoutManager orderLayourManager = null;

    ImageButton reviewDown, reviewup;
    ImageButton orderDown, orderUp;
    LinearLayout reviewText;
    LinearLayout orderText;
    RecyclerView orderRecycler, reviewRecycler;
    TextView tvInsertReview;

    ArrayList<MyDeliveryOrderDto> delivery;
    ArrayList<MyOrderListDto> order;
    ArrayList<MyReviewDto> review;

    String deliveryUrl = "http://" + urlIp + ":8080/test/supiaDeliveryMypage.jsp?userId="+userId;
    String orderUrl = "http://" + urlIp + ":8080/test/supiaOrderlist.jsp?userId="+userId;
    String reviewUrl = "http://" + urlIp + ":8080/test/supiaReviewList.jsp?userId="+userId;
    MyReviewListAdapter reviewAdapter = null;
    MyOrderListAdapter orderAdapter = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);

        //-----------For Recycler----------------//
        order = new ArrayList<MyOrderListDto>();
        orderRecycler = findViewById(R.id.order_orderlist);
        orderRecycler.setHasFixedSize(true);
        orderLayourManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(orderLayourManager);
        //---------------------------------------//
        //-----------For Recycler----------------//
        review = new ArrayList<MyReviewDto>();
        reviewRecycler = findViewById(R.id.review_reviewlist);
        reviewRecycler.setHasFixedSize(true);
        reviewLayoutManager = new LinearLayoutManager(this);
        reviewRecycler.setLayoutManager(reviewLayoutManager);
        //---------------------------------------//


        tvInsertReview = findViewById(R.id.tv_writereivew_myorderlist);

        //---------------------리뷰&주문목록----------------------//

        reviewText = findViewById(R.id.review_text);
        reviewDown = findViewById(R.id.review_down);
        reviewup = findViewById(R.id.review_up);

        orderDown = findViewById(R.id.order_down);
        orderUp = findViewById(R.id.order_up);
        orderText = findViewById(R.id.order_text);

        reviewText.setOnClickListener(reviewListClickListener);
        reviewDown.setOnClickListener(reviewListClickListener);
        reviewup.setOnClickListener(reviewListClickListener);

        orderDown.setOnClickListener(orderListClickListener);
        orderUp.setOnClickListener(orderListClickListener);
        orderText.setOnClickListener(orderListClickListener);
        //-----------------------------------------------------//


        tvOrderName = findViewById(R.id.tv_username_myorder);
        tvOrderAddr = findViewById(R.id.tv_useraddr_myorder);
        tvOrderTel = findViewById(R.id.tv_usertel_myorder);


        connectGetData1();
        connectGetData2();
        connectGetData3();


        if (delivery.size() != 0) { //get position 이 0이 아닐때 아무것도 안띄우기
            tvOrderName.setText(delivery.get(0).getDeliveryName());
            tvOrderAddr.setText(delivery.get(0).getDeliveryAddr());
            tvOrderTel.setText(delivery.get(0).getDeliveryTel());
        }


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


        //------------------------------------사진 불러오기---------------------------------------//
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(MyOrderActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)
        //------------------------------------------------------------------------------------//


    }//---------------onCreate


    //---------------------------주문목록 클릭 이벤트-------------------------------//
    View.OnClickListener orderListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.order_down:
                    orderDown.setVisibility(View.INVISIBLE);
                    orderUp.setVisibility(View.VISIBLE);
                    orderText.setVisibility(View.VISIBLE);
                    break;
                case R.id.order_up:
                    orderDown.setVisibility(View.VISIBLE);
                    orderUp.setVisibility(View.INVISIBLE);
                    orderText.setVisibility(View.GONE);
                    break;

            }
        }
    };
    //--------------------------------------------------------------------------//

    //---------------------------리뷰목록 클릭 이벤트-------------------------------//
    View.OnClickListener reviewListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.review_down:
                    reviewDown.setVisibility(View.INVISIBLE);
                    reviewup.setVisibility(View.VISIBLE);
                    reviewText.setVisibility(View.VISIBLE);
                    break;
                case R.id.review_up:
                    reviewDown.setVisibility(View.VISIBLE);
                    reviewup.setVisibility(View.INVISIBLE);
                    reviewText.setVisibility(View.GONE);
                    break;

            }


        }
    };
    //--------------------------------------------------------------------------//


    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MyOrderActivity.this, MyPageMainActivity.class);
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
            Intent headerforOrder = new Intent(MyOrderActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyOrderActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyOrderActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//



    //----------------------------------connectGetData1----------------------------------//
    private void connectGetData1() {
        try {

            MyPageDeliveryOrderNetworkTask networkTask = new MyPageDeliveryOrderNetworkTask(MyOrderActivity.this, deliveryUrl, "select");
            Object obj = networkTask.execute().get();
            delivery = (ArrayList<MyDeliveryOrderDto>) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------//
    //----------------------------------connectGetData2----------------------------------//
    private void connectGetData2() {
        try {

            Log.v("여기", "겟데이타2");

            MyOrderListNetworkTask networkTask1 = new MyOrderListNetworkTask(MyOrderActivity.this, orderUrl, "select");
            Object obj = networkTask1.execute().get();
            order = (ArrayList<MyOrderListDto>) obj;
            orderAdapter = new MyOrderListAdapter(MyOrderActivity.this, R.layout.activity_my_order, order);
            orderRecycler.setAdapter(orderAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//
    //----------------------------------connectGetData3----------------------------------//
    private void connectGetData3() {
        try {

            Log.v("여기", "겟데이타2");

            MyReviewInsertNetworkTask networkTask1 = new MyReviewInsertNetworkTask(MyOrderActivity.this, reviewUrl, "select");
            Object obj = networkTask1.execute().get();
            review = (ArrayList<MyReviewDto>) obj;
            reviewAdapter = new MyReviewListAdapter(MyOrderActivity.this, R.layout.activity_my_order, review);
            reviewRecycler.setAdapter(reviewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//


}//--------------끝