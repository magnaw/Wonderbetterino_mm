package com.betterino.magnus.wonderbetterino_mm;

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

public class JoinedLobby extends AppCompatActivity {

    private LobbyDTO lobby;
    private TextView game;
    private TextView bet;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String userID;
    private String hostID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_lobby);
        lobby = (LobbyDTO) getIntent().getSerializableExtra("lobbyNr");

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        this.user = SingletonApplications.user;
        userID = user.getUid();




        game = (TextView) findViewById(R.id.joined_game_textview);
        bet = (TextView) findViewById(R.id.joined_bet_textview);

        bet.setText("Host: "+lobby.getHost());






        myRef.child("lobbys").child(lobby.getHost()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameString = String.valueOf(dataSnapshot.child("game").getValue());
                updateText(gameString);
                //getData(dataSnapshot);

                String isMyGameStarted = String.valueOf(dataSnapshot.child("started").getValue());
                int isMyGameStartedInteger = 0;
                try {
                    isMyGameStartedInteger = Integer.parseInt(isMyGameStarted);
                } catch (NumberFormatException e) {
                    Log.d("", "problem formating string to int");
                }
                if (isMyGameStartedInteger == 1) {
                    //LAUNCH THE GAME
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        //game.setText("Vi skal spille "+lobby.getGame()+"!\nVent venligst på at spillet begynder");
        //bet.setText("I dette spil er bettet: "+lobby.getBet());



    }

    private void updateText(String gameString) {
        game.setText("Is this your game?  "+gameString);
    }

    private void getData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            LobbyDTO lob = ds.getValue(LobbyDTO.class);
        }
    }


}
