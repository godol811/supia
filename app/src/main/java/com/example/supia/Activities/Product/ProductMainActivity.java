package com.example.supia.Activities.Product;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.MyPage.MyOrderActivity;
import com.example.supia.Activities.MyPage.MyPageMainActivity;
import com.example.supia.Adapter.Banner.BannerAdapter;
import com.example.supia.Adapter.Product.MainAdapter;
import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProductMainActivity extends Activity {


    final static String TAG = "메인엑티비티";

    ViewPager viewPager;
    int currentPage = 0;
    private BannerAdapter bannerAdapter;
    Timer timer;
    final long DELAY_MS = 1000;//작업실행전 시간
    final long PERIOD_MS = 3000; //배너사이 간격 시간


    ImageButton ibtnSearchActivity,ibtnCartActivity;//검색버튼
    TextView tvMore;

    Button btnCategory1,btnCategory2,btnCategory3;


    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar (애정추가)

    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;


    String urlAddr = null;


    ArrayList<ProductDto> product;
    MainAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist


        ActivityCompat.requestPermissions(ProductMainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)

        recyclerView = findViewById(R.id.rl_product_main);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }





        //뷰페이저 선언
        viewPager = findViewById(R.id.autoViewPager);
        bannerAdapter = new BannerAdapter(this, data);
        viewPager.setAdapter(bannerAdapter); //Auto Viewpager에 Adapter 장착

        ibtnSearchActivity = findViewById(R.id.search_header);
        ibtnCartActivity = findViewById(R.id.cart_header);



        btnCategory1 = findViewById(R.id.btn_category1_product);
        btnCategory2 = findViewById(R.id.btn_category2_product);
        btnCategory3 = findViewById(R.id.btn_category3_product);
        tvMore =findViewById(R.id.tv_more_orederlist); //---애츄 추가 (더보기 클릭시 주문내역으로)

        //----------bottom bar 아이디(애정추가)----------//
        ibtnMall = findViewById(R.id.mall_bottom_bar);
        ibtnHome = findViewById(R.id.home_bottom_bar);
        ibtnMypage = findViewById(R.id.mypage_bottom_bar);
        //-------------------------------------------//

        ibtnSearchActivity.setOnClickListener(searchOnClickListener);
        ibtnCartActivity.setOnClickListener(cartOnClickListener);

        btnCategory1.setOnClickListener(btnCategoryOnClickListener);
        btnCategory2.setOnClickListener(btnCategoryOnClickListener);
        btnCategory3.setOnClickListener(btnCategoryOnClickListener);
        tvMore.setOnClickListener(tvMoreClickListener);

        //애정추가-----------------//
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        //---------------------//


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






        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/recentlyorderlist.jsp";
        urlAddr = urlAddr + "?userId=" + ShareVar.sharvarUserId;

        connectGetData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
        /////////////////////////////////////////보람 추가 - 값 받아가요///////////////////////////////////////////////////////
       adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(View v, int position) {
               Intent intent = new Intent(ProductMainActivity.this, ProductDetailActivity.class);

               Log.v(TAG,"전달 전");
               //-- fragment1로 값 전달

               intent.putExtra("urlIp",ShareVar.urlIp);
               intent.putExtra("productNo", product.get(position).getProductNo());
               intent.putExtra("productName", product.get(position).getProductName());
               intent.putExtra("productPrice", product.get(position).getProductPrice());
               intent.putExtra("productBrand", product.get(position).getProductBrand());
               intent.putExtra("productImagePath", product.get(position).getProductImagePath());
               intent.putExtra("productInfo", product.get(position).getProductInfo());
               Log.v(TAG,"productName " + product.get(position).getProductName());

               startActivity(intent);

           }
       });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    View.OnClickListener tvMoreClickListener = new View.OnClickListener() { // 더보기 클릭시 주문내역 페이지로 이동 (애츄추가)
        @Override
        public void onClick(View v) {

            Intent more = new Intent(ProductMainActivity.this, MyOrderActivity.class);
            startActivity(more);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };

    View.OnClickListener searchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.hold, R.anim.hold);
        }
    };


    View.OnClickListener cartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(ProductMainActivity.this, CartActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.hold, R.anim.hold);
        }
    } ;



    View.OnClickListener btnCategoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btn_category1_product:
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory1 = btnCategory1.getText().toString().trim();


                    intent.putExtra("strBtnCategory", strBtnCategory1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.hold, R.anim.hold);

                    break;


                case R.id.btn_category2_product:
                    Intent intent1 = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory2 = (String)btnCategory2.getText();
                    intent1.putExtra("strBtnCategory", strBtnCategory2);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.hold, R.anim.hold);
                    break;


                case R.id.btn_category3_product:
                    Intent intent2 = new Intent(getApplicationContext(), CategoryActivity.class);
                    String strBtnCategory3 = (String)btnCategory3.getText();
                    intent2.putExtra("strBtnCategory", strBtnCategory3);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.hold, R.anim.hold);
                    break;
            }
        }
    };





    //--------------------------------------바텀바 마이페이지 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(ProductMainActivity.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(ProductMainActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

   //--------------------------------------바텀바 쇼필몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(ProductMainActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//
    private void connectGetData() {
        try {

            NetworkTaskProduct networkTask = new NetworkTaskProduct(ProductMainActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            product = (ArrayList<ProductDto>) obj;


            adapter = new MainAdapter(ProductMainActivity.this, R.layout.listlayout_main, product);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}