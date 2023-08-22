package com.example.attendancesystem_omotec.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.attendancesystem_omotec.MarkAttPage;
import com.example.attendancesystem_omotec.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {


    public AttendanceFragment() {
        // Required empty public constructor
    }

    private ImageView school;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_attendance, container, false);

        school=view.findViewById(R.id.gkg);
         school.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(getContext(), MarkAttPage.class);
                 startActivity(intent);

             }
         });

        return view;



    }

}
