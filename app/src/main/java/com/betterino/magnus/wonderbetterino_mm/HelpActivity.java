package com.betterino.magnus.wonderbetterino_mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private ExpandableListView list;
    private helpExpandListAdapter listAdapter;

    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        list = (ExpandableListView) findViewById(R.id.helpListView);
        initData();
        listAdapter = new helpExpandListAdapter(this,listDataHeader,listHash);
        list.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Profile");
        listDataHeader.add("Creating a game");
        listDataHeader.add("Joining a game");
        listDataHeader.add("Games");

        List<String> profile = new ArrayList<>();
        profile.add("In the profile tab, you can see the name registred with your account, and your wallet balance.\n" +
                "For testing purposes, there's currently a button that will add 1 coin to the accounts wallet.");

        List<String> createGame = new ArrayList<>();
        createGame.add("When creating a game, you get the options to choose a game, and set a bet for the players.");

        List<String> joinGame = new ArrayList<>();
        joinGame.add("When joining a game, you will only be able to see the lobbies that has a bet lower or equal to the amount of coins you have in your wallet.\n" +
                "Once you have chosen a lobby, you will have to wait for the host to start the game.");

        List<String> Games = new ArrayList<>();
        Games.add("Hangman\n" +
                "Playing the hangman game, you will be given a random word which you must guess, one letter at a time. The person with the least mistakes will win, however, should 2 people finish with the same score, the fastest person will be chosen as the winner.");
        //Games.add("UWP Google Map");

        listHash.put(listDataHeader.get(0),profile);
        listHash.put(listDataHeader.get(1),createGame);
        listHash.put(listDataHeader.get(2),joinGame);
        listHash.put(listDataHeader.get(3),Games);
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
