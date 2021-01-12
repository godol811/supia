package com.example.supia.Activities.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.supia.Activities.MyPage.MyCartListActivity;
import com.example.supia.Activities.MyPage.MyPageMainActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.HashSet;

public class MainCalendar extends FragmentActivity {

    public String urlAddr,urlIp,userId;

    public TextView tvDday;
    public Button btnedit;
    public ImageButton gotosub;
    public MaterialCalendarView materialCalendarView_main;
    public String Dday;

    private HashSet<CalendarDay> dates;

    public static String TAG ="메인캘린더";


    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar (애정추가)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_maincalendar);//xml연결

        //----------bottom bar 아이디(애정추가)----------//
        ibtnMall = findViewById(R.id.mall_bottom_bar);
        ibtnHome = findViewById(R.id.home_bottom_bar);
        ibtnMypage = findViewById(R.id.mypage_bottom_bar);
        //-------------------------------------------//


        userId = ShareVar.sharvarUserId;//사용자 아이디를 받아옴
        urlIp = ShareVar.urlIp;//아이피 받아옴
        urlAddr = "http://"+urlIp+":8080/test/supiaCalendarSelectMens.jsp";//jsp주소
        urlAddr = urlAddr + "?userId="+ userId;

        tvDday = findViewById(R.id.tv_maincalendar_mensDday);
        btnedit = findViewById(R.id.btn_maincalendar_mensedit);
        gotosub = findViewById(R.id.btn_maincalendar_gotosub);
        materialCalendarView_main =findViewById(R.id.materialcalendar_maincalendar);


        //애정추가-----------------//
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //---------------------//

        tvDday.setText("월경"+Dday+"일전");

//        EventDecorator eventDecorator = new EventDecorator();
//        materialCalendarView_main.addDecorator(eventDecorator);

        gotosub.setOnClickListener(new View.OnClickListener() {//sub페이지로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCalendar.this, SubCalendar.class);
                startActivity(intent);
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {//월경일 편집- datepicker
            @Override
            public void onClick(View v) {
                MensEdit dialog = new MensEdit(MainCalendar.this);
                dialog.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        connectGetData();
    }

    private void connectGetData() {
        try {

            CalendarNetworkTask networkTask = new CalendarNetworkTask(MainCalendar.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            dates = (HashSet<CalendarDay>) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //애정존-----------------------------------

    //--------------------------------------바텀바 마이페이지 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MainCalendar.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MainCalendar.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼필몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MainCalendar.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//



}