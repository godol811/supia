package com.example.supia.Adapter.Product;


import android.content.Context;
import android.nfc.Tag;
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
import android.widget.Toast;

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
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    final static String TAG = "카트어뎁터";


    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    public SparseBooleanArray array = new SparseBooleanArray();




    Context mContext = null;
    int layout = 0;
    LayoutInflater inflater = null;

    private ArrayList<CartDto> mDataset;



    ArrayList<CartDto> sendCartData ;


    CartAdapter adapter = null;




    int Counter;

    int result;

    String urlAddr = "http://"+ ShareVar.urlIp +":8080/pictures/";//Ip


    //전체 체크용
    List<CheckBox> intradayCheckboxsList = new ArrayList<>();





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




        // 체크박스 리스트에 전부 추가하기
        intradayCheckboxsList.add(holder.cbSelect);


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
         * 수량 플러스,마이너스, 리스트에서 바로 삭제하는 버튼
         */
//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "삭제버튼 클릭시 삭제");
//                int cartNo = mDataset.get(position).getCartNo();
//                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/deletecart.jsp?";
//                urlAddr = urlAddr + "cartNo=" + cartNo;
//
//                connectGetData();
//
//                mDataset.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
//                notifyItemChanged(holder.getAdapterPosition());
//
//
//            }
//        });

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 누를때마다 수량 증가
                holder.productQuantity.setText(String.valueOf(++holder.count));

                holder.count = Integer.parseInt((String) holder.productQuantity.getText());


                //price onBind할떄 구한 가격 * 현재 수량
                result = holder.price * holder.count;

                Log.d(TAG, "result : " + result);

