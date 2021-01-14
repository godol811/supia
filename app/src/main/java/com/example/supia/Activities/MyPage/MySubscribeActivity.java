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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductDetilFragment;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Adapter.MyPage.MyOrderListAdapter;
import com.example.supia.Adapter.MyPage.MyReviewListAdapter;
import com.example.supia.Adapter.MyPage.MySubOrderListAdapter;
import com.example.supia.Dto.MyPage.MyDeliveryOrderDto;
import com.example.supia.Dto.MyPage.MyOrderListDto;
import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.NetworkTask.MyPage.MyPageDeliveryOrderNetworkTask;
import com.example.supia.NetworkTask.MyPage.MyPageSubscribe2NetworkTask;
import com.example.supia.NetworkTask.MyPage.MyPageSubscribeNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MySubscribeActivity extends Activity {

    String TAG = "서브액티비티";


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar


    LinearLayout llSubscribeSetting, llSubscribeDetail;
    RadioGroup rgSubscribe;
    RadioButton rbSubscribeSetting, rbSubscribeDetail;
    TextView tvProductPrice, tvPriceTotal, tvChangePayment, tvCxl;
    TextView tvStartDate, tvState, tvAddrMyPage, tvPriceMyPage, tvPaymentMyPage, tvNextPayDay;

    TextView tvMySubProductName, tvMySubProductPrice, tvMySubProductQuantity;
    ImageView ivMySubProductImg;
    TextView tvChangeArrd, tvStopSub;


    ArrayList<MySubscribeDto> members; //구독관리
    ArrayList<MySubscribeDto> members2; //구독내역

    String url = "http://" + ShareVar.urlIp + ":8080/test/supiaSubscribe.jsp?userId=" + ShareVar.sharvarUserId;
    String url2 = "http://" + ShareVar.urlIp+ ":8080/test/supiaMyPageSubOrderList.jsp?userId=" + ShareVar.sharvarUserId;
    int productPriceDialog = 0;
    int productQuantity = 0;
    TextView paymentDetail;
    RecyclerView.LayoutManager subLayoutManager = null;
    RecyclerView subscriveRv;
    MySubOrderListAdapter subAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);

        //-----------For Recycler----------------//
        members = new ArrayList<MySubscribeDto>();
        subscriveRv = findViewById(R.id.rv_mysubscribe);
        subscriveRv.setHasFixedSize(true);
        subLayoutManager = new LinearLayoutManager(this);
        subscriveRv.setLayoutManager(subLayoutManager);
        //---------------------------------------//


        tvStopSub = findViewById(R.id.tv_stop_my_subscribe);
        tvChangeArrd = findViewById(R.id.tv_changeaddr_my_subscribe);
        //----------------라디오 버튼-----------------//
        rgSubscribe = findViewById(R.id.toggle);
        rbSubscribeDetail = findViewById(R.id.rb_detail_my_subscribe);
        rbSubscribeSetting = findViewById(R.id.rb_setting_my_subscribe);
        //-----------------------------------------//
        llSubscribeSetting = findViewById(R.id.ll_subscribe_setting); //구독관리 LinearLayout
        llSubscribeDetail = findViewById(R.id.ll_subscribe_detial); //구독내역 LinearLayout

        tvMySubProductName = findViewById(R.id.tv_productname_mysubscribe_detail);
        tvMySubProductPrice = findViewById(R.id.tv_productprice_mysubscribe_detail);
        tvMySubProductQuantity = findViewById(R.id.tv_productquantity_mysubscribe_detail);
        ivMySubProductImg = findViewById(R.id.iv_product_mysubscribe_detail);




        tvStartDate = findViewById(R.id.tv_startdate_my_subscribe);
        tvState = findViewById(R.id.tv_state_my_subscribe);
        tvAddrMyPage = findViewById(R.id.tv_address_my_subscribe);
        tvPriceMyPage = findViewById(R.id.tv_price_my_subscribe);
        tvPaymentMyPage = findViewById(R.id.tv_payment_my_subscribe);
        tvNextPayDay = findViewById(R.id.tv_next_payday_my_subscribe);
        paymentDetail = findViewById(R.id.tv_pay_detail_my_subscribe);

        tvProductPrice = findViewById(R.id.tv_productprice_mysubscribe);
        tvPriceTotal = findViewById(R.id.tv_producttotal_mysubscribe);
        tvChangePayment = findViewById(R.id.tv_changepayment_mysubscribe);
        tvCxl = findViewById(R.id.tv_cxl_mysubscribe);


        connectGetData();
        connectGetData1();

        Log.v(TAG, "시작" + members.get(0).getSubscribeOrderDate());
        tvStartDate.setText(members.get(0).getSubscribeOrderDate());

        if (members.get(0).getProductId() == members.get(0).getProductNo()) {  //구독 여부
            tvState.setText("구독중");
        } else {
            tvState.setText("구독중이 아닙니다.");
        }

        Log.v(TAG, "주소" + members.get(0).getSubscribeOrderAddr());

        tvAddrMyPage.setText(members.get(0).getSubscribeOrderAddr());

        Log.v(TAG, "가격" + members.get(0).getProductPrice() * members.get(0).getSubscribeOrderQuantity());

        tvPriceMyPage.setText(Integer.toString((members.get(0).getProductPrice() * members.get(0).getSubscribeOrderQuantity()) + 2500) + "원");

        Log.v(TAG, "결제수단" + members.get(0).getSubscribeOrderPayment());

        tvPaymentMyPage.setText(members.get(0).getSubscribeOrderPayment());
        Log.v(TAG, "담달" + members.get(0).getSubscribeOrderDate());
        tvNextPayDay.setText(members.get(0).getSubscribeOrderDate());
        productPriceDialog = members.get(0).getProductPrice(); // 커스텀 다이얼로그
        productQuantity = members.get(0).getSubscribeOrderQuantity(); // 커스텀 다이얼로그


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
        rgSubscribe.setOnCheckedChangeListener(radioGroupClickListener); //라디오버튼
        paymentDetail.setOnClickListener(payMentDetailClickListener); // 결제상세 커스텀다이얼로그
        tvChangeArrd.setOnClickListener(changeAddrClickListener);
        tvStopSub.setOnClickListener(stopSubClickListener);
        //------------------------------------------//

        //------------------------------------사진 불러오기---------------------------------------//
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(MySubscribeActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)
        //------------------------------------------------------------------------------------//


    }//----------------onCreate


    //-------------------------------------배송지 변경 클릭 이벤트---------------------------------------//
    View.OnClickListener changeAddrClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MySubscribeActivity.this, MyPageSubscribeDeliveryModifyActivity.class);
            intent.putExtra("deliveryAddr",members.get(0).getSubscribeOrderAddr());
            intent.putExtra("deliveryAddrDetail",members.get(0).getSubscribeOrderAddrDetail());
            startActivity(intent);

        }
    };
    //---------------------------------------------------------------------------------------------//


    //-------------------------------------구독 취소 클릭 이벤트---------------------------------------//
    View.OnClickListener stopSubClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    //---------------------------------------------------------------------------------------------//


    //----------------------------------------결제상세정보 버튼 클릭 이벤트------------------------------------------//
    View.OnClickListener payMentDetailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            MySubscribeChangeDialog subscribe = new MySubscribeChangeDialog(MySubscribeActivity.this, productPriceDialog, productQuantity);
            subscribe.callFunction();


        }
    };
    //--------------------------------------------------------------------------------------------------------//


    //-------------------------------------------라디오 버튼 클릭이벤트----------------------------------------//
    RadioGroup.OnCheckedChangeListener radioGroupClickListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int i) {
            if (rbSubscribeDetail.isChecked()) {
                llSubscribeSetting.setVisibility(View.GONE);
                llSubscribeDetail.setVisibility(View.VISIBLE);
            }
            if (rbSubscribeSetting.isChecked()) {
                llSubscribeSetting.setVisibility(View.VISIBLE);
                llSubscribeDetail.setVisibility(View.GONE);

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


    // ----------------------------------connectGetData1----------------------------------//
    private void connectGetData1() {
        try {

            MyPageSubscribe2NetworkTask networkTask2 = new MyPageSubscribe2NetworkTask(MySubscribeActivity.this, url2, "select");
            Object obj = networkTask2.execute().get();
            members2 = (ArrayList<MySubscribeDto>) obj;
            subAdapter = new MySubOrderListAdapter(MySubscribeActivity.this, R.layout.activity_my_subscribe, members2);
            subscriveRv.setAdapter(subAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//


}//--------------------끝