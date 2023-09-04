package com.example.attendancesystem_omotec;

import static android.provider.MediaStore.MediaColumns.DOCUMENT_ID;
import static androidx.fragment.app.FragmentManager.TAG;
import static com.example.attendancesystem_omotec.DatabaseQueries.schoolModelList;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.attendancesystem_omotec.Adaptors.List_View_Adaptor;
import com.example.attendancesystem_omotec.Adaptors.School_Adaptor;
import com.example.attendancesystem_omotec.Models.List_view_model;
import com.example.attendancesystem_omotec.Models.School_Model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class MarkAttPage extends AppCompatActivity {

    private TextView schoolLogoName;
    private DatabaseReference RootRef;
   // private int listIndex = -1;

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static String school = "";
    String logo="";
    private Set<String> classList = new HashSet<>();
    private String classes[];

    private ArrayAdapter<String> arrayAdapter;
    private List<List_view_model> student_list;
    private List_View_Adaptor student_adaptor;
    private RecyclerView student_recyclerView;
    private ProgressDialog LodingBar;
    String day;
    private ImageView schoolLogo;


    private Spinner autoCompleteTextView;
    static LocalDate today = LocalDate.now();

    DayOfWeek dayOfWeek = today.getDayOfWeek();

    public MarkAttPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_att_page);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(school);
        student_list = new ArrayList<>();
        schoolLogo = findViewById(R.id.schoollogo);
        LodingBar = new ProgressDialog(this);
        schoolLogoName = findViewById(R.id.schoolNameATT);
        schoolLogoName.setText(school);
        student_recyclerView = findViewById(R.id.student_recyclerView);

       logo = getIntent().getStringExtra("logo");
        if (!logo.equals("")) {
            Glide.with(this).load(logo).apply(new RequestOptions().placeholder(R.drawable.baseline_school_24)).into(schoolLogo);
        } else {
            Glide.with(this).load(R.drawable.baseline_school_24).apply(new RequestOptions().placeholder(R.drawable.baseline_school_24)).into(schoolLogo);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        String day1 = "";
        for (int i = 0; i < day.length(); i++) {
            if (i == 0) {
                day1 = day1 + "" + day.charAt(i);
            } else {
                day1 = day1 + "" + day.toLowerCase().charAt(i);
            }
        }
        day = day1;

        RootRef = FirebaseDatabase.getInstance().getReference();
        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list_filter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(MarkAttPage.this, "document is Loaded ", Toast.LENGTH_SHORT).show();
                loadStudents(!dataSnapshot.child("Schools").child(school).child(today.toString()).exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }


    private void loadStudents(boolean isLoaded) {
        RootRef = FirebaseDatabase.getInstance().getReference();
        LodingBar.setTitle("Student List");
        LodingBar.setMessage("please wait,While we are loading Students Data");
        LodingBar.setCanceledOnTouchOutside(false);
        LodingBar.show();
        if (student_list.size() == 0) {
            firebaseFirestore.collection("Schools").document(school).collection("CLASSES").document(day).collection("STUDENTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {


                        //DocumentReference docRef =firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString());

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {


                            if (isLoaded) {
                                final HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("name", documentSnapshot.get("name").toString());
                                if(!documentSnapshot.get("roll_no").equals("")){
                                userdataMap.put("roll_no", Integer.parseInt(documentSnapshot.get("roll_no").toString()));
                                }else{
                                    userdataMap.put("roll_no", documentSnapshot.get("roll_no").toString());
                                }
                                userdataMap.put("section", documentSnapshot.get("section").toString());
                                userdataMap.put("isAbsent", false);
                                HashMap<String, Object> updates = new HashMap<String, Object>();
                                updates.put("isLoaded", true);
                                RootRef.child("Schools").child(school).child(today.toString()).updateChildren(updates);
                                final HashMap<String, Object> dateId = new HashMap<>();
                                dateId.put("id", today.toString());
                                firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString()).set(dateId);
                                firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString()).collection("STUDENTS").document(documentSnapshot.get("name").toString()).set(userdataMap);
                            }
                        }
                        firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString()).collection("STUDENTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                if (task1.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot1 : task1.getResult()) {
                                        student_list.add(new List_view_model(Integer.parseInt(documentSnapshot1.get("roll_no").toString()), documentSnapshot1.get("name").toString(), documentSnapshot1.get("section").toString(), (Boolean) documentSnapshot1.get("isAbsent")));
                                        if(!documentSnapshot1.getString("section").equals("")){
                                        classList.add(documentSnapshot1.getString("section"));
                                        }
                                    }
                                    int i = 0;
                                    classes = new String[classList.size()];
                                    // iterating over the hashset
                                    for (String ele : classList) {
                                        classes[i++] = ele;
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MarkAttPage.this, R.layout.section_list_items, classes);
                                    adapter.setDropDownViewResource(R.layout.section_list_items);
                                    autoCompleteTextView.setAdapter(adapter);

                                    student_adaptor = new List_View_Adaptor(student_list,true);
                                    student_recyclerView.setAdapter(student_adaptor);
                                    student_recyclerView.setLayoutManager(new LinearLayoutManager(MarkAttPage.this));

                                    student_adaptor.notifyDataSetChanged();
                                    list_filter();
                                    LodingBar.dismiss();
                                } else {
                                    LodingBar.dismiss();
                                    String error = task.getException().getMessage();
                                    Toast.makeText(MarkAttPage.this, error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        Toast.makeText(MarkAttPage.this, "From Database", Toast.LENGTH_SHORT).show();

                    } else {
                        LodingBar.dismiss();
                    }

                }
            });
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MarkAttPage.this, R.layout.section_list_items, classes);
            adapter.setDropDownViewResource(R.layout.section_list_items);
            autoCompleteTextView.setAdapter(adapter);

            student_adaptor = new List_View_Adaptor(student_list,true);
            student_recyclerView.setAdapter(student_adaptor);
            student_recyclerView.setLayoutManager(new LinearLayoutManager(MarkAttPage.this));

            student_adaptor.notifyDataSetChanged();
            list_filter();
            Toast.makeText(MarkAttPage.this, "From Local list", Toast.LENGTH_SHORT).show();
            LodingBar.dismiss();
        }

    }


    void list_filter() {
        String List_Fil = (String) autoCompleteTextView.getSelectedItem();
        List<List_view_model> new_studentList = new ArrayList<>();
        for (List_view_model list_item : student_list) {
            if (list_item.getStudent_Section().equals(List_Fil)) {

                new_studentList.add(list_item);

            }
        }
        student_adaptor = new List_View_Adaptor(new_studentList,true);
        student_recyclerView.setAdapter(student_adaptor);
        student_adaptor.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void updateAttendance(boolean isAbsent, String id) {
        final HashMap<String, Object> userdataMap = new HashMap<>();
        userdataMap.put("isAbsent", isAbsent);
        firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString()).collection("STUDENTS").document(id).update(userdataMap);
    }

}