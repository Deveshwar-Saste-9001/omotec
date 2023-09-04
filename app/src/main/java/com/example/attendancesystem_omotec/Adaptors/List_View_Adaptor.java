package com.example.attendancesystem_omotec.Adaptors;


import static com.example.attendancesystem_omotec.MarkAttPage.school;
import static com.example.attendancesystem_omotec.MarkAttPage.updateAttendance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_omotec.Models.List_view_model;
import com.example.attendancesystem_omotec.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class List_View_Adaptor extends RecyclerView.Adapter<List_View_Adaptor.ListViewHolder> {
    public List<List_view_model> student_List;

    public List_View_Adaptor(List<List_view_model> student_List) {
        this.student_List = student_List;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_view, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//        holder.roll_no.setText(position);
//        holder.student_name.setText(student_List.get(position).getStudent_Name());
//        holder.student_class.setText(student_List.get(position).getStudent_Section());
        String name = student_List.get(position).getStudent_Name();
        int rollNo = student_List.get(position).getRoll_No();
        String stud_class = student_List.get(position).getStudent_Section();
        boolean absent = student_List.get(position).isStudent_Absent();
        holder.setData((position), name, stud_class, absent);

    }

    @Override
    public int getItemCount() {
        return student_List.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView roll_no, student_name, student_class;
        CheckBox student_absent;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            roll_no = itemView.findViewById(R.id.roll_no_list_view);
            student_name = itemView.findViewById(R.id.student_name_list_view);
            student_class = itemView.findViewById(R.id.section_list_view);
            student_absent = itemView.findViewById(R.id.student_absent_list_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item_clicked(view, getAdapterPosition(), student_name.getText().toString(), student_class.getText().toString(), student_absent.isChecked());
                }
            });
            student_absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item_clicked(view, getAdapterPosition(), student_name.getText().toString(), student_class.getText().toString(), student_absent.isChecked());
                }
            });

        }

        void item_clicked(@NonNull View itemView, int i, String name, String classes, boolean ab) {


            if (student_absent.getText() == "A") {
                student_absent.setText("P");
                student_List.get(i).setStudent_Absent(false);
                student_absent.setTextColor(Color.parseColor("#00FF00"));
                student_absent.setChecked(false);
                updateAttendance(false, name);
                notifyDataSetChanged();

            } else {
                student_absent.setText("A");
                student_absent.setTextColor(Color.parseColor("#FF0000"));
                student_List.get(i).setStudent_Absent(true);
                student_absent.setChecked(true);

                updateAttendance(true, name);
                //firebaseFirestore.collection("Schools").document(school).collection("ATTENDANCES").document(today.toString()).collection("STUDENTS").document(documentSnapshot.get("name").toString()).set(userdataMap);

                notifyDataSetChanged();
            }
        }

        void setData(int rollNo, String student_Name, String student_Class, boolean Absent) {
            this.roll_no.setText(Integer.toString(rollNo));
            this.student_name.setText(student_Name);
            this.student_class.setText(student_Class);
            if (Absent) {
                student_absent.setText("A");
                student_absent.setTextColor(Color.parseColor("#FF0000"));
                student_absent.setChecked(true);
            } else {
                student_absent.setText("P");
                student_absent.setTextColor(Color.parseColor("#00FF00"));

                student_absent.setChecked(false);
            }
        }

    }

}
