package com.betterino.magnus.wonderbetterino_mm;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseUser;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

/**
 * Created by Magnus on 09-10-2017.
 */

public class SingletonApplications extends Application {
    public static boolean changepic = false;
    public static FirebaseUser user;
    public static ArrayList<UserDTO> userArray = new ArrayList<UserDTO>();
    public static String name;
    public static int wallet;
    //public static String[] gameArray = {"Hangman", "Game 2", "Game 3"}; //De tilgængelige spil
    public static String[] gameArray = {"Hangman"}; //De tilgængelige spil

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

}