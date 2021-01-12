package com.example.supia.Adapter.Product;


import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.supia.Dto.Product.CartDto;
import com.example.supia.NetworkTask.Product.NetworkTaskCart;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  implements Checkable {
    final static String TAG = "카트어뎁터";

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    Context mContext = null;
    int layout = 0;
    LayoutInflater inflater = null;

    private ArrayList<CartDto> mDataset;

    CartAdapter adapter = null;
    ArrayList<CartDto> cart;
    private RecyclerView recyclerView = null;


    int Counter;



    String urlAddr = "http://"+ ShareVar.urlIp +":8080/pictures/";//Ip


    private Object ArrayList;



    public CartAdapter(Context mContext, int layout, ArrayList<CartDto> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.mDataset = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listlayout_cart, parent, false);
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

//        final int pos = position;


        Glide.with(holder.productImg)
                .load(urlAddr + mDataset
                        .get(position).getCartProductImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)//이미지가 로딩하는 동안 보여질 이미
                .override(120, 120)//이미지 크기
                .apply(new RequestOptions()).into(holder.productImg);//사진




        //현재 카운트된 수량
        holder.count = Integer.parseInt((String) holder.productQuantity.getText());


        /**
         * 수량 플러스,마이너스,삭제버튼
         */
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"삭제버튼 클릭시 삭제");
                int cartNo = mDataset.get(position).getCartNo();
                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/deletecart.jsp?";
                urlAddr = urlAddr + "cartNo=" + cartNo ;
                connectGetData();

                mDataset.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.productQuantity.setText(String.valueOf(++holder.count));

                int cartProductQuantity = Integer.parseInt((String) holder.productQuantity.getText());
                int cartNo = mDataset.get(position).getCartNo();

                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/updateQuantity.jsp?";
                urlAddr = urlAddr + "cartProductQuantity=" + cartProductQuantity + "&cartNo=" + cartNo;
                connectGetData();

            }
        });


        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Counter =  Integer.parseInt((String) holder.productQuantity.getText());
                if (Counter >1){

                    holder.count = Integer.parseInt((String) holder.productQuantity.getText());

                    holder.productQuantity.setText(String.valueOf(--holder.count));

                    int cartProductQuantity = Integer.parseInt((String) holder.productQuantity.getText());
                    int cartNo = mDataset.get(position).getCartNo();

                    urlAddr = "http://" + ShareVar.urlIp + ":8080/test/updateQuantity.jsp?";
                    urlAddr = urlAddr + "cartProductQuantity=" + cartProductQuantity + "&cartNo=" + cartNo;


                    connectGetData();
                }

            }
        });


        /**
         * 체크박스 부분
         */


        //in some cases, it will prevent unwanted situations
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.isPressed();


        //if true, your checkbox will be selected, else unselected
//        holder.cbSelect.setChecked(objIncome.isSelected());
//        holder.cbSelect.setSelected(mDataset.get(position).isSelected());


        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


//                checked[position] = isChecked;

                String result = (String) ArrayList;

                if (holder.cbSelect.isChecked()) {
                    Log.d(TAG, "콤보박스 값 선택되는지 확인");
//                    for (int i = 0; i<items.)

                    result += mDataset.get(position).getCartProductName();
                    Log.d(TAG, "listItem : " + result);
//                    Log.d(TAG, "checked : " + checked[position]);
                } else {
                }
            }

        });
//        holder.cbSelect.setChecked(checked[position]);



    }


    /**
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {

        return false;
    }

    @Override
    public void toggle() {

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



        /**
         * 상품명,상품이미지,상품가격 선언
         */


        public ImageView productImg;
        public TextView productName,productPrice,productQuantity,deleteBtn;
        public Button plusBtn, minusBtn;
        public CheckBox cbSelect;
        int count = 1;


        private CheckBox[] cb;


        MyViewHolder(View v) {

            super(v);

            productName = v.findViewById(R.id.tv_productName_listlayout_cart);
            productPrice = v.findViewById(R.id.tv_productPrice_listlayout_cart);
            productQuantity = v.findViewById(R.id.tv_productQuantity_listlayout_cart);

            productImg = v.findViewById(R.id.iv_product_listlayout_cart);
            deleteBtn = v.findViewById(R.id.delete_Btn_listlayout_cart);
            plusBtn = v.findViewById(R.id.plus_btn_listlayout_cart);
            minusBtn = v.findViewById(R.id.minus_btn_listlayout_cart);

            cbSelect = v.findViewById(R.id.cbSelect_listlayout_cart);

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


        public void onBind(CartDto cartDto) {

            /**
             * 상품 이름,가격
             */

            productName.setText(cartDto.getCartProductName());
            DecimalFormat myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(Integer.parseInt( cartDto.getCartProductPrice()));
            productPrice.setText(formattedStringPrice);

            //db에서 수량 새로고침
            productQuantity.setText(cartDto.getCartProductQuantity());
        }




    }

    private void connectGetData() {
        try {

            NetworkTaskCart networkTask = new NetworkTaskCart(mContext, urlAddr,"like");

            Object obj = networkTask.execute().get();


//            cart = (ArrayList<CartDto>) obj;
//            adapter = new CartAdapter(mContext, R.layout.listlayout_cart, cart);
//            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}//------------------------------