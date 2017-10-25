package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateLobby extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    TextView betInfoText;
    SeekBar betValue;
    TextView seekbarInfoText;
    TextView gameInfoText;
    Spinner chosenGame;
    Button createLobby;
    int bet;
    String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);


        betInfoText = (TextView) findViewById(R.id.createLobby_textView);
        betInfoText.setText("Set the coin bet:");


        seekbarInfoText = (TextView) findViewById(R.id.createLobby_textview3);
        seekbarInfoText.setText("3");


        betValue = (SeekBar) findViewById(R.id.createLobby_seekBar);
        betValue.setProgress(1);
        betValue.incrementProgressBy(1);
        betValue.setMax(9);
        betValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarInfoText.setText(String.valueOf(progress+1));
                bet = progress+1;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        gameInfoText = (TextView) findViewById(R.id.createLobby_textView2);
        gameInfoText.setText("Chose game:");


        chosenGame = (Spinner) findViewById(R.id.createLobby_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SingletonApplications.gameArray);
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

            Intent i = new Intent(this, Lobby.class);
            startActivity(i);

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


}
