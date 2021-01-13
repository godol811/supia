package com.example.supia.Activities.Payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Dto.Product.CartDto;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class PurchaseCheckActivity extends Activity {

    final static String TAG = "결제";
    Button btnDeliveryAddressModify, btnPaymentMethod, btnPayment;
    TextView tvDeliveryName, tvDeliveryAddress, tvDeliveryTel, tvPaymentMethod, tvDeliveryAddressDetail;
    String strDeliveryAddr, strDeliveryTel, strDeliveryName, strUserId, strMethodItem, strDeliveryAddrDetail;
    int intDeliveryNo;
    private String urlAddr;
    private String macIp;
    int intentIndex = 3;
    String strPayMethod = "";
    ArrayList<UserDeliveryAddrDto> user;


    ArrayList<CartDto> list;


    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_check);

        Intent intent = getIntent();
        intDeliveryNo = intent.getIntExtra("deliveryNo", 0);
        strDeliveryAddr = intent.getStringExtra("deliveryAddr");
        strDeliveryAddrDetail = intent.getStringExtra("deliveryAddrDetail");
        strDeliveryTel = intent.getStringExtra("deliveryTel");
        strDeliveryName = intent.getStringExtra("deliveryName");
        strPayMethod = intent.getStringExtra("PAYMETHOD");
        strMethodItem = intent.getStringExtra("ITEM");
        strUserId = ShareVar.sharvarUserId;




        btnDeliveryAddressModify = findViewById(R.id.btn_deliveryinfomodify);
        btnPaymentMethod = findViewById(R.id.btn_paymentmodify);
        btnPayment = findViewById(R.id.btn_payment);

        tvDeliveryAddress = findViewById(R.id.tv_deliveryaddress);
        tvDeliveryAddressDetail = findViewById(R.id.tv_deliveryaddressDetail);
        tvDeliveryTel = findViewById(R.id.tv_deliverytel);
        tvDeliveryName = findViewById(R.id.tv_deliveryName);
        tvPaymentMethod = findViewById(R.id.tv_paymentmethod);
        tvPaymentMethod.setText(strPayMethod);


        btnPayment.setOnClickListener(mOnclickListener);
        btnPaymentMethod.setOnClickListener(mOnclickListener);
        btnDeliveryAddressModify.setOnClickListener(mOnclickListener);



        if (strDeliveryTel != null) {

            tvDeliveryAddress.setText(strDeliveryAddr);
            tvDeliveryAddressDetail.setText(strDeliveryAddrDetail);
            tvDeliveryName.setText(strDeliveryName);
            tvDeliveryTel.setText(strDeliveryTel);
        } else {
            getDeliveryAddress();

        }




        /**
         *  1월 13일 인우추가
         *  장바구니에서 보내는값 받기
         */

        list = (ArrayList<CartDto>) intent.getSerializableExtra("cartData");

        for (int i = 0; i < list.size(); i++){

            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartProductId()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartProductName()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartProductQuantity()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartProductPrice()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartProductImagePath()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartUserId()));
            Log.d(TAG, "PurchaseCheckActivity 메소드 사용 : " + String.valueOf(list.get(i).getCartNo()));


        }







    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_deliveryinfomodify://배송지 변경
                    Intent intent = new Intent(PurchaseCheckActivity.this, DeliveryAddressListActivity.class);
                    intent.putExtra("way","normal");
                    startActivity(intent);
                    break;
                case R.id.btn_paymentmodify://결제수단 변경
                    Intent intent1 = new Intent(PurchaseCheckActivity.this, PaymentModifyActivity.class);
                    intent1.putExtra("way","normal");
                    startActivity(intent1);
                    break;
                case R.id.btn_payment:
                    strDeliveryAddr = tvDeliveryAddress.getText().toString().trim();
                    strDeliveryAddrDetail = tvDeliveryAddressDetail.getText().toString().trim();
                    strDeliveryTel =tvDeliveryTel.getText().toString().trim();

                    intentIndex();//0일경우엔 카드 1일경우엔 은행 2일경우엔 폰
                    if (intentIndex == 0) {
                        Intent intent2 = new Intent(PurchaseCheckActivity.this, PaymentCardActivity.class);
                        intent2.putExtra("ITEM", strMethodItem);
                        intent2.putExtra("way","normal");
                        intent2.putExtra("orderAddr",strDeliveryAddr);
                        intent2.putExtra("orderAddrDetail",strDeliveryAddrDetail);
                        intent2.putExtra("orderTel",strDeliveryTel);
                        intent2.putExtra("orderQuantity","오더퀀티티 스트링");
                        intent2.putExtra("orderTotalPrice","총가격 불러오는대로");
                        intent2.putExtra("productId","프로덕트아이디 가져오는대로");
                        startActivity(intent2);
                        break;
                    } else if (intentIndex == 1) {
                        Intent intent3 = new Intent(PurchaseCheckActivity.this, PaymentBankActivity.class);
                        intent3.putExtra("ITEM", strMethodItem);
                        intent3.putExtra("way","normal");
                        intent3.putExtra("orderTel",strDeliveryTel);
                        intent3.putExtra("orderAddr",strDeliveryAddr);
                        intent3.putExtra("orderAddrDetail",strDeliveryAddrDetail);
                        intent3.putExtra("orderQuantity","오더퀀티티 스트링");
                        intent3.putExtra("orderTotalPrice","총가격 불러오는대로");
                        intent3.putExtra("productId","프로덕트아이디 가져오는대로");
                        startActivity(intent3);
                        break;
                    } else if (intentIndex == 2) {
                        Intent intent4 = new Intent(PurchaseCheckActivity.this, PaymentPhoneActivity.class);
                        intent4.putExtra("ITEM", strMethodItem);
                        intent4.putExtra("way","normal");
                        intent4.putExtra("orderTel",strDeliveryTel);
                        intent4.putExtra("orderAddr",strDeliveryAddr);
                        intent4.putExtra("orderAddrDetail",strDeliveryAddrDetail);
                        intent4.putExtra("orderQuantity","오더퀀티티 스트링");
                        intent4.putExtra("orderTotalPrice","총가격 불러오는대로");
                        intent4.putExtra("productId","프로덕트아이디 가져오는대로");
                        startActivity(intent4);
                        break;
                    } else {

                        new AlertDialog.Builder(PurchaseCheckActivity.this)
                                .setTitle("결제 수단 선택")
                                .setMessage("결제 수단을 선택해주세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        break;
                    }


            }
        }
    };


    //기존 데이터로 부터 가져오기
    private void getDeliveryAddress() {
        Log.v(TAG, "connectGetData()");
        try {

            macIp = ShareVar.urlIp;
            urlAddr = "http:/" + macIp + ":8080/test/supiaUserDeliveryAddrCheck.jsp?"; //localhost나  127.0.0.1을 넣을경우 LOOP가 생길 수 있으므로 할당된 IP 주소를 사용할것

            urlAddr = urlAddr + "userId=" + strUserId;//jsp에 ID값 Request할 수 있게 페이지 설정.
            Log.v(TAG, urlAddr);
            DeliveryAddressNetWorkTask networkTask = new DeliveryAddressNetWorkTask(PurchaseCheckActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get(); //obj를 받아들여서
            user = (ArrayList<UserDeliveryAddrDto>) obj; //userInfoDtos 다시 풀기


            if (user.isEmpty()) {//아이디가 없는값이라면 없는 아이디라 메세지 띄우고 (없는 값이기 때문에 불러와지지 않으므로

            } else {
                String deliveryAddr = user.get(0).getDeliveryAddr();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.Log.d(TAG, userIdCheck);
                String deliveryAddrDetail = user.get(0).getDeliveryAddDetail();
                String deliveryTel = user.get(0).getDeliveryTel();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.Log.d(TAG, userIdCheck);
                String deliveryName = user.get(0).getDeliveryName();//dto에서 0번째로 낚아 채기 (어짜피 한개 밖에 없음.Log.d(TAG, userIdCheck);
                tvDeliveryAddress.setText(deliveryAddr);
                tvDeliveryAddressDetail.setText(deliveryAddrDetail);
                tvDeliveryTel.setText(deliveryTel);
                tvDeliveryName.setText(deliveryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void intentIndex() {
        if (tvPaymentMethod.getText().toString().trim().equals("신용/체크카드")) {
            intentIndex = 0;
        } else if (tvPaymentMethod.getText().toString().trim().equals("계좌이체/무통장입금")) {
            intentIndex = 1;
        } else if (tvPaymentMethod.getText().toString().trim().equals("휴대폰")) {
            intentIndex = 2;
        } else {
            intentIndex = 3;
        }
    }
}