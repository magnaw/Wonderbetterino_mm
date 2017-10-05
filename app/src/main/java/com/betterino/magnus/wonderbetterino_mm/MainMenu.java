package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private Button joinButton;
    private Button createButton;
    private Button avatarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        joinButton = (Button) findViewById(R.id.buttonJoin);
        joinButton.setOnClickListener((View.OnClickListener) this);

        createButton = (Button) findViewById(R.id.buttonCreate);
        createButton.setOnClickListener((View.OnClickListener) this);

        avatarButton = (Button) findViewById(R.id.buttonProfile);
        avatarButton.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v) {
        if(v == joinButton){
            Intent i = new Intent(this, JoinLobby.class);
            startActivity(i);
        }
        else if (v == createButton) {
            Intent i = new Intent(this, CreateLobby.class);
            startActivity(i);
        }
        else if (v == avatarButton) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        }
    }
}
