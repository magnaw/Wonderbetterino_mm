package com.betterino.magnus.wonderbetterino_mm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private Context context;
    private ImageView img;



    protected JoinLobbyListAdapter(Activity activity, ArrayList<LobbyDTO> lobbyList) {
        context = activity;
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
        img = (ImageView) rowView.findViewById(R.id.LobbyImageList);




        String lobbyGame = lobbyelement.getGame();

        game.setText("Game: "+lobbyGame);


        int lobbyBet = lobbyelement.getBet();

        bet.setText("Bet: "+lobbyBet);

        switch(lobbyGame) {
            case "Hangman":
                img.setImageResource(R.drawable.galge_forkert6); break;
            default:
                img.setImageResource(R.mipmap.ic_launcher); break;

        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof JoinLobby) {
                    ((JoinLobby)context).removeListener();
                }

                Intent i = new Intent(akt, JoinedLobby.class);
                i.putExtra("lobbyNr", lobbyList.get(position));
                akt.startActivity(i);
                akt.finish();








            }
        });

        return rowView;
    }
}
