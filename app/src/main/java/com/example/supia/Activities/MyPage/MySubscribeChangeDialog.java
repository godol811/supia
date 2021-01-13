package com.example.supia.Activities.MyPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.NetworkTask.UserInfoNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MySubscribeChangeDialog extends Dialog {


    ///////////////////////////////////////////
    // MyinfoActivity 에서 쓰이는 커스텀 다이얼로그 //
    ///////////////////////////////////////////


    private Context context;
    private int productPriceDialog = 0;
    private int productQuantity = 0;


    public MySubscribeChangeDialog(@NonNull Context context,int productPriceDialog,int productQuantity) {
        super(context);
        this.context = context;
        this.productPriceDialog = productPriceDialog;
        this.productQuantity = productQuantity;


    }

    public void callFunction() {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.subscribe_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        final TextView tvProductPrice = dialog.findViewById(R.id.tv_productprice_mysubscribe);
        final TextView tvPriceTotal = dialog.findViewById(R.id.tv_producttotal_mysubscribe);
        final TextView tvChangePayment = dialog.findViewById(R.id.tv_changepayment_mysubscribe);
        final TextView tvCxl = dialog.findViewById(R.id.tv_cxl_mysubscribe);
        final String userId = ShareVar.sharvarUserId;

        tvProductPrice.setText(productPriceDialog*productQuantity+"원");
        tvPriceTotal.setText((productPriceDialog*productQuantity)+2500+"원");


        tvChangePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyPageUpdatePaymentActivity.class);
                intent.putExtra("userId",userId);

                context.startActivity(intent);



            }
        });


        tvCxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }




}//---------------
