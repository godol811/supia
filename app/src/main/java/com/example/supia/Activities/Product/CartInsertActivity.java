package com.example.supia.Activities.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

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
        productQuantity = 1;
        productPrice = intent.getStringExtra("productPrice");
        productName = intent.getStringExtra("productName");
        productImagePath = intent.getStringExtra("productImagePath");


        urlAddr = "http://"+urlIp+":8080/test/insertcart.jsp?";
        urlAddr = urlAddr + "productNo=" + productNo + "&productQuantity=" + productQuantity+ "&productPrice=" + productPrice +"&productName=" + productName
                +"&productImagePath=" + productImagePath;

        connectGetData();

        intent = new Intent(CartInsertActivity.this, CategoryActivity.class);
        intent.putExtra("strBtnCategory",ShareVar.strBtnCategory);
        startActivity(intent);

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
