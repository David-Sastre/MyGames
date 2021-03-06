package com.example.my2048game.Games;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my2048game.R;
import com.example.my2048game.Utils.DBHelper;

import java.util.Random;

public class Game2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private DBHelper mDB;
    private int bestScore = 0;
    private TextView tscore;
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 100;
    private GestureDetector Detector;
    private int score;
    private int scorecopy;
    private int i;
    private Chronometer mChronometer;
    private int aux = 0;
    private Button [][] butons= new Button [4][4];
    private String [][] copia = new String [4][4];

    public int getScore() {
        return score;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048game);
        mDB = new DBHelper(this);
//        bestScoreText = findViewById(R.id.bestScore);
        mChronometer = findViewById(R.id.chronometer);
        //Iniciamos el GestureDetector
        Detector = new GestureDetector(this, this);
        for (int i = 0; i<butons.length; i++){
            for(int j=0; j<butons[0].length; j++){
                String buttonID = "btn" + i +j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                butons[i][j] = findViewById(resID);
            }
        }
        checkTablero();
        for (int x = 0; x < 2; x++) {
            insertRandom();
        }
        changeColor();
        boardCopy();
        startTimer();
    }

    /**
     * M??todo para insertar un n??mero (2 o 4) en posiciones aleatorias
     */
    private void insertRandom() {
        if(checkTablero()){
            int i = new Random().nextInt(butons.length);
            int j = new Random().nextInt(butons.length);
            while (!(butons[i][j].getText().equals(""))){
                i = new Random().nextInt(butons.length);
                j = new Random().nextInt(butons.length);
            }
            int x = new Random().nextInt(11);
            if (x>9){
                butons[i][j].setText("4");
            } else {
                butons[i][j].setText("2");
            }
        }
        changeColor();
        score(score);
        gameOver();
    }


    /**
     * M??todo que al hacer un swipe a la derecha realizar?? diversos m??todos
     */
    private void swipeRight (){
        if (checkRight()) {
            boardCopy();
            for (i = 0; i < butons.length; i++) {
                desplazarFilasDcha(i);
                sumarDerecha(i);
                winner();
                desplazarFilasDcha(i);
            }
            insertRandom();
        }
    }

    /**
     * M??todo que al hacer un swipe a la izquierda realizar?? diversos m??todos
     */
    private void swipeLeft () {
        if (checkLeft()){
            boardCopy();
            for (i = 0; i < butons.length; i++) {
                desplazarFilasIzq(i);
                sumarIzquierda(i);
                winner();
                desplazarFilasIzq(i);
            }
        insertRandom();
        }
    }

    /**
     * M??todo que al hacer un swipe hacia arriba realizar?? diversos m??todos
     */
    private void swipeUp(){
        if (checkUp()) {
            boardCopy();
            for (i = 0; i < butons.length; i++) {
                desplazarFilasArriba(i);
                sumarArriba(i);
                winner();
                desplazarFilasArriba(i);
            }
            insertRandom();
        }
    }

    /**
     * M??todo que al hacer un swipe hacia abajo realizar?? diversos m??todos
     */
    public void swipeDown(){
        if (checkDown()) {
            boardCopy();
            for (i = 0; i < butons.length; i++) {
                desplazarFilasAbajo(i);
                sumarAbajo(i);
                desplazarFilasAbajo(i);
            }
            winner();
            insertRandom();
        }
    }

    /**
     * Cambiar color de las casillas seg??n su texto.
     */
    private void changeColor() {
        for (int i=0; i< butons.length; i++){
            for (int j=0; j< butons[0].length; j++) {
                if (butons[i][j].getText().equals("")){
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color0));
                }else if (butons[i][j].getText().equals("2")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color2));
                } else if (butons[i][j].getText().equals("4")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color4));
                } else if (butons[i][j].getText().equals("8")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color8));
                } else if (butons[i][j].getText().equals("16")){
                butons[i][j].setBackground(this.getResources().getDrawable(R.color.color16));
                } else if (butons[i][j].getText().equals("32")){
                butons[i][j].setBackground(this.getResources().getDrawable(R.color.color32));
                } else if (butons[i][j].getText().equals("64")){
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color64));
                } else if (butons[i][j].getText().equals("128")){
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color128));
                } else if (butons[i][j].getText().equals("256")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color256));
                } else if (butons[i][j].getText().equals("512")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color512));
                }else if (butons[i][j].getText().equals("1024")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color1024));
                }else if (butons[i][j].getText().equals("2048")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color2048));
                }else if (butons[i][j].getText().equals("4096")) {
                    butons[i][j].setBackground(this.getResources().getDrawable(R.color.color4096));
                }
            }

        }
    }

    /**
     * Metodo que sumar?? las casillas cuando se cumplan las condiciones establecidas.
     * @param fila integer que recorrer?? las filas
     */
    public void sumarDerecha(int fila) {
        int resultado, boton;
        String resString;
        for (int j = butons[0].length-1; j > 0; j--) {
            if ((butons[fila][j].getText()).equals((butons[fila][j - 1].getText()))) {
                if ((butons[fila][j].getText()).equals("") || (butons[fila][j - 1].getText().equals(""))) {
                    //Al no poder sumar no hace nada.
                } else {
                    boton = Integer.parseInt(butons[fila][j].getText().toString());
                    butons[fila][j - 1].setText("");
                    resultado = boton * 2;
                    resString = Integer.toString(resultado);
                    butons[fila][j].setText(resString);
                    score += resultado;
                }
            }
        }
    }

    /**
     *M??todo que desplazar?? las casillas.
     * @param fila integer que recorrer?? las filas
     */
    public void desplazarFilasDcha(int fila) {
        for (int i = 0; i < butons.length; i++) {
            for (int j = butons[0].length-1; j > 0; j--) {
                if ((butons[fila][j].getText()).equals("") && !(butons[fila][j - 1].getText()).equals("")) {
                    butons[fila][j].setText(butons[fila][j - 1].getText());
                    butons[fila][j - 1].setText("");
                }
            }
        }
    }

    /**
     * Metodo que sumar?? las casillas cuando se cumplan las condiciones establecidas.
     * @param fila integer que recorrer?? las filas
     */
    public void sumarIzquierda(int fila){
        int resultado, boton;
        String resString;
        for (int j = 0; j < butons[0].length-1; j++) {
            if ((butons[fila][j].getText()).equals((butons[fila][j + 1].getText()))) {
                if ((butons[fila][j].getText()).equals("") || (butons[fila][j + 1].getText().equals(""))) {
                    //Al no poder sumar no hace nada.
                } else {
                    boton = Integer.parseInt(butons[fila][j].getText().toString());
                    butons[fila][j + 1].setText("");
                    resultado = boton * 2;
                    resString = Integer.toString(resultado);
                    butons[fila][j].setText(resString);
                    score += resultado;
                }
            }
        }
    }

    /**
     *M??todo que desplazar?? las casillas.
     * @param fila integer que recorrer?? las filas
     */
    public void desplazarFilasIzq(int fila) {
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length-1; j++) {
                if ((butons[fila][j].getText()).equals("") && !(butons[fila][j + 1].getText()).equals("")) {
                    butons[fila][j].setText(butons[fila][j + 1].getText());
                    butons[fila][j + 1].setText("");
                }
            }
        }
    }

    /**
     * Metodo que sumar?? las casillas cuando se cumplan las condiciones establecidas.
     * @param columna integer que recorrer?? las filas
     */
    public void sumarArriba(int columna){
        int resultado, boton;
        String resString;
        for (int i = 0; i < 3 ; i++) {
            if ((butons[i][columna].getText()).equals((butons[i+1][columna].getText()))) {
                if ((butons[i][columna].getText()).equals("") || (butons[i+1][columna].getText().equals(""))) {
                    //Al no poder sumar no hace nada.
                } else {
                    boton = Integer.parseInt(butons[i][columna].getText().toString());
                    butons[i+1][columna].setText("");
                    resultado = boton * 2;
                    resString = Integer.toString(resultado);
                    butons[i][columna].setText(resString);
                    score += resultado;

                }
            }
        }
    }


    /**
     * M??todo que desplazar?? las casillas.
     * @param columna
     */
    public void desplazarFilasArriba(int columna){
        for (int x = 0; x < butons.length; x++) {
            for (int i = 0; i < butons.length-1; i++) {
                if ((butons[i][columna].getText()).equals("") && !(butons[i+1][columna].getText()).equals("")) {
                    butons[i][columna].setText(butons[i+1][columna].getText());
                    butons[i+1][columna].setText("");
                }
            }
        }
    }

    /**
     * Metodo que sumar?? las casillas cuando se cumplan las condiciones establecidas.
     * @param columna
     */
    public void sumarAbajo (int columna){
        int resultado, boton;
        String resString;
        for (int i = butons.length-1; i > 0 ; i--) {
            if ((butons[i][columna].getText()).equals((butons[i-1][columna].getText()))) {
                if ((butons[i][columna].getText()).equals("") || (butons[i-1][columna].getText().equals(""))) {
                } else {
                    boton = Integer.parseInt(butons[i][columna].getText().toString());
                    butons[i-1][columna].setText("");
                    resultado = boton * 2;
                    resString = Integer.toString(resultado);
                    butons[i][columna].setText(resString);
                    score += resultado;

                }
            }
        }
    }

    /**
     * M??todo que desplazar?? las casillas.
     * @param columna
     */
    public void desplazarFilasAbajo(int columna){
        for (int x = 0; x < butons.length; x++) {
            for (int i = butons.length-1; i > 0; i--) {
                if ((butons[i][columna].getText()).equals("") && !(butons[i-1][columna].getText()).equals("")) {
                    butons[i][columna].setText(butons[i - 1][columna].getText());
                    butons[i - 1][columna].setText("");
                }
            }
        }
    }

    /**
     * @return Devolvera un true or false, si el tablero est?? lleno o vacio.
     */
    public boolean checkTablero() {
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                if (butons[i][j].getText().equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return Devolvera true or false, depediendo de si hay algun movimiento dispnible
     * hacia la derecha
     */
    public boolean checkRight (){
        for (int fila = 0; fila < butons.length; fila++) {
            for (int j = butons.length-1; j > 0; j--) {
                if ((butons[fila][j].getText()).equals("") && !(butons[fila][j - 1].getText()).equals("")) {
                    return true;
                } else if ((butons[fila][j].getText()).equals((butons[fila][j - 1].getText())) &&
                        (!(butons[fila][j].getText().equals("")))){
                    return true;
                }
            }
        } return false;
    }

    /**
     * @return Devolvera true or false, depediendo de si hay algun movimiento dispnible
     * hacia la izquierda
     */
    public boolean checkLeft (){
        for (int  fila = 0;  fila < butons.length;  fila++) {
            for (int j = 0; j < butons[0].length-1; j++) {
                if ((butons[fila][j].getText()).equals((butons[fila][j + 1].getText()))
                        && (!(butons[fila][j].getText().equals("")))) {
                    return true;
                } else if ((butons[fila][j].getText()).equals("") && !(butons[fila][j + 1].getText()).equals(""))
                        {
                    return true;
                }
            }
        } return false;
    }

    /**
     * @return Devolvera true or false, depediendo de si hay algun movimiento dispnible
     * hacia arriba
     */
    public boolean checkUp() {
        for (int columna = 0; columna < butons.length; columna++) {
            for (int i = 0; i < butons[0].length-1; i++) {
                if ((butons[i][columna].getText()).equals("") && !(butons[i+1][columna].getText()).equals("")) {
                    return true;
                } else if ((butons[i][columna].getText()).equals((butons[i + 1][columna].getText()))
                        && (!butons[i][columna].getText().equals(""))) {
                    return true;
                }
            }
        }return false;
    }

    /**
     * @return Devolvera true or false, depediendo de si hay algun movimiento dispnible
     * hacia la abajo
     */
    public boolean checkDown(){
        for (int columna = 0; columna < butons.length; columna++) {
            for (int i = butons.length-1; i > 0; i--) {
                if ((butons[i][columna].getText()).equals("") && !(butons[i-1][columna].getText()).equals("")) {
                    return true;
                } else if ((butons[i][columna].getText()).equals((butons[i - 1][columna].getText()))
                        && (!butons[i][columna].getText().equals(""))) {
                    return true;
                }
            }
        }return false;
    }

    /**
     * Metodo para que salga por pantalla la puntuaci??n.
     * @param score
     */
    public void score(int score) {
        tscore = (TextView) findViewById(R.id.score);
        tscore.setText(Integer.toString(score));
    }


    /**
     * Genera una actividad y termina la anterior.
     * @param view
     */
    public void restart(View view) {
        Intent i = new Intent(this, Game2048.class);
        startActivity(i);
        finish();
    }

    /**
     * Copiar tablero
     */
    public void boardCopy(){
        scorecopy = getScore();
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                copia[i][j] = String.valueOf((butons[i][j].getText()));
            }
        }
    }

    /**
     * Deshacer el movimiento anterior.
     * @param view
     */
    public void Undo(View view){

        for (int i = 0; i < copia.length; i++) {
            for (int j = 0; j < copia[0].length; j++) {
                butons[i][j].setText(copia[i][j]);
            }
        }
        score = scorecopy;
        score(score);
        changeColor();
    }

    /**
     * Iniciamos el chronometro.
     */
    private void startTimer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    /**
     * Metodo para verificar si hay ganador
     */
    public void winner() {
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                if (butons[i][j].getText().equals("2048") && aux==0){
                    aux++;
                    Toast.makeText(this, "ENHORABUENA CAMPE??N", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Devolvera un true or false, para averiguar si la partida ha terminado
     * @return
     */
    public boolean gameOver(){
        if (!checkTablero()){
            if ((!checkRight())&&(!checkLeft())&&(!checkUp())&&(!checkDown())){
                finish();
                Intent intent = new Intent(Game2048.this, SplashGameOver.class);
                intent.putExtra("score", tscore.getText().toString());
                startActivity(intent);
                mDB.insertScore(MenuJuegos.user,"2048", mChronometer.getText().toString(), String.valueOf(score));
                finish();
                return true;
            }
        } return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //Valor swipe horizontal
                float ValueX = (x2 - x1);
                //Valor swipe Vertical
                float ValueY = (y2 - y1);

                if (Math.abs(ValueX) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        swipeRight();
//                        Toast.makeText(this, "Swipe Right", Toast.LENGTH_SHORT).show();
                    } else {
                        swipeLeft();
//                        Toast.makeText(this, "Swipe Left", Toast.LENGTH_SHORT).show();
                    }
                } else if (Math.abs(ValueY) > MIN_DISTANCE) {
                    if (y2 > y1) {
                        swipeDown();
//                        Toast.makeText(this, "Swipe Down", Toast.LENGTH_SHORT).show();
                    } else {
                        swipeUp();
//                        Toast.makeText(this, "Swipe Up", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Game2048.this);
        builder.setMessage("Are you sure you want to exit?\n" +
                "\n" +
                "The data will not be saved.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Game2048.this,
                                Main2048.class));
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



