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
    int check;
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
        check = intent.getIntExtra("check",0);
        likeCheck = intent.getStringExtra("likeCheck");


        Log.d(TAG,"likeCheck : " + likeCheck);

        Log.d(TAG,"이거 likeProductId" + likeProductId);
        Log.d(TAG,"이거 likeCheck" + likeCheck);


        if (likeCheck.equals("null")){
        urlAddr = "http://"+ ShareVar.urlIp +":8080/test/insertlike.jsp?";
        urlAddr = urlAddr + "likeProductId=" + likeProductId + "&likeCheck=1&likeUserId=" + ShareVar.sharvarUserId;
        connectGetData();
        }else {
            urlAddr = "http://"+ ShareVar.urlIp +":8080/test/updatelike.jsp?";
            urlAddr = urlAddr + "likeProductId=" + likeProductId + "&likeCheck=" + likeCheck + "&likeUserId=" + ShareVar.sharvarUserId;
            connectUpdateData();
        }


        if (check == 1){

            Log.d(TAG,"메인에서는 일로가야해");
            intent = new Intent(LikeActivity.this, ProductMainActivity.class);
            intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
            startActivity(intent);

        }else {

            Log.d(TAG,"카테고에서는 일로가야해");
            intent = new Intent(LikeActivity.this, CategoryActivity.class);
            intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
            startActivity(intent);
        }



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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void connectUpdateData() {
        try {
            NetworkTaskProduct updateworkTask = new NetworkTaskProduct(LikeActivity.this, urlAddr, "like");
            updateworkTask.execute().get();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
