package com.betterino.magnus.wonderbetterino_mm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by magnusenevoldsen on 30/10/2017.
 */

public class JoinLobbyListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private ArrayList<LobbyDTO> lobbyList;
    private Activity akt;
    private TextView game;
    private TextView bet;
    private String id;



    protected JoinLobbyListAdapter(Activity activity, ArrayList<LobbyDTO> lobbyList) {
        Context context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lobbyList = lobbyList;

        this.akt = activity;
    }

    @Override
    public int getCount() {
        return lobbyList.size(); //Return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView;
        rowView = inflater.inflate(R.layout.join_listview, null);
        final LobbyDTO lobbyelement = lobbyList.get(position);

        game = (TextView) rowView.findViewById(R.id.lobbyGameList);
        bet = (TextView) rowView.findViewById(R.id.LobbyBetList);




        String lobbyGame = lobbyelement.getGame();

        game.setText("Game: "+lobbyGame);


        int lobbyBet = lobbyelement.getBet();

        bet.setText("Bet: "+lobbyBet);


        //String lobbyID = lobbyelement.get;
        // Her skal den joine den lobby der passer til det ID vi har hentet lige henover denne linje

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(akt, JoinedLobby.class);
                    i.putExtra("lobbyNr", lobbyList.get(position));
                    akt.startActivity(i);
            }
        });

        return rowView;
    }
}
