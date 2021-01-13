package com.example.supia.Activities.Product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supia.R;

import java.text.DecimalFormat;

public class ProductDetilFragment extends Fragment {

    private final String TAG = "스크롤 1";
    TextView text10, text20, text30, text40, text50;
    ProgressBar progressBar10, progressBar20, progressBar30, progressBar40, progressBar50;

    ImageView ivProductImage;
    TextView tvBrandName;
    TextView tvProductName;
    TextView tvProductPrice;

    String urlIp;
    int productNo;
    String productName;
    String productPrice;
    String productBrand;
    String productImagePath;

    public ProductDetilFragment(String urlIp, int productNo, String productName, String productPrice, String productBrand, String productImagePath) {
        this.urlIp = urlIp;
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
        this.productImagePath = productImagePath;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "oncreate");
        View view = inflater.inflate(R.layout.fragment_product_detil, null);

        ivProductImage = view.findViewById(R.id.productImage_productdetilfragment);
        tvBrandName = view.findViewById(R.id.brandName_productdetilfragment);
        tvProductName = view.findViewById(R.id.productName_productdetilfragment);
        tvProductPrice = view.findViewById(R.id.productPrice_productdetilfragment);

        tvBrandName.setText(productBrand);
        tvProductName.setText(productName);
        tvProductPrice.setText("₩ "+productPrice);

        String urlAddr = "http://" + urlIp + ":8080/pictures/";
        Glide.with(ProductDetilFragment.this).
                load(urlAddr + productImagePath).
                override(300, 300).
                placeholder(R.drawable.blank_square).
                apply(new RequestOptions()).into(ivProductImage);




        //프로그래스바
        text10 = view.findViewById(R.id.text10);
        progressBar10 = view.findViewById(R.id.progress10);

        text20 = view.findViewById(R.id.text20);
        progressBar20 = view.findViewById(R.id.progress20);

        text30 = view.findViewById(R.id.text30);
        progressBar30 = view.findViewById(R.id.progress30);

        text40 = view.findViewById(R.id.text40);
        progressBar40 = view.findViewById(R.id.progress40);

        text50 = view.findViewById(R.id.text50);
        progressBar50 = view.findViewById(R.id.progress50);

        setProgressBar();


        return view;
    }

    public void setProgressBar() {
        String inputStr10 = text10.getText().toString().trim();
        int input10 = Integer.parseInt(inputStr10);     // 문자 -> 숫자 변환

        String inputStr20 = text20.getText().toString().trim();
        int input20 = Integer.parseInt(inputStr20);     // 문자 -> 숫자 변환

        String inputStr30 = text30.getText().toString().trim();
        int input30 = Integer.parseInt(inputStr30);     // 문자 -> 숫자 변환

        String inputStr40 = text40.getText().toString().trim();
        int input40 = Integer.parseInt(inputStr40);     // 문자 -> 숫자 변환

        String inputStr50 = text50.getText().toString().trim();
        int input50 = Integer.parseInt(inputStr50);     // 문자 -> 숫자 변환

        progressBar10.setProgress(input10);         // 0~ 100 까지 설정
        progressBar20.setProgress(input20);         // 0~ 100 까지 설정
        progressBar30.setProgress(input30);         // 0~ 100 까지 설정
        progressBar40.setProgress(input40);         // 0~ 100 까지 설정
        progressBar50.setProgress(input50);         // 0~ 100 까지 설정


    }



}//끄읕