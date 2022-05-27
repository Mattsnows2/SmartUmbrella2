package com.example.smartumbrella;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView umbrella, goutte1,goutte2,goutte3,goutte4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        topAnim= AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        umbrella=findViewById(R.id.imageUmbrella2);
        goutte1=findViewById(R.id.imageGoutte1);
        goutte2=findViewById(R.id.imageGoutte2);
        goutte3=findViewById(R.id.imageGoutte3);
        goutte4=findViewById(R.id.imageGoutte4);

        umbrella.setAnimation(topAnim);
        goutte1.setAnimation(bottomAnim);
        goutte2.setAnimation(bottomAnim);
        goutte3.setAnimation(bottomAnim);
        goutte4.setAnimation(bottomAnim);


        new Handler().postDelayed(()->{
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);

            finish();
        },3000);
    }
}