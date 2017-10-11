package com.betterino.magnus.wonderbetterino_mm;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Magnus on 09-10-2017.
 */

public class SingletonApplications extends Application {
    public static boolean changepic = false;
    public static FirebaseUser user;
    public static ArrayList<UserDTO> userArray = new ArrayList<UserDTO>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

}