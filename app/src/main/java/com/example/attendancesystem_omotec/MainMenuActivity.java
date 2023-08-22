package com.example.attendancesystem_omotec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainMenuActivity extends AppCompatActivity {


    private Button loginButton;
    private EditText loginEmail,loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loginButton=findViewById(R.id.loginBtn);
        loginEmail=findViewById(R.id.editEmail);
        loginPassword=findViewById(R.id.Edit_password);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}