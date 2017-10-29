package com.betterino.magnus.wonderbetterino_mm;

import java.util.ArrayList;

/**
 * Created by Magnus on 26-10-2017.
 */

public class LobbyDTO {


    public int bet;
    public String game;
    public int started;
    public ArrayList<players> players;


    public int getBet() { return bet; }
    public void setBet(int bet) { this.bet = bet; }
    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
    public int getStarted() { return started; }
    public void setStarted(int started) { this.started = started; }
    public ArrayList<LobbyDTO.players> getPlayers() { return players; }
    public void setPlayers(ArrayList<LobbyDTO.players> players) { this.players = players; }


    public LobbyDTO(int bet, String game, int started, ArrayList<players> players) {
        this.bet = bet;
        this.game = game;
        this.started = started;
        this.players = players;
    }





    public static class players {



        public int finished;
        public String id;
        public int score;

        public int getFinished() { return finished; }
        public void setFinished(int finished) { this.finished = finished; }


        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }


        public players(int finished, String id, int score) {
            this.finished = finished;
            this.id = id;
            this.score = score;
        }


    }

}


