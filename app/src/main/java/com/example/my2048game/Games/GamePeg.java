package com.example.my2048game.Games;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.my2048game.R;
import com.example.my2048game.Utils.DBHelper;


public class GamePeg extends AppCompatActivity {

    private DBHelper mDB;
    private Chronometer mChronometer;
    public static int HOLE =0, PEG = 1, SELECTED = 3;
    private int casillas = 7;
    private int movimientos = 0;
    private int score;
    private String totalscore;
    private String scorecopy;
    private TextView txtscore;
    private Button [][] butons = new Button[casillas][casillas];
    private boolean firstClick = true;
    private boolean firstMove = true;
    private boolean move = false;
    private int prevI;
    private int prevJ;
    private int [][] copiaPeg = new int [casillas][casillas];
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
        mDB = new DBHelper(this);
        contarFichas();
        updatePeg();
        mChronometer = findViewById(R.id.chronometer);
        txtscore = findViewById(R.id.score);
        for (int i = 0; i<butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                copiaPeg[i][j]=pegMap[i][j];
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                butons[i][j] = findViewById(resID);
            }
        }

    }

    /**
     * Al presionar en un boton, obtendrá I,J.
     * Lo que permitirá realizar movimientos dependiendo del segundo click.
     * @param view
     */
    public void Click(View view) {
        int i = Character.getNumericValue(view.getTag().toString().charAt(0));
        int j = Character.getNumericValue(view.getTag().toString().charAt(1));

        //Primer movimiento, se debe seleccionar una casilla que incluya un peg.
        if (firstClick && pegMap[i][j]==PEG) {
            if (pegMap[prevI][prevJ] == PEG) {
                butons[prevI][prevJ].setBackground(getDrawable(R.drawable.shape_peg));
            }
            butons[i][j].setBackground(getDrawable(R.drawable.selected_shape_peg));
            prevI = i;
            prevJ = j;
            //Devolvemos un false que será el segundo movimiento.
            firstClick = false;
        //Para que haya un movimiento valido tiene que clicarse en una casilla vacia.
        }else if (!firstClick && pegMap[i][j] == HOLE){
            for (int x = 0; x < pegMap.length; x++) {
                for (int y = 0; y < pegMap.length; y++) {
                    copiaPeg[x][y]=pegMap[x][y];
                    scorecopy = txtscore.getText().toString();
                }
            }
            checkMovimiento(i,j);
            if (gameOver()){
                if (contarFichas()==1){
                    Intent intent = new Intent(GamePeg.this, SplashVictory.class);
                    intent.putExtra("score", txtscore.getText().toString());
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(GamePeg.this, SplashGameOver.class);
                    intent1.putExtra("score", txtscore.getText().toString());
                    startActivity(intent1);
                }
                mDB.insertScore(MenuJuegos.user,"PEG", mChronometer.getText().toString(), totalscore);
                finish();
            }

        } else if (!firstClick && pegMap[i][j]==PEG) {
            butons[prevI][prevJ].setBackground(getDrawable(R.drawable.shape_peg));
            butons[i][j].setBackground(getDrawable(R.drawable.selected_shape_peg));
            prevI = i;
            prevJ = j;
            firstClick = false;
        }
    }


    /**
     * Revisa si se puede hacer el movimiento.
     * @param i variable del último botón clicado.
     * @param j variable del último boton clicado.
     */
    private void checkMovimiento(int i, int j) {
        if (j == prevJ) {
            //Movimiento de abajo hacia arriba.
            if ((i - prevI) == -2 && pegMap[i +1][j] == PEG) {
                butons[prevI][prevJ].setBackground(getDrawable(R.drawable.no_shape));
                butons[i + 1][j].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j].setBackground(getDrawable(R.drawable.shape_peg));
                pegMap[i][j]=PEG;
                pegMap[i+1][j] = HOLE;
                pegMap[prevI][prevJ] = HOLE;
                firstClick = true;
                movimientos++;
                move = true;
                //Actualizamos el texto con los pegs totales.
                updatePeg();
                //Actualizamos puntuación
                newScore();
                //Iniciamos el timer cuando se realiza el primer movimiento.
                if (firstMove){
                    startTimer();
                    firstMove = false;
                }
            }
            //Movimiento de arriba a abajo.
            else if ((i - prevI) == 2 && pegMap[i-1][j] == PEG) {
                butons[prevI][prevJ].setBackground(getDrawable(R.drawable.no_shape));
                butons[i-1][j].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j].setBackground(getDrawable(R.drawable.shape_peg));
                pegMap[i][j]=PEG;
                pegMap[i-1][j] = HOLE;
                pegMap[prevI][prevJ] = HOLE;
                firstClick = true;
                movimientos++;
                move = true;
                //Actualizamos el texto con los pegs totales.
                updatePeg();
                //Actualizamos puntuación
                newScore();
                //Iniciamos el timer cuando se realiza el primer movimiento.
                if (firstMove){
                    startTimer();
                    firstMove = false;
                }
            }
        }
        else if (i == prevI){
            //Movimiento de Derecha a Izquierda
            if ((j - prevJ) == -2 && pegMap[i][j+1] == PEG) {
                butons[prevI][prevJ].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j+1].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j].setBackground(getDrawable(R.drawable.shape_peg));
                pegMap[i][j]=PEG;
                pegMap[i][j+1] = HOLE;
                pegMap[prevI][prevJ] = HOLE;
                firstClick = true;
                movimientos++;
                move = true;
                //Actualizamos el texto con los pegs totales.
                updatePeg();
                //Actualizamos puntuación
                newScore();
                //Iniciamos el timer cuando se realiza el primer movimiento.
                if (firstMove){
                    startTimer();
                    firstMove = false;
                }
            }
            //Movimiento de izquierda a derecha
            else if ((j - prevJ) == 2 && pegMap[i][j-1] == PEG) {
                butons[prevI][prevJ].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j-1].setBackground(getDrawable(R.drawable.no_shape));
                butons[i][j].setBackground(getDrawable(R.drawable.shape_peg));
                pegMap[i][j]=PEG;
                pegMap[i][j-1] = HOLE;
                pegMap[prevI][prevJ] = HOLE;
                firstClick = true;
                movimientos++;
                move = true;
                //Actualizamos el texto con los pegs totales.
                updatePeg();
                //Actualizamos puntuación
                newScore();
                //Iniciamos el timer cuando se realiza el primer movimiento.
                if (firstMove){
                    startTimer();
                    firstMove = false;
                }
            }
        // En caso de que no se pueda realizar el movimiento lo dejaremos como esta.
        } else {
            if (pegMap[prevI][prevJ]==PEG && pegMap[i][j]==HOLE){

            }
        }
    }

    /**
     * Deshacer un movimiento.
     * @param view
     */
    public void undo(View view){
        totalscore = scorecopy;
        for (int i = 0; i < copiaPeg.length; i++) {
            for (int j = 0; j < copiaPeg[0].length; j++) {
                pegMap[i][j]=copiaPeg[i][j];
            }
        }
        for (int i = 0; i < pegMap.length; i++) {
            for (int j = 0; j < pegMap[0].length; j++) {
                if (pegMap[i][j] == PEG){
                    butons[i][j].setBackground(getDrawable(R.drawable.shape_peg));
                }
                else if (pegMap[i][j] == HOLE){
                    butons[i][j].setBackground(getDrawable(R.drawable.no_shape));
                }
            }
        }
        txtscore.setText(totalscore);
        firstClick = true;
    }

    /**
     * Inicia de nuevo la actividad.
     * @param view
     */
    public void restart(View view){
        finish();
        Intent i = new Intent(this, GamePeg.class);
        startActivity(i);
        firstClick = true;
        firstMove=true;
    }

    /**
     * Cuenta las fichas del tablero
     * @return devuele el número exacto.
     */
    public int contarFichas(){
        int suma = 0;
        for (int i = 0; i < pegMap.length; i++) {
            for (int j = 0; j < pegMap[0].length; j++) {
                if (pegMap[i][j]==PEG){
                    suma++;
                }
            }
        }
        return  suma;
    }


    /**
     * Devolverá true o false, de si la partida termina o no.
     * @return true = gameOver.
     */
    public boolean gameOver() {
        //Leemos de Izquierda a derecha
        for (int i = 0; i < pegMap.length; i++) {
            for (int j = 1; j < pegMap[0].length - 2; j++) {
                if (pegMap[i][j - 1] == PEG && pegMap[i][j] == PEG) {
                    if (pegMap[i][j + 1] == HOLE) {
                        return false;
                    }
                }
                //Comprobamos la casilla de la izquierda
                if (pegMap[i][j] == PEG && pegMap[i][j + 1] == PEG) {
                    if (pegMap[i][j - 1] == HOLE) {
                        return false;
                    }
                }
                //Comprobamos los bordes (Evitamos ArrayOutOfBounds)
                if (pegMap[i][j + 1] == PEG && pegMap[i][j + 2] == PEG) {
                    if (pegMap[i][j] == HOLE) {
                        return false;
                    }
                }
            }
        }
        //Leemos de arriba abajo
        for (int i = 1; i < pegMap.length - 2; i++) {
            for (int j = 0; j < pegMap[0].length; j++) {
                if (pegMap[i - 1][j] == PEG && pegMap[i][j] == PEG) {
                    if (pegMap[i + 1][j] == HOLE) {
                        return false;
                    }
                }
                //Comprobamos la casilla de Arriba si está vacia o no.
                if (pegMap[i][j] == PEG && pegMap[i + 1][j] == PEG) {
                     if (pegMap[i - 1][j] == HOLE) {
                        return false;
                    }
                }
                //Comprobamos los bordes (Evitamos ArrayOutOfBounds)
                if (pegMap[i + 1][j] == PEG && pegMap[i + 2][j] == PEG) {
                    if (pegMap[i][j] == HOLE) {
                        return false;
                    }
                }
            }
        }return true;
    }

    /**
     * Método para obtener la puntuación y que salga por pantalla.
     */
    public void newScore(){
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        int minusTime = (int) ((elapsedMillis/1000));
        if (minusTime<25) {
            score +=5;
        } else if ( minusTime >= 25 && minusTime < 40){
            score +=4;
        } else if ( minusTime >= 40 && minusTime < 60){
            score +=3;
        } else {
            score +=2;
        }
        if ( score< 0){
            score = 0;
        }
        totalscore = String.valueOf(score);
        txtscore.setText(totalscore);
    }

    /**
     * Iniciar el chronometro
     */
    private void startTimer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    /**
     * Revision de cuantas fichas quedan.
     */
    public void updatePeg(){
        TextView txtpeg = (TextView) findViewById(R.id.Txtpeg);
        txtpeg.setText("Hay " + contarFichas() + " pegs en el tablero.");
    }

    /**
     * En caso de salir se pierde el registro.
     */
    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(GamePeg.this);
        builder.setMessage("Are you sure you want to exit?\n" +
                "\n" +
                "The data will not be saved.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(GamePeg.this,
                                MainPegSolitaire.class));
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}






  
