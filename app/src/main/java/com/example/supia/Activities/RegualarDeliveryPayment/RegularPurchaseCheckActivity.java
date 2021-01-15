package com.example.supia.Activities.RegualarDeliveryPayment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.supia.Activities.Payment.DeliveryAddressListActivity;
import com.example.supia.Activities.Payment.PaymentBankActivity;
import com.example.supia.Activities.Payment.PaymentCardActivity;
import com.example.supia.Activities.Payment.PaymentModifyActivity;
import com.example.supia.Activities.Payment.PaymentPhoneActivity;
import com.example.supia.Activities.Payment.PurchaseCheckActivity;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class RegularPurchaseCheckActivity extends AppCompatActivity {

    final static String TAG = "정기배송";
    Button btnDeliveryAddressModify, btnPaymentMethod, btnPayment;
    TextView tvDeliveryName, tvDeliveryAddress, tvDeliveryTel, tvPaymentMethod, tvDeliveryAddressDetail, tvProductName, tvProductQuantity, tvTotalprice;
    String strDeliveryAddr, strDeliveryTel, strDeliveryName, strUserId, strMethodItem, strDeliveryAddrDetail,
            strProductName, strProductPrice;
    int intProductPrice, intProductQuantity, intTotalPrice, intProductNo;
    int intDeliveryNo;
    private String urlAddr;
    private String macIp;
    int intentIndex = 3;
    String strPrepared = "";
    ArrayList<UserDeliveryAddrDto> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_purchase_check);



        intProductNo = PaymentShareVar.paymentProductNo;
        strProductName = PaymentShareVar.paymentProductName;
        strProductPrice = PaymentShareVar.paymentProductPrice;
        intProductQuantity = PaymentShareVar.paymentProductQuantity;
        intProductPrice = Integer.parseInt(strProductPrice);
        intTotalPrice = intProductPrice * intProductQuantity;
        PaymentShareVar.totalPayment = intTotalPrice;
        strUserId = ShareVar.sharvarUserId;

        strDeliveryAddr = PaymentShareVar.deliveryAddr;

        btnDeliveryAddressModify = findViewById(R.id.btn_deliveryinfomodify_regular);
        btnPaymentMethod = findViewById(R.id.btn_paymentmodify_regular);
        btnPayment = findViewById(R.id.btn_payment_regular);

        tvDeliveryAddress = findViewById(R.id.tv_deliveryaddress_regular);
        tvDeliveryAddressDetail = findViewById(R.id.tv_deliveryaddressDetail_regular);
        tvDeliveryTel = findViewById(R.id.tv_deliverytel_regular);
        tvDeliveryName = findViewById(R.id.tv_deliveryName_regular);
        tvPaymentMethod = findViewById(R.id.tv_paymentmethod_regular);
        tvProductName = findViewById(R.id.tv_productName_regular);
        tvProductQuantity = findViewById(R.id.tv_productQuantity_regular);
        tvTotalprice = findViewById(R.id.tv_totalprice_regular);


        //배송지 관련--

        if (strDeliveryAddr != null) {
            tvDeliveryAddress.setText(PaymentShareVar.deliveryAddr);
            tvDeliveryAddressDetail.setText(PaymentShareVar.deliveryAddrDetail);
            tvDeliveryTel.setText(PaymentShareVar.deliveryTel);
            tvDeliveryName.setText(PaymentShareVar.deliveryName);
        } else {
            getDeliveryAddress();
        }



        //결제 방식 관련
        tvPaymentMethod.setText(PaymentShareVar.payMethod);


        //추가--
        tvProductName.setText(strProductName);
        tvTotalprice.setText(Integer.toString(intTotalPrice));
        tvProductQuantity.setText(Integer.toString(intProductQuantity));
        //--추가


        btnPayment.setOnClickListener(mOnclickListener);
        btnPaymentMethod.setOnClickListener(mOnclickListener);
        btnDeliveryAddressModify.setOnClickListener(mOnclickListener);
    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_deliveryinfomodify_regular://배송지 변경
                    Intent intent = new Intent(RegularPurchaseCheckActivity.this, RegularDeliveryAddressListActivity.class);
                    intent.putExtra("way", "regular");
                    startActivity(intent);
                    break;
                case R.id.btn_paymentmodify_regular://결제수단 변경
                    Intent intent1 = new Intent(RegularPurchaseCheckActivity.this, PaymentModifyActivity.class);
                    intent1.putExtra("way", "regular");
                    startActivity(intent1);
                    break;
                case R.id.btn_payment_regular:
                    PaymentShareVar.deliveryAddr = tvDeliveryAddress.getText().toString();
                    PaymentShareVar.deliveryAddrDetail = tvDeliveryAddressDetail.getText().toString();
                    PaymentShareVar.deliveryTel = tvDeliveryTel.getText().toString();
                    PaymentShareVar.deliveryName = tvDeliveryName.getText().toString();
                    intentIndex();//0일경우엔 카드 1일경우엔 은행 2일경우엔 폰
                    if (intentIndex == 0) {
                        Intent intent2 = new Intent(RegularPurchaseCheckActivity.this, PaymentCardActivity.class);
                        intent2.putExtra("way", "regular");

                        startActivity(intent2);
                        break;
                    } else if (intentIndex == 1) {
                        Intent intent3 = new Intent(RegularPurchaseCheckActivity.this, PaymentBankActivity.class);
                        intent3.putExtra("way", "regular");

                        startActivity(intent3);
                        break;
                    } else if (intentIndex == 2) {
                        Intent intent4 = new Intent(RegularPurchaseCheckActivity.this, PaymentPhoneActivity.class);
                        intent4.putExtra("way", "regular");

                        startActivity(intent4);
                        break;
                    } else {

                        new AlertDialog.Builder(RegularPurchaseCheckActivity.this)
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
            DeliveryAddressNetWorkTask networkTask = new DeliveryAddressNetWorkTask(RegularPurchaseCheckActivity.this, urlAddr, "select");
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
                if(deliveryName.equals("null")){
                    deliveryName = "";
                }


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