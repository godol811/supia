package com.example.supia.Activities.Product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.supia.R;

public class PurchaseFragment extends Fragment {

    ImageButton ibDeliverydown, ibDeliveryup ,ibExchangeup, ibExchangedown;
    LinearLayout llDeliverytext, llExchangetext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_purchase, null);
        llDeliverytext = view.findViewById(R.id.deliverytext_purchasefragment);
        ibDeliverydown = view.findViewById(R.id.deliverydown_purchasefragment);
        ibDeliveryup = view.findViewById(R.id.deliveryup_purchasefragment);

        llExchangetext = view.findViewById(R.id.exchangetext_purchasefragment);
        ibExchangedown = view.findViewById(R.id.exchangedown_purchasefragment);
        ibExchangeup = view.findViewById(R.id.exchangeup_purchasefragment);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.deliverydown_purchasefragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibDeliverydown.setVisibility(View.INVISIBLE);
                ibDeliveryup.setVisibility(View.VISIBLE);
                llDeliverytext.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.deliveryup_purchasefragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibDeliverydown.setVisibility(View.VISIBLE);
                ibDeliveryup.setVisibility(View.INVISIBLE);
                llDeliverytext.setVisibility(View.GONE);

            }
        });

        getView().findViewById(R.id.exchangedown_purchasefragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibExchangedown.setVisibility(View.INVISIBLE);
                ibExchangeup.setVisibility(View.VISIBLE);
                llExchangetext.setVisibility(View.VISIBLE);

            }
        });

        getView().findViewById(R.id.exchangeup_purchasefragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibExchangedown.setVisibility(View.VISIBLE);
                ibExchangeup.setVisibility(View.INVISIBLE);
                llExchangetext.setVisibility(View.GONE);

            }
        });

    }

}//끄읕