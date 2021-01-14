package com.example.supia.Activities.MyPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.supia.Activities.Payment.PaymentModifyActivity;
import com.example.supia.Activities.Payment.PurchaseCheckActivity;
import com.example.supia.Activities.Product.LikeActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.MyPage.MyPagePaymentNetworkTask;
import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyPageUpdatePaymentActivity extends Activity {

    Spinner myPageSpinnerBank, myPageSpinnerCard, myPageSpinnerPhone;
    RadioButton rbBankMyPage, rbCardMyPage, rbPhoneMyPage;
    RadioGroup rgPaymentMyPage;
    TextView selectBank, selectCard, selectPhone;
    LinearLayout llBankMyPage, llCardMyPage, llPhoneMyPage;
    String strWay;
    String userId = ShareVar.sharvarUserId;
    String urlIp = ShareVar.urlIp;
    String url = null;
    ArrayList<MySubscribeDto> payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_update_payment);


        overridePendingTransition(R.anim.hold, R.anim.hold);

        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");

        rbBankMyPage = findViewById(R.id.rb_bank_mypage);
        rbCardMyPage = findViewById(R.id.rb_card_mypage);
        rbPhoneMyPage = findViewById(R.id.rb_phone_mypage);

        llBankMyPage = findViewById(R.id.ll_bank_Layout_payment_mypage);
        llCardMyPage = findViewById(R.id.ll_card_Layout_payment_mypage);
        llPhoneMyPage = findViewById(R.id.ll_phone_Layout_payment_mypage);

        myPageSpinnerBank = findViewById(R.id.sp_bank_spinner_mypage);
        myPageSpinnerCard = findViewById(R.id.sp_card_spinner_mypage);
        myPageSpinnerPhone = findViewById(R.id.sp_phone_spinner_mypage);

        selectBank = findViewById(R.id.tv_checkbank_mypage);
        selectCard = findViewById(R.id.tv_checkcard_mypage);
        selectPhone = findViewById(R.id.tv_checkphone_mypage);

        selectBank.setOnClickListener(selectPaymentClickListener);
        selectCard.setOnClickListener(selectPaymentClickListener);
        selectPhone.setOnClickListener(selectPaymentClickListener);





        rgPaymentMyPage = findViewById(R.id.rg_payment_mypage);


        rgPaymentMyPage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bank_mypage:
                        llBankMyPage.setVisibility(View.VISIBLE);
                        llCardMyPage.setVisibility(View.GONE);
                        llPhoneMyPage.setVisibility(View.GONE);
                        break;
                    case R.id.rb_card_mypage:
                        llCardMyPage.setVisibility(View.VISIBLE);
                        llPhoneMyPage.setVisibility(View.GONE);
                        llBankMyPage.setVisibility(View.GONE);
                        break;
                    case R.id.rb_phone_mypage:
                        llCardMyPage.setVisibility(View.GONE);
                        llPhoneMyPage.setVisibility(View.VISIBLE);
                        llBankMyPage.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }//--------------onCreate


    //------------------------------------------라디오 버튼 클릭 이벤트------------------------------------------//
    View.OnClickListener selectPaymentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_checkbank_mypage: // 계좌이체/무통장입금 일때

                    String strBank = myPageSpinnerBank.getSelectedItem().toString().trim();
                    Intent intent1 = new Intent(MyPageUpdatePaymentActivity.this, MySubscribeActivity.class);
                    PaymentShareVar.payMethodItem = strBank;
                    PaymentShareVar.payMethod = "계좌이체/무통장입금";
                    url = "http://"+urlIp+":8080/test/supiaMySubscribePaymentUpdate.jsp?subscribeOrderPayment="+strBank+"&userId="+userId;
                    connectUpdateData();
                    startActivity(intent1);


                    break;

                case R.id.tv_checkcard_mypage: // 신용/체크카드 일때

                    String strCard = myPageSpinnerCard.getSelectedItem().toString().trim();
                    Intent intent2 = new Intent(MyPageUpdatePaymentActivity.this, MySubscribeActivity.class);
                    PaymentShareVar.payMethodItem = strCard;
                    PaymentShareVar.payMethod = "신용/체크카드";
                    url = "http://"+urlIp+":8080/test/supiaMySubscribePaymentUpdate.jsp?subscribeOrderPayment="+strCard+"&userId="+userId;
                    connectUpdateData();
                    startActivity(intent2);

                    break;
                case R.id.tv_checkphone_mypage: // 휴대폰 일때
                    String strPhone = myPageSpinnerPhone.getSelectedItem().toString().trim();
                    Intent intent3 = new Intent(MyPageUpdatePaymentActivity.this, MySubscribeActivity.class);
                    PaymentShareVar.payMethodItem = strPhone;
                    PaymentShareVar.payMethod = "휴대폰";
                    url = "http://"+urlIp+":8080/test/supiaMySubscribePaymentUpdate.jsp?subscribeOrderPayment="+strPhone+"&userId="+userId;
                    connectUpdateData();
                    startActivity(intent3);


                    break;
            }

        }
    };
    //-----------------------------------------------------------------------------------------------------------------//


    //--------------------------------------------------connectUpdateData-----------------------------------------------//
    private void connectUpdateData() {
        try {
            MyPagePaymentNetworkTask updateworkTask = new MyPagePaymentNetworkTask(MyPageUpdatePaymentActivity.this, url, "result");
            updateworkTask.execute().get();
            Object obj = updateworkTask.execute().get();
            payment = (ArrayList<MySubscribeDto>) obj;



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //------------------------------------------------------------------------------------------------------------------//




}//---------------끝-------------