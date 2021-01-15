package com.example.supia.Activities.Payment;

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
import androidx.appcompat.app.AppCompatActivity;


import com.example.supia.Activities.Login.AddressWebViewActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeliveryAddressAddActivity extends AppCompatActivity {

    TextView deliveryAddr;
    EditText deliverAddrDetail, deliveryTel, deliveryName;
    Button btnAdd, btnCancel;
    String urlAddr = null;
    String urlIp = null;
    String strUserId;
    String strWay;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_add);

        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");

        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaDeliveryAddrInsert.jsp?";

        deliveryAddr = findViewById(R.id.et_address_addradd);
        deliverAddrDetail = findViewById(R.id.et_address_detail_addradd);
        deliveryTel = findViewById(R.id.et_TelNumber_addradd);
        deliveryName = findViewById(R.id.et_deliveryname_addradd);


        btnAdd = findViewById(R.id.btn_changeInsertAddr_addradd);
        btnCancel = findViewById(R.id.btn_cancel_addradd);
        btnAdd.setOnClickListener(mOnclickListener);
        btnCancel.setOnClickListener(mOnclickListener);


        deliveryTel.addTextChangedListener(new TextWatcher() {//자동으로 "-" 생성해서 카드번호에 붙여주기
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
                    deliveryTel.getText().delete(s.length() - 1, s.length());
                    Log.d("addTextChangedListener", "onTextChanged: Intput text is wrong (Type : Number)");
                    return;
                }
                afterLenght = s.length();
                String tel = String.valueOf(deliveryTel.getText());
                tel.substring(0, 1);
                if (beforeLenght < afterLenght) {// 타자를 입력 중이면
                    if (afterLenght == 3) { //subSequence로 지정된 문자열을 반환해서 "-"폰을 붙여주고 substring
                        deliveryTel.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                    } else if (afterLenght == 8) {
                        deliveryTel.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                    }
                }
                deliveryTel.setSelection(deliveryTel.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 생략
            }
        });



        deliveryAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryAddressAddActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            String strDeliveryAddr = deliveryAddr.getText().toString().trim();
            String strDeliveryAddrDetail = deliverAddrDetail.getText().toString().trim();
            String strDeliveryTel = deliveryTel.getText().toString().trim();
            String strDeliveryName = deliveryName.getText().toString().trim();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(calendar.getTime());//파일에도 날짜를 넣기위한 메소드
            calendar.add(Calendar.MONTH, 1);
            String addMonth = df.format(calendar.getTime());//한달 추가


            switch (v.getId()) {
                case R.id.btn_changeInsertAddr_addradd:


                    if (strDeliveryAddr.trim().length() != 0 && strDeliveryAddrDetail.trim().length() != 0 && strDeliveryTel.trim().length() != 0 && strDeliveryName.trim().length() != 0) {

                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                .setTitle("입력")
                                .setMessage("주소록에 추가하시겠습니까?")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("아니오", null)
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        urlAddr = urlAddr + "userId=" + ShareVar.sharvarUserId + "&deliveryAddr=" + strDeliveryAddr + "&deliveryAddrDetail=" + strDeliveryAddrDetail
                                                + "&deliveryTel=" + strDeliveryTel + "&deliveryName=" + strDeliveryName + "&insertDate=" + today + "&nextDeliveryDate=" + addMonth;
                                        connectInsertData();
                                        Log.d("Url", urlAddr);

                                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                                .setTitle("완료")
                                                .setMessage("입력이 완료 되었습니다.")
                                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        String strDeliveryAddr = deliveryAddr.getText().toString();
                                                        String strDeliveryAddrDetail = deliverAddrDetail.getText().toString();
                                                        String strDeliveryTel = deliveryTel.getText().toString();
                                                        String strDeliveryName = deliveryName.getText().toString();
                                                        if (strWay.equals("normal")) {
                                                            Intent intent = new Intent(DeliveryAddressAddActivity.this, PurchaseCheckActivity.class);
                                                            intent.putExtra("prepared","prepared");
                                                            PaymentShareVar.deliveryAddr = strDeliveryAddr;
                                                            PaymentShareVar.deliveryAddrDetail = strDeliveryAddrDetail;
                                                            PaymentShareVar.deliveryTel = strDeliveryTel;
                                                            PaymentShareVar.deliveryName = strDeliveryName;
                                                            startActivity(intent);
                                                        }else if(strWay.equals("regular")) {
                                                            Intent intent = new Intent(DeliveryAddressAddActivity.this, RegularPurchaseCheckActivity.class);
                                                            intent.putExtra("prepared","prepared");
                                                            PaymentShareVar.deliveryAddr = strDeliveryAddr;
                                                            PaymentShareVar.deliveryAddrDetail = strDeliveryAddrDetail;
                                                            PaymentShareVar.deliveryTel = strDeliveryTel;
                                                            PaymentShareVar.deliveryName = strDeliveryName;
                                                            startActivity(intent);

                                                        }else {
                                                            Intent intent = new Intent(DeliveryAddressAddActivity.this, BasketPurchaseCheckActivity.class);
                                                            intent.putExtra("prepared","prepared");
                                                            PaymentShareVar.deliveryAddr = strDeliveryAddr;
                                                            PaymentShareVar.deliveryAddrDetail = strDeliveryAddrDetail;
                                                            PaymentShareVar.deliveryTel = strDeliveryTel;
                                                            PaymentShareVar.deliveryName = strDeliveryName;
                                                            startActivity(intent);


                                                        }
                                                    }
                                                })
                                                .show();
                                    }
                                })
                                .show();


                    } else if (strDeliveryTel.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                .setTitle("전화번호를 입력하세요")
                                .setMessage("전화번호를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        deliveryTel.setFocusable(true);
                    } else if (strDeliveryAddrDetail.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                .setTitle("상세 주소를 입력하세요")
                                .setMessage("상세 주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        deliverAddrDetail.setFocusable(true);
                    } else if (strDeliveryAddr.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                .setTitle("주소를 입력하세요")
                                .setMessage("주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                    } else {
                        new AlertDialog.Builder(DeliveryAddressAddActivity.this)
                                .setTitle("받는 사람을 입력하세요")
                                .setMessage("받는 사람을 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();

                    }
                    break;

                case R.id.btn_cancel_addradd:
                    onBackPressed();
                    break;
            }
        }
    };


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        deliveryAddr.setText(data);
                    }
                }
                break;
        }
    }

    private void connectInsertData() {
        try {
            Log.d("확인", "connectInsertData");
            DeliveryAddressNetWorkTask insertworkTask = new DeliveryAddressNetWorkTask(DeliveryAddressAddActivity.this, urlAddr, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}