package com.betterino.magnus.wonderbetterino_mm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinLobby extends AppCompatActivity {

    private ListView list;
    private JoinLobbyListAdapter listAdapter;
    private ArrayList<LobbyDTO> gameList;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_lobby);




        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        this.user = SingletonApplications.user;
        userID = user.getUid();


        gameList = new ArrayList<LobbyDTO>();



        myRef.child("lobbys").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    getData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //Skal hente en liste af LobbyDTO fra firebase, som en form for objekter der skal swipes/scrolles igennem, og sorteres på game, bet osv.
        //Man skal ikke kunne se lobbys man ikke har nok coins til at være med i.

        list = (ListView) findViewById(R.id.join_list);
        listAdapter = new JoinLobbyListAdapter(this, gameList);
        list.setAdapter(listAdapter);





    }

    private void getData(DataSnapshot dataSnapshot) {
        gameList.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            LobbyDTO lob = ds.getValue(LobbyDTO.class);
            gameList.add(lob);
            listAdapter.notifyDataSetChanged();
        }
    }

    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }


}
