package com.example.supia.Activities.Calendar;

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

import com.example.supia.Activities.MyPage.MyCartListActivity;
import com.example.supia.Activities.MyPage.MyPageMainActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Collections;
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


    private HashSet<CalendarDay> dates;

    public static String TAG = "메인캘린더";
    public int intdelyear, intstayear, intfinyear, intbiryear,
            intdelmonth, intstamonth, intfinmonth, intbirmonth,
            intdelday, intstaday, intfinday, intbirday;


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
        urlAddr = "http://" + urlIp + ":8080/test/supiaCalendarSelectMens.jsp";//jsp주소
        urlAddr = urlAddr + "?userId=" + userId;

        tvDday = findViewById(R.id.tv_maincalendar_mensDday);
        btnedit = findViewById(R.id.btn_maincalendar_mensedit);
        gotosub = findViewById(R.id.btn_maincalendar_gotosub);
        materialCalendarView_main = findViewById(R.id.materialcalendar_maincalendar);

        connectGetData();

        strcalendarStratDate = ShareVar.calendarsharvarStartdate;
        strcalendarFinishDate = ShareVar.calendarsharvarFinishdate;
        strcalendarDeliveryDate = ShareVar.calendarsharvarDeliverydate;
        strcalendarBirthDate = ShareVar.calendarsharvarBirthdate;
        Log.v(TAG, "쉐어바데이트" + strcalendarStratDate + strcalendarFinishDate + strcalendarDeliveryDate + strcalendarBirthDate);

        String [] strarray = strcalendarStratDate.split("-");
        String [] strarray2 = strcalendarFinishDate.split("-");
        String [] strarray3 = strcalendarDeliveryDate.split("-");
        String [] strarray4 = strcalendarBirthDate.split("-");

        intdelyear = Integer.parseInt(strarray[0]);
        intdelmonth = Integer.parseInt(strarray[1]) - 1;
        intdelday = Integer.parseInt(strarray[2]);

        intbiryear = Integer.parseInt(strarray2[0]);
        intbirmonth = Integer.parseInt(strarray2[1]) - 1;
        intbirday = Integer.parseInt(strarray2[2]);

        intstayear = Integer.parseInt(strarray3[0]);
        intstamonth = Integer.parseInt(strarray3[1]) - 1;
        intstaday = Integer.parseInt(strarray3[2]);

        intfinyear = Integer.parseInt(strarray4[0]);
        intfinmonth = Integer.parseInt(strarray4[1]) - 1;
        intfinday = Integer.parseInt(strarray4[2]);
        //애정추가-----------------//
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //---------------------//

        tvDday.setText("월경" + Dday + "일전");

        //materialCalendarView_main.setBackgroundResource(R.drawable.calendar_background_total);

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intdelyear, intdelmonth, intdelday));
        materialCalendarView_main.addDecorator(new EventDecorator(Color.YELLOW, Collections.singleton(CalendarDay.from(intdelyear, intdelmonth, intdelday))));

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intbiryear, intbirmonth, intbirday));
        materialCalendarView_main.addDecorator(new EventDecorator(Color.BLUE, Collections.singleton(CalendarDay.from(intbiryear, intbirmonth, intbirday))));

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intstayear, intstamonth, intstaday));
        materialCalendarView_main.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(intstayear, intstamonth, intstaday))));

        materialCalendarView_main.setSelectedDate(CalendarDay.from(intfinyear, intfinmonth, intfinday));
        materialCalendarView_main.addDecorator(new EventDecorator(Color.GREEN, Collections.singleton(CalendarDay.from(intfinyear, intfinmonth, intfinday))));

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

    }

    private void connectGetData() {
        try {

            CalendarNetworkTask networkTask = new CalendarNetworkTask(MainCalendar.this, urlAddr, "select");
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