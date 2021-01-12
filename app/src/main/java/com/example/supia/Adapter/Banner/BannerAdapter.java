package com.example.supia.Adapter.Banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.supia.Activities.Product.ProductMainActivity;
import com.example.supia.R;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter{
    Context context;
    ArrayList<String> data;

    //drawable이미지
    int[] images = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};

    public BannerAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    public BannerAdapter(ProductMainActivity mainActivity) {
    }


    @Override//실제 레이아웃을 출력하는 메소드
    public Object instantiateItem(ViewGroup container, int position) {

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.banner_auto_viewpager,null);
//        View v = inflater.inflate(R.layout.auto_viewpager,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.image_container);
        Glide.with(context).load(images[position]).into(image_container);
        container.addView(v);

        return v;

//        View view = LayoutInflater.from(context).inflate(R.layout.auto_viewpager,container,false);
//        ImageView imageView1 = view.findViewById(R.id.image_container);
//        imageView1.setImageResource(images[position]);
//        Log.d("이미지리소스", Integer.toString(images[position]));
//        container.addView(view);
//        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override//사용 가능한 뷰의 개수를 return
    public int getCount() {
        return images.length;
    }

//    @Override
//    public int getCount() {
//        return data.size();
//    }

    @Override//페이지의 뷰가 생성된 뷰인지 아닌지를 확인합니다.
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
