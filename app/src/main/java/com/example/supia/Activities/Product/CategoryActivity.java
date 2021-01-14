package com.example.supia.Activities.Product;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;


import java.util.ArrayList;

public class CategoryActivity extends Activity {

    private static String  TAG = "카테고리";


    //field
    String urlAddr = null;
    String urlIp = ShareVar.urlIp;

    String strBtnCategory;
    String array;

    ImageButton ibtnSearchActivity;//검색버튼
    ImageButton ibtnCartActivity;//장바구니버튼

    Spinner spiner;
    String selectSpiner;

//    ArrayList<ProductDto> product;
    ArrayList<ProductDto> product;
    ProductAdapter adapter = null;


    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;

    Button btnSmall,btnMedium,btnLarge,btnOverNight,btnLiner,btnsoft,btnhard,btnlite,btnregular,btnsuper,btnCotton;
    String small,medium,large,overNight,liner,soft,hard,lite,regular,strSuper,cotton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        spiner = findViewById(R.id.spinner_category);



        ActivityCompat.requestPermissions(CategoryActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)


        recyclerView = findViewById(R.id.rl_product_category);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }



        //생리대
        btnSmall = findViewById(R.id.btn_small_product);
        btnMedium = findViewById(R.id.btn_medium_product);
        btnLarge = findViewById(R.id.btn_large_product);
        btnOverNight = findViewById(R.id.btn_overnight_product);
        btnLiner = findViewById(R.id.btn_liner_product);




        //생리컵
        btnsoft = findViewById(R.id.btn_soft_product);
        btnhard = findViewById(R.id.btn_hard_product);


        //템폰
        btnlite = findViewById(R.id.btn_lite_product);
        btnregular = findViewById(R.id.btn_regular_product);
        btnsuper = findViewById(R.id.btn_super_product);

        //검색,장바군 ㅣ버튼
        ibtnSearchActivity = findViewById(R.id.search_header);//헤더 검색 버튼
        ibtnCartActivity = findViewById(R.id.cart_header);


        overridePendingTransition(R.anim.fadeout, R.anim.fadein);


        btnSmall.setOnClickListener(btnOnClickListener);
        btnMedium.setOnClickListener(btnOnClickListener);
        btnLarge.setOnClickListener(btnOnClickListener);
        btnOverNight.setOnClickListener(btnOnClickListener);
        btnLiner.setOnClickListener(btnOnClickListener);
        btnsoft.setOnClickListener(btnOnClickListener);
        btnhard.setOnClickListener(btnOnClickListener);
        btnlite.setOnClickListener(btnOnClickListener);
        btnregular.setOnClickListener(btnOnClickListener);
        btnsuper.setOnClickListener(btnOnClickListener);

        ibtnSearchActivity.setOnClickListener(ibtnOnClickListener);
        ibtnCartActivity.setOnClickListener(ibtnOnClickListener);



        Intent intent = getIntent();   //IP 받아오자

        strBtnCategory = intent.getStringExtra("strBtnCategory");//클릭한 카테고리값 받아오기

//        urlAddr = "http://" + urlIp + ":8080/test/category.jsp";
//
//        urlAddr = urlAddr + "?productCategory1=" + strBtnCategory;

