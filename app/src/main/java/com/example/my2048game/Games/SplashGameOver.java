package com.example.my2048game.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my2048game.R;


public class SplashGameOver extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 6500;

    private String scoreUser;
    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_game_over);
        score = findViewById(R.id.tvshowScore);

        ImageView imageView = (ImageView) findViewById(R.id.gameOver);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fade);

        Bundle extras = getIntent().getExtras();
        scoreUser = extras.getString("score");

        score.setText(scoreUser);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_SCREEN);
    }


    @Override
    public void onBackPressed() { }
}