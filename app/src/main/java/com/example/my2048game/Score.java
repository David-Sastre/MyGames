package com.example.my2048game;

public class Score {
    // Member variables representing the title and information about the sport.
    private String username;
    private String game;
    private String time;
    private String scoreTotal;



    Score(String username, String game, String time, String scoreTotal) {
        this.username = username;
        this.game = game;
        this.time = time;
        this.scoreTotal = scoreTotal;

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

}
