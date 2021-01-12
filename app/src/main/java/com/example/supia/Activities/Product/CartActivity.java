package com.example.supia.Activities.Product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Adapter.Product.CartAdapter;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;

    private RecyclerView.LayoutManager layoutManager = null;


    String urlAddr = null;
    ArrayList<CartDto> cart;
    CartAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.rl_cart_category);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        overridePendingTransition(R.anim.fadeout, R.anim.fadein);


        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/cartlist.jsp";

        connectGetData();


    }


    @Override
    protected void onResume() {
        super.onResume();
//        connectGetData();
        registerForContextMenu(recyclerView);
    }

    private void connectGetData() {
        try {

            NetworkTaskCart networkTask = new NetworkTaskCart(CartActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            cart = (ArrayList<CartDto>) obj;


            adapter = new CartAdapter(CartActivity.this, R.layout.listlayout_cart, cart);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