//        connectGetData();


        Log.v(TAG,"strBtnCategory" + strBtnCategory);

        switch (strBtnCategory) {

            case "생리대":
                btnSmall.setVisibility(View.VISIBLE);
                btnMedium.setVisibility(View.VISIBLE);
                btnLarge.setVisibility(View.VISIBLE);
                btnOverNight.setVisibility(View.VISIBLE);
                btnLiner.setVisibility(View.VISIBLE);


                small = btnSmall.getText().toString();
                medium = btnMedium.getText().toString();
                large = btnregular.getText().toString();
                overNight = btnOverNight.getText().toString();
                liner = btnLiner.getText().toString();


                ShareVar.strBtnCategory = strBtnCategory;
                break;


            case "생리컵":
                btnsoft.setVisibility(View.VISIBLE);
                btnhard.setVisibility(View.VISIBLE);

                soft = btnsoft.getText().toString();
                hard = btnhard.getText().toString();


                ShareVar.strBtnCategory = strBtnCategory;
                break;


            case "탐폰":
                btnlite.setVisibility(View.VISIBLE);
                btnregular.setVisibility(View.VISIBLE);
                btnsuper.setVisibility(View.VISIBLE);

                lite = btnlite.getText().toString();
                regular = btnregular.getText().toString();
                strSuper = btnsuper.getText().toString();

                ShareVar.strBtnCategory = strBtnCategory;
                break;





        }

        Log.v(TAG, "urlIp : " + urlIp);

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, String.valueOf(parent.getItemAtPosition(position)));
                selectSpiner = String.valueOf(parent.getItemAtPosition(position));


                switch (selectSpiner){
                    case "최신순":

                        if(strBtnCategory.equals("생리대") || strBtnCategory.equals("생리컵") || strBtnCategory.equals("탐폰")){
                            Log.d(TAG,"최신순일떄");
                            urlAddr = "http://" + urlIp + ":8080/test/category.jsp";
                            urlAddr = urlAddr + "?productCategory1=" + strBtnCategory;

                        }

//                        if (small.equals("소형") || medium.equals("중형") || large.equals("대형")){
//                            Log.d(TAG,"태그별 드러옴");
//                            urlAddr = "http://" + urlIp + ":8080/test/categorytagprice.jsp";
//                            urlAddr = urlAddr + "?tag=" + btnSmall.getText().toString() + "&array=" + array;
//                        }
                        break;

                    case "높은가격순":
                        if(strBtnCategory.equals("생리대") || strBtnCategory.equals("생리컵") || strBtnCategory.equals("탐폰")) {
                            Log.d(TAG, "높은가격순일때");

                            array = "desc";

                            urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
                            urlAddr = urlAddr + "?productCategory=" + strBtnCategory + "&array=" + array;

                        }
                        break;

                    case "낮은가격순":
                        if(strBtnCategory.equals("생리대") || strBtnCategory.equals("생리컵") || strBtnCategory.equals("탐폰")) {
                            Log.d(TAG, "낮은가격일떄");

                            array = "asc";

                            urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
                            urlAddr = urlAddr + "?productCategory=" + strBtnCategory + "&array=" + array;

                        }
                        break;

                    case "인기순":
                        if(strBtnCategory.equals("생리대") || strBtnCategory.equals("생리컵") || strBtnCategory.equals("탐폰")) {
                            Log.d(TAG, "인기순일떄");
                            urlAddr = "http://" + urlIp + ":8080/test/categorypricelike.jsp";
                            urlAddr = urlAddr + "?productCategory1=" + strBtnCategory +"&likeUserId=" + ShareVar.sharvarUserId;

                        }
                        break;

                }
                connectGetData();

