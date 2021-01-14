package com.example.supia.Adapter.MyPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supia.Activities.Product.CartInsertActivity;
import com.example.supia.Dto.MyPage.MyLikeListDto;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.MyPage.MyPageLikeListNetworkTask;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;

public class MyLikeListAdapter extends RecyclerView.Adapter<MyLikeListAdapter.MyViewHolder> {


    private Context mContext = null;
    private int layout = 0;
    private ArrayList<MyLikeListDto> data = null;
    private ArrayList<CartDto> cart;
    private LayoutInflater inflater = null;
    String urlAddr1;
    String urlIp = ShareVar.urlIp;
    String userId = ShareVar.sharvarUserId;




    String urlAddr = "http://"+urlIp+":8080/pictures/";

    public MyLikeListAdapter(Context mContext, int layout, ArrayList<MyLikeListDto> data) {
        Log.v("여기", "첫뻔째 어댑터 ");


        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("여기", "두번째 어댑터 ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mypage_likelist_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.v("이프의 이프안임유", "" + data.get(position).getLikeCheck());


        holder.productName.setText("" + data.get(position).getProductName());
        holder.productPrice.setText(Integer.toString(data.get(position).getProductPrice()));
        if (data.get(position).getLikeCheck().equals("1")) {
            holder.like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_click));
            Log.v("이프안임유", "" + data.get(position).getLikeCheck());

        }


        Glide.with(holder.productImagePath)
                .load(urlAddr + data
                        .get(position).getProductImagePath())
                .override(120, 120)
                .apply(new RequestOptions().circleCrop()).into(holder.productImagePath);//사진

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setIcon(R.mipmap.supia)
                        .setTitle("알림")
                        .setMessage("장바구니에 넣겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("취소",null)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * 장바구니 클릭시 jsp 실행
                                 * 넘겨줘야될값 : 상품 번호,수량,가격
                                 */


                                int productNo = data.get(position).getProductNo();


                                int productPrice = data.get(position).getProductPrice();
                                String productName = data.get(position).getProductName();
                                String ProductImagePath = data.get(position).getProductImagePath();

                                Intent intent = new Intent(v.getContext(), CartInsertActivity.class);

                                intent.putExtra("productNo", productNo);
                                intent.putExtra("productPrice", productPrice);
                                intent.putExtra("productName", productName);
                                intent.putExtra("productImagePath", ProductImagePath);
                                v.getContext().startActivity(intent);

                            }
                        }).show();



            }
        });



        holder.like.setOnClickListener(new View.OnClickListener() { // 눌려있는 하트 다시 눌렀을때 찜목록에서 삭제
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setTitle("알림")
                        .setIcon(R.mipmap.supia)
                        .setMessage("찜목록에서 삭제 하시겠습니까?")
                        .setCancelable(false)
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url1 = "http://"+urlIp+":8080/text/supiaLikeListDelete.jsp?likeProductId="+data.get(position).getLikeProductId()+"&userId="+userId;
                                urlAddr1 = url1;
                                connectDeleteData();
                                data.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemChanged(holder.getAdapterPosition());

                            }


                        })
                        .setPositiveButton("취소",null)
                        .show();


            }
        });



    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {

        void onItemClick(View v, int position);


    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView productImagePath;
        TextView productName;
        TextView productPrice;
        ImageButton cart;
        ImageButton like;


        public MyViewHolder(@NonNull View v) {

            super(v);


            Log.v("여기 부홀더", "제발되라고 샹");
            productName = v.findViewById(R.id.tv_productname_mylikelist);
            productPrice = v.findViewById(R.id.tv_productprice_mylikelist);
            like = v.findViewById(R.id.ibm_like_mylikelist);
            productImagePath = v.findViewById(R.id.iv_productimg_mylikelist);
            cart = v.findViewById(R.id.ibn_cart_mylikelist);


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
    private void connectDeleteData() {

        try {

            MyPageLikeListNetworkTask deleteworkTask = new MyPageLikeListNetworkTask(mContext, urlAddr1, "delete");
            deleteworkTask.execute().get();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//-------------------------
