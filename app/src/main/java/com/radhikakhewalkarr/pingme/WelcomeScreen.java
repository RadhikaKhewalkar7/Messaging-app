package com.radhikakhewalkarr.pingme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    public void registerScreen(View v){

        Intent nextScreenLoad = new Intent(this,SignUpActivity.class);
        startActivity(nextScreenLoad);


    }


    public void logInScreen(View v){

        Intent nextScreenLoad = new Intent(this,LoginScreen.class);
        startActivity(nextScreenLoad);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }
}