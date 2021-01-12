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

import com.example.supia.R;

public class PaymentModifyActivity extends AppCompatActivity {

    Spinner spinnerBank, spinnerCard, spinnerPhone;
    RadioButton rbBank, rbCard, rbPhone;
    RadioGroup rgPayment;
    Button btnBank, btnCard, btnPhone;
    LinearLayout llBank, llCard, llPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_modify);

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
                    String strBank = spinnerBank.getSelectedItem().toString().trim();
                    Intent intent1 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                    intent1.putExtra("ITEM",strBank);
                    intent1.putExtra("PAYMETHOD","계좌이체/무통장입금");
                    startActivity(intent1);
                    break;

                case R.id.btnCard:
                    String strCard = spinnerCard.getSelectedItem().toString().trim();
                    Intent intent2 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                    intent2.putExtra("ITEM",strCard);
                    intent2.putExtra("PAYMETHOD","신용/체크카드");
                    startActivity(intent2);
                    break;
                case R.id.btnPhone:
                    String strPhone = spinnerPhone.getSelectedItem().toString().trim();
                    Intent intent3 = new Intent(PaymentModifyActivity.this, PurchaseCheckActivity.class);
                    intent3.putExtra("ITEM",strPhone);
                    intent3.putExtra("PAYMETHOD","휴대폰");
                    startActivity(intent3);
                    break;
            }

        }
    };


}