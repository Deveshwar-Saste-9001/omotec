package com.example.attendancesystem_omotec;

import static com.example.attendancesystem_omotec.DatabaseQueries.firebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.attendancesystem_omotec.Adaptors.List_View_Adaptor;
import com.example.attendancesystem_omotec.Adaptors.SessionDateAdaptor;
import com.example.attendancesystem_omotec.Models.List_view_model;
import com.example.attendancesystem_omotec.Models.SessionDateModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {
    private String school = "";
    private Dialog loadingDialog;
    private List<SessionDateModel> sessionDateModelList = new ArrayList<>();
    private SessionDateAdaptor sessionDateAdaptor;
    private RecyclerView dateRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        school = getIntent().getStringExtra("school");
        dateRecyclerView = findViewById(R.id.dateRecyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.att_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(school);
        loadingDialog = new Dialog(ViewAttendanceActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // sessionDateModelList.add(new SessionDateModel("26-09-2023"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sessionDateModelList.size() == 0) {
            //Toast.makeText(ViewAttendanceActivity.this, school, Toast.LENGTH_SHORT).show();
            FirebaseFirestore.getInstance().collection("Schools").document(school).collection("ATTENDANCES").orderBy("id", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()) {
                        //    Toast.makeText(ViewAttendanceActivity.this, documentSnapshot1.get("id").toString(), Toast.LENGTH_SHORT).show();
                            sessionDateModelList.add(new SessionDateModel(documentSnapshot1.get("id").toString()));

                        }

                        sessionDateAdaptor = new SessionDateAdaptor(sessionDateModelList,school);
                        dateRecyclerView.setAdapter(sessionDateAdaptor);
                        dateRecyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendanceActivity.this));

                        sessionDateAdaptor.notifyDataSetChanged();

                        loadingDialog.dismiss();
                    } else {
                        loadingDialog.dismiss();
                        String error = task.getException().getMessage();
                        Toast.makeText(ViewAttendanceActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {

            sessionDateAdaptor = new SessionDateAdaptor(sessionDateModelList,school);
            dateRecyclerView.setAdapter(sessionDateAdaptor);
            dateRecyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendanceActivity.this));
            sessionDateAdaptor.notifyDataSetChanged();
         //   Toast.makeText(ViewAttendanceActivity.this, school, Toast.LENGTH_SHORT).show();

            //   Toast.makeText(ViewAttendanceActivity.this, "From Local list", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
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