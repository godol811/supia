package com.example.supia.Activities.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.supia.R;

public class SplashActivity extends AppCompatActivity {

    //field

    Animation animation;
    ImageView view;
    private final int SPLASH_DISPLAY_LENGTH = 2500;//Splash 유지 시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        view = findViewById(R.id.logo_splash);



        animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate); //animation 시작 위치 및 사용될 layout
        animation.setFillAfter(true);
        view.startAnimation(animation);



        //------------------------------------------------------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent mainIntent = new Intent(SplashActivity.this, tutorial1.class); //Splash 끝나고 이동경로
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }




    @Override
    public void onBackPressed() { //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함

    }


}