package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.betterino.magnus.wonderbetterino_mm.Games.Galgeleg.Hangman;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinedLobby extends AppCompatActivity {

    private LobbyDTO lobby;
    private TextView game;
    private TextView bet;
    private TextView info;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String hostID;

    //Lobby info
    private String gameString;
    private String gameBet;

    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_lobby);
        lobby = (LobbyDTO) getIntent().getSerializableExtra("lobbyNr");

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        this.user = SingletonApplications.user;
        userID = user.getUid();

        //Join the lobby
        joinGameLobby();




        game = (TextView) findViewById(R.id.joined_game_textview);
        bet = (TextView) findViewById(R.id.joined_bet_textview);
        info = (TextView) findViewById(R.id.joined_info_textview);

        bet.setText("Host: "+lobby.getHost());
        info.setText("Please wait for your game to start\nThe game will launch automatically when the host press start.");






        myRef.child("lobbys").child(lobby.getHost()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameString = String.valueOf(dataSnapshot.child("game").getValue());
                gameBet = String.valueOf(dataSnapshot.child("bet").getValue());



                String isMyGameStarted = String.valueOf(dataSnapshot.child("started").getValue());
                int isMyGameStartedInteger = 0;
                try {
                    isMyGameStartedInteger = Integer.parseInt(isMyGameStarted);
                } catch (NumberFormatException e) {
                    Log.d("", "problem formating string to int");
                }
                if (isMyGameStartedInteger == 1) {
                    //Launch game
                    startGame();
                }

                updateText(gameString, gameBet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        //game.setText("Vi skal spille "+lobby.getGame()+"!\nVent venligst på at spillet begynder");
        //bet.setText("I dette spil er bettet: "+lobby.getBet());



    }

    private void startGame() {
        //Currently only hangman
        if (gameString.equals("Hangman")){
            Intent i = new Intent(this, Hangman.class);
            i.putExtra("lobby", lobby);
            i.putExtra("userID", userID);
            startActivity(i);
            finish();
        }
        else
            info.setText(info.getText()+"\n NOTICE: The host tried to start the game, but it has not yet been implemented");
    }

    private void updateText(String gameString, String betString) {
        game.setText("Game: "+gameString);
        bet.setText("Bet: "+betString);
    }


    public void joinGameLobby() {
        ArrayList<LobbyDTO.players> players = lobby.getPlayers();
        LobbyDTO.players p = new LobbyDTO.players(0, userID, 0);
        players.add(p);
        LobbyDTO addtoLobby = new LobbyDTO(lobby.getBet(), lobby.getGame(), lobby.getStarted(), players, lobby.getHost());
        myRef.child("lobbys").child(lobby.getHost()).setValue(addtoLobby);
    }


}
