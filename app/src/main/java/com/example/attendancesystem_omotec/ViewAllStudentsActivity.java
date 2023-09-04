package com.example.attendancesystem_omotec;

import static com.example.attendancesystem_omotec.DatabaseQueries.firebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attendancesystem_omotec.Adaptors.AllStudentAdaptor;
import com.example.attendancesystem_omotec.Adaptors.List_View_Adaptor;
import com.example.attendancesystem_omotec.Models.List_view_model;
import com.example.attendancesystem_omotec.Models.Student_ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewAllStudentsActivity extends AppCompatActivity {
    private Spinner autoCompleteTextView, dayAutocomplete;
    private String school = "";
    private List<Student_ViewModel> student_list = new ArrayList<>();
    private ProgressDialog LodingBar;
    private Set<String> classList = new HashSet<>();
    private String classes[];
    int j = -1;
    String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private AllStudentAdaptor student_adaptor;
    private RecyclerView studentRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_students);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        dayAutocomplete = findViewById(R.id.dayAutocompleteText);
        school = getIntent().getStringExtra("school");
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(school);

        LodingBar = new ProgressDialog(this);
        studentRecyclerView = findViewById(R.id.student_recyclerView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewAllStudentsActivity.this, R.layout.section_list_items, days);
        adapter.setDropDownViewResource(R.layout.section_list_items);
        dayAutocomplete.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dayAutocomplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadStudents(dayAutocomplete.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list_filter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //loadStudents("Monday");


    }

    private void loadStudents(String day) {
        LodingBar.setTitle("Student List");
        LodingBar.setMessage("please wait,While we are loading Students Data");
        LodingBar.setCanceledOnTouchOutside(false);
        LodingBar.show();
        firebaseFirestore.collection("Schools").document(school).collection("CLASSES").document(day).collection("STUDENTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    classList.clear();


                    //DocumentReference docRef =firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString());

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        student_list.add(new Student_ViewModel(documentSnapshot.get("roll_no").toString(), documentSnapshot.get("name").toString(), documentSnapshot.get("section").toString()));
                        if (!documentSnapshot.getString("section").equals("")) {
                            classList.add(documentSnapshot.getString("section"));


                            int i = 0;
                            classes = new String[classList.size()];
                            // iterating over the hashset
                            for (String ele : classList) {
                                classes[i++] = ele;
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewAllStudentsActivity.this, R.layout.section_list_items, classes);
                            adapter.setDropDownViewResource(R.layout.section_list_items);
                            autoCompleteTextView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            student_adaptor = new AllStudentAdaptor(student_list);
                            studentRecyclerView.setAdapter(student_adaptor);
                            studentRecyclerView.setLayoutManager(new LinearLayoutManager(ViewAllStudentsActivity.this));

                            student_adaptor.notifyDataSetChanged();
                            list_filter();
                            LodingBar.dismiss();
                        } else {
                            LodingBar.dismiss();
//                                String error = task.getException().getMessage();
                            //Toast.makeText(ViewAllStudentsActivity.this, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

        });


    }


    void list_filter() {
        String List_Fil = (String) autoCompleteTextView.getSelectedItem();
        List<Student_ViewModel> new_studentList = new ArrayList<>();
        for (Student_ViewModel list_item : student_list) {
            if (list_item.getSection().equals(List_Fil)) {

                new_studentList.add(list_item);

            }
        }
        student_adaptor = new AllStudentAdaptor(new_studentList);
        studentRecyclerView.setAdapter(student_adaptor);
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