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
import com.example.supia.NetworkTask.Product.NetworkTaskBuyCount;
import com.example.supia.NetworkTask.Product.NetworkTaskPreference;
import com.example.supia.R;

import java.text.DecimalFormat;

public class ProductDetilFragment extends Fragment {

    private final String TAG = "스크롤 1";
    TextView text10, text20, text30, text40, text50;
    ProgressBar progressBar10, progressBar20, progressBar30, progressBar40, progressBar50;

    ImageView ivProductImage;
    ImageView ivProductInfo;
    TextView tvBrandName;
    TextView tvProductName;
    TextView tvProductPrice;

    String urlIp;
    int productNo;
    String productName;
    String productPrice;
    String productBrand;
    String productImagePath;
    String productInfo; //이거 추가

    public ProductDetilFragment(String urlIp, int productNo, String productName, String productPrice, String productBrand, String productImagePath, String productInfo) {
        this.urlIp = urlIp;
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBrand = productBrand;
        this.productImagePath = productImagePath;
        this.productInfo = productInfo;
    } //생성자 변경

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "oncreate");
        View view = inflater.inflate(R.layout.fragment_product_detil, null);

        ivProductImage = view.findViewById(R.id.productImage_productdetilfragment);
        ivProductInfo = view.findViewById(R.id.infoimg_productdetil); // 아이디 추가
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


        Glide.with(ProductDetilFragment.this).
                load(urlAddr + productInfo).
                override(3000, 1500).
                placeholder(R.drawable.blank_productinfo).
                apply(new RequestOptions()).into(ivProductInfo);




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

        connectGetDataTen();
        connectGetDataTwenty();;
        connectGetDataThirty();
        connectGetDataForty();
        connectGetDataFifty();


        return view;
    }


    //------ 10대 선호도
    private void connectGetDataTen() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaPreferenceTen.jsp?productNo="+productNo;
            NetworkTaskPreference networkTask2 = new NetworkTaskPreference(getContext(), buyUrl, "select");
            Object obj10 = networkTask2.execute().get();
            String birth = (String) obj10;
            int birthData = Integer.parseInt(birth)*10;
            String Preference10 = Integer.toString(birthData);
            text10.setText(Preference10); //json 파싱
            String inputStr10 = text10.getText().toString().trim(); //프로그래스바
            int input10 = Integer.parseInt(inputStr10);
            progressBar10.setProgress(input10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------

    //------ 20대 선호도
    private void connectGetDataTwenty() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaPreferenceTwenty.jsp?productNo="+productNo;
            NetworkTaskPreference networkTask2 = new NetworkTaskPreference(getContext(), buyUrl, "select");
            Object obj20 = networkTask2.execute().get();
            String birth = (String) obj20;
            int birthData = Integer.parseInt(birth)*10;
            String Preference20 = Integer.toString(birthData);
            text20.setText(Preference20); //json 파싱
            String inputStr20 = text20.getText().toString().trim(); //프로그래스바
            int input20 = Integer.parseInt(inputStr20);
            progressBar20.setProgress(input20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------

    //------ 30대 선호도
    private void connectGetDataThirty() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaPreferenceThirty.jsp?productNo="+productNo;
            NetworkTaskPreference networkTask2 = new NetworkTaskPreference(getContext(), buyUrl, "select");
            Object obj30 = networkTask2.execute().get();
            String birth = (String) obj30;
            int birthData = Integer.parseInt(birth)*10;
            String Preference30 = Integer.toString(birthData);
            text10.setText(Preference30); //json 파싱
            String inputStr30 = text30.getText().toString().trim(); //프로그래스바
            int input30 = Integer.parseInt(inputStr30);
            progressBar30.setProgress(input30);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------

    //------ 40대 선호도
    private void connectGetDataForty() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaPreferenceForty.jsp?productNo="+productNo;
            NetworkTaskPreference networkTask2 = new NetworkTaskPreference(getContext(), buyUrl, "select");
            Object obj40 = networkTask2.execute().get();
            String birth = (String) obj40;
            int birthData = Integer.parseInt(birth)*10;
            String Preference40 = Integer.toString(birthData);
            text40.setText(Preference40); //json 파싱
            String inputStr40 = text40.getText().toString().trim(); //프로그래스바
            int input40 = Integer.parseInt(inputStr40);
            progressBar40.setProgress(input40);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------

    //------ 50대 선호도
    private void connectGetDataFifty() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaPreferenceFifty.jsp?productNo="+productNo;
            NetworkTaskPreference networkTask2 = new NetworkTaskPreference(getContext(), buyUrl, "select");
            Object obj50 = networkTask2.execute().get();
            String birth = (String) obj50;
            int birthData = Integer.parseInt(birth)*10;
            String Preference50 = Integer.toString(birthData);
            text50.setText(Preference50); //json 파싱
            String inputStr50 = text50.getText().toString().trim(); //프로그래스바
            int input50 = Integer.parseInt(inputStr50);
            progressBar50.setProgress(input50);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------






}//끄읕