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
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductDetailActivity;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.Adapter.MyPage.MyLikeListAdapter;
import com.example.supia.Adapter.Product.MainAdapter;
import com.example.supia.Dto.MyPage.MyLikeListDto;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.MyPage.MyPageLikeListNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyLikeListActivity extends Activity {

    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    String userId = ShareVar.sharvarUserId;
    String urlIp = ShareVar.urlIp;

    String TAG ="마이라이크리스트액티비티";


    //Recycler
    RecyclerView recyclerView;
    ArrayList<MyLikeListDto> like;
    ArrayList<ProductDto> cart;
    RecyclerView.LayoutManager layoutManager = null;
    MyLikeListAdapter adapter = null;



    String url = "http://"+urlIp+":8080/test/supiaLikeList.jsp?userId="+userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_list);

        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        ActivityCompat.requestPermissions(MyLikeListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)

        Log.v("라이크리스트",url);

        //-----------For Recycler----------------//
        like = new ArrayList<MyLikeListDto>();
        recyclerView = findViewById(R.id.mypage_likelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //---------------------------------------//


        //----------header 아이디----------//
        ibtnBack = findViewById(R.id.ibtn_back_mypage_header); //뒤로가기
        tvMypage = findViewById(R.id.tv_mypage_mypage_header); //마이페이지
        tvSubscribe = findViewById(R.id.tv_subscribe_mypage_header); //정기구독
        tvOrder = findViewById(R.id.tv_order_mypage_header); //주문내역
        //-------------------------------------//

        //---------------클릭이벤트--------------------//
        ibtnBack.setOnClickListener(backClickListener); //header 뒤로가기
        tvMypage.setOnClickListener(myPageClickListener); //header 마이페이지
        tvSubscribe.setOnClickListener(subscribeClickListener); //header 정기구독
        tvOrder.setOnClickListener(orderClickListener); //header 주문내역

        //------------------------------------------//



        //------------------------------------사진 불러오기---------------------------------------//
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(MyLikeListActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)
        //------------------------------------------------------------------------------------//

        getdata();


    }//---------------------onCreate


    @Override
    protected void onResume() {
        super.onResume();
        getdata();
        /////////////////////////////////////////보람 추가 - 값 받아가요///////////////////////////////////////////////////////
        adapter.setOnItemClickListener(new MyLikeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MyLikeListActivity.this, ProductDetailActivity.class);

                //-- fragment1로 값 전달

                Log.v(TAG,""+like.get(position).getProductNo());
                intent.putExtra("urlIp",ShareVar.urlIp);
                intent.putExtra("productNo", like.get(position).getProductNo());
                intent.putExtra("productName", like.get(position).getProductName());
                intent.putExtra("productPrice", like.get(position).getProductPrice());
                intent.putExtra("productBrand", like.get(position).getProductBrand());
                intent.putExtra("productImagePath", like.get(position).getProductImagePath());
                intent.putExtra("productInfo", like.get(position).getProductInfo());
//                Log.v(TAG,"productName " + cart.get(position).getProductName());

                startActivity(intent);

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }


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
            Intent headerforOrder = new Intent(MyLikeListActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyLikeListActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyLikeListActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//



    //------------------------------------------------getdata------------------------------------------------//
    private void getdata() {
        try {
            MyPageLikeListNetworkTask networkTask = new MyPageLikeListNetworkTask(MyLikeListActivity.this, url, "select");
            Object obj = networkTask.execute().get();
            like = (ArrayList<MyLikeListDto>) obj;

            adapter = new MyLikeListAdapter(MyLikeListActivity.this, R.layout.activity_my_like_list, like,cart);
            recyclerView.setAdapter(adapter); //리사이클러뷰 어댑터 연결


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------------------------------------//


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyLikeListActivity.this, MyPageMainActivity.class);
        startActivity(intent);
    } // 뒤로가기 버튼 클릭했을 때 메인으로



}//-----------------------끝