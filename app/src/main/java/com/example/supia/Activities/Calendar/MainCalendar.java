package com.example.supia.Activities.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.supia.Activities.MyPage.MyPageMainActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class MainCalendar extends FragmentActivity {

    public String urlAddr, urlIp, userId;

    public TextView tvDday;
    public Button btnedit;
    public ImageButton gotosub;
    public MaterialCalendarView materialCalendarView_main;
    public String strcalendarStratDate, strcalendarFinishDate, strcalendarDeliveryDate, strcalendarBirthDate;

    public String Dday;
    public String CurrentStartDay,LastFinishDay;

    myDBHelper databaseHelper;

    private HashSet<CalendarDay> dates;

    public static String TAG = "메인캘린더";
    public int intdelyear, intstayear, intfinyear, intbiryear,
            intdelmonth, intstamonth, intfinmonth, intbirmonth,
            intdelday, intstaday, intfinday, intbirday;

    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar (애정추가)
    View drdeli;
    Drawable drbirth;
    Drawable drline;

    @SuppressLint("ResourceType")
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
        urlAddr = "http://" + urlIp + ":8080/test/supiaCalendarSelectMens.jsp";//jsp주소
        urlAddr = urlAddr + "?userId=" + userId;

        tvDday = findViewById(R.id.tv_maincalendar_mensDday);
        btnedit = findViewById(R.id.btn_maincalendar_mensedit);
        gotosub = findViewById(R.id.btn_maincalendar_gotosub);
        materialCalendarView_main = findViewById(R.id.materialcalendar_maincalendar);

        connectGetData();//mySQL 연결

        strcalendarStratDate = ShareVar.calendarsharvarStartdate;
        strcalendarFinishDate = ShareVar.calendarsharvarFinishdate;
        strcalendarDeliveryDate = ShareVar.calendarsharvarDeliverydate;
        strcalendarBirthDate = ShareVar.calendarsharvarBirthdate;
        Log.v(TAG, "쉐어바데이트" + strcalendarStratDate + strcalendarFinishDate + strcalendarDeliveryDate + strcalendarBirthDate);

        String [] strarray = strcalendarStratDate.split("-");
        String [] strarray2 = strcalendarFinishDate.split("-");
        String [] strarray3 = strcalendarDeliveryDate.split("-");
        String [] strarray4 = strcalendarBirthDate.split("-");

        intstayear = Integer.parseInt(strarray[0]);
        intstamonth = Integer.parseInt(strarray[1]) - 1;
        intstaday = Integer.parseInt(strarray[2]);

        intfinyear = Integer.parseInt(strarray2[0]);
        intfinmonth = Integer.parseInt(strarray2[1]) - 1;
        intfinday = Integer.parseInt(strarray2[2]);

        intdelyear = Integer.parseInt(strarray3[0]);
        intdelmonth = Integer.parseInt(strarray3[1]) - 1;
        intdelday = Integer.parseInt(strarray3[2]);

        intbiryear = Integer.parseInt(strarray4[0]);
        intbirmonth = Integer.parseInt(strarray4[1]) - 1;
        intbirday = Integer.parseInt(strarray4[2]);
        //애정추가-----------------//
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //---------------------//

        Dday = "23";
        tvDday.setText("월경   " + Dday + "   일전");

        //materialCalendarView_main.setBackgroundResource(R.drawable.calendar_background_total);

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intdelyear, intdelmonth, intdelday));
        materialCalendarView_main.addDecorator(new EventDecoratorDraw(MainCalendar.this, Collections.singleton(CalendarDay.from(intdelyear, intdelmonth, intdelday))));

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intbiryear, intbirmonth, intbirday));
        materialCalendarView_main.addDecorator(new EventDecoratorDraw2(MainCalendar.this, Collections.singleton(CalendarDay.from(intbiryear, intbirmonth, intbirday))));

        for (int i=intstaday; i<=intfinday; i++) {
            materialCalendarView_main.setSelectedDate(CalendarDay.from(intstayear, intstamonth, i));
            materialCalendarView_main.addDecorator(new EventDecorator(MainCalendar.this, Collections.singleton(CalendarDay.from(intstayear, intstamonth, i))));
        }//기념일 호출하여 배경 그리기

        materialCalendarView_main.setSelectedDate(CalendarDay.today());

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
                mensUpdate dialog = new mensUpdate(MainCalendar.this);
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void connectGetData() {
        try {

            CalendarNetworkTask networkTask = new CalendarNetworkTask(MainCalendar.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            dates = (HashSet<CalendarDay>) obj;
            strcalendarStratDate = dates.toString();
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