//                if (selectSpiner.equals("최신순")){
//                    Log.d(TAG,"최신순일떄");
//                    urlAddr = "http://" + urlIp + ":8080/test/category.jsp";
//                    urlAddr = urlAddr + "?productCategory1=" + strBtnCategory;
//                    connectGetData();
//
//                }else if (String.valueOf(parent.getItemAtPosition(position)).equals("높은가격순")){
//                    Log.d(TAG,"높은가격순일때");
//                    array = "desc";
//                    urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
//                    urlAddr = urlAddr + "?productCategory=" + strBtnCategory +"&array="+ array;
//                    connectGetData();
//
//                }else if (String.valueOf(parent.getItemAtPosition(position)).equals("낮은가격순")){
//                    Log.d(TAG,"낮은가격일떄");
//                    array = "asc";
//                    urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
//                    urlAddr = urlAddr + "?productCategory=" + strBtnCategory +"&array="+ array;
//                    connectGetData();
//
//                }else if (String.valueOf(parent.getItemAtPosition(position)).equals("인기순")) {
//                    Log.d(TAG,"인기순일떄");
//                    urlAddr = "http://" + urlIp + ":8080/test/categorypricelike.jsp";
//                    urlAddr = urlAddr + "?productCategory1=" + strBtnCategory;
//                    connectGetData();
//                if (small.equals("소형") && medium.equals("중형") && medium.equals("대형")){
//                    if (String.valueOf(parent.getItemAtPosition(position)).equals("최신순")){
//                        Log.d(TAG,"태그최신순일떄");
//                        urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
//                        urlAddr = urlAddr + "?tag=" + btnSmall.getText().toString();
//                        connectGetData();
//
//                    }else if (String.valueOf(parent.getItemAtPosition(position)).equals("높은가격순")){
//                        Log.d(TAG,"태그높은가격순일때");
//                        array = "desc";
//                        urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
//                        urlAddr = urlAddr + "?productCategory=" + strBtnCategory +"&array="+ array;
//                        connectGetData();
//
//                    }else if (String.valueOf(parent.getItemAtPosition(position)).equals("낮은가격순")){
//                        Log.d(TAG,"태그낮은가격일떄");
//                        array = "asc";
//                        urlAddr = "http://" + urlIp + ":8080/test/categoryprice.jsp";
//                        urlAddr = urlAddr + "?productCategory=" + strBtnCategory +"&array="+ array;
//                        connectGetData();
//
//                    }else if (String.valueOf(parent.getItemAtPosition(position)).equals("인기순")) {
//                        Log.d(TAG, "태그인기순일떄");
//                        urlAddr = "http://" + urlIp + ":8080/test/categorypricelike.jsp";
//                        urlAddr = urlAddr + "?productCategory1=" + strBtnCategory;
//                        connectGetData();
//                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    View.OnClickListener ibtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_header:
                    Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;


                case R.id.cart_header:


                    Intent intent1 = new Intent(CategoryActivity.this, CartActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    };

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btn_small_product:

                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnSmall.getText().toString() + "&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_medium_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnMedium.getText().toString() + "&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_large_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnLarge.getText().toString() +"&likeUserId=" + ShareVar.sharvarUserId;;

                    break;
                case R.id.btn_overnight_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnOverNight.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_liner_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnLiner.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;


                case R.id.btn_soft_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnsoft.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_hard_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnhard.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;


                case R.id.btn_lite_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnlite.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_regular_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnregular.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;

                    break;
                case R.id.btn_super_product:
                    urlAddr = "http://" + urlIp + ":8080/test/categorytag.jsp";
                    urlAddr = urlAddr + "?tag=" + btnsuper.getText().toString()+"&likeUserId=" + ShareVar.sharvarUserId;
                    break;


            }
            connectGetData();
            /////////////////////////////////////////보람 추가 - 값 받아가요///////////////////////////////////////////////////////
            adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(CategoryActivity.this, ProductDetailActivity.class);

                    Log.v(TAG,"전달 전");
                    //-- fragment1로 값 전달

                    intent.putExtra("urlIp",urlIp);
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
    };





    @Override
    protected void onResume() {
        super.onResume();

        connectGetData();

        registerForContextMenu(recyclerView);

        /////////////////////////////////////////보람 추가 - 값 받아가요///////////////////////////////////////////////////////
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(CategoryActivity.this, ProductDetailActivity.class);

                Log.v(TAG,"전달 전");
                //-- fragment1로 값 전달

                intent.putExtra("urlIp",urlIp);
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





    private void connectGetData() {
        try {

            NetworkTaskProduct networkTask = new NetworkTaskProduct(CategoryActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            product = (ArrayList<ProductDto>) obj;


            adapter = new ProductAdapter(CategoryActivity.this, R.layout.listlayout, product);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //배경 터치 시 키보드 사라지게
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm;
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryActivity.this, ProductMainActivity.class);
        startActivity(intent);
    } // 뒤로가기 버튼 클릭했을 때 메인으로



}