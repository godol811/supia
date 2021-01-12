package com.example.supia.Activities.Product;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;

public class WriteQnAActivity extends AppCompatActivity {

    EditText etWriteQnawrite;
    TextView tvCharnumQnawrite ,tvProductNameQnawrite ;
    Button btnCancle , btnOk;

    String urlAddr = null;
    String urlIp;
    int productNo;
    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_qna);
        //값 받아오기
        Intent intent = getIntent();
        urlIp= intent.getStringExtra("urlIp");
        productNo = intent.getIntExtra("productNo", 0);
        productName= intent.getStringExtra("productName");

        urlAddr = "http://" + urlIp + ":8080/test/pnaInsert.jsp?";

        tvProductNameQnawrite = findViewById(R.id.productName_qnawrite);
        etWriteQnawrite = (EditText) findViewById(R.id.write_qnawrite);
        tvProductNameQnawrite.setText(productName);

        tvCharnumQnawrite = (TextView) findViewById(R.id.charnum_qnawrite);
        btnCancle = findViewById(R.id.cancel_qnawrite);
        btnCancle.setOnClickListener(cancelClick);

        btnOk = findViewById(R.id.ok_qnawrite);
        btnOk.setOnClickListener(okOnclick);


        etWriteQnawrite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = etWriteQnawrite.getText().toString();
                tvCharnumQnawrite.setText(input.length() + " / 300 글자");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // qna 작성 취소
    View.OnClickListener cancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(WriteQnAActivity.this, CategoryActivity.class);
            startActivity(intent);

        }
    };

    //qna 작성 확인
    View.OnClickListener okOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new AlertDialog.Builder(WriteQnAActivity.this)
                    .setTitle("Q & A 작성 완료")
                    .setMessage("작성한 Q & A 는 마이페이지에서 \n 조회 가능 합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //sql에 넣을 값
                            String productNameQna  = tvProductNameQnawrite.getText().toString();
                            String qnaContent = etWriteQnawrite.getText().toString();
                            String userId = "sibal1";

                            urlAddr = urlAddr + "qnaContent=" + qnaContent + "&userId=" + userId + "&productName=" + productNameQna;
                            connectInsertData();
                            Intent intent = new Intent(WriteQnAActivity.this, ProductMainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setCancelable(true)
                    .show();

        }
    };




    private void connectInsertData() {
        try {
            NetworkTaskProduct insertworkTask = new NetworkTaskProduct(WriteQnAActivity.this, urlAddr, "insert");
            insertworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //배경 터치 시 키보드 사라지게
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

}//끄읕