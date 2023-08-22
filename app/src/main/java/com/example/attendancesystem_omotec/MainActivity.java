package com.example.attendancesystem_omotec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(MainActivity.this, MainMenuActivity.class);
                startActivity(intent);
                // close this activity
                finish();
            }
        },  3000);


    }
}