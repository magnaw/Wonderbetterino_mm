package com.betterino.magnus.wonderbetterino_mm;

/**
 * Created by magnusenevoldsen on 30/10/2017.
 */

public class LobbyElement {

    private String lobbyID;
    private String lobbyGame;
    private int lobbyBet;

    public LobbyElement(String lobbyID, String lobbyGame, int lobbyBet) {
        this.lobbyID = lobbyID;
        this.lobbyGame = lobbyGame;
        this.lobbyBet = lobbyBet;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getLobbyGame() {
        return lobbyGame;
    }

    public void setLobbyGame(String lobbyGame) {
        this.lobbyGame = lobbyGame;
    }

    public int getLobbyBet() {
        return lobbyBet;
    }

    public void setLobbyBet(int lobbyBet) {
        this.lobbyBet = lobbyBet;
    }






}
