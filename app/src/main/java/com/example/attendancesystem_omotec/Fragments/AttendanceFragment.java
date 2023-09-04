package com.example.attendancesystem_omotec.Fragments;


import static com.example.attendancesystem_omotec.DatabaseQueries.loadSchools;
import static com.example.attendancesystem_omotec.DatabaseQueries.schoolModelList;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendancesystem_omotec.Adaptors.School_Adaptor;
import com.example.attendancesystem_omotec.R;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {

    School_Adaptor schoolAdaptor;


    public AttendanceFragment() {
        // Required empty public constructor
    }

    RecyclerView school_RecyclerView;
    private Dialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        school_RecyclerView = view.findViewById(R.id.schoolRecyclerView);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialog.show();
        if (schoolModelList.size() == 0) {
            loadSchools();


            schoolAdaptor = new School_Adaptor(schoolModelList);
            school_RecyclerView.setAdapter(schoolAdaptor);
            school_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            schoolAdaptor.notifyDataSetChanged();

        } else {
            schoolAdaptor = new School_Adaptor(schoolModelList);
            school_RecyclerView.setAdapter(schoolAdaptor);
            school_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            schoolAdaptor.notifyDataSetChanged();
            loadingDialog.dismiss();
        }

    }


}
