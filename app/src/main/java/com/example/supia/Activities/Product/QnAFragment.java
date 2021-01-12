package com.example.supia.Activities.Product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.supia.R;

public class QnAFragment extends Fragment {

    Button btnQnaWrite;
    ImageButton ibQna1down, ibQna1up, ibQna2down, ibQna2up, ibQna3down, ibQna3up, ibQna4down, ibQna4up;

    LinearLayout llQna1Text, llQna2Text, llQna3Text, llQna4Text;

    String urlIp;
    int productNo;
    String productName;

    public QnAFragment(String urlIp, int productNo, String productName) {
        this.urlIp = urlIp;
        this.productNo = productNo;
        this.productName = productName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qna, null);

        btnQnaWrite = view.findViewById(R.id.qna_write);

        ibQna1down = view.findViewById(R.id.qna1down_qnafragment);
        ibQna1up = view.findViewById(R.id.qna1up_qnafragment);
        llQna1Text = view.findViewById(R.id.qna1_text_qnafragment);

        ibQna2down = view.findViewById(R.id.qna2down_qnafragment);
        ibQna2up = view.findViewById(R.id.qna2up_qnafragment);
        llQna2Text = view.findViewById(R.id.qna2_text_qnafragment);

        ibQna3down = view.findViewById(R.id.qna3down_qnafragment);
        ibQna3up = view.findViewById(R.id.qna3up_qnafragment);
        llQna3Text = view.findViewById(R.id.qna3_text_qnafragment);

        ibQna4down = view.findViewById(R.id.qna4down_qnafragment);
        ibQna4up = view.findViewById(R.id.qna4up_qnafragment);
        llQna4Text = view.findViewById(R.id.qna4_text_qnafragment);



        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.qna_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WriteQnAActivity.class);
                intent.putExtra("urlIp" ,urlIp);
                intent.putExtra("productNo",productNo);
                intent.putExtra("productName",productName);
                startActivity(intent);

            }

        });

        getView().findViewById(R.id.qna1down_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna1down.setVisibility(View.INVISIBLE);
                ibQna1up.setVisibility(View.VISIBLE);
                llQna1Text.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.qna1up_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna1down.setVisibility(View.VISIBLE);
                ibQna1up.setVisibility(View.INVISIBLE);
                llQna1Text.setVisibility(View.GONE);

            }
        });

        getView().findViewById(R.id.qna2down_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna2down.setVisibility(View.INVISIBLE);
                ibQna2up.setVisibility(View.VISIBLE);
                llQna2Text.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.qna2up_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna2down.setVisibility(View.VISIBLE);
                ibQna2up.setVisibility(View.INVISIBLE);
                llQna2Text.setVisibility(View.GONE);

            }
        });

        getView().findViewById(R.id.qna3down_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna3down.setVisibility(View.INVISIBLE);
                ibQna3up.setVisibility(View.VISIBLE);
                llQna3Text.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.qna3up_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna3down.setVisibility(View.VISIBLE);
                ibQna3up.setVisibility(View.INVISIBLE);
                llQna3Text.setVisibility(View.GONE);

            }
        });

        getView().findViewById(R.id.qna4down_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna4down.setVisibility(View.INVISIBLE);
                ibQna4up.setVisibility(View.VISIBLE);
                llQna4Text.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.qna4up_qnafragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibQna4down.setVisibility(View.VISIBLE);
                ibQna4up.setVisibility(View.INVISIBLE);
                llQna4Text.setVisibility(View.GONE);

            }
        });

    }
}//끄읕