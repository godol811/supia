package com.example.supia.Activities.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Activities.Payment.BasketPurchaseCheckActivity;
import com.example.supia.Activities.Payment.PurchaseCheckActivity;
import com.example.supia.Adapter.Product.CartAdapter;
import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class CartActivity extends Activity implements OnChangeCheckedPrice{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.rl_cart_category);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        overridePendingTransition(R.anim.fadeout, R.anim.fadein);


        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/cartlist.jsp";
        urlAddr = urlAddr + "?cartUserId='" + ShareVar.sharvarUserId +"'";
//        connectGetData();


        multipleCheck = findViewById(R.id.cb_allselect_cart);

        deleteBtn = findViewById(R.id.btn_delete_cart);
        paymentBtn = findViewById(R.id.btn_payment_cart);
        payment = findViewById(R.id.tv_payment_cart);




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

                Log.d(TAG,"여기에 넣어줘야됨");
                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/cartlist.jsp";
                urlAddr = urlAddr + "?cartUserId='" + ShareVar.sharvarUserId +"'";

//              adapter.sendDate();

                adapter.connectDeleteData();
                connectGetData();


//                for (int i = 0; i < adapter.sendDate().size(); i++){
//                    Log.d(TAG, "액티비티에서 메소드 사용 : " + String.valueOf(adapter.sendDate().get(i).getCartProductQuantity()));
//                    Log.d(TAG, "액티비티에서 메소드 사용 : " + String.valueOf(adapter.sendDate().get(i).getCartProductName()));
//                }


            }
        });


        /**
         *  구매하기 누를시 값 보내주기
         */


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartActivity.this, BasketPurchaseCheckActivity.class);
                intent.putExtra("cartData",adapter.sendDate());
                startActivity(intent);

            }
        });

        connectGetData();






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


            adapter = new CartAdapter(CartActivity.this, R.layout.listlayout_cart, cart, this);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public void changedPrice(int totalPrice) {

        payment.setText("총" + totalPrice +"원 주문하기");
    }
}
