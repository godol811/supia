package com.example.supia.Adapter.Product;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import com.example.supia.Activities.Product.LikeActivity;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder>{
    final static String TAG = "어뎁터";

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    Context mContext = null;
    int layout = 0;
    LayoutInflater inflater = null;


    private ArrayList<ProductDto> mDataset;

    String likeCheck;

    MainAdapter adapter = null;

    private RecyclerView recyclerView = null;



    String urlAddr = "http://"+ ShareVar.urlIp+":8080/pictures/";




    public MainAdapter(Context mContext, int layout, ArrayList<ProductDto> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.mDataset = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listlayout_main, parent, false);
        return new MyViewHolder(view);
    }





    /**
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     * 재활용 되는 뷰가 호출하여 실행되는 메소드
     * 뷰 홀더를 전달하고 어댑터는 position 의 데이터를 결합
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.onBind(mDataset.get(position));






//      상품넘버 저장
        ShareVar.productId = mDataset.get(position).getProductNo();




        likeCheck = mDataset.get(position).getLikeCheck();



        if (likeCheck.equals("1")){
            Log.d(TAG, "1일떄" + mDataset.get(position).getLikeCheck());
            holder.likeListbtn.setImageResource(R.drawable.like_click);
        }else if (likeCheck.equals("0") || mDataset.get(position).getLikeCheck().equals("null")){
            Log.d(TAG, "0일때" + mDataset.get(position).getLikeCheck());
            holder.likeListbtn.setImageResource(R.drawable.like);
        }


        Glide.with(holder.productImg)
                .load(urlAddr + mDataset
                        .get(position).getProductImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)//이미지가 로딩하는 동안 보여질 이미
                .override(120, 120)//이미지 크기
                .apply(new RequestOptions()).into(holder.productImg);//사진


        holder.likeListbtn.setTag(position);
            holder.likeListbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"여기로도 들어오나? + TAG" + holder.likeListbtn.getTag());

                Log.d(TAG, "position : " + position);

                likeCheck = mDataset.get(position).getLikeCheck();

                if (mSelectedItems.get(position,false)){

                }else {

                    if (likeCheck.equals("null")){
                            Log.d(TAG,"테이블에 아무것도 없을떄 어디로 들어가는지 확인해보자 null");
                            holder.likeListbtn.setImageResource(R.drawable.like_click);
                            Intent intent = new Intent(v.getContext(), LikeActivity.class);
//                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("likeProductId", mDataset.get(position).getProductNo());
                            intent.putExtra("likeCheck", likeCheck);
                            v.getContext().startActivity(intent);
                        }else {

                        if (likeCheck.equals("1")){
                            Log.d(TAG,"테이블에 아무것도 없을떄 어디로 들어가는지 확인해보자 1");
                            holder.likeListbtn.setImageResource(R.drawable.like);
                            Intent intent = new Intent(v.getContext(), LikeActivity.class);
                            intent.putExtra("likeProductId", mDataset.get(position).getProductNo());
                            intent.putExtra("likeCheck", "0");
                            v.getContext().startActivity(intent);

                        }else if (likeCheck.equals("0")){
                            Log.d(TAG,"테이블에 아무것도 없을떄 어디로 들어가는지 확인해보자 0");
                            holder.likeListbtn.setImageResource(R.drawable.like_click);
                            Intent intent = new Intent(v.getContext(), LikeActivity.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("likeProductId", mDataset.get(position).getProductNo());
                            intent.putExtra("likeCheck", "1");
                            v.getContext().startActivity(intent);
                        }
                    }

                }
            }
        });



            holder.cartListbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /**
                     * 장바구니 클릭시 jsp 실행
                     * 넘겨줘야될값 : 상품 번호,수량,가격
                     */
                    Log.d(TAG,"장바구니클릭시 값들어오지는지 확인");

                    int productNo = mDataset.get(position).getProductNo();


                    String productPrice = mDataset.get(position).getProductPrice();
                    String productName = mDataset.get(position).getProductName();
                    String ProductImagePath = mDataset.get(position).getProductImagePath();

                    Intent intent = new Intent(v.getContext(), CartInsertActivity.class);

                    intent.putExtra("productNo", productNo);
                    intent.putExtra("productPrice", productPrice);
                    intent.putExtra("productName", productName);
                    intent.putExtra("productImagePath", ProductImagePath);
                    v.getContext().startActivity(intent);

                }
            });
    }



    //인터페이스 선언
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }

    private OnItemClickListener mListener = null;

    //메인에서 사용할 클릭메서드 선언
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    private OnItemLongClickListener mLongListener = null;

    public void setOnItemLongClickListener(OnItemLongClickListener longListener) {
        this.mLongListener = longListener;
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        final static String TAG1 = "MyViewHolder";
        // each data item is just a string in this case
        public TextView productName;
        public TextView productPrice;
        public ImageView productImg;


        public ImageButton likeListbtn;//라이크리스트버튼
        public ImageButton cartListbtn;//장바구니 추가 버튼

        int count = 1;

        MyViewHolder(View v) {

            super(v);
            productName = v.findViewById(R.id.tv_productName_listlayout);
            productPrice = v.findViewById(R.id.tv_productPrice_listlayout);
            productImg = v.findViewById(R.id.iv_product_listlayout);

            likeListbtn = v.findViewById(R.id.btn_likelist_product);
            cartListbtn = v.findViewById(R.id.btn_cart_product);


            Log.v(TAG1, "MyViewHolder");
            // 뷰홀더에서만 리스트 포지션값을 불러올 수 있음.

            //-----------------Click Event---------------------
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();//어뎁터 포지션값
                    // 뷰홀더에서 사라지면 NO_POSITION 을 리턴
                    Log.d(TAG,"상품상세페이지로 이동");
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, position);

                        }
                    }
                }
            });
        }


        public void onBind(ProductDto productDto) {


            /**
             * 가격 형식 표시
             */
            productName.setText(productDto.getProductName());

            DecimalFormat myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(Integer.parseInt( productDto.getProductPrice()));
            productPrice.setText(formattedStringPrice);


        }





    }





}//------------------------------