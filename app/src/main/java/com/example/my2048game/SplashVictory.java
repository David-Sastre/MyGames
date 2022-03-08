package com.example.my2048game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashVictory extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 5500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_victory);

        ImageView imageView = (ImageView) findViewById(R.id.victory);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_SCREEN);
    }
}
