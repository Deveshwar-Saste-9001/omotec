package com.example.attendancesystem_omotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SchoolMenuActivity extends AppCompatActivity {
    private Button viewAttendance,addStudents,viewStudents,editSchool;
    private String school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_menu);
        viewAttendance=findViewById(R.id.viewAtendanceBtn);
        addStudents=findViewById(R.id.addStudentsBtn);
        viewStudents=findViewById(R.id.viewStudentsBtn);
        editSchool=findViewById(R.id.editSchoolBtn);
        school=getIntent().getStringExtra("school");
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SchoolMenuActivity.this,ViewAllStudentsActivity.class);
                intent.putExtra("school",school);
                startActivity(intent);
            }
        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SchoolMenuActivity.this,ViewAttendanceActivity.class);
                intent.putExtra("school",school);
                startActivity(intent);
            }
        });
        addStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SchoolMenuActivity.this, addStudentList.class);
                intent.putExtra("school",school);
                startActivity(intent);
            }
        });
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