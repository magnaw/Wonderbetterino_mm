package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.betterino.magnus.wonderbetterino_mm.Games.Galgeleg.Hangman;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Lobby extends AppCompatActivity implements View.OnClickListener{


    private TextView game;
    private TextView bet;
    private TextView players;
    private Button startGame;
    private int nrOfPlayers = 0;
    private LobbyDTO lobby;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String userID;


    //Lobby info
    private String gameString;
    private String gameBet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        this.user = SingletonApplications.user;
        userID = user.getUid();


        lobby = (LobbyDTO) getIntent().getSerializableExtra("lobby");


        game = (TextView) findViewById(R.id.lobby_game);
        bet = (TextView) findViewById(R.id.lobby_bet);
        players = (TextView) findViewById(R.id.lobby_players);


        players.setText("Players: ");
        bet.setText("Bet: ");
        game.setText("Game: ");


        startGame = (Button) findViewById(R.id.lobby_startgame);
        startGame.setOnClickListener(this);
        startGame.setText("Start game");


        myRef.child("lobbys").child(lobby.getHost()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lobby = dataSnapshot.getValue(LobbyDTO.class);
                gameString = String.valueOf(dataSnapshot.child("game").getValue());
                gameBet = String.valueOf(dataSnapshot.child("bet").getValue());
                nrOfPlayers = lobby.players.size();


                updateText(gameString, gameBet, nrOfPlayers);
                if(nrOfPlayers >= 2)
                    startGame.setEnabled(true);
                else
                    startGame.setEnabled(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void startGame() {
        lobby.setStarted(1);
        myRef.child("lobbys").child(lobby.getHost()).setValue(lobby);


        if (gameString.equals("Hangman")){
            Intent i = new Intent(this, Hangman.class);
            i.putExtra("lobby", lobby);
            i.putExtra("userID", userID);
            startActivity(i);
            finish();
        }

    }



    private void updateText(String gameString, String betString, int nrOfPlayers) {
        game.setText("Game: "+gameString);
        bet.setText("Bet: "+betString);
        players.setText("Players: "+nrOfPlayers);
    }

    @Override
    public void onClick(View view) {
        if (view == startGame) {
            if(nrOfPlayers >= 2) {
                startGame();
            }
            else {
                makeToast("Error: Not enough players have joined your game");
            }
        }

    }

    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }




}
