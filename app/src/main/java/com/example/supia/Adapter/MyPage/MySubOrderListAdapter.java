package com.example.supia.Adapter.MyPage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supia.Activities.MyPage.MyPageReviewInsertActivity;
import com.example.supia.Dto.MyPage.MyOrderListDto;
import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MySubOrderListAdapter extends RecyclerView.Adapter<MySubOrderListAdapter.MyViewHolder>{
    String TAG = "tjqm오더리스트어댑터";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MySubscribeDto> data = null;
    private LayoutInflater inflater = null;
    private String urlIp = ShareVar.urlIp;

    String urlAddr = "http://"+urlIp+":8080/pictures/";


    public MySubOrderListAdapter(Context mContext, int layout, ArrayList<MySubscribeDto> data) {

        Log.v(TAG, "첫뻔째 어댑터 ");

        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_subscribe_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {





        holder.productPrice.setText(Integer.toString(data.get(position).getSubscribeProductPrice()));

        holder.subscribeQuantity.setText(Integer.toString(data.get(position).getSubscribeOrderQuantity()));
        holder.productName.setText(data.get(position).getSubscribeProductName());

        Glide.with(holder.productImagePath)
                .load(urlAddr + data
                        .get(position).getProductImagePath())
//                .placeholder(R.drawable.noimg)
                .override(120, 120)
                .apply(new RequestOptions().circleCrop()).into(holder.productImagePath);//사진



    }



    public interface OnItemClickListener {

        void onItemClick(View v, int position);


    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView productImagePath;
        TextView productName;
        TextView productPrice;
        TextView subscribeQuantity;



        public MyViewHolder(@NonNull View v) {
            super(v);
            Log.v(TAG, "마이 뷰홀더");
            productName = v.findViewById(R.id.tv_productname_mysubscribe_detail);
            productPrice = v.findViewById(R.id.tv_productprice_mysubscribe_detail);
            productImagePath = v.findViewById(R.id.iv_product_mysubscribe_detail);
            subscribeQuantity = v.findViewById(R.id.tv_productquantity_mysubscribe_detail);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();//어뎁터 포지션값
                    // 뷰홀더에서 사라지면 NO_POSITION 을 리턴
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });




        }
    }
}//------------------
