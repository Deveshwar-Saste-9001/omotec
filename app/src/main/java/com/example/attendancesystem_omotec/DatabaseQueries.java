package com.example.attendancesystem_omotec;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.attendancesystem_omotec.Models.School_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<School_Model> schoolModelList = new ArrayList<>();


    public static void loadSchools() {
        schoolModelList.clear();


        firebaseFirestore.collection("Schools").orderBy("Name").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            schoolModelList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                schoolModelList.add(new School_Model(documentSnapshot.get("school_id").toString(), documentSnapshot.get("Name").toString(), documentSnapshot.get("logo").toString(), documentSnapshot.get("location").toString()));
                            }

                        } else {
                            String error = task.getException().getMessage();
                        }
                    }

                });

    }
}
