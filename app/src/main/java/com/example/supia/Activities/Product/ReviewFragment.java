package com.example.supia.Activities.Product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Adapter.Product.ProductReviewListAdapter;
import com.example.supia.Dto.MyPage.MyReviewDto;
import com.example.supia.NetworkTask.Product.NetworkTaskBuyCount;
import com.example.supia.NetworkTask.Product.ProductReviewInsertNetworkTask;
import com.example.supia.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    String urlIp;
    int productNo;


    private RecyclerView recyclerView;
    private ProductReviewListAdapter adapter;
    private ArrayList<MyReviewDto> list;
    RecyclerView.LayoutManager reviewLayoutManager = null;

    TextView tvBuycount;


    public ReviewFragment(String urlIp, int productNo) {
        this.urlIp = urlIp;
        this.productNo = productNo;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_review, container, false);


        tvBuycount = rootView.findViewById(R.id.buycount_fragmentqna);

        list = new ArrayList<MyReviewDto>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_review_fragmentqna);
        recyclerView.setHasFixedSize(true);
        reviewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reviewLayoutManager);



        connectGetDataBuy();
        connectGetData();
        return rootView;

    }
//---리뷰
    private void connectGetData() {
        try {

            String reviewUrl = "http://" + urlIp + ":8080/test/supiaProductReviewList.jsp?productNo="+productNo;
            ProductReviewInsertNetworkTask networkTask1 = new ProductReviewInsertNetworkTask(getActivity(), reviewUrl, "select");
            Object obj = networkTask1.execute().get();
            list = (ArrayList<MyReviewDto>) obj;
            adapter = new ProductReviewListAdapter(getContext(), R.layout.activity_my_order, list);
            recyclerView.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//-----

    //------ 몇명이 구매 했는지
    private void connectGetDataBuy() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaBuyCount.jsp?productNo="+productNo;
            NetworkTaskBuyCount networkTask2 = new NetworkTaskBuyCount(getContext(), buyUrl, "select");
            Object obj2 = networkTask2.execute().get();
            Log.v("안들어오니?" ,"obj"+obj2);
            String cnt = (String) obj2;
            tvBuycount.setText(cnt);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//--------

}//끄읕