package com.example.my2048game.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my2048game.R;
import com.example.my2048game.Utils.DBHelper;

import java.util.ArrayList;

public class ScoreGames extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Member variables.
    private DBHelper mDB;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;
    private ArrayList<Score> mScoreData;
    private ScoresAdapter mAdapter;
    private String username;
    private Spinner mSpinner;
    private String[] spiners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mDB = new DBHelper(this);
        db = mDB.getReadableDatabase();
        username = MenuJuegos.user;
        int gridColumnCount = 1;
        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        //Initializa the spinner.
        mSpinner = findViewById(R.id.spinner);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
        // Initialize the ArrayList that will contain the data.
        mScoreData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ScoresAdapter(this, mScoreData);
        mRecyclerView.setAdapter(mAdapter);
        initializeData();
        spiners = new String[]{"user", "game", "time", "total_score"};
        mSpinner.setPrompt("Ordenar");
        ArrayAdapter<String> mspinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spiners);
        mspinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(mspinAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }
    /**
     * Initialize the Scores data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] ScoresUser = mDB.getScores("user");
        String[] game = mDB.getScores("game");
        String[] ScoresTime = mDB.getScores("time");
        String[] ScoresTotal = mDB.getScores("total_Score");
        mScoreData.clear();

        for (int i = 0; i < ScoresUser.length; i++) {
            mScoreData.add(new Score(ScoresUser[i], game[i], ScoresTime[i], ScoresTotal[i]));
        }
        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
    public void back (View view){
        Intent intent = new Intent(ScoreGames.this,
                MenuJuegos.class);
        intent.putExtra("user", username);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String orderBy;
        switch (i) {
            case -1:
                break;
            case 0:
                orderBy = "user";
                db.rawQuery("select * from Scores order by " + orderBy+ "", null);
                break;
            case 1:
                orderBy = "game";
                db.rawQuery("select * from Scores order by " + orderBy+ "", null);
                break;
            case 2:
                orderBy = "time";
                db.rawQuery("select * from Scores order by " + orderBy+ "", null);
                break;
            case 3:
                orderBy = "total_score";
                db.rawQuery("select * from Scores order by " + orderBy+ "", null);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
