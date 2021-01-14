package com.example.supia.Activities.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Activities.MyPage.MyLikeListActivity;
import com.example.supia.Adapter.Product.CartAdapter;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class CartInsertActivity extends Activity {

    final static String TAG = "서치액티비티";


    String urlAddr = null;
    String urlIp = null;

    int productNo;
    int productQuantity;
    int check;
    String productPrice;
    String productName;
    String productImagePath;

    CartAdapter adapter = null;
    ArrayList<CartDto> cart;
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadeout, R.anim.fadein);

        //받아오는 ip값
        Intent intent = getIntent();

        urlIp = ShareVar.urlIp;

        productNo = intent.getIntExtra("productNo",0);
        check = intent.getIntExtra("check",0);
        productQuantity = 1;
        productPrice = intent.getStringExtra("productPrice");
        productName = intent.getStringExtra("productName");
        productImagePath = intent.getStringExtra("productImagePath");



        //insert하기전에 장바구니에 똑같은 물건이 있으면 다이얼로그 띄우기
//        if ()
        urlAddr = "http://"+urlIp+":8080/test/insertcart.jsp?";
        urlAddr = urlAddr + "productNo=" + productNo + "&productQuantity=" + productQuantity+ "&productPrice=" + productPrice +"&productName=" + productName
                +"&productImagePath=" + productImagePath +"&cartUserId=" + ShareVar.sharvarUserId;

        connectGetData();




        if (check == 0){
            intent = new Intent(CartInsertActivity.this, CategoryActivity.class);
            intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
            startActivity(intent);
        }else if (check == 1){
            intent = new Intent(CartInsertActivity.this, ProductMainActivity.class);
//            intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
            startActivity(intent);
        } else if (check == 2){
            intent = new Intent(CartInsertActivity.this, MyLikeListActivity.class);
//            intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
            startActivity(intent);
        }


    }



    private void connectGetData() {
        try {
            NetworkTaskCart networkTaskCart = new NetworkTaskCart(CartInsertActivity.this, urlAddr, "like");
            networkTaskCart.execute().get();
//            Object obj = networkTaskCart.execute().get();

//            cart = (ArrayList<CartDto>) obj;
//            adapter = new CartAdapter(CartInsertActivity.this, R.layout.listlayout_cart, cart);
//            recyclerView.setAdapter(adapter);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
