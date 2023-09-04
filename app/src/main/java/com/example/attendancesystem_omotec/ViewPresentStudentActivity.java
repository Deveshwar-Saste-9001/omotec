package com.example.attendancesystem_omotec;

import static com.example.attendancesystem_omotec.DatabaseQueries.firebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancesystem_omotec.Adaptors.List_View_Adaptor;
import com.example.attendancesystem_omotec.Models.List_view_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ViewPresentStudentActivity extends AppCompatActivity {
    public static String dateTitle = "";

    private TextView schoolLogoName;
    private DatabaseReference RootRef;
    // private int listIndex = -1;

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static String school = "";
    private Set<String> classList = new HashSet<>();
    private String classes[];

    private List<List_view_model> student_list;
    private List_View_Adaptor student_adaptor;
    private RecyclerView student_recyclerView;
    private ProgressDialog LodingBar;

    private Spinner autoCompleteTextView;
    static LocalDate today = LocalDate.now();

    private TextView date;
    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_present_student);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        school = getIntent().getStringExtra("school");
        dateTitle = getIntent().getStringExtra("date");
        date = findViewById(R.id.date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(school);
        loadingDialog = new Dialog(ViewPresentStudentActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        student_list = new ArrayList<>();
        student_recyclerView = findViewById(R.id.student_recyclerView);
        student_list = new ArrayList<>();
        LodingBar = new ProgressDialog(this);
        student_recyclerView = findViewById(R.id.student_recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(ViewPresentStudentActivity.this,dateTitle+school+"",Toast.LENGTH_SHORT).show();

        date.setText(dateTitle);
        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list_filter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       loadStudents();


    }


    private void loadStudents() {
        RootRef = FirebaseDatabase.getInstance().getReference();
        LodingBar.setTitle("Student List");
        LodingBar.setMessage("please wait,While we are loading Students Data");
        LodingBar.setCanceledOnTouchOutside(false);
        LodingBar.show();
        if (student_list.size() == 0) {


            firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(dateTitle).collection("STUDENTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                    if (task1.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot1 : task1.getResult()) {
                            student_list.add(new List_view_model(Integer.parseInt(documentSnapshot1.get("roll_no").toString()), documentSnapshot1.get("name").toString(), documentSnapshot1.get("section").toString(), (Boolean) documentSnapshot1.get("isAbsent")));
                            if (!documentSnapshot1.getString("section").equals("")) {
                                classList.add(documentSnapshot1.getString("section"));
                            }
                        }
                        int i = 0;
                        classes = new String[classList.size()];
                        // iterating over the hashset
                        for (String ele : classList) {
                            classes[i++] = ele;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewPresentStudentActivity.this, R.layout.section_list_items, classes);
                        adapter.setDropDownViewResource(R.layout.section_list_items);
                        autoCompleteTextView.setAdapter(adapter);

                        student_adaptor = new List_View_Adaptor(student_list, false);
                        student_recyclerView.setAdapter(student_adaptor);
                        student_recyclerView.setLayoutManager(new LinearLayoutManager(ViewPresentStudentActivity.this));

                        student_adaptor.notifyDataSetChanged();
                        list_filter();
                        LodingBar.dismiss();
                    } else {
                        LodingBar.dismiss();
                        String error = task1.getException().getMessage();
                        Toast.makeText(ViewPresentStudentActivity.this, error, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewPresentStudentActivity.this, R.layout.section_list_items, classes);
            adapter.setDropDownViewResource(R.layout.section_list_items);
            autoCompleteTextView.setAdapter(adapter);

            student_adaptor = new List_View_Adaptor(student_list, false);
            student_recyclerView.setAdapter(student_adaptor);
            student_recyclerView.setLayoutManager(new LinearLayoutManager(ViewPresentStudentActivity.this));

            student_adaptor.notifyDataSetChanged();
            list_filter();
            Toast.makeText(ViewPresentStudentActivity.this, "From Local list", Toast.LENGTH_SHORT).show();
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
        student_adaptor = new List_View_Adaptor(new_studentList, false);
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


}