package com.example.supia.Activities.Product;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.supia.Activities.Payment.PurchaseCheckActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.Activities.ui.main.SectionsPagerAdapter;
import com.example.supia.Adapter.Product.CartAdapter;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ProductDetailActivity extends AppCompatActivity {
    final static String TAG ="프로덕트 디테일 엑티비티";

    TextView tvMinusBottomsheet, tvProductQuantityNumBottomsheet, tvPlusBottomsheet;
    Button btnCartAdd, btnBuy;


    int productNo;
    int productQuantity;
    String productName;
    String productPrice;
    String productBrand;
    String productImagePath;
    String urlIp;
    String urlAddr = null;


    private ViewPager mViewPager;
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //값 받아오기
        Intent intent = getIntent();
        urlIp = intent.getStringExtra("urlIp");
        productNo = intent.getIntExtra("productNo", 0);
        productName = intent.getStringExtra("productName");
        productPrice = intent.getStringExtra("productPrice");
        productBrand = intent.getStringExtra("productBrand");
        productImagePath = intent.getStringExtra("productImagePath");


        //탭
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        adapter.addFragment(new ProductDetilFragment(urlIp, productNo, productName, productPrice, productBrand, productImagePath), "상품설명");
        adapter.addFragment(new PurchaseFragment(), "구매정보");
        adapter.addFragment(new ReviewFragment(urlIp, productNo), "리뷰");
        adapter.addFragment(new QnAFragment(urlIp, productNo, productName), "Q & A");
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
        bottomSheetBehavior.setPeekHeight(180);

// set hideable or not
        bottomSheetBehavior.setHideable(false);


        //바텀시트 안에 버튼
        btnCartAdd = findViewById(R.id.cart_bottomsheet);
        btnCartAdd.setOnClickListener(cartClick);

        btnBuy = findViewById(R.id.buy_bottomsheet);
        btnBuy.setOnClickListener(buyClcick);

    }

    View.OnClickListener plusClick = new View.OnClickListener() { //수량 더하기
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

                quantityNum = Integer.toString(quanNum - 1);
                tvProductQuantityNumBottomsheet.setText(quantityNum);

            }

        }

    };

    View.OnClickListener minusClick = new View.OnClickListener() { // 수량 뺴기
        @Override
        public void onClick(View v) {

            int quan = Integer.parseInt(tvProductQuantityNumBottomsheet.getText().toString());
            int quanNum = quan - 1;
            String quantityNum = Integer.toString(quanNum);
            tvProductQuantityNumBottomsheet.setText(quantityNum);

            if (quanNum == 0) {
                new AlertDialog.Builder(ProductDetailActivity.this)
                        .setTitle("경고")
                        .setMessage("최소 구입 수량은 1개 입니다")
                        .setPositiveButton("확인", null)
                        .setCancelable(true)
                        .show();

                quantityNum = Integer.toString(quanNum + 1);
                tvProductQuantityNumBottomsheet.setText(quantityNum);
            }


        }
    };

    View.OnClickListener cartClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            productQuantity = Integer.parseInt(tvProductQuantityNumBottomsheet.getText().toString());

            urlAddr = "http://" + urlIp + ":8080/test/insertcart.jsp?";
            urlAddr = urlAddr + "productNo=" + productNo + "&productQuantity=" + productQuantity + "&productPrice=" + productPrice + "&productName=" + productName
                    + "&productImagePath=" + productImagePath;
            connectGetData();

            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);


        }
    };

    //-------------------------01.13 새벽 추가 ----------------------------------------------------
    View.OnClickListener buyClcick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            productQuantity = Integer.parseInt(tvProductQuantityNumBottomsheet.getText().toString());

            new AlertDialog.Builder(ProductDetailActivity.this)
                    .setMessage("지금 선택하신 정보로 정기구독이\n 가능합니다.\n\n 정기구독을 만나보세요")
                    .setPositiveButton("일반결제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(ProductDetailActivity.this, PurchaseCheckActivity.class);
                            PaymentShareVar.paymentProductNo = productNo;
                            PaymentShareVar.paymentProductName = productName;
                            PaymentShareVar.paymentProductPrice = productPrice;
                            PaymentShareVar.paymentProductQuantity = productQuantity;

                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("정기구독", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ProductDetailActivity.this, RegularPurchaseCheckActivity.class);
                            PaymentShareVar.paymentProductNo = productNo;
                            PaymentShareVar.paymentProductName = productName;
                            PaymentShareVar.paymentProductPrice = productPrice;
                            PaymentShareVar.paymentProductQuantity = productQuantity;
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("취소", null)
                    .setCancelable(true)
                    .show();
        }
    };
    //-------------------------------------------------------------------

    private void connectGetData() {
        try {
            NetworkTaskCart networkTaskCart = new NetworkTaskCart(ProductDetailActivity.this, urlAddr, "insert");
            networkTaskCart.execute().get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}//끄읕