package com.betterino.magnus.wonderbetterino_mm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class JoinLobby extends AppCompatActivity {

    private ListView list;
    private JoinLobbyListAdapter listAdapter;
    private ArrayList<LobbyDTO> gameList;
    private TextView text;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String userID;

    //Listener
    private ValueEventListener valueEvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.joinlobbytitle);
        setContentView(R.layout.activity_join_lobby);




        //Firebase DB
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        this.user = SingletonApplications.user;
        userID = user.getUid();


        gameList = new ArrayList<LobbyDTO>();



        valueEvList = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.child("lobbys").child(userID).removeValue();


        myRef.child("lobbys").addValueEventListener(valueEvList);

        list = (ListView) findViewById(R.id.join_list);
        listAdapter = new JoinLobbyListAdapter(this, gameList);
        list.setAdapter(listAdapter);


        text = (TextView) findViewById(R.id.joinLobbyText);
        text.setText(R.string.joinLobbyText);





    }

    public void removeListener(){
        myRef.removeEventListener(valueEvList);
    }

    private void getData(DataSnapshot dataSnapshot) {
        gameList.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            LobbyDTO lob = ds.getValue(LobbyDTO.class);
            if (lob.getStarted() == 0 && lob.getBet() <= SingletonApplications.wallet)
                gameList.add(lob);
            listAdapter.notifyDataSetChanged();

        }
        if(gameList.isEmpty()){
            text.setText(R.string.joinLobbyTextEmpty);
        }
        else{
            text.setText(R.string.joinLobbyText);
        }
    }

    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }




}
