package com.example.supia.Activities.Product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.supia.R;

public class ReviewFragment extends Fragment {

    String urlIp;
    int productNo;

    public ReviewFragment(String urlIp, int productNo) {
        this.urlIp = urlIp;
        this.productNo = productNo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }
}//끄읕