package com.example.attendancesystem_omotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.attendancesystem_omotec.Adaptors.List_View_Adaptor;
import com.example.attendancesystem_omotec.Models.List_view_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarkAttPage extends AppCompatActivity {
    private String classes[] = {
            "4A",
            "4B",
            "4C",
            "4D",
            "4E",
            "4F",
            "5A",
            "5B"
    };
    private ArrayAdapter<String> arrayAdapter;
    private List<List_view_model> student_list = new ArrayList<>();
    private List_View_Adaptor student_adaptor;
    private RecyclerView student_recyclerView;
    private Button submit_btn;


    private Spinner autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_att_page);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        student_recyclerView = findViewById(R.id.student_recyclerView);
        submit_btn = findViewById(R.id.submit_att_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MarkAttPage.this, student_list.get(0).isStudent_Absent() + "", Toast.LENGTH_LONG).show();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        autoCompleteTextView.setAdapter(adapter);

        student_list.add(new List_view_model(1, "deveshwar Saste", "9D", false));
        student_list.add(new List_view_model(2, "sakshee gandge", "5D", false));
        student_list.add(new List_view_model(3, "Darshana Sonwane", "9D", false));
        student_list.add(new List_view_model(4, "Jitesh", "7D", true));
        student_list.add(new List_view_model(5, "Kunal Badgujar", "4A", false));
        student_list.add(new List_view_model(6, "Basavaraj", "8D", false));
        student_list.add(new List_view_model(7, "Swarnima", "3D", true));
        student_list.add(new List_view_model(8, "Trupti Muley", "9D", false));
        student_list.add(new List_view_model(9, "Sharayu Vanjari", "3D", false));
        student_list.add(new List_view_model(1, "deveshwar Saste", "9D", false));
        student_list.add(new List_view_model(2, "sakshee gandge", "5D", false));
        student_list.add(new List_view_model(3, "Darshana Sonwane", "9D", false));
        student_list.add(new List_view_model(4, "Jitesh", "7D", true));
        student_list.add(new List_view_model(5, "Kunal Badgujar", "4A", false));
        student_list.add(new List_view_model(6, "Basavaraj", "8D", false));
        student_list.add(new List_view_model(7, "Swarnima", "3D", true));
        student_list.add(new List_view_model(8, "Trupti Muley", "9D", false));
        student_list.add(new List_view_model(9, "Sharayu Vanjari", "3D", false));
        student_list.add(new List_view_model(1, "deveshwar Saste", "9D", false));
        student_list.add(new List_view_model(2, "sakshee gandge", "5D", false));
        student_list.add(new List_view_model(3, "Darshana Sonwane", "9D", false));
        student_list.add(new List_view_model(4, "Jitesh", "7D", true));
        student_list.add(new List_view_model(5, "Kunal Badgujar", "4A", false));
        student_list.add(new List_view_model(6, "Basavaraj", "8D", false));
        student_list.add(new List_view_model(7, "Swarnima", "3D", true));
        student_list.add(new List_view_model(8, "Trupti Muley", "9D", false));
        student_list.add(new List_view_model(9, "Sharayu Vanjari", "3D", false));
        student_list.add(new List_view_model(1, "deveshwar Saste", "9D", false));
        student_list.add(new List_view_model(2, "sakshee gandge", "5D", false));
        student_list.add(new List_view_model(3, "Darshana Sonwane", "9D", false));
        student_list.add(new List_view_model(4, "Jitesh", "7D", true));
        student_list.add(new List_view_model(5, "Kunal Badgujar", "4A", false));
        student_list.add(new List_view_model(6, "Basavaraj", "8D", false));
        student_list.add(new List_view_model(7, "Swarnima", "3D", true));
        student_list.add(new List_view_model(8, "Trupti Muley", "9D", false));
        student_list.add(new List_view_model(9, "Sharayu Vanjari", "3D", false));


        student_adaptor = new List_View_Adaptor(student_list);
        student_recyclerView.setAdapter(student_adaptor);
        student_recyclerView.setLayoutManager(new LinearLayoutManager(MarkAttPage.this));

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


    void list_filter() {
        String List_Fil = autoCompleteTextView.getSelectedItem().toString();
        List<List_view_model> new_studentList = new ArrayList<>();
        for (List_view_model list_item : student_list) {
            if (list_item.getStudent_Section().equals(List_Fil)) {

                new_studentList.add(list_item);

            }
        }
        student_adaptor = new List_View_Adaptor(new_studentList);
        student_recyclerView.setAdapter(student_adaptor);
        student_adaptor.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}