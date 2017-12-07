package com.betterino.magnus.wonderbetterino_mm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener {



    private TextView name;
    private TextView wallet;
    private int coins;
    private Button addCoins;


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String userID;

    private ValueEventListener valueEvList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.profiletitle);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.profile_name);
        name.setText("");

        wallet = (TextView) findViewById(R.id.profile_wallet);
        wallet.setText("");

        addCoins = (Button) findViewById(R.id.profile_addcoin);
        addCoins.setOnClickListener(this);
        addCoins.setText("Add 1 coin");


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
                    // User is signed in
                    Log.d("", "onAuthStateChanged:signed_in:" + SingletonApplications.user.getUid());

                } else {
                    // User is signed out
                    Log.d("", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        valueEvList = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = String.valueOf(dataSnapshot.child("name").getValue());
                SingletonApplications.name = name;
                String wallet = String.valueOf(dataSnapshot.child("wallet").getValue());
                try {
                    SingletonApplications.wallet = Integer.parseInt(wallet);
                } catch (NumberFormatException e) {
                    makeToast("Error loading wallet. Try again later.");
                }
                updateScreen();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child("users").child(userID).addValueEventListener(valueEvList);

    }




    @Override
    public void onClick(View view) {
        if (view == addCoins) {
            //Add 1 coin to wallet.
            UserDTO user = new UserDTO(SingletonApplications.name, SingletonApplications.wallet+1);
            myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
        }

    }

    @Override
    public void onBackPressed() {
        myRef.removeEventListener(valueEvList);
        finish();
    }

    public void updateScreen() {
        name.setText("Name: "+SingletonApplications.name);
        wallet.setText("Coins: "+SingletonApplications.wallet);
    }


    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }

}
