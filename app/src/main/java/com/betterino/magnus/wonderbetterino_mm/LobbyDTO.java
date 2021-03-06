package com.betterino.magnus.wonderbetterino_mm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Magnus on 26-10-2017.
 */

public class LobbyDTO implements Serializable{


    public int bet;
    public String game;
    public int started;
    public ArrayList<players> players;
    public String host;



    //Started so far:
    //0 = lobby igang, kan joines
    //1 = spil startet
    //2 = mellemværdi, skal sættes til dette når resultater postes
    //3 = spillet er slut
    //4 = Midlertidig blokering, så der kun kan være 2 spillere i et spil


    public int getBet() { return bet; }
    public void setBet(int bet) { this.bet = bet; }
    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
    public int getStarted() { return started; }
    public void setStarted(int started) { this.started = started; }
    public ArrayList<LobbyDTO.players> getPlayers() { return players; }
    public void setPlayers(ArrayList<LobbyDTO.players> players) { this.players = players; }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }


    public LobbyDTO(int bet, String game, int started, ArrayList<players> players, String host) {
        this.bet = bet;
        this.game = game;
        this.started = started;
        this.players = players;
        this.host = host;
    }

    public LobbyDTO() {

    }

    @Override
    public String toString() {
        return "LobbyDTO{" +
                "bet=" + bet +
                ", game='" + game + '\'' +
                ", started=" + started +
                ", players=" + players +
                ", host='" + host + '\'' +
                '}';
    }


    public static class players implements Serializable{



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

        public players() {

        }


    }

}


