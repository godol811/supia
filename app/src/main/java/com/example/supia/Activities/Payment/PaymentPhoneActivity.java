package com.example.supia.Activities.Payment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PaymentPhoneActivity extends Activity {


    TextView phoneItem;
    EditText phoneNumber;
    Button btnNext;
    String urlAddr = null;
    String urlIp = null;
    String strOrderDate;
    String strOrderQuantity;
    String strOrderAddr;
    String strOrderAddrDetail;
    String strOrderPayment;
    String strOrderTotalPrice;
    String userId;
    String productId;
    String strItem;
    String strOrderTel;
    String strWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_phone);


        Intent intent = getIntent();
        strItem = intent.getStringExtra("ITEM");
        userId = intent.getStringExtra("userId");
        strOrderAddr = intent.getStringExtra("orderAddr");
        strWay = intent.getStringExtra("way");
        strOrderAddrDetail = intent.getStringExtra("orderAddrDetail");
        strOrderQuantity = "1";//intent.getStringExtra("orderQuantity");
        strOrderTotalPrice = "1"; // intent.getStringExtra("orderTotalPrice");
        productId = "1"; //intent.getStringExtra("productId");
        strOrderTel = intent.getStringExtra("orderTel");

        phoneItem = findViewById(R.id.tv_itemselected_paymentPhone);
        phoneItem.setText(strItem);
        btnNext = findViewById(R.id.btn_paymentNext_Phonepayment);
        btnNext.setOnClickListener(mOnclickListener);

        phoneNumber = findViewById(R.id.phoneNumberEditText);

        phoneNumber.addTextChangedListener(new TextWatcher() {//자동으로 "-" 생성해서 카드번호에 붙여주기
            private int beforeLenght = 0;
            private int afterLenght = 0;

            //입력 혹은 삭제 전의 길이와 지금 길이를 비교하기 위해 beforeTextChanged에 저장
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeLenght = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //아무글자도 없는데 지우려고 하면 로그띄우기 에러방지
                if (s.length() <= 0) {
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Length)");
                    return;
                }
                //특수문자 입력 방지
                char inputChar = s.charAt(s.length() - 1);
                if (inputChar != '-' && (inputChar < '0' || inputChar > '9')) {
                    phoneNumber.getText().delete(s.length() - 1, s.length());
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Number)");
                    return;
                }
                afterLenght = s.length();
                String tel = String.valueOf(phoneNumber.getText());
                tel.substring(0, 1);
                if (beforeLenght < afterLenght) {// 타자를 입력 중이면
                    if (afterLenght == 3) { //subSequence로 지정된 문자열을 반환해서 "-"폰을 붙여주고 substring
                        phoneNumber.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                    } else if (afterLenght == 8) {
                        phoneNumber.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                    }
                }
                phoneNumber.setSelection(phoneNumber.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });


    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.btn_paymentNext_Phonepayment:

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String today = df.format(calendar.getTime());//파일에도 날짜를 넣기위한 메소드

                    urlIp = ShareVar.urlIp;
                    urlAddr = "http://" + urlIp + ":8080/test/supiaUserOrderInsert.jsp?";//바로 삭제 할 수 있도록 한다.(따로 페이지 필요 X)
                    if (phoneNumber.getText().toString().trim().length() == 13) {
                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                .setTitle("구매")
                                .setMessage("진짜로 구매 하시겠습니까?")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("아니오", null)
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        urlAddr = urlAddr + "orderDate=" + today + "&orderQuantity=" + strOrderQuantity + "&orderAddr=" + strOrderAddr + "&orderAddrDetail=" + strOrderAddrDetail
                                                + "&orderPayment=" + "휴대폰" + "&orderTotalPrice=" + strOrderTotalPrice + "&userId=" + userId + "&productId=" + productId + "&orderTel=" + strOrderTel;
                                        connectInsertData();

                                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                                .setTitle("완료")
                                                .setMessage("구매가 완료 되었습니다.")
                                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(PaymentPhoneActivity.this, PaymentConfirmActivity.class);//
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();
                                    }
                                }).show();


                        Log.d("수정및삭", "들어가나?");
                        break;
                    } else if (phoneNumber.getText().toString().length() == 0) {
                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                .setTitle("전화번호 오류")
                                .setMessage("전화번호를 입력해주세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                    } else {
                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                .setTitle("전화번호 오류")
                                .setMessage("유효한 전화번호를 입력해주세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                    }
            }
        }


    };

    private void connectInsertData() {
        try {
            Log.d("확인", "connectInsertData");
            DeliveryAddressNetWorkTask insertworkTask = new DeliveryAddressNetWorkTask(PaymentPhoneActivity.this, urlAddr, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}