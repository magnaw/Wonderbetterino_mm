package com.betterino.magnus.wonderbetterino_mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Lobby extends AppCompatActivity implements View.OnClickListener{


    private TextView game;
    private TextView bet;
    private TextView players;
    private Button startGame;
    private boolean host = true;
    private int nrOfPlayers = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        game = (TextView) findViewById(R.id.lobby_game);
        game.setText("");

        bet = (TextView) findViewById(R.id.lobby_bet);
        bet.setText("");

        players = (TextView) findViewById(R.id.lobby_players);
        players.setText("");


        startGame = (Button) findViewById(R.id.lobby_startgame);
        startGame.setOnClickListener(this);
        if(!host)
            startGame.setVisibility(View.INVISIBLE);
        startGame.setText("Start game");




    }


    @Override
    public void onClick(View view) {
        if (view == startGame) {
            if(nrOfPlayers > 2) {
                //Dont start the game...
            }
            else {
                //Start the game...
            }
        }

    }





}
