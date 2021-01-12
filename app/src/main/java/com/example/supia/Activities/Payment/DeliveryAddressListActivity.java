package com.example.supia.Activities.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.supia.Adapter.DeliveryAddressAdapter;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;

import java.util.ArrayList;

public class DeliveryAddressListActivity extends AppCompatActivity {

    ArrayList<UserDeliveryAddrDto> addrDtos;
    ListView deliveryAddrList;
    DeliveryAddressAdapter deliveryAddressAdapter;
    Button addDeliveryList;

    String urlAddr = null;
    String urlIp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_list);

        deliveryAddrList = findViewById(R.id.lv_deliveryaddress);

        connectGetData();

        deliveryAddressAdapter = new DeliveryAddressAdapter(this, R.layout.listlayout_delivery_address, addrDtos);

        deliveryAddrList.setAdapter(deliveryAddressAdapter);

        addDeliveryList = findViewById(R.id.btn_addAddrList_deliveryaddresslist);//배송지 추가
        addDeliveryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddressListActivity.this,DeliveryAddressAddActivity.class);
                intent.putExtra("USERID","jongchanko@gmail.com");
                startActivity(intent);

            }
        });




    }




    private void connectGetData() {
        try {
            urlIp = "192.168.35.147";
            urlAddr = "http://" + urlIp + ":8080/test/supiaUserDeliveryAddrCheck.jsp?";
            urlAddr = urlAddr+"userId="+ "jongchanko@gmail.com";


            DeliveryAddressNetWorkTask networkTask = new DeliveryAddressNetWorkTask(DeliveryAddressListActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            addrDtos = (ArrayList<UserDeliveryAddrDto>) obj;


            deliveryAddressAdapter = new DeliveryAddressAdapter(DeliveryAddressListActivity.this, R.layout.listlayout_delivery_address, addrDtos);
            deliveryAddrList.setAdapter(deliveryAddressAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}