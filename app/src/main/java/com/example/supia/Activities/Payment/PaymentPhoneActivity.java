package com.example.supia.Activities.Payment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Activities.MyPage.MyOrderActivity;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.CalendarNetworkTask;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PaymentPhoneActivity extends Activity {


    TextView phoneItem;
    EditText phoneNumber;
    Button btnNext;
    String urlAddr = null;
    String urlIp = null;
    String strOrderDate;
    int intOrderQuantity;
    String strOrderAddr;
    String strOrderAddrDetail;
    String strOrderPayment;
    int intOrderTotalPrice;
    String userId;
    String productId;
    String strItem;
    String strOrderTel;
    String strWay;
    ArrayList<CartDto> listPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_phone);


        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");
        strItem = PaymentShareVar.payMethodItem;


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

                    if (strWay.equals("normal") || strWay.equals("basket")) {
                        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserOrderInsert.jsp?";//일반 결제 or 장바구니 결제
                    } else {
                        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserRegularOrderInsert.jsp?";//정기결제
                    }

                    if (phoneNumber.getText().toString().trim().length() == 13) {
                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                .setTitle("구매")
                                .setMessage("진짜로 구매 하시겠습니까?")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("아니오", null)
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (strWay.equals("basket")) {
                                            listPayment = PaymentShareVar.list;
                                            for (int i = 0; i < listPayment.size(); i++) {
                                                String productId = String.valueOf(listPayment.get(i).getCartProductId());
                                                String productName = String.valueOf(listPayment.get(i).getCartProductName());
                                                String productQuantity = String.valueOf(listPayment.get(i).getCartProductQuantity());
                                                String productPrice = String.valueOf(listPayment.get(i).getCartProductPrice());
                                                int price = Integer.parseInt(String.valueOf(listPayment.get(i).getCartProductPrice()));
                                                int quantity = Integer.parseInt(String.valueOf(listPayment.get(i).getCartProductQuantity()));
                                                Log.d("배열확인", productId + "와" + productName);
                                                urlAddr = urlAddr + "orderDate=" + today + "&orderQuantity=" + productQuantity + "&orderAddr=" + PaymentShareVar.deliveryAddr + "&orderAddrDetail=" + PaymentShareVar.deliveryAddrDetail
                                                        + "&orderPayment=" + "핸드폰" + "&orderTotalPrice=" + (price * quantity) + "&userId=" + ShareVar.sharvarUserId + "&productId="
                                                        + productId + "&orderTel=" + PaymentShareVar.deliveryTel + "&subscribeProductName=" + productName + "&subscribeProductPrice=" + productPrice;
                                                connectInsertData();
                                                PaymentShareVar.paymentProductNo = listPayment.get(i).getCartProductId();
                                                connectGetDataCateDelete();
                                                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaUserOrderInsert.jsp?";
                                            }
                                            connectUpdateData();


                                        } else {
                                            urlAddr = urlAddr + "orderDate=" + today + "&orderQuantity=" + PaymentShareVar.paymentProductQuantity + "&orderAddr=" + PaymentShareVar.deliveryAddr + "&orderAddrDetail=" + PaymentShareVar.deliveryAddrDetail
                                                    + "&orderPayment=" + "핸드폰" + "&orderTotalPrice=" + PaymentShareVar.totalPayment + "&userId=" + ShareVar.sharvarUserId + "&productId="
                                                    + PaymentShareVar.paymentProductNo + "&orderTel=" + PaymentShareVar.deliveryTel + "&subscribeProductName=" + PaymentShareVar.paymentProductName + "&subscribeProductPrice=" + PaymentShareVar.paymentProductPrice;
                                            connectInsertData();
                                            connectUpdateData();

                                        }


                                        new AlertDialog.Builder(PaymentPhoneActivity.this)
                                                .setTitle("완료")
                                                .setMessage("구매가 완료 되었습니다.")
                                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(PaymentPhoneActivity.this, MyOrderActivity.class);//
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

    //--------------------------------------애정 추가  배경 터치 시 키보드 사라지게----------------------------------//
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm;
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    //---------------------------------------------------------------------------------------------//

    /**
     * 인우 추가
     * 결제수단마다 결제될떄 삭제해주기
     */
    private void connectGetDataCateDelete() {

        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/deletecartpayment.jsp";
        urlAddr = urlAddr + "?cartProductId=" + PaymentShareVar.paymentProductNo;

        try {

            NetworkTaskCart networkTask = new NetworkTaskCart(PaymentPhoneActivity.this, urlAddr, "like");
            networkTask.execute().get();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 종찬 추가
     * 배송날짜 추가
     */

    private void connectUpdateData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(calendar.getTime());//파일에도 날짜를 넣기위한 메소드
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String addMonth = df.format(calendar.getTime());//한달 추가


        urlAddr = "http://" + urlIp + ":8080/test/supiaCalendarUpdate.jsp?";
        urlAddr = urlAddr + "calendarDeliveryDate="+addMonth;

        try {
            CalendarNetworkTask updatenetworkTask = new CalendarNetworkTask(PaymentPhoneActivity.this, urlAddr, "update");
            updatenetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}