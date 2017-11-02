package com.betterino.magnus.wonderbetterino_mm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.betterino.magnus.wonderbetterino_mm.Games.Galgeleg.Hangman;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private Button joinButton;
    private Button createButton;
    private Button avatarButton;
    final Context context = this;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String userID;

    private Button logout;





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


        logout = (Button) findViewById(R.id.buttonLogout);
        logout.setOnClickListener((View.OnClickListener) this);


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





        myRef.child("users").child(userID).addValueEventListener(new ValueEventListener() {
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
//                makeToast("You have "+SingletonApplications.wallet+" coins in your wallet");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

















    }

    public void onClick(View v) {
        if(v == joinButton){
            Intent i = new Intent(this, JoinLobby.class);
            startActivity(i);
            //Intent i = new Intent(this, Hangman.class);
            //startActivity(i);

        }
        else if (v == createButton) {
            Intent i = new Intent(this, CreateLobby.class);
            startActivity(i);

            //Add 1 coin to wallet.
//            UserDTO user = new UserDTO(SingletonApplications.name, SingletonApplications.wallet+1);
//            myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);




        }
        else if (v == avatarButton) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);


        }
        else if (v == logout) {
            startActivity(new Intent(MainMenu.this, LoginActivity.class));
            finish();
            mAuth.signOut();
            makeToast("Signed out");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        SingletonApplications.user = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.getCurrentUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void registerPopup(String email) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Register user");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText emailInput = new EditText(context);
        emailInput.setHint("Insert email");
        emailInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(emailInput);

        final EditText passwordInput = new EditText(context);
        passwordInput.setHint("Insert password");
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(passwordInput);

        final EditText passwordInput2 = new EditText(context);
        passwordInput2.setHint("Insert password again");
        passwordInput2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(passwordInput2);

        if (!email.equals(""))
            emailInput.setText(email);

        alertDialogBuilder.setView(layout);


        alertDialogBuilder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!emailInput.getText().toString().contains("@")) {
                    registerPopup(emailInput.getText().toString());
                }
                else if (emailInput.getText().toString().matches("")) {
                    registerPopup(emailInput.getText().toString());
                }
                else if (passwordInput.getText().toString().matches("") || passwordInput2.getText().toString().matches("")) {
                    registerPopup(emailInput.getText().toString());
                }
                else if (passwordInput.getText().toString().length() < 6 || passwordInput2.getText().toString().length() < 6) {
                    registerPopup(emailInput.getText().toString());
                }
                else if (!passwordInput.getText().toString().equals(passwordInput2.getText().toString())){
                    registerPopup(emailInput.getText().toString());
                }
                else {
//                    signUp(emailInput.getText().toString(), passwordInput.getText().toString());
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Umiddelbart lukkes popup bare når der trykkes her, så jeg efterlader det as is
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



//    public void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.getChildren()) {
////            UserDTO uInfo = new UserDTO();
//            SingletonApplications.userInfo.setName(ds.child(userID).getValue(UserDTO.class).getName());
//            SingletonApplications.userInfo.setWallet(ds.child(userID).getValue(UserDTO.class).getWallet());
//
////            makeToast(ds.child(userID).getValue(UserDTO.class).getName());
//
//
////            SingletonApplications.userInfo = uInfo;
////            SingletonApplications.userArray.add(uInfo);
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        mAuth.signOut();
//        finish();
//        SingletonApplications.userArray = new ArrayList<UserDTO>();
//        makeToast("Signed out");
    }






    public void makeToast(String option) {
        Toast.makeText(this, option, Toast.LENGTH_SHORT).show();
    }



}
















