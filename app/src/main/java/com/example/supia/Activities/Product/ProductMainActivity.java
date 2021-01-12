package com.example.supia.Activities.Product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.supia.Adapter.Banner.BannerAdapter;
import com.example.supia.R;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProductMainActivity extends AppCompatActivity {

    final static String TAG = "메인엑티비티";

    ViewPager viewPager;
    int currentPage = 0;
    private BannerAdapter bannerAdapter;
    Timer timer;
    final long DELAY_MS = 1000;//작업실행전 시간
    final long PERIOD_MS = 3000; //배너사이 간격 시간

    String urlIp = null;
    ImageButton ibtnSearchActivity;//검색버튼

    Button btnCategory1,btnCategory2,btnCategory3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist



        //뷰페이저 선언
        viewPager = findViewById(R.id.autoViewPager);
        bannerAdapter = new BannerAdapter(this, data);
        viewPager.setAdapter(bannerAdapter); //Auto Viewpager에 Adapter 장착

        ibtnSearchActivity = findViewById(R.id.search_header);



        btnCategory1 = findViewById(R.id.btn_category1_product);
        btnCategory2 = findViewById(R.id.btn_category2_product);
        btnCategory3 = findViewById(R.id.btn_category3_product);




        ibtnSearchActivity.setOnClickListener(searchOnClickListener);

        btnCategory1.setOnClickListener(btnCategoryOnClickListener);
        btnCategory2.setOnClickListener(btnCategoryOnClickListener);
        btnCategory3.setOnClickListener(btnCategoryOnClickListener);


        //오토스크롤
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 4) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    View.OnClickListener searchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
    };


    View.OnClickListener btnCategoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btn_category1_product:
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory1 = btnCategory1.getText().toString().trim();
                    Log.v(TAG, strBtnCategory1);


                    intent.putExtra("strBtnCategory", strBtnCategory1);
                    startActivity(intent);

                    break;


                case R.id.btn_category2_product:
                    Intent intent1 = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory2 = (String)btnCategory2.getText();
                    intent1.putExtra("strBtnCategory", strBtnCategory2);
                    startActivity(intent1);
                    break;


                case R.id.btn_category3_product:
                    Intent intent2 = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory3 = (String)btnCategory3.getText();
                    intent2.putExtra("strBtnCategory", strBtnCategory3);
                    startActivity(intent2);
                    break;
            }
        }
    };



}