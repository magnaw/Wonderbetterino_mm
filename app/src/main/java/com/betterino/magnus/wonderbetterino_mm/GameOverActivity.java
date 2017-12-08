package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private Boolean weAreDoneHere;


    private int finished = 1;
    private int score = 0;
    private int nrOfPlayers = 0;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private LobbyDTO loadedLobby;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Listeners
    private ValueEventListener valueEvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gameovertitle);
        setContentView(R.layout.activity_game_over);

        weAreDoneHere = false;



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




        valueEvList = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    finish();
                    makeToast("Your lobby was removed.");
                    openMainMenu();


                }


                else if (!weAreDoneHere && dataSnapshot.getValue() != null) {

                    try {

                    loadedLobby = dataSnapshot.getValue(LobbyDTO.class);

                    int i = 0;
                    int bestScore = 0;
                    LobbyDTO.players winner = new LobbyDTO.players();
                    for (LobbyDTO.players player : loadedLobby.players) {
                        if (player.getFinished() == 1) {
                            if (player.getScore() >= bestScore) {
                                winner = player;
                                bestScore = winner.score;
                            }
                            i++;
                        }
                    }
                    if (i == nrOfPlayers) {

                        //Hvis alle er færdige:
                        if (winner.getId().equals(userID)) {
                            //Overfør coins.
                            int bet = lobby.getBet();
                            int winnings = (bet*nrOfPlayers)/2;

                            UserDTO user = new UserDTO(SingletonApplications.name, SingletonApplications.wallet+winnings);
                            myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            gameFinished();
                            makeToast("You won! Congratulations!");

                        }
                        else {
                            makeToast("You lost, try again!");
                        }

                    }

                    if(loadedLobby.getStarted() == 3) {
                        finishGameAndGoHome();
                    }

                    }
                    catch (NullPointerException e) {
                        finish();
                        makeToast("Your lobby was removed.");
                        openMainMenu();
                    }


                }
                else if (dataSnapshot.getValue() == null) {
                    finish();
                    makeToast("Your lobby was removed.");

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.child("lobbys").child(hostID).addValueEventListener(valueEvList);


        info.setText("You finished the game!\n\nPlease wait for the rest of the players to also finish.\n\nThis screen will automaticly disappear when all players have finished the game.");



    }



    public void openMainMenu() {
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }

    public void finishGameAndGoHome() {
        myRef.removeEventListener(this.valueEvList);
        weAreDoneHere = true;
        finish();
    }




    public void postResults() {

        LobbyDTO.players p = new LobbyDTO.players(finished, userID, score);

        ArrayList<LobbyDTO.players> players = lobby.getPlayers();
        System.out.println("USER ID = "+userID);
        for (int i = 0; i<players.size(); i++) {
            System.out.println("FORLOOP PLAYER AT = "+players.get(i).getId());
            if (players.get(i).getId().equals(userID) && players.get(i).getFinished() == 0) {
                System.out.println("FORLOOP CHOSEN = "+players.get(i).getId());
                myRef.child("lobbys").child(lobby.getHost()).child("players").child(""+i).setValue(p);
            }
        }

    }

    public void gameFinished() {
        LobbyDTO updateLobby = new LobbyDTO(loadedLobby.getBet(), loadedLobby.getGame(), 3, loadedLobby.players, lobby.getHost());
        myRef.child("lobbys").child(hostID).setValue(updateLobby);
        System.out.println("Lobby når spillet er slut: "+updateLobby);
    }

    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }








}
