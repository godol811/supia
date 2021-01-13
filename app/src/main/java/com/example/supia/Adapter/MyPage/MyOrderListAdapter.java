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
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyOrderListAdapter  extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder>{
    String TAG = "오더리스트어댑터";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyOrderListDto> data = null;
    private LayoutInflater inflater = null;
    private String urlIp = ShareVar.urlIp;

    String urlAddr = "http://"+urlIp+":8080/pictures/";


    public MyOrderListAdapter(Context mContext, int layout, ArrayList<MyOrderListDto> data) {

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
        View view = inflater.inflate(R.layout.myorder_list_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.v(TAG,""+data.get(position).getProductName());


        holder.productName.setText(data.get(position).getProductName());
        holder.productPrice.setText(Integer.toString(data.get(position).getProductPrice()));
        holder.orderQuantity.setText(Integer.toString(data.get(position).getOrderQuantity()));


        Glide.with(holder.productImagePath)
                .load(urlAddr + data
                        .get(position).getProductImagePath())
//                .placeholder(R.drawable.noimg)
                .override(120, 120)
                .apply(new RequestOptions().circleCrop()).into(holder.productImagePath);//사진

        holder.insertReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tv_writereivew_myorderlist:
                        Intent intent = new Intent(v.getContext(), MyPageReviewInsertActivity.class);
                        intent.putExtra("productName",data.get(position).getProductName());
                        intent.putExtra("productPrice",data.get(position).getProductPrice());
                        intent.putExtra("productNo",data.get(position).getProductNo());
                        intent.putExtra("orderNo",data.get(position).getOrderNo());
                        v.getContext().startActivity(intent);
                        break;
                }
            }
        });



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

        TextView insertReview;

        ImageView productImagePath;
        TextView productName;
        TextView productPrice;
        TextView orderQuantity;



        public MyViewHolder(@NonNull View v) {
            super(v);
            Log.v(TAG, "마이 뷰홀더");
            productName = v.findViewById(R.id.tv_productname_myorderlist);
            productPrice = v.findViewById(R.id.tv_productprice_myorderlist);
            productImagePath = v.findViewById(R.id.iv_productimg_myorderlist);
            orderQuantity = v.findViewById(R.id.tv_productquantity_myorderlist);
            insertReview = v.findViewById(R.id.tv_writereivew_myorderlist);


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
