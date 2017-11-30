package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorSeekBarType;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.TickType;

import java.util.ArrayList;


public class CreateLobby extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private TextView betInfoText;
    private SeekBar betValue;
    private IndicatorSeekBar indicatorSeekBar;
    //private TextView seekbarInfoText;
    private TextView gameInfoText;
    private Spinner chosenGame;
    private Button createLobby;
    private int bet = 5;
    private String game;
    private LobbyDTO lobby;

    //Firebase
    private FirebaseUser user;
    private String userID;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.createlobbytitle);
        setContentView(R.layout.activity_create_lobby);


        this.user = SingletonApplications.user;
        userID = user.getUid();

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                SingletonApplications.user = firebaseAuth.getCurrentUser();
                if (SingletonApplications.user != null) {
                    Log.d("", "onAuthStateChanged:signed_in:" + SingletonApplications.user.getUid());
                } else {
                    Log.d("", "onAuthStateChanged:signed_out");
                }
            }
        };




        //Slet lobby, hvis du allerede har en aktiv lobby

        myRef.child("lobbys").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LobbyDTO lobby = dataSnapshot.getValue(LobbyDTO.class);
                myRef.child("lobbys").child(userID).removeValue();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        betInfoText = (TextView) findViewById(R.id.createLobby_textView);
        betInfoText.setText("Coin bet: "+bet);


        indicatorSeekBar = (IndicatorSeekBar) findViewById(R.id.createLobby_seekBar);
        //indicatorSeekBar = new IndicatorSeekBar.Builder(this)
        //        .setMax(10)
        //        .setMin(1)
        //        .setProgress(5)
        //        .setSeekBarType(IndicatorSeekBarType.DISCRETE_TICKS)
        //        .setTickType(TickType.OVAL)
        //        .setTickColor(Color.parseColor("#0000FF"))
        //        .setTickSize(8)//dp size
        //        .setTickNum(1)
        //        .setBackgroundTrackSize(2)//dp size
        //        .setBackgroundTrackColor(Color.parseColor("#666666"))
        //        .setProgressTrackSize(3)//dp size
        //        .setProgressTrackColor(Color.parseColor("#0000FF"))
        //        .showIndicator(true)
        //        .setIndicatorType(IndicatorType.SQUARE_CORNERS)
        //        .setIndicatorColor(Color.parseColor("#0000FF"))
        //        .build();

        indicatorSeekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                betInfoText.setText("Coin bet: "+String.valueOf(progress));
                bet = progress;
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {
                //only callback on discrete series SeekBar type.
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        //seekbarInfoText = (TextView) findViewById(R.id.createLobby_textview3);
        //seekbarInfoText.setText(""+bet);


        //betValue = (SeekBar) findViewById(R.id.createLobby_seekBar);
        //betValue.setProgress(bet-2);
        //betValue.incrementProgressBy(1);
        //betValue.setMax(9);
        //betValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

        //@Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                //seekbarInfoText.setText(String.valueOf(progress+1));
        //              betInfoText.setText("Coin bet: "+String.valueOf(progress+1));
        //      bet = progress+1;
        //  }
        //  @Override
        //  public void onStartTrackingTouch(SeekBar seekBar) {}
        //  @Override
        //  public void onStopTrackingTouch(SeekBar seekBar) {}
        //});



        gameInfoText = (TextView) findViewById(R.id.createLobby_textView2);
        gameInfoText.setText("Chose game:");


        chosenGame = (Spinner) findViewById(R.id.createLobby_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text, SingletonApplications.gameArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chosenGame.setAdapter(adapter);
        chosenGame.setOnItemSelectedListener(this);



        createLobby = (Button) findViewById(R.id.createLobby_button2);
        createLobby.setOnClickListener((View.OnClickListener) this);
        createLobby.setText("Create Lobby");



    }

    @Override
    public void onClick(View view) {
        if (view == createLobby) {
            makeToast("You created a lobby with the bet: "+bet+", and the game: "+game+".");
            createLobbyOnFinished();

            Intent i = new Intent(this, Lobby.class);
            i.putExtra("lobby", lobby);
            i.putExtra("userID", userID);
            startActivity(i);
            finish();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        game = SingletonApplications.gameArray[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }

    public void createLobbyOnFinished() {
        ArrayList<LobbyDTO.players> players = new ArrayList<>();
        LobbyDTO.players p = new LobbyDTO.players(0, userID, 0);
        players.add(p);
        lobby = new LobbyDTO(bet, game, 0, players, userID);
        //Vi har nu oprettet en lobby, den skal pushes til firebase med generated ID
        //String key = myRef.push().getKey();
        myRef.child("lobbys").child(userID).setValue(lobby);

    }































}
