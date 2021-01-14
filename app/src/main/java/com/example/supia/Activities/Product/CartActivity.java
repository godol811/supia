package com.example.supia.Activities.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.supia.Activities.Payment.BasketPurchaseCheckActivity;

import com.example.supia.Adapter.Product.CartAdapter;

import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;


import java.util.ArrayList;


public class CartActivity extends Activity {

    private RecyclerView recyclerView = null;

    private RecyclerView.LayoutManager layoutManager = null;


    final static String TAG = "카트액티비티";

    String urlAddr = null;
    ArrayList<CartDto> cart;
    CartAdapter adapter = null;

    CheckBox multipleCheck;

    Button deleteBtn,paymentBtn;
    TextView payment;
    int CartTotalPrice;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);








        recyclerView = findViewById(R.id.rl_cart_category);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        overridePendingTransition(R.anim.fadeout, R.anim.fadein);


        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/cartlist.jsp";
        urlAddr = urlAddr + "?cartUserId='" + ShareVar.sharvarUserId +"'";
//        connectGetData();


        multipleCheck = findViewById(R.id.cb_allselect_cart);

        deleteBtn = findViewById(R.id.btn_delete_cart);
        paymentBtn = findViewById(R.id.btn_payment_cart);
        payment = findViewById(R.id.tv_payment_cart);
        backBtn = findViewById(R.id.ibtn_back_mypage_header);




        multipleCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (multipleCheck.isChecked()){

                    adapter.checkBoxOperation(true);
                }else {
                    adapter.checkBoxOperation(false);
                }

            }
        });




        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/cartlist.jsp";
                urlAddr = urlAddr + "?cartUserId='" + ShareVar.sharvarUserId +"'";


                if (cart.size() != 0) {

                    if (adapter.sendDate().size() == 0) {

                        new AlertDialog.Builder(CartActivity.this)
                                .setTitle("알림")
                                .setMessage("체크된 상품이 없습니다.")
                                .setPositiveButton("확인", null)
                                .setCancelable(true)
                                .show();
                    } else {

                        adapter.connectDeleteData();
                        connectGetData();

                    }
                } else {
                    new AlertDialog.Builder(CartActivity.this)
                            .setTitle("알림")
                            .setMessage("삭제할 상품이 없습니다.")
                            .setPositiveButton("확인", null)
                            .setCancelable(true)
                            .show();
                    deleteBtn.setClickable(false);
                }

            }
        });


        /**
         *  구매하기 누를시 값 보내주기
         */


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //장바구니 리스트에 상품이 있으면 Intent 없으면 알람띄우기
                if (cart.size() != 0) {

                    //
                    if (adapter.sendDate().size() == 0) {

                        new AlertDialog.Builder(CartActivity.this)
                                .setTitle("알림")
                                .setMessage("체크된 상품이 없습니다.")
                                .setPositiveButton("확인", null)
                                .setCancelable(true)
                                .show();
                    } else {

                        Intent intent = new Intent(CartActivity.this, BasketPurchaseCheckActivity.class);
                        intent.putExtra("cartData", adapter.sendDate());
                        startActivity(intent);

                    }
                    } else {
                     new AlertDialog.Builder(CartActivity.this)
                            .setTitle("알림")
                            .setMessage("장바구니에 상품이 없습니다.")
                            .setPositiveButton("확인", null)
                            .setCancelable(true)
                            .show();
                paymentBtn.setClickable(false);
            }
            }
        });

        connectGetData();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
//        connectGetData();
        registerForContextMenu(recyclerView);

        //종찬 추가 PaymentShareVar 초기화 0114//
        int zero = 0;
        PaymentShareVar.list =null;
        PaymentShareVar.totalQuantity = zero;
        PaymentShareVar.totalPayment = zero;
        PaymentShareVar.paymentProductName = null;
        /////////////////////////////////////////
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





//    @Override
//    public void changedPrice(int totalPrice) {
//
//        payment.setText("총" + totalPrice +"원 주문하기");
//    }
}
