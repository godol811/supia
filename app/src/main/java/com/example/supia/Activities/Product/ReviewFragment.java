package com.example.supia.Activities.Product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Adapter.Product.ProductReviewListAdapter;
import com.example.supia.Dto.Product.ProductReviewDto;
import com.example.supia.NetworkTask.MyPage.MyReviewInsertNetworkTask;
import com.example.supia.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    String urlIp;
    int productNo;


    private RecyclerView recyclerView;
    private ProductReviewListAdapter adapter;
    private ArrayList<ProductReviewDto> list;
    RecyclerView.LayoutManager reviewLayoutManager = null;



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


        list = new ArrayList<ProductReviewDto>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_review_fragmentqna);
        recyclerView.setHasFixedSize(true);
        reviewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reviewLayoutManager);


        connectGetData();
        return rootView;

    }

    private void connectGetData() {
        try {

            String reviewUrl = "http://" + urlIp + ":8080/test/supiaProductReviewList.jsp?productNo="+productNo;
            MyReviewInsertNetworkTask networkTask1 = new MyReviewInsertNetworkTask(getActivity(), reviewUrl, "select");
            Object obj = networkTask1.execute().get();
            list = (ArrayList<ProductReviewDto>) obj;
            adapter = new ProductReviewListAdapter(getContext(), R.layout.activity_my_order, list);
            recyclerView.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//끄읕