package com.example.supia.Adapter.MyPage;

import android.content.Context;
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
import com.example.supia.Dto.MyPage.MyQnaDto;
import com.example.supia.Dto.MyPage.MySubscribeDto;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyQnaListAdapter extends RecyclerView.Adapter<MyQnaListAdapter.MyViewHolder> {
    String TAG = "마이큐엔에이어댑터";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyQnaDto> data = null;
    private LayoutInflater inflater = null;
    private String urlIp = ShareVar.urlIp;

    String urlAddr = "http://" + urlIp + ":8080/pictures/";


    public MyQnaListAdapter(Context mContext, int layout, ArrayList<MyQnaDto> data) {

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
        View view = inflater.inflate(R.layout.myqna_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.qnaProductNameMypage.setText(data.get(position).getProductName());
            holder.qnaContentMypage.setText(data.get(position).getQnaContent());

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

        TextView qnaProductNameMypage;
        TextView qnaContentMypage;


        public MyViewHolder(@NonNull View v) {
            super(v);
            Log.v(TAG, "마이 뷰홀더");

            qnaProductNameMypage = v.findViewById(R.id.tv_productname_myqna);
            qnaContentMypage = v.findViewById(R.id.tv_content_myqna);


        }
    }
}//------------------
