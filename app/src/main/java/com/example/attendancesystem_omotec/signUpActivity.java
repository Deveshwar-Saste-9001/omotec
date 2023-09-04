package com.example.attendancesystem_omotec;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signUpActivity extends AppCompatActivity {

    private ProgressDialog LodingBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText emailValue, nameValue;
    private Spinner selectLocationEditView;
    private TextInputEditText mobileValue;
    private TextInputEditText passwordValue;
    private DatabaseReference RootRef;
    private String location="";
    private String[] locations={
            "pune","mumbai","delhi"
    };
    public static boolean disableRegCloseBtn = false;
    private Button signup, backbtn;

// ...
// Initialize Firebase Auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailValue = findViewById(R.id.addEmail);
        passwordValue = findViewById(R.id.addpassword);
        nameValue = findViewById(R.id.addname);
        mobileValue = findViewById(R.id.addmobile);
        selectLocationEditView=findViewById(R.id.selectLocationreg);
        LodingBar = new ProgressDialog(this);
        signup = findViewById(R.id.signupBtn);
        backbtn = findViewById(R.id.backBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        //auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUpActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(signUpActivity.this, R.layout.section_list_items, locations);
        adapter.setDropDownViewResource(R.layout.section_list_items);
        selectLocationEditView.setAdapter(adapter);
        selectLocationEditView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location=selectLocationEditView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void CreateAccount() {
        String name = nameValue.getText().toString();
        String mobile = "+91"+mobileValue.getText().toString();
        String email = emailValue.getText().toString();
        String password = passwordValue.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "Enter your mobile number...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter your password...", Toast.LENGTH_SHORT).show();

        } else {
            LodingBar.setTitle("Create Account");
            LodingBar.setMessage("please wait,While we are checking the credential");
            LodingBar.setCanceledOnTouchOutside(false);
            LodingBar.show();

            ValidatePhoneNumber(name, mobile, password, email,location);
        }

    }

    private void ValidatePhoneNumber(final String Name, final String Mobile, final String Password, final String Email,String loc) {

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(Mobile).exists())) {
                    final HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Mobile", Mobile);
                    userdataMap.put("Name", Name);
                    userdataMap.put("Email", Email);
                    userdataMap.put("Location", loc);
                    userdataMap.put("Password", Password);
                    userdataMap.put("Status", "pending");

                    CheckEmailAndPassword(Mobile, Name, Email, Password,loc);


                } else {
                    Toast.makeText(signUpActivity.this, "this mobile number" + Mobile + "alredy exist", Toast.LENGTH_SHORT).show();
                    LodingBar.dismiss();
                    Toast.makeText(signUpActivity.this, "Please try again using another number", Toast.LENGTH_SHORT).show();
                    mobileValue.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void CheckEmailAndPassword(final String Mobile, final String Name, final String Email, final String Password,String loc) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if (emailValue.getText().toString().matches(emailPattern)) {
            firebaseAuth.createUserWithEmailAndPassword(emailValue.getText().toString(), passwordValue.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("Mobile", Mobile);
                                userdataMap.put("Name", Name);
                                userdataMap.put("Email", Email);
                                userdataMap.put("profile", "");
                                userdataMap.put("Location", loc);
                                userdataMap.put("id", firebaseAuth.getUid());
                                userdataMap.put("Password", Password);
                                userdataMap.put("Status", "pending");

                                firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                        .set(userdataMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    CollectionReference UserDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

//////////////////MAPS
                                                    Map<String, Object> notificationMap = new HashMap<>();
                                                    notificationMap.put("list_size", (long) 0);

                                                    //////
                                                    final List<String> documentsNames = new ArrayList<>();
                                                    documentsNames.add("MY_NOTIFICATIONS");

                                                    List<Map<String, Object>> documentsFields = new ArrayList<>();
                                                    documentsFields.add(notificationMap);

                                                    for (int x = 0; x < documentsNames.size(); x++) {
                                                        final int finalX = x;
                                                        UserDataReference.document(documentsNames.get(x))
                                                                .set(documentsFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            if (finalX == documentsNames.size() - 1) {
                                                                                RootRef.child("Users").child(Mobile).updateChildren(userdataMap)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
//
                                                                                                    if (disableRegCloseBtn) {
                                                                                                        Toast.makeText(signUpActivity.this, "Congratulation your account created", Toast.LENGTH_SHORT).show();
                                                                                                        disableRegCloseBtn = false;
                                                                                                        Intent intent = new Intent(signUpActivity.this, MainMenuActivity.class);
                                                                                                        startActivity(intent);
                                                                                                        finish();
                                                                                                    } else {
                                                                                                        Toast.makeText(signUpActivity.this, "Congratulation your account created", Toast.LENGTH_SHORT).show();
                                                                                                        Intent intent = new Intent(signUpActivity.this, MainMenuActivity.class);
                                                                                                        startActivity(intent);
                                                                                                        finish();
                                                                                                        disableRegCloseBtn = false;
                                                                                                    }
                                                                                                    LodingBar.dismiss();
                                                                                                    nameValue.setText("");
                                                                                                    mobileValue.setText("");
                                                                                                    passwordValue.setText("");
                                                                                                    emailValue.setText("");
                                                                                                } else {
                                                                                                    LodingBar.dismiss();
                                                                                                    disableRegCloseBtn = false;
                                                                                                    Toast.makeText(signUpActivity.this, "Network Error Please Try Again", Toast.LENGTH_SHORT).show();
                                                                                                }

                                                                                            }
                                                                                        });
                                                                            }
                                                                        } else {
                                                                            LodingBar.dismiss();
                                                                            Toast.makeText(signUpActivity.this, "Network Error Please Try Again", Toast.LENGTH_SHORT).show();

                                                                        }


                                                                    }
                                                                });
                                                    }

                                                } else {
                                                    LodingBar.dismiss();
                                                    Toast.makeText(signUpActivity.this, "Network Error Please Try Again", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            } else {
                                LodingBar.dismiss();
                                String error = task.getException().getMessage();
                                Toast.makeText(signUpActivity.this, error, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        } else {
            LodingBar.dismiss();
            Toast.makeText(signUpActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (FirebaseAuth.getInstance().getUid() != null) {
            FirebaseAuth.getInstance().signOut();
        }
        nameValue.setText("");
        mobileValue.setText("");
        emailValue.setText("");
        passwordValue.setText("");

        Intent homeIntent = new Intent(signUpActivity.this, MainMenuActivity.class);
        startActivity(homeIntent);
        finish();
    }




}