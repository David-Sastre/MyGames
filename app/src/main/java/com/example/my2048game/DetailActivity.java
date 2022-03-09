package com.example.my2048game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private String user;
    private DBHelper mDB;
    private TextView scoreUser, gameScore,scoreTime, scoreTotal;
    private String username, game, time, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDB = new DBHelper(this);
        scoreUser = (TextView)findViewById(R.id.userDetail);
        gameScore = (TextView)findViewById(R.id.gameDetail);
        scoreTime = (TextView)findViewById(R.id.timeDetail);
        scoreTotal = (TextView)findViewById(R.id.scoreDetail);
        user = MenuJuegos.user;

        initValues();
    }

    private void initValues(){
        Bundle extras = getIntent().getExtras();
        username = extras.getString("user");
        game = extras.getString("game");
        time = extras.getString("time");
        score = extras.getString("score");

        scoreUser.setText(username);
        gameScore.setText(game);
        scoreTime.setText(time);
        scoreTotal.setText(score);
    }

    public void delete(View view) {
        if (user.equals("admin")){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Do you want to delete this score?")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDB.deleteScore(username, game, time, score);
                            Toast.makeText(getApplicationContext(), "Deleting score....", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailActivity.this,
                                    ScoreGames.class));
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Cancel....", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else if (username.equals(user)){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Do you want to delete this score?")
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDB.deleteScore(username, game, time, score);
                            Toast.makeText(getApplicationContext(), "Deleting score....", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailActivity.this,
                                    ScoreGames.class));
                            finish();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Cancel....", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("You can not delete another user's score.")
                    .setTitle("ERROR")
                    .show();
        }

    }
}