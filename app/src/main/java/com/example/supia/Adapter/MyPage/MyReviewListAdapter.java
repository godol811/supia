package com.example.supia.Adapter.MyPage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Dto.MyPage.MyReviewDto;
import com.example.supia.R;

import java.util.ArrayList;

public class MyReviewListAdapter extends RecyclerView.Adapter<MyReviewListAdapter.MyViewHolder>{


    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyReviewDto> data = null;
    private LayoutInflater inflater = null;



    public MyReviewListAdapter(Context mContext, int layout, ArrayList<MyReviewDto> data) {
        Log.v("여기", "첫뻔째 어댑터 ");


        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.myreview_list_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.reviewProductName.setText(data.get(position).getProductName());
        holder.reviewContent.setText(data.get(position).getReviewContent());
        holder.reviewTitle.setText(data.get(position).getReviewTitle());

        Log.v("프로덕트네임",""+data.get(position).getProductName());
        Log.v("리뷰컨텐트",""+data.get(position).getReviewContent());
        Log.v("리뷰타이틀",""+data.get(position).getReviewTitle());

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

        TextView reviewProductName;
        TextView reviewContent;
        TextView reviewTitle;




        public MyViewHolder(@NonNull View v) {
            super(v);
            Log.v("여기 부홀더", "제발되라고 샹");
            reviewProductName = v.findViewById(R.id.review_productname);
            reviewContent = v.findViewById(R.id.review_content);
            reviewTitle = v.findViewById(R.id.review_title);


        }
    }
}//------------------