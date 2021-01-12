package com.example.supia.Activities.RegualarDeliveryPayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.supia.Activities.Payment.DeliveryAddressAddActivity;
import com.example.supia.Activities.Payment.DeliveryAddressListActivity;
import com.example.supia.Adapter.DeliveryAddressAdapter;
import com.example.supia.Adapter.RegularDeliveryAdapter;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class RegularDeliveryAddressListActivity extends AppCompatActivity {

    ArrayList<UserDeliveryAddrDto> addrDtos;
    ListView regularDeliveryList;
    RegularDeliveryAdapter regularDeliveryAdapter;
    Button addDeliveryList;
    String strWay;

    String urlAddr = null;
    String urlIp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_delivery_address_list);

        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");

        regularDeliveryList = findViewById(R.id.lv_deliveryaddress_regular);

        connectGetData();

        regularDeliveryAdapter = new RegularDeliveryAdapter(this, R.layout.listlayout_delivery_address_regular, addrDtos);

        regularDeliveryList.setAdapter(regularDeliveryAdapter);

        addDeliveryList = findViewById(R.id.btn_addAddrList_deliveryaddresslist_regular);//배송지 추가
        addDeliveryList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularDeliveryAddressListActivity.this, DeliveryAddressAddActivity.class);
                intent.putExtra("way",strWay);
                startActivity(intent);

            }
        });




    }




    private void connectGetData() {
        try {

            urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserDeliveryAddrCheck.jsp?";
            urlAddr = urlAddr+"userId="+ ShareVar.sharvarUserId;


            DeliveryAddressNetWorkTask networkTask = new DeliveryAddressNetWorkTask(RegularDeliveryAddressListActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            addrDtos = (ArrayList<UserDeliveryAddrDto>) obj;


            regularDeliveryAdapter = new RegularDeliveryAdapter(RegularDeliveryAddressListActivity.this, R.layout.listlayout_delivery_address_regular, addrDtos);
            regularDeliveryList.setAdapter(regularDeliveryAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}