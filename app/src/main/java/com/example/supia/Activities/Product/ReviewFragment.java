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
import com.example.supia.NetworkTask.MyPage.MyReviewInsertNetworkTask;
import com.example.supia.NetworkTask.Product.NetworkTaskBuyCount;
import com.example.supia.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    String urlIp;
    int productNo;


    private RecyclerView recyclerView;
    private ProductReviewListAdapter adapter;
    private ArrayList<MyReviewDto> list;
    RecyclerView.LayoutManager reviewLayoutManager = null;

    TextView Buycount;


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


        Buycount = rootView.findViewById(R.id.buycount_fragmentqna);

        list = new ArrayList<MyReviewDto>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_review_fragmentqna);
        recyclerView.setHasFixedSize(true);
        reviewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(reviewLayoutManager);



        connectGetDataBuy();
        connectGetData();
        return rootView;

    }

    private void connectGetData() {
        try {

            String reviewUrl = "http://" + urlIp + ":8080/test/supiaProductReviewList.jsp?productNo="+productNo;
            MyReviewInsertNetworkTask networkTask1 = new MyReviewInsertNetworkTask(getActivity(), reviewUrl, "select");
            Object obj = networkTask1.execute().get();
            Log.v("뭐지" ,"obj"+obj);
            list = (ArrayList<MyReviewDto>) obj;
            adapter = new ProductReviewListAdapter(getContext(), R.layout.activity_my_order, list);
            recyclerView.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectGetDataBuy() {
        try {

            String buyUrl = "http://" + urlIp + ":8080/test/supiaBuyCount.jsp?productNo="+productNo;
            NetworkTaskBuyCount networkTask2 = new NetworkTaskBuyCount(getActivity(), buyUrl, "select");
            Object obj = networkTask2.execute().get();
            Log.v("안들어오니?" ,"obj"+obj);
            String cnt = (String) obj;
            Buycount.setText(cnt);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}//끄읕