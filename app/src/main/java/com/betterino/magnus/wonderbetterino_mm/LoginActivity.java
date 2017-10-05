package com.betterino.magnus.wonderbetterino_mm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v) {
        if(v == buttonLogin){
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
        }
    }
}
