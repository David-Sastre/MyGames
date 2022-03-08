package com.example.my2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreGames extends AppCompatActivity {

    // Member variables.
    private DBHelper mDB;
    private RecyclerView mRecyclerView;
    private ArrayList<Score> mScoreData;
    private ScoresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score2048);
        mDB = new DBHelper(this);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize the ArrayList that will contain the data.
        mScoreData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ScoresAdapter(this, mScoreData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();

//        int swipeDirs;
//        if (gridColumnCount > 1) {
//            swipeDirs = 0;
//        } else {
//            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        }
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
//                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
//                        | ItemTouchHelper.UP, swipeDirs) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView,
//                                  RecyclerView.ViewHolder viewHolder,
//                                  RecyclerView.ViewHolder target) {
//
//                int from = viewHolder.getAdapterPosition();
//                int to = target.getAdapterPosition();
//                Collections.swap(mScoreData, from, to);
//                mAdapter.notifyItemMoved(from, to);
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                mScoreData.remove(viewHolder.getAdapterPosition());
//                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//
//            }
//        });
//        helper.attachToRecyclerView(mRecyclerView);
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


        // Clear the existing data (to avoid duplication).
        mScoreData.clear();

        // Create the ArrayList of Scores objects with titles and
        // information about each Score.
        for (int i = 0; i < ScoresUser.length; i++) {
            mScoreData.add(new Score(ScoresUser[i], game[i], ScoresTime[i], ScoresTotal[i],
                    R.drawable.ic_twotone_emoji_events_24));
        }
        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

//    public void resetScores(View view) {
//        initializeData();
//    }
}