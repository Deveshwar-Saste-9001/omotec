package com.example.attendancesystem_omotec.Fragments;


import static com.example.attendancesystem_omotec.DatabaseQueries.loadSchools;
import static com.example.attendancesystem_omotec.DatabaseQueries.schoolModelList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.attendancesystem_omotec.AddSchoolActivity;
import com.example.attendancesystem_omotec.DatabaseQueries;
import com.example.attendancesystem_omotec.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchoolFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import com.example.attendancesystem_omotec.Adaptors.School_Adaptor;
import com.example.attendancesystem_omotec.Adaptors.School_View_Adaptor;


/**
 * @noinspection ALL
 */
public class SchoolFragment extends Fragment {


    public SchoolFragment() {
        // Required empty public constructor
    }

    public static School_View_Adaptor schoolAdaptor;
    public static RecyclerView school_RecyclerView;
    private ProgressDialog LodingBar;
    private Dialog loadingDialog;
    private Button addSchool;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schools, container, false);
        school_RecyclerView = view.findViewById(R.id.schoolRecyclerView);
        addSchool=view.findViewById(R.id.addSchoolBtn);
        LodingBar = new ProgressDialog(getContext());
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_bar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.color.recyclerViewBackground));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddSchoolActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadingDialog.show();
        if (schoolModelList.size() == 0) {
            loadSchools(DatabaseQueries.location);
            schoolAdaptor = new School_View_Adaptor(schoolModelList);
            school_RecyclerView.setAdapter(schoolAdaptor);
            school_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            schoolAdaptor.notifyDataSetChanged();
            loadingDialog.dismiss();


        } else {

            schoolAdaptor = new School_View_Adaptor(schoolModelList);
            school_RecyclerView.setAdapter(schoolAdaptor);
            school_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            schoolAdaptor.notifyDataSetChanged();
            loadingDialog.dismiss();
        }

    }
}