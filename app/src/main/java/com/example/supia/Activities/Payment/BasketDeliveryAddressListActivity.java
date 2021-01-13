package com.example.supia.Activities.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.supia.Adapter.BasketDeliveryAdapter;
import com.example.supia.Adapter.DeliveryAddressAdapter;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class BasketDeliveryAddressListActivity extends AppCompatActivity {

    ArrayList<UserDeliveryAddrDto> addrDtos;
    ListView deliveryAddrList;
    BasketDeliveryAdapter basketDeliveryAdapter;
    Button addDeliveryList;
    String strWay;

    String urlAddr = null;
    String urlIp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_delivery_address_list);

        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");

        deliveryAddrList = findViewById(R.id.lv_deliveryaddress_basket);

        connectGetData();

        basketDeliveryAdapter = new BasketDeliveryAdapter(this, R.layout.listlayout_delivery_address_basket, addrDtos);

        deliveryAddrList.setAdapter(basketDeliveryAdapter);

        addDeliveryList = findViewById(R.id.btn_addAddrList_deliveryaddresslist_basket);//배송지 추가
        addDeliveryList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasketDeliveryAddressListActivity.this,DeliveryAddressAddActivity.class);
                intent.putExtra("way",strWay);
                startActivity(intent);
            }
        });
    }



    private void connectGetData() {
        try {
            urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserDeliveryAddrCheck.jsp?";
            urlAddr = urlAddr+"userId="+ ShareVar.sharvarUserId;

            DeliveryAddressNetWorkTask networkTask = new DeliveryAddressNetWorkTask(BasketDeliveryAddressListActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            addrDtos = (ArrayList<UserDeliveryAddrDto>) obj;


            basketDeliveryAdapter = new BasketDeliveryAdapter(BasketDeliveryAddressListActivity.this, R.layout.listlayout_delivery_address_basket, addrDtos);
            deliveryAddrList.setAdapter(basketDeliveryAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}