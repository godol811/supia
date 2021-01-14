package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Adapter.MyPage.MyOrderListAdapter;
import com.example.supia.Adapter.MyPage.MyQnaListAdapter;
import com.example.supia.Adapter.MyPage.MySubOrderListAdapter;
import com.example.supia.Dto.MyPage.MyOrderListDto;
import com.example.supia.Dto.MyPage.MyQnaDto;
import com.example.supia.Dto.MyPage.MyReviewDto;
import com.example.supia.NetworkTask.MyPage.MyOrderListNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyQnaListActivity extends AppCompatActivity {

    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar


    RecyclerView rvQna;
    RecyclerView.LayoutManager qnaLayoutManager = null;
    String urlIp = ShareVar.urlIp;
    String userId = ShareVar.sharvarUserId;
    ArrayList<MyQnaDto> qna;
    String qnaUrl = "http://" + urlIp + ":8080/test/supiaMyQnaList.jsp?userId="+userId;
    MyQnaListAdapter qnaAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qna_list);

        connectGetData();

        //-----------For Recycler----------------//
        qna = new ArrayList<MyQnaDto>();
        rvQna = findViewById(R.id.rv_qnalist_myqna);
        rvQna.setHasFixedSize(true);
        qnaLayoutManager = new LinearLayoutManager(this);
        rvQna.setLayoutManager(qnaLayoutManager);
        //---------------------------------------//

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

    }


    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MyQnaListActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼핑몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MyQnaListActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//




    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MyQnaListActivity.this, MyPageMainActivity.class);
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
            Intent headerforOrder = new Intent(MyQnaListActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyQnaListActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyQnaListActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//

    //----------------------------------connectGetData2----------------------------------//
    private void connectGetData() {
        try {

            MyOrderListNetworkTask networkTask1 = new MyOrderListNetworkTask(MyQnaListActivity.this, qnaUrl, "select");
            Object obj = networkTask1.execute().get();
            qna = (ArrayList<MyQnaDto>) obj;
            qnaAdapter = new MyQnaListAdapter(MyQnaListActivity.this, R.layout.activity_my_qna_list, qna);
            rvQna.setAdapter(qnaAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------//


}//------------------끝

