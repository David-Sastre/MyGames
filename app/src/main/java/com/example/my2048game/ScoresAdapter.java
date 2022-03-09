package com.example.my2048game;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder>{
    // Member variables.
    private ArrayList<Score> mScoresData;
    private Context mContext;

    ScoresAdapter(Context context, ArrayList<Score> ScoresData) {
        this.mScoresData = ScoresData;
        this.mContext = context;

    }

    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.score_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoresAdapter.ViewHolder holder,
                                 int position) {
        // Get current Score.
        Score currentScore = mScoresData.get(position);
        // Populate the textviews with data.
        holder.bindTo(currentScore);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Score currentScore = mScoresData.get(getAdapterPosition());
                    Intent detailIntent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    detailIntent.putExtra("user", currentScore.getUsername());
                    detailIntent.putExtra("game", currentScore.getGame());
                    detailIntent.putExtra("time", currentScore.getTime());
                    detailIntent.putExtra("score", currentScore.getScoreTotal());
                    holder.itemView.getContext().startActivity(detailIntent);

                }
        });
    }

    @Override
    public int getItemCount() {
        return mScoresData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Member Variables for the TextViews
        private TextView mScoreUser;
        private TextView mGame;
        private TextView mScoreTime;
        private TextView mScoreTotal;

        ViewHolder(View itemView) {
            super(itemView);
            mScoreUser = itemView.findViewById(R.id.scoreUser);
            mGame = itemView.findViewById(R.id.game);
            mScoreTime = itemView.findViewById(R.id.scoreTime);
            mScoreTotal = itemView.findViewById(R.id.scoreTotal);

        }

        public TextView getScoreUser() {
            return mScoreUser;
        }

        public TextView getGame() {
            return mGame;
        }

        public TextView getScoreTime() {
            return mScoreTime;
        }

        public TextView getScoreTotal() {
            return mScoreTotal;
        }

        void bindTo(Score currentScore){

            mScoreUser.setText(currentScore.getUsername());
            mGame.setText(currentScore.getGame());
            mScoreTime.setText(currentScore.getTime());
            mScoreTotal.setText(currentScore.getScoreTotal());
        }
    }
}