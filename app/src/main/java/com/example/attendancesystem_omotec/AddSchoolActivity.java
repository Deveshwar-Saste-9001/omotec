package com.example.attendancesystem_omotec;

import static android.app.PendingIntent.getActivity;
import static com.example.attendancesystem_omotec.DatabaseQueries.schoolModelList;
import static com.example.attendancesystem_omotec.MarkAttPage.firebaseFirestore;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attendancesystem_omotec.Models.School_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddSchoolActivity extends AppCompatActivity {
    private ImageView selectSchoolLogo;
    private TextInputEditText schoolname;
    private Dialog loadingDialog;

    private String schoollocation;
    private String Locations[] = {"pune"};
    private Button addSchoolBtn;
    private Spinner schoolLocationSelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        selectSchoolLogo = findViewById(R.id.schoollogoAdd);
        schoolname = findViewById(R.id.schoolNameAdd);
        schoolLocationSelector = findViewById(R.id.schoolLocationAdd);
        addSchoolBtn = findViewById(R.id.addSchoolBtnadd);
        loadingDialog = new Dialog(AddSchoolActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addSchoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatePhotoF();

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddSchoolActivity.this, R.layout.section_list_items, Locations);
        adapter.setDropDownViewResource(R.layout.section_list_items);
        schoolLocationSelector.setAdapter(adapter);


        schoolLocationSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoollocation = schoolLocationSelector.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void updatePhotoF() {
        ////updating photo
        loadingDialog.show();


        Map<String, Object> updatedata = new HashMap<>();
        updatedata.put("school_id", schoolname.getText().toString() + "" + schoollocation.toString());
        updatedata.put("Name", schoolname.getText().toString());
        updatedata.put("logo", "");
        updatedata.put("location", schoollocation);
        FirebaseFirestore.getInstance().collection("Schools").document().set(updatedata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    CollectionReference UserDataReference = firebaseFirestore.collection("Schools").document(schoolname.getText().toString()).collection("CLASSES");

                    final Map<String, Object> documentsNames = new HashMap<String, Object>();
                    documentsNames.put("name", "");
                    documentsNames.put("roll_no", "");
                    documentsNames.put("section", "");
                    UserDataReference.document("Monday").collection("STUDENTS").document("EmptyDoc").set(documentsNames);

                    schoolModelList.add(new School_Model(schoolname.getText().toString(), schoolname.getText().toString(), "", schoollocation));
                    Toast.makeText(AddSchoolActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AddSchoolActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
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