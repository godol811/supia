package com.example.supia.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.supia.Activities.Payment.DeliveryAddressListActivity;
import com.example.supia.Activities.Payment.DeliveryAddressModifyActivity;
import com.example.supia.Activities.Payment.PurchaseCheckActivity;
import com.example.supia.Dto.UserDeliveryAddrDto;
import com.example.supia.NetworkTask.DeliveryAddressNetWorkTask;
import com.example.supia.R;

import java.util.ArrayList;

public class DeliveryAddressAdapter extends BaseAdapter {

    final static String TAG = "어드레스어뎁터";
    private String urlAddr;//합칠땐 상수를 넣기로
    private String macIp;//특히 IP 주소

    Context mContext = null;
    int layout = 0;
    LayoutInflater inflater = null;
    private ArrayList<UserDeliveryAddrDto> mDataset;
    Button btnModify, btnDelete, btnMoveNext;
    DeliveryAddressListActivity deliveryAddressListActivity;


    public DeliveryAddressAdapter(Context mContext, int layout, ArrayList<UserDeliveryAddrDto> mDataset) {
        this.mContext = mContext;
        this.layout = layout;
        this.mDataset = mDataset;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }//Bean에서 가져온 사이즈만큼의 카운트

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//ListView를 보여주는곳
        View view = inflater.inflate(R.layout.listlayout_delivery_address, null);//listlayout과 통신을 해준다.
        TextView deliveryName = view.findViewById(R.id.tv_deliveryName_dlistlayout);//view를 넣어야 findviewid가 활성화 된다.(listview에 있는 id값이기 때문)
        TextView deliveryAddress = view.findViewById(R.id.tv_deliveryaddress_dlistlayout);
        TextView deliveryAddressDetail = view.findViewById(R.id.tv_deliveryaddressDetail_dlistlayout);
        TextView deliveryTel = view.findViewById(R.id.tv_deliverytel_dlistlayout);

        deliveryName.setText(mDataset.get(position).getDeliveryName());//가져온값들을 리스트 뷰에 넣는다.
        deliveryAddress.setText(mDataset.get(position).getDeliveryAddr());
        deliveryAddressDetail.setText(mDataset.get(position).getDeliveryAddDetail());
        deliveryTel.setText(mDataset.get(position).getDeliveryTel());

        btnModify = view.findViewById(R.id.btn_moveModify_dlistlayout);
        btnDelete = view.findViewById(R.id.btn_deleteAdress_dlistlayout);
        btnMoveNext = view.findViewById(R.id.btn_movePayment_dlistlayout);


        btnModify.setOnClickListener(new View.OnClickListener() {//수정
            @Override
            public void onClick(View v) {//수정버튼을 누를시 그 position에 있는 값들을 intent로 뿌린다.





                Intent intent = new Intent(mContext, DeliveryAddressModifyActivity.class);
                intent.putExtra("deliveryNo", mDataset.get(position).getDeliveryNo());//No는 index값이므로 매우 중요하다.
                intent.putExtra("deliveryName", mDataset.get(position).getDeliveryName());
                intent.putExtra("way","normal");
                intent.putExtra("deliveryAddr", mDataset.get(position).getDeliveryAddr());
                intent.putExtra("deliveryAddrDetail", mDataset.get(position).getDeliveryAddDetail());
                intent.putExtra("deliveryTel", mDataset.get(position).getDeliveryTel());
                mContext.startActivity(intent);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {//삭제
            @Override
            public void onClick(View v) {
                macIp = "192.168.35.147";
                urlAddr = "http://" + macIp + ":8080/test/supiaUserDeliveryAddrDelete.jsp?";
                new AlertDialog.Builder(mContext)
                        .setTitle("삭제")
                        .setMessage("진짜로 삭제 하시겠습니까?")
                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                        .setPositiveButton("아니오", null)
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                urlAddr = urlAddr + "deliveryNo=" + mDataset.get(position).getDeliveryNo();
                                connectDeleteData();

                                new AlertDialog.Builder(mContext)
                                        .setTitle("완료")
                                        .setMessage("삭제가 완료 되었습니다.")
                                        .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(mContext, DeliveryAddressListActivity.class);
                                                mContext.startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        }).show();
            }
        });

        btnMoveNext.setOnClickListener(new View.OnClickListener() {//선택
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(mContext, PurchaseCheckActivity.class);//배송지를 선택했다면 그부분으로 넘어간다.
                intent2.putExtra("deliveryNo", mDataset.get(position).getDeliveryNo());
                intent2.putExtra("deliveryName", mDataset.get(position).getDeliveryName());
                intent2.putExtra("way","normal");
                intent2.putExtra("deliveryAddr", mDataset.get(position).getDeliveryAddr());
                intent2.putExtra("deliveryAddrDetail", mDataset.get(position).getDeliveryAddDetail());
                intent2.putExtra("deliveryTel", mDataset.get(position).getDeliveryTel());
                mContext.startActivity(intent2);
            }
        });


        return view;
    }
    private void connectDeleteData() {//삭제관련 VOID 메소드
        try {
            Log.d("url 들어간거1",urlAddr);
            DeliveryAddressNetWorkTask deleteworkTask = new DeliveryAddressNetWorkTask(mContext, urlAddr, "delete");
            deleteworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
