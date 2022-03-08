package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        imageView.startAnimation(spinin);

        TextView textView = (TextView) findViewById(R.id.TextViewBottomVersion);
        Animation fad = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(fad);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        imageView.clearAnimation();

        TextView textView = (TextView) findViewById(R.id.TextViewBottomVersion);
        textView.clearAnimation();

    }


}