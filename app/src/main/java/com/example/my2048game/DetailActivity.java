package com.example.my2048game;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2048);
        TextView scoreUser = (TextView)findViewById(R.id.userDetail);
        TextView scoreTime = (TextView)findViewById(R.id.timeDetail);
        TextView scoreTotal = (TextView)findViewById(R.id.scoreDetail);
        ImageView scoreImage = (ImageView)findViewById(R.id.scoreImageDetail);
        scoreUser.setText(getIntent().getStringExtra("Username"));
        scoreUser.setText(getIntent().getStringExtra("Time"));
        scoreUser.setText(getIntent().getStringExtra("Score"));
        Glide.with(this).load(getIntent().getIntExtra("image_resource",0))
                .into(scoreImage);
    }
}