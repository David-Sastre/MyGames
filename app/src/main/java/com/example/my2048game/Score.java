package com.example.my2048game;

public class Score {
    // Member variables representing the title and information about the sport.
    private String username;
    private String game;
    private String time;
    private String scoreTotal;
    private final int imageResource;


    Score(String username, String game, String time, String scoreTotal, int imageResource) {
        this.username = username;
        this.game = game;
        this.time = time;
        this.scoreTotal = scoreTotal;
        this.imageResource = imageResource;
    }

    String getUsername() {
        return username;
    }

    String getGame(){
        return game;
    }

    String getTime(){
        return time;
    }


    String getScoreTotal() {
        return scoreTotal;
    }

    public int getImageResource() {
        return imageResource;
    }
}
