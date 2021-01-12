package com.example.supia.Activities.Product;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supia.Activities.ui.main.SectionsPagerAdapter;
import com.example.supia.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;


public class ProductDetailActivity extends AppCompatActivity {

    TextView tvMinusBottomsheet, tvProductQuantityNumBottomsheet, tvPlusBottomsheet;


    int productNo;
    String productName;
    String productPrice;
    String productBrand;
    String productImagePath;
    String urlIp;


    private ViewPager mViewPager;
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //값 받아오기
        Intent intent = getIntent();
        urlIp= intent.getStringExtra("urlIp");
        productNo = intent.getIntExtra("productNo", 0);
        productName= intent.getStringExtra("productName");
        productPrice= intent.getStringExtra("productPrice");
        productBrand= intent.getStringExtra("productBrand");
        productImagePath= intent.getStringExtra("productImagePath");



        //탭
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        adapter.addFragment(new ProductDetilFragment(urlIp,productNo,productName,productPrice,productBrand,productImagePath), "상품설명");
        adapter.addFragment(new PurchaseFragment(), "구매정보");
        adapter.addFragment(new ReviewFragment(urlIp,productNo), "리뷰");
        adapter.addFragment(new QnAFragment(urlIp,productNo,productName), "Q & A");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //---


        //---수량
        tvMinusBottomsheet = findViewById(R.id.minus_bottomsheet);
        tvProductQuantityNumBottomsheet = findViewById(R.id.productQuantityNum_bottomsheet);
        tvPlusBottomsheet = findViewById(R.id.plus_bottomsheet);

        tvMinusBottomsheet.setOnClickListener(minusClick);
        tvPlusBottomsheet.setOnClickListener(plusClick);
        //---



//-------바텀시트--------
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

// init the bottom sheet behavior
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

// change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
        bottomSheetBehavior.setPeekHeight(230);

// set hideable or not
        bottomSheetBehavior.setHideable(false);

// set callback for changes
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });




    }

    View.OnClickListener plusClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int quan = Integer.parseInt(tvProductQuantityNumBottomsheet.getText().toString());
            int quanNum = quan + 1;
            String quantityNum = Integer.toString(quanNum);
            tvProductQuantityNumBottomsheet.setText(quantityNum);

            if (quanNum == 11) {

                new AlertDialog.Builder(ProductDetailActivity.this)
                        .setTitle("경고")
                        .setMessage("최대 구입 가능 수량은 10개 입니다")
                        .setPositiveButton("확인", null)
                        .setCancelable(true)
                        .show();

                quantityNum = Integer.toString(quanNum-1);
                tvProductQuantityNumBottomsheet.setText(quantityNum);

            }

        }

    };

    View.OnClickListener minusClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int quan = Integer.parseInt(tvProductQuantityNumBottomsheet.getText().toString());
            int quanNum = quan - 1;
            String quantityNum = Integer.toString(quanNum);
            tvProductQuantityNumBottomsheet.setText(quantityNum);

            if (quanNum == 0){
                new AlertDialog.Builder(ProductDetailActivity.this)
                        .setTitle("경고")
                        .setMessage("최소 구입 수량은 1개 입니다")
                        .setPositiveButton("확인",null)
                        .setCancelable(true)
                        .show();

                quantityNum = Integer.toString(quanNum+1);
                tvProductQuantityNumBottomsheet.setText(quantityNum);
            }


        }
    };


}//끄읕