//                String[] strPrice = holder.price.split(",");
//                for (int i = 0;i<strPrice.length;i++){
//                     result = strPrice[i];
//                    Log.d(TAG,"result : " + result);
//                }
                //수량 * 가격 가격에 표시
                holder.productPrice.setText(Integer.toString(result));

                //가격 , 찍기
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                String formattedStringPrice = myFormatter.format(result);
                holder.productPrice.setText(formattedStringPrice);

                //db에 넘겨줄값
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
                Counter = Integer.parseInt((String) holder.productQuantity.getText());
                if (Counter > 1) {
//                    int productQuantity = Integer.parseInt(String.valueOf(holder.productQuantity));

                    holder.productQuantity.setText(String.valueOf(--holder.count));

                    holder.count = Integer.parseInt((String) holder.productQuantity.getText());


                    Log.d(TAG, "마이너스버튼 price : " + String.valueOf(holder.price));

//                    result = result - holder.price;


                    result = holder.price * holder.count;


                    Log.d(TAG, "productQuantity값 int로 변환 : " + holder.count);
                    holder.productPrice.setText(Integer.toString(result));


                    int cartProductQuantity = Integer.parseInt((String) holder.productQuantity.getText());
                    int cartNo = mDataset.get(position).getCartNo();


                    DecimalFormat myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(result);
                    holder.productPrice.setText(formattedStringPrice);


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
//        holder.cbSelect.setOnCheckedChangeListener(null);
//        holder.cbSelect.isPressed();


        /**
         * 바인딩 문제는 해결되지만 체크박스가 해제됨, 클릭리스너로 하면된다.
         */

        if (array.get(position)) {
            holder.cbSelect.setChecked(true);
        } else {
            holder.cbSelect.setChecked(false);
        }

    }




    /**
     * 체크박스 전체선택 옵션 메소드
     * @param what
     */

    public void checkBoxOperation(boolean what){
        for (CheckBox checkBox : intradayCheckboxsList ){
            //true면 체크
            //false면 체크풀기
            checkBox.setChecked(what);
        }
    }

    public ArrayList<CartDto> checkBoxChecked(){

        return checkBoxChecked();

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


    /**
     *
     *아이템 갯수를 사이즈로 리턴
     * @return
     */

    @Override
    public int getItemCount() {
        if (mDataset != null)
        return mDataset.size();
        else
        return 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }





     /**
     * 선택한 뷰는 뷰홀더가 가지고있기 때문에
     * ViewHolder에 Checkable interface를 구현
     */

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


        int price;




        private CheckBox[] cb;


         MyViewHolder(View v) {

            super(v);



            sendCartData = new ArrayList<CartDto>();

            productName = v.findViewById(R.id.tv_productName_listlayout_cart);
            productPrice = v.findViewById(R.id.tv_productPrice_listlayout_cart);
            productQuantity = v.findViewById(R.id.tv_productQuantity_listlayout_cart);

            productImg = v.findViewById(R.id.iv_product_listlayout_cart);
//            deleteBtn = v.findViewById(R.id.delete_tv_listlayout_cart);
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
            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sendCartData.clear();


                    for (int i = 0; i < intradayCheckboxsList.size(); i ++){
                        if (intradayCheckboxsList.get(i).isChecked() == true){
                            sendCartData.add(mDataset.get(i));
                            Log.d(TAG,"-----------------------------------");
                            Log.d(TAG, "mDataset데이터 이름 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductName()));
                            Log.d(TAG, "mDataset데이터 이미지 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductImagePath()));
                            Log.d(TAG, "mDataset데이터 가격 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductPrice()));
                            Log.d(TAG, "mDataset데이터 수량 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductQuantity()));
                            Log.d(TAG,"-----------------------------------");
                        }
                    }
                }
            });

//            cbSelect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String str = "";
//                  sendCartData.clear();

//                    for (int i =0; i < mDataset.size(); i++){
//                        if (cbSelect.isChecked() == true){
//                            sendCartData.add(mDataset.get(getAdapterPosition()));
//                            Log.d(TAG,"position" + String.valueOf(getAdapterPosition()));
//                            Log.d(TAG,"position" + String.valueOf(mDataset.get(getAdapterPosition()).getCartProductName()));
//
//                        }else {
//                            Log.d(TAG,"체크해제");

//                    for (int i = 0; i < intradayCheckboxsList.size(); i ++){
//                        if (intradayCheckboxsList.get(i).isChecked() == true){
//                            sendCartData.add(mDataset.get(i));
//                            Log.d(TAG,"-----------------------------------");
//                            Log.d(TAG, "mDataset데이터 이름 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductName()));
//                            Log.d(TAG, "mDataset데이터 이미지 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductImagePath()));
//                            Log.d(TAG, "mDataset데이터 가격 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductPrice()));
//                            Log.d(TAG, "mDataset데이터 수량 " + i + "번째 " + String.valueOf(mDataset.get(i).getCartProductQuantity()));
//                            Log.d(TAG,"-----------------------------------");
//                        }
//                    }

//                }
//            });



        }


        public void onBind(CartDto cartDto) {

            /**
             * 상품 이름,가격
             */


            productName.setText(cartDto.getCartProductName());
            Log.d(TAG, "if문에 넣어줄값" + String.valueOf(cartDto.getCartProductId()));

            price = Integer.parseInt(cartDto.getCartProductPrice());


            price = Integer.parseInt((String) cartDto.getCartProductPrice());
            count = Integer.parseInt((String) cartDto.getCartProductQuantity());
            result = price * count;

            Log.d(TAG,"OnBind result : " + result);
            DecimalFormat myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(result);
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


    /**
     * 카트 삭제 메소드
     */
    // 장바구니 선택 제품 삭제
    public void connectDeleteData() {

        if (sendCartData.size() == 0) {
            Toast.makeText(mContext, "삭제할 제품을 선택해주세요", Toast.LENGTH_SHORT).show();
        } else {
//            String urlAddrDelete;

            for (int i = 0; i < sendCartData.size(); i++) {
//                urlAddrDelete = "http://" + ShareVar.urlIp + ":8080/JSP/cart_delete_inCart.jsp?userEmail=" + userEmail + "&prdName=";
//                urlAddrDelete = urlAddrDelete + sendCartData.get(i).getCartNo();

                int cartNo = sendCartData.get(i).getCartNo();
                urlAddr = "http://" + ShareVar.urlIp + ":8080/test/deletecart.jsp?";
                urlAddr = urlAddr + "cartNo=" + cartNo;

                try {
                    NetworkTaskCart networkTask = new NetworkTaskCart(mContext, urlAddr,"like");

                    Object obj = networkTask.execute().get();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public ArrayList<CartDto> sendDate(){

        return sendCartData;

    }



}//------------------------------