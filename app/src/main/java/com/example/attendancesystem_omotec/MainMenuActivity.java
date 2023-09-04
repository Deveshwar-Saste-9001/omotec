package com.example.attendancesystem_omotec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancesystem_omotec.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainMenuActivity extends AppCompatActivity {

    private TextInputEditText InputNunmber;
    private TextInputEditText InputPassword;
    private Button LoginButton;
    private ProgressDialog LodingBar;
    private String ParentDbName = "Users";
    //private String Adminmobile, Adminpass;
    //private com.rey.material.widget.CheckBox RememberMeckbox;
   // private TextView Resisteruser, ForgetPass, AdminBack;
    public static boolean disableCloseBtn = false;
    private Dialog loadingDialog;



    private Button loginButton;
    private Button signUpBtn;

    //private EditText loginEmail,loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loginButton=findViewById(R.id.loginBtn);
       // loginEmail=findViewById(R.id.editEmail);
      //  loginPassword=findViewById(R.id.Edit_password);
        signUpBtn=findViewById(R.id.signupBtnLogin);

        loadingDialog = new Dialog(MainMenuActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        InputNunmber = (TextInputEditText) findViewById(R.id.editEmail);
//        LoginButton = (Button) findViewById(R.id.user_login);
//        Resisteruser = (TextView) findViewById(R.id.res_link);
//        ForgetPass = (TextView) findViewById(R.id.forget_pass);
//        AdminBack = (TextView) findViewById(R.id.admin_Back);
      InputPassword = (TextInputEditText) findViewById(R.id.Edit_password);
        LodingBar = new ProgressDialog(this);
        //RememberMeckbox = (CheckBox) findViewById(R.id.remember_checkbox);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,signUpActivity.class);
                startActivity(intent);
                finish();

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               Loginuser();

            }
        });



    }

    private void Loginuser() {
        String Mobile = "+91" + InputNunmber.getText().toString();
        String Password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(InputNunmber.getText())) {
            Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Enter your password...", Toast.LENGTH_SHORT).show();

        } else {
            loadingDialog.show();
            AllowAccessToAccount(Mobile, Password);
        }
    }

    private void AllowAccessToAccount(final String Mobile, final String Password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        loadingDialog.show();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ParentDbName).child(Mobile).exists()) {
                    final Users usersData = dataSnapshot.child(ParentDbName).child(Mobile).getValue(Users.class);
                    String UserMobile = Objects.requireNonNull(usersData).getMobile().toString();
                    String UserPassword = usersData.getPassword().toString();
                    String email = usersData.getEmail().toString();
                    if (UserMobile.equals(Mobile)) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (disableCloseBtn) {
                                        loadingDialog.dismiss();
                                        disableCloseBtn = false;
                                        FirebaseAuth.getInstance().getUid();
                                        finish();

                                    } else {
                                        loadingDialog.dismiss();
                                        Intent intent = new Intent(MainMenuActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        disableCloseBtn = false;
                                        finish();
                                    }
                                } else {
                                    loadingDialog.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(MainMenuActivity.this, error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        LodingBar.dismiss();
                        Toast.makeText(MainMenuActivity.this, "This user does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    LodingBar.dismiss();
                    Toast.makeText(MainMenuActivity.this, "This user does not exist", Toast.LENGTH_SHORT).show();

                }
                loadingDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                LodingBar.dismiss();
            }
        });

    }



}