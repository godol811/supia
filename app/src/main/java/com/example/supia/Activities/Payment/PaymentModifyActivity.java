package com.example.supia.Activities.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;

public class PaymentModifyActivity extends AppCompatActivity {

    Spinner spinnerBank, spinnerCard, spinnerPhone;
    RadioButton rbBank, rbCard, rbPhone;
    RadioGroup rgPayment;
    Button btnBank, btnCard, btnPhone;
    LinearLayout llBank, llCard, llPhone;
    String strWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_modify);

        Intent intent = getIntent();
        strWay = intent.getStringExtra("way");

        rbBank = findViewById(R.id.radioButtonBank);
        rbCard = findViewById(R.id.radioButtonCard);
        rbPhone = findViewById(R.id.radioButtonPhone);
        llBank = findViewById(R.id.rb1_Layout_payment);
        llCard = findViewById(R.id.rb2_Layout_payment);
        llPhone = findViewById(R.id.rb3_Layout_payment);

        spinnerBank = findViewById(R.id.rb1_spinner);
        spinnerCard = findViewById(R.id.rb2_spinner);
        spinnerPhone = findViewById(R.id.rb3_spinner);

        llBank.setVisibility(View.GONE);
        llCard.setVisibility(View.GONE);
        llPhone.setVisibility(View.GONE);

        btnBank = findViewById(R.id.btnBank);
        btnCard = findViewById(R.id.btnCard);
        btnPhone = findViewById(R.id.btnPhone);

        btnBank.setOnClickListener(mOnclickListener);
        btnCard.setOnClickListener(mOnclickListener);
        btnPhone.setOnClickListener(mOnclickListener);


        rgPayment = findViewById(R.id.paymentRadio);


        rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonBank:
                        llBank.setVisibility(View.VISIBLE);
                        llCard.setVisibility(View.GONE);
                        llPhone.setVisibility(View.GONE);
                        break;
                    case R.id.radioButtonCard:
                        llCard.setVisibility(View.VISIBLE);
                        llPhone.setVisibility(View.GONE);
                        llBank.setVisibility(View.GONE);
                        break;
                    case R.id.radioButtonPhone:
                        llCard.setVisibility(View.GONE);
                        llPhone.setVisibility(View.VISIBLE);
                        llBank.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnBank:
                    if (strWay.equals("normal")) {
                        String strBank = spinnerBank.getSelectedItem().toString().trim();
                        Intent intent1 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strBank;
                        PaymentShareVar.payMethod = "계좌이체/무통장입금";
                        startActivity(intent1);
                    } else {
                        String strBank = spinnerBank.getSelectedItem().toString().trim();
                        Intent intent1 = new Intent(PaymentModifyActivity.this, RegularPurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strBank;
                        PaymentShareVar.payMethod = "계좌이체/무통장입금";
                        startActivity(intent1);

                    }
                    break;

                case R.id.btnCard:
                    if (strWay.equals("normal")) {
                        String strCard = spinnerCard.getSelectedItem().toString().trim();
                        Intent intent2 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strCard;
                        PaymentShareVar.payMethod = "신용/체크카드";
                        startActivity(intent2);
                    } else {
                        String strCard = spinnerCard.getSelectedItem().toString().trim();
                        Intent intent2 = new Intent(PaymentModifyActivity.this, RegularPurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strCard;
                        PaymentShareVar.payMethod = "신용/체크카드";
                        startActivity(intent2);
                    }
                    break;
                case R.id.btnPhone:
                    if (strWay.equals("normal")) {
                        String strPhone = spinnerPhone.getSelectedItem().toString().trim();
                        Intent intent3 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strPhone;
                        PaymentShareVar.payMethod = "휴대폰";
                        startActivity(intent3);
                    } else {
                        String strPhone = spinnerPhone.getSelectedItem().toString().trim();
                        Intent intent3 = new Intent(PaymentModifyActivity.this, RegularPurchaseCheckActivity.class);
                        PaymentShareVar.payMethodItem= strPhone;
                        PaymentShareVar.payMethod = "휴대폰";
                        startActivity(intent3);

                    }

                    break;
            }

        }
    };


}