package com.example.my2048game;


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
import java.util.Random;

public class Game2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private DBHelper mDB;
    private int bestScore = 0;
    TextView tscore;
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
//        bestScore();
        gameOver();
    }


    private void swipeRight (){
        boardCopy();
        if (checkRight()) {
            for (i = 0; i < butons.length; i++) {
                desplazarFilasDcha(i);
                sumarDerecha(i);
                winnerChickenDinner();
                desplazarFilasDcha(i);
            }
            insertRandom();
        }
    }

    private void swipeLeft () {
        boardCopy();
        if (checkLeft()){
            for (i = 0; i < butons.length; i++) {
                desplazarFilasIzq(i);
                sumarIzquierda(i);
                winnerChickenDinner();
                desplazarFilasIzq(i);
            }
        insertRandom();
        }
    }

    private void swipeUp(){
        boardCopy();
        if (checkUp()) {
            for (i = 0; i < butons.length; i++) {
                desplazarFilasArriba(i);
                sumarArriba(i);
                winnerChickenDinner();
                desplazarFilasArriba(i);
            }
            insertRandom();
        }
    }

    public void swipeDown(){
        boardCopy();
        if (checkDown()) {
            for (i = 0; i < butons.length; i++) {
                desplazarFilasAbajo(i);
                sumarAbajo(i);
                desplazarFilasAbajo(i);
            }
            winnerChickenDinner();
            insertRandom();
        }
    }

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

    public void sumarDerecha(int fila) {
        int resultado, boton;
        String resString;
        for (int j = butons[0].length-1; j > 0; j--) {
            if ((butons[fila][j].getText()).equals((butons[fila][j - 1].getText()))) {
                if ((butons[fila][j].getText()).equals("") || (butons[fila][j - 1].getText().equals(""))) {
                    System.out.println("No se puede sumar");
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

    public void sumarIzquierda(int fila){
        int resultado, boton;
        String resString;
        for (int j = 0; j < butons[0].length-1; j++) {
            if ((butons[fila][j].getText()).equals((butons[fila][j + 1].getText()))) {
                if ((butons[fila][j].getText()).equals("") || (butons[fila][j + 1].getText().equals(""))) {
                    System.out.println("No se puede sumar");
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

    public void sumarArriba(int columna){
        int resultado, boton;
        String resString;
        for (int i = 0; i < 3 ; i++) {
            if ((butons[i][columna].getText()).equals((butons[i+1][columna].getText()))) {
                if ((butons[i][columna].getText()).equals("") || (butons[i+1][columna].getText().equals(""))) {
                    System.out.println("No se puede sumar");
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

    public void sumarAbajo (int columna){
        int resultado, boton;
        String resString;
        for (int i = butons.length-1; i > 0 ; i--) {
            if ((butons[i][columna].getText()).equals((butons[i-1][columna].getText()))) {
                if ((butons[i][columna].getText()).equals("") || (butons[i-1][columna].getText().equals(""))) {
                    System.out.println("No se puede sumar");
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

    public void score(int score) {
        tscore = (TextView) findViewById(R.id.score);
        tscore.setText(Integer.toString(score));
    }

    public void restart(View view) {
        Intent i = new Intent(this, Game2048.class);
        startActivity(i);
        finish();
    }

    public void boardCopy(){
        scorecopy = getScore();
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                copia[i][j] = String.valueOf((butons[i][j].getText()));
            }
        }
    }

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

    private void startTimer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    public void winnerChickenDinner() {
        for (int i = 0; i < butons.length; i++) {
            for (int j = 0; j < butons[0].length; j++) {
                if (butons[i][j].getText().equals("2048") && aux==0){
                    aux++;
                    Toast.makeText(this, "ENHORABUENA CAMPEÓN", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean gameOver(){
        if (!checkTablero()){
            if ((!checkRight())&&(!checkLeft())&&(!checkUp())&&(!checkDown())){
                finish();
                Intent intent = new Intent(Game2048.this, SplashGameOver.class);
                startActivity(intent);
                mDB.insertScore(MenuJuegos.user,"2048", mChronometer.getText().toString(), String.valueOf(score));
                finish();
                return true;
            }
        } return false;
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
            for(int j=0; j<butons.length; j++){
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
        startTimer();
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

}



