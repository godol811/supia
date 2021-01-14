package com.example.supia.Activities.Calendar;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.supia.Activities.MyPage.MyPageMainActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Collections;
import java.util.HashSet;

public class SubCalendar extends FragmentActivity {

    public String urlAddr,urlIp,userId;
    MaterialCalendarView materialCalendarView_sub;
    Button btnedit;
    TextView tvmensCycle, tvmensTerm, tvDeliveryDay;

    private HashSet<CalendarDay> dates;

    public String strcalendarStratDate, strcalendarFinishDate, strcalendarDeliveryDate, strcalendarBirthDate;
    String[] strarray;
    String[] strarray2;
    String[] strarray3;
    String[] strarray4;

    public int intdelyear, intstayear, intfinyear, intbiryear,
            intdelmonth, intstamonth, intfinmonth, intbirmonth,
            intdelday, intstaday, intfinday, intbirday;

    public static String TAG ="서브캘린더";


    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar (애정추가)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_subcalendar);

        userId = ShareVar.sharvarUserId;//사용자 아이디를 받아옴
        urlIp = ShareVar.urlIp;//아이피 받아옴
        urlAddr = "http://"+urlIp+":8080/test/supiaCalendarSelectMens.jsp";//jsp주소
        urlAddr = urlAddr + "?userId="+ userId;

        materialCalendarView_sub =findViewById(R.id.subcalendar_calendarView);

        btnedit = findViewById(R.id.btn_edit_subcalendar);
        tvmensCycle = findViewById(R.id.tv_average_menscycle_sucalendar);
        tvmensTerm = findViewById(R.id.tv_average_mensterm_sucalendar);
        tvDeliveryDay = findViewById(R.id.tv_deliveryday_subcalendar);

        //----------bottom bar 아이디(애정추가)----------//
        ibtnMall = findViewById(R.id.mall_bottom_bar);
        ibtnHome = findViewById(R.id.home_bottom_bar);
        ibtnMypage = findViewById(R.id.mypage_bottom_bar);
        //-------------------------------------------//

        connectGetData();


        strcalendarStratDate = ShareVar.calendarsharvarStartdate;
        strcalendarFinishDate = ShareVar.calendarsharvarFinishdate;
        strcalendarDeliveryDate = ShareVar.calendarsharvarDeliverydate;
        strcalendarBirthDate = ShareVar.calendarsharvarBirthdate;
        Log.v(TAG, "쉐어바데이트" + strcalendarStratDate + strcalendarFinishDate + strcalendarDeliveryDate + strcalendarBirthDate);

        strarray = strcalendarStratDate.split("-");
        strarray2 = strcalendarFinishDate.split("-");
        strarray3 = strcalendarDeliveryDate.split("-");
        strarray4 = strcalendarBirthDate.split("-");


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

        tvmensCycle.setText(Integer.toString(27));
        tvmensTerm.setText(Integer.toString(intfinday-intstaday));
        tvDeliveryDay.setText(Integer.toString(intdelday));
        //애정추가-----------------//
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //---------------------//




        materialCalendarView_sub.setSelectedDate(CalendarDay.from(intdelyear, intdelmonth, intdelday));
        materialCalendarView_sub.addDecorator(new EventDecoratorDraw(SubCalendar.this,Collections.singleton(CalendarDay.from(intdelyear, intdelmonth, intdelday))));

        materialCalendarView_sub.setSelectedDate(CalendarDay.from(intbiryear, intbirmonth, intbirday));
        materialCalendarView_sub.addDecorator(new EventDecoratorDraw2(SubCalendar.this, Collections.singleton(CalendarDay.from(intbiryear, intbirmonth, intbirday))));

        materialCalendarView_sub.setSelectedDate(CalendarDay.from(intstayear, intstamonth, intstaday));
        materialCalendarView_sub.addDecorator(new EventDecorator(SubCalendar.this, Collections.singleton(CalendarDay.from(intstayear, intstamonth, intstaday))));

        materialCalendarView_sub.setSelectedDate(CalendarDay.from(intfinyear, intfinmonth, intfinday));
        materialCalendarView_sub.addDecorator(new EventDecorator(SubCalendar.this, Collections.singleton(CalendarDay.from(intfinyear, intfinmonth, intfinday))));


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker dialog = new datePicker(SubCalendar.this);
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

            CalendarNetworkTask networkTask = new CalendarNetworkTask(SubCalendar.this, urlAddr,"select");
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
            Intent gotoMainMypage = new Intent(SubCalendar.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(SubCalendar.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼필몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(SubCalendar.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//



}

