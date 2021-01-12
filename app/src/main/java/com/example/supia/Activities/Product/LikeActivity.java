package com.example.supia.Activities.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;
import java.util.ArrayList;

public class LikeActivity extends Activity {

    final static String TAG = "서치액티비티";


    String urlAddr = null;


    int likeProductId;
    String likeCheck;
    ArrayList<ProductDto> product;
    ProductAdapter adapter = null;

    private RecyclerView recyclerView = null;

    private RecyclerView.LayoutManager layoutManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        recyclerView = findViewById(R.id.rl_product_category);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        overridePendingTransition(R.anim.fadeout, R.anim.fadein);

        //받아오는 ip값
        Intent intent = getIntent();



        likeProductId = intent.getIntExtra("likeProductId",0);
        likeCheck = intent.getStringExtra("likeCheck");



        Log.d(TAG,"이거 likeProductId" + likeProductId);
        Log.d(TAG,"이거 likeCheck" + likeCheck);


        if (likeCheck.equals("null")){
        urlAddr = "http://"+ ShareVar.urlIp +":8080/test/insertlike.jsp?";
        urlAddr = urlAddr + "likeProductId=" + likeProductId + "&likeCheck=1";
        connectGetData();
        }else {
            urlAddr = "http://"+ ShareVar.urlIp +":8080/test/updatelike.jsp?";
            urlAddr = urlAddr + "likeProductId=" + likeProductId + "&likeCheck=" + likeCheck;
            connectUpdateData();
        }

        intent = new Intent(LikeActivity.this, CategoryActivity.class);
        intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
        startActivity(intent);


    }



    @Override
    protected void onResume() {
        super.onResume();


//        connectGetData();
        registerForContextMenu(recyclerView);



    }




    private void connectGetData() {
        try {
            NetworkTaskProduct networkTaskLike = new NetworkTaskProduct(LikeActivity.this, urlAddr, "like");
            networkTaskLike.execute().get();
            Object obj = networkTaskLike.execute().get();

            product = (ArrayList<ProductDto>) obj;
            adapter = new ProductAdapter(LikeActivity.this, R.layout.listlayout_cart, product);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void connectUpdateData() {
        try {
            NetworkTaskProduct updateworkTask = new NetworkTaskProduct(LikeActivity.this, urlAddr, "like");
            updateworkTask.execute().get();
            Object obj = updateworkTask.execute().get();


            product = (ArrayList<ProductDto>) obj;
            adapter = new ProductAdapter(LikeActivity.this, R.layout.listlayout_cart, product);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}