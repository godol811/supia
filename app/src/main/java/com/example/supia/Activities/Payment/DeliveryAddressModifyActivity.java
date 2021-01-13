package com.example.supia.Activities.Payment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Activities.Login.AddressWebViewActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularDeliveryAddressListActivity;
import com.example.supia.Activities.RegualarDeliveryPayment.RegularPurchaseCheckActivity;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.PaymentShareVar;
import com.example.supia.ShareVar.ShareVar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeliveryAddressModifyActivity extends Activity {

    TextView deliveryAddr;
    EditText deliverAddrDetail, deliveryTel, deliveryName;
    Button btnChange, btnCancel, btnDelete;
    String urlAddr = null;
    String urlIp = null;
    String strWay;
    int intDeliveryNo;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_modify);

        Intent intent = getIntent();
        intDeliveryNo = intent.getIntExtra("deliveryNo", 0);//리스트뷰에서 있는 값 넘어오기
        String strDeliveryName = intent.getStringExtra("deliveryName");
        String strDeliveryAddr = intent.getStringExtra("deliveryAddr");
        strWay = intent.getStringExtra("way");
        String strDeliveryAddrDetail = intent.getStringExtra("deliveryAddrDetail");
        String strDeliveryTel = intent.getStringExtra("deliveryTel");


        urlAddr = "http://" + ShareVar.urlIp + ":8080/test/supiaDeliveryAddrModify.jsp?";

        deliveryAddr = findViewById(R.id.et_address);
        deliverAddrDetail = findViewById(R.id.et_address_detail);
        deliveryTel = findViewById(R.id.et_TelNumber);
        deliveryName = findViewById(R.id.et_deliveryname);

        deliveryName.setText(strDeliveryName);
        deliveryAddr.setText(strDeliveryAddr);
        deliverAddrDetail.setText(strDeliveryAddrDetail);
        deliveryTel.setText(strDeliveryTel);


        btnChange = findViewById(R.id.btn_changeDeliveryAddr);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_deleteDeliveryAddr);
        btnChange.setOnClickListener(mOnclickListener);
        btnCancel.setOnClickListener(mOnclickListener);
        btnDelete.setOnClickListener(mOnclickListener);

        deliveryAddr.setOnClickListener(new View.OnClickListener() {//주소 검색 API
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryAddressModifyActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

    }//onCreate끝

    View.OnClickListener mOnclickListener = new View.OnClickListener() {//클릭 이벤트

        @Override
        public void onClick(View v) {
            String strUserId = null;
            String strDeliveryAddr = deliveryAddr.getText().toString().trim();
            String strDeliveryAddrDetail = deliverAddrDetail.getText().toString().trim();
            String strDeliveryTel = deliveryTel.getText().toString().trim();
            String strDeliveryName = deliveryName.getText().toString().trim();

            Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyy-MM-dd").format(date));//파일에도 날짜를 넣기위한 메소드 (최신날짜의 배송지를 불러오기 위한 수단)
            switch (v.getId()) {
                case R.id.btn_changeDeliveryAddr:

                    if (strDeliveryAddr.trim().length() != 0 && strDeliveryAddrDetail.trim().length() != 0 && strDeliveryTel.trim().length() != 0 && strDeliveryName.trim().length() != 0) {//빈칸이 없을경우엔

                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                .setTitle("수정")
                                .setMessage("수정하시겠습니까?")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("아니오", null)
                                .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        urlAddr = urlAddr + "userId=" + ShareVar.sharvarUserId + "&deliveryAddr=" + strDeliveryAddr + "&deliveryAddrDetail=" + strDeliveryAddrDetail + "&deliveryTel=" + strDeliveryTel
                                                + "&deliveryName=" + strDeliveryName + "&insertDate=" + today + "&deliveryNo=" + intDeliveryNo;
                                        connectInsertData();
                                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                                .setTitle("완료")
                                                .setMessage("수정이 완료 되었습니다.")
                                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {//닫기와 동시에 결제 화면으로 돌아가서 결제 화면에 있는 배송지를 띄우는 역할
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if (strWay.equals("normal")) {
                                                            Intent intent = new Intent(DeliveryAddressModifyActivity.this, PurchaseCheckActivity.class);
                                                            PaymentShareVar.deliveryAddr = strDeliveryAddr;
                                                            PaymentShareVar.deliveryAddrDetail = strDeliveryAddrDetail;
                                                            PaymentShareVar.deliveryTel = strDeliveryTel;
                                                            PaymentShareVar.deliveryName = strDeliveryName;
                                                            startActivity(intent);
                                                        } else {
                                                            Intent intent = new Intent(DeliveryAddressModifyActivity.this, RegularPurchaseCheckActivity.class);
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
                        break;
                    } else if (strDeliveryTel.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                .setTitle("전화번호를 입력하세요")
                                .setMessage("전화번호를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        deliveryTel.setFocusable(true);
                        break;
                    } else if (strDeliveryAddrDetail.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                .setTitle("상세 주소를 입력하세요")
                                .setMessage("상세 주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        deliverAddrDetail.setFocusable(true);
                        break;
                    } else if (strDeliveryAddr.trim().length() == 0) {
                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                .setTitle("주소를 입력하세요")
                                .setMessage("주소를 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        break;
                    } else {
                        new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                .setTitle("받는 사람을 입력하세요")
                                .setMessage("받는 사람을 입력하세요.")
                                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                .setPositiveButton("닫기", null)
                                .show();
                        break;

                    }
                case R.id.btn_deleteDeliveryAddr:
                    urlIp = ShareVar.urlIp;
                    urlAddr = "http://" + urlIp + ":8080/test/supiaUserDeliveryAddrDelete.jsp?";//바로 삭제 할 수 있도록 한다.(따로 페이지 필요 X)

                    new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                            .setTitle("삭제")
                            .setMessage("진짜로 삭제 하시겠습니까?")
                            .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                            .setPositiveButton("아니오", null)
                            .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    urlAddr = urlAddr + "deliveryNo=" + intDeliveryNo;
                                    connectInsertData();

                                    new AlertDialog.Builder(DeliveryAddressModifyActivity.this)
                                            .setTitle("완료")
                                            .setMessage("삭제가 완료 되었습니다.")
                                            .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                            .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (strWay.equals("normal")) {
                                                        Intent intent = new Intent(DeliveryAddressModifyActivity.this, DeliveryAddressListActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(DeliveryAddressModifyActivity.this, RegularDeliveryAddressListActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            })
                                            .show();
                                }
                            }).show();


                    Log.d("수정및삭", "들어가나?");
                    break;
                case R.id.btn_cancel:
                    onBackPressed();//뒤로가기
                    break;

            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {//다음 주소 API 에서 가져온 값을 setText 함
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        deliveryAddr.setText(data);//여기서
                    }
                }
                break;
        }
    }

    private void connectInsertData() {//수정관련 NetWorkTask로 넘어감
        try {
            Log.d("url 들어간거1", urlAddr);
            DeliveryAddressNetWorkTask updateworkTask = new DeliveryAddressNetWorkTask(DeliveryAddressModifyActivity.this, urlAddr, "update");
            updateworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}