package com.example.my2048game;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class Game2 extends AppCompatActivity implements View.OnClickListener {

    public static int PEG = 1, HOLE = 0, NONE = 2;

    private Button[][] butons = new Button[7][7];
    private boolean firstClick = true;
    private int[] ballSelected;
    private int[][] pegMap = {
            {2, 2, 1, 1, 1, 2, 2},
            {2, 2, 1, 1, 1, 2, 2},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {2, 2, 1, 1, 1, 2, 2},
            {2, 2, 1, 1, 1, 2, 2}};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegsolitaire);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                butons[i][j] = findViewById(resID);
                butons[i][j].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View view) {
        //select(view);
    }

//    public void select(View view) {
//        int ballSelected[] = getPosition();
//                if ((firstClick) && (ballSelected == PEG)) {
//                    System.out.println(ballSelected);
//                    view.setBackground(getDrawable(R.drawable.selected_shape_peg));
//                    firstClick = false;
//                }
//                if (!firstClick && pegMap[i][j] == HOLE) {
//                    int ballSelected[] = getPosition();
//                    view.setBackground(getDrawable(R.drawable.selected_shape_peg));
//                    firstClick = true;
//                }
//            }
//
//    public int[] getPosition() {
//        int ball[] = new int[0];
//        for (int i = 0; i < butons.length; i++) {
//            for (int j = 0; j < butons[0].length; j++) {
//                ball = new int[]{i, j};
//            }
//        }
//        return ball;
//    }
}





  
