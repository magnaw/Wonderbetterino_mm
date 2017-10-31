package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {


    private TextView info;


    private LobbyDTO lobby;
    private String userID;
    private String hostID;


    private int finished = 1;
    private int score = 0;
    private int nrOfPlayers = 0;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private LobbyDTO loadedLobby;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);



        info = (TextView) findViewById(R.id.Gameover_textview);






        lobby = (LobbyDTO) getIntent().getSerializableExtra("lobby");
        userID = (String) getIntent().getStringExtra("userID");
        String a = (String) getIntent().getStringExtra("score");
        try {
            score = Integer.parseInt(a);
        } catch (NumberFormatException e) {
            Log.d("", "error loading score");
        }


        hostID = lobby.getHost();
        nrOfPlayers = lobby.getPlayers().size();


        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                SingletonApplications.user = firebaseAuth.getCurrentUser();
                if (SingletonApplications.user != null) {
                    // User is signed in
                    Log.d("", "onAuthStateChanged:signed_in:" + SingletonApplications.user.getUid());

                } else {
                    // User is signed out
                    Log.d("", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        myRef.child("lobbys").child(hostID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadedLobby = dataSnapshot.getValue(LobbyDTO.class);
                postResults();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        myRef.child("lobbys").child(hostID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadedLobby = dataSnapshot.getValue(LobbyDTO.class);


                int i = 0;
                int bestScore = 0;
                LobbyDTO.players winner = new LobbyDTO.players();
                for (LobbyDTO.players player : loadedLobby.players) {
                    if (player.getFinished() == 1) {
                        if (player.getScore() >= bestScore) {
                            winner = player;
                        }
                        i++;
                    }
                }
                if (i == nrOfPlayers) {

                    //Hvis alle er færdige:
                    if (winner.getId() == userID) {
                        //Overfør coins.
                        UserDTO user = new UserDTO(SingletonApplications.name, SingletonApplications.wallet+(lobby.bet*nrOfPlayers));
                        myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                        gameFinished();

                    }

                }






                if(loadedLobby.getStarted() == 2) {
                    //Go to mainmenu and clear all previous intents
                    //openMainMenu();
                    finish();
                }



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });











        info.setText("You finished the game!\nWaiting for the rest of the players\nThis screen will automaticly disappear when all players have finished the game");










    }
    public void openMainMenu() {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }




    public void postResults() {
        ArrayList<LobbyDTO.players> players = loadedLobby.getPlayers();
        for (LobbyDTO.players p : players) {
            if (p.getId() == userID) {
                players.remove(p);
            }
        }
        LobbyDTO.players p = new LobbyDTO.players(finished, userID, score);
        players.add(p);
        LobbyDTO updateLobby = new LobbyDTO(loadedLobby.getBet(), loadedLobby.getGame(), loadedLobby.getStarted(), loadedLobby.players, lobby.getHost());
        myRef.child("lobbys").child(hostID).setValue(updateLobby);
        System.out.println("Lobby når vi poster: "+updateLobby);
    }

    public void gameFinished() {
        LobbyDTO updateLobby = new LobbyDTO(loadedLobby.getBet(), loadedLobby.getGame(), 2, loadedLobby.players, lobby.getHost());
        myRef.child("lobbys").child(hostID).setValue(updateLobby);
        System.out.println("Lobby når spillet er slut: "+updateLobby);
    }






}
