package com.betterino.magnus.wonderbetterino_mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JoinLobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);



        //Skal hente en liste af LobbyDTO fra firebase, som en form for objekter der skal swipes/scrolles igennem, og sorteres på game, bet osv.
        //Man skal ikke kunne se lobbys man ikke har nok coins til at være med i.




    }
}
