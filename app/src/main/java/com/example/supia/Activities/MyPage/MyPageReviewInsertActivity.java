package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.supia.Activities.Calendar.MainCalendar;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.NetworkTask.MyPage.MyReviewInsertNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

public class MyPageReviewInsertActivity extends Activity {


    //filed
    TextView tvMypage, tvSubscribe, tvOrder; // header
    ImageButton ibtnBack; // header
    ImageButton ibtnMall, ibtnHome, ibtnMypage; // bottom bar



    Button btnInsertReview; // 입력버튼
    String rbReviewTitle; // 후기 제목
    RadioButton rbReviewLike, rbReviewUnLike; // 후기제목 선택
    RadioGroup rgReviewTitle;
    String reviewProductName;
    int reviewProductPrice;
    int reviewProductNo;
    TextView tvReviewProductName;
    String url;
    String urlIp = ShareVar.urlIp;
    int reviewOrderId;

    EditText etWriteReviewwrite;
    TextView tvCharnumReviewwrite, tvProductNameReviewwrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_review_insert);


        Intent intent = getIntent();

        reviewProductName = intent.getStringExtra("productName");
        reviewProductPrice = intent.getIntExtra("productPrice", 0);
        reviewProductNo = intent.getIntExtra("productNo", 0);
        reviewOrderId = intent.getIntExtra("orderNo", 0);
        tvReviewProductName = findViewById(R.id.tv_productname_review);
        tvReviewProductName.setText(reviewProductName);


        tvCharnumReviewwrite =  findViewById(R.id.tv_showtext_myreview);
        etWriteReviewwrite = findViewById(R.id.et_insertcontent_myreview);


        rbReviewLike = findViewById(R.id.rb_good_myreview);
        rbReviewUnLike = findViewById(R.id.rb_bad_myreview);
        rgReviewTitle = findViewById(R.id.rg_inserttitle_myreview);

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


        btnInsertReview = findViewById(R.id.btn_reviewinsert_myreview);
        etWriteReviewwrite = findViewById(R.id.et_insertcontent_myreview);

        //---------------클릭이벤트--------------------//
        ibtnBack.setOnClickListener(backClickListener); //header 뒤로가기
        tvMypage.setOnClickListener(myPageClickListener); //header 마이페이지
        tvSubscribe.setOnClickListener(subscribeClickListener); //header 정기구독
        tvOrder.setOnClickListener(orderClickListener); //header 주문내역
        ibtnMypage.setOnClickListener(bottomMypageClickListener); //bottombar 마이페이지
        ibtnHome.setOnClickListener(bottomHomeClickListener); // bottombar 홈
        ibtnMall.setOnClickListener(bottomMallClickListener); //bottombar 쇼핑몰
        btnInsertReview.setOnClickListener(insertReviewClickListener);
        //--------------------------------------------//


        //----------------------------리뷰작성시 글자수 보여주기------------------------------//
        etWriteReviewwrite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = etWriteReviewwrite.getText().toString();
                tvCharnumReviewwrite.setText(input.length() + " / 300 글자");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //-----------------------------------------------------------------------------//


    }//------------------onCreate


    //---------------------------입력 버튼-----------------------------//
    View.OnClickListener insertReviewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (rbReviewLike.isChecked() == true) {
                rbReviewTitle = rbReviewLike.getText().toString();

            } else if (rbReviewUnLike.isChecked() == true) {
                rbReviewTitle = rbReviewUnLike.getText().toString();
            }

            String reviewContent = etWriteReviewwrite.getText().toString();
            String reviewTitle = rbReviewTitle;

            url = "http://" + urlIp + ":8080/test/supiaReviewInsert.jsp?reviewContent=" + reviewContent + "&reviewTitle=" + reviewTitle + "&productNo=" + reviewProductNo + "&orderId=" + reviewOrderId + "&productName=" + reviewProductName;
            Log.v("보내유", url);
            connectInsertData();

            Intent intent = new Intent(MyPageReviewInsertActivity.this, MyOrderActivity.class);
            intent.putExtra("reviewTitle", reviewTitle);
            intent.putExtra("reviewContent", reviewContent);
            intent.putExtra("productNo", reviewProductNo);
            intent.putExtra("orderId", reviewOrderId);
            startActivity(intent);

        }
    };
    //--------------------------------------------------------------//


    //--------------------------------------바텀바 홈 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomHomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoHomePage = new Intent(MyPageReviewInsertActivity.this, MainCalendar.class);
            startActivity(gotoHomePage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //--------------------------------------바텀바 쇼핑몰 클릭 이벤트 애정추가----------------------------------//
    View.OnClickListener bottomMallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMallPage = new Intent(MyPageReviewInsertActivity.this, ProductMainActivity.class);
            startActivity(gotoMallPage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//


    //--------------------------------------바텀바 마이페이지 클릭 이벤트----------------------------------//
    View.OnClickListener bottomMypageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoMainMypage = new Intent(MyPageReviewInsertActivity.this, MyPageMainActivity.class);
            startActivity(gotoMainMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold);

        }
    };
    //---------------------------------------------------------------------------------------------//

    //----------------------------------뒤로가기 버튼 이벤트----------------------------------//
    View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (etWriteReviewwrite.getText().length() >= 1) { // 작성중이던 후기 글자가 1이상이면 경고창띄워주기
                new AlertDialog.Builder(MyPageReviewInsertActivity.this)
                        .setTitle("경고")
                        .setMessage("지금 돌아가면 실행중이던 작업이 취소됩니다.\n 계속 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("아니오", null)
                        .setNegativeButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                overridePendingTransition(R.anim.hold, R.anim.fadein);

                                onBackPressed();
                            }
                        }).show();

            } else { // 아닐땐 그냥 뒤로가기 실행
                overridePendingTransition(R.anim.hold, R.anim.fadein);
                onBackPressed();

            }


        }
    };
    //-----------------------------------------------------------------------------------//

    //-----------------------------------header OrderList 이동--------------------------------//
    View.OnClickListener orderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent headerforOrder = new Intent(MyPageReviewInsertActivity.this, MyOrderActivity.class);
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
            Intent headerSubscribe = new Intent(MyPageReviewInsertActivity.this, MySubscribeActivity.class);
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
            Intent forMypage = new Intent(MyPageReviewInsertActivity.this, MyPageMainActivity.class);
            tvMypage.setTypeface(tvMypage.getTypeface(), Typeface.BOLD);
            startActivity(forMypage);
            overridePendingTransition(R.anim.hold, R.anim.hold); //화면전환시 애니메이션 적용
        }
    };
    //------------------------------------------------------------------------------------//



    //---------------------------------------------connectInsertData-------------------------------------------//
    private void connectInsertData() {
        try {
            MyReviewInsertNetworkTask insertworkTask = new MyReviewInsertNetworkTask(MyPageReviewInsertActivity.this, url, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //---------------------------------------------------------------------------------------------------------//

}//--------------------