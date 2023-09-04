package com.example.attendancesystem_omotec.Adaptors;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_omotec.MarkAttPage;
import com.example.attendancesystem_omotec.Models.School_Model;
import com.example.attendancesystem_omotec.R;
import com.example.attendancesystem_omotec.addStudentList;

import java.util.List;

public class School_View_Adaptor extends RecyclerView.Adapter<School_View_Adaptor.ViewHolder> {

    private List<School_Model> school_List;

    public School_View_Adaptor(List<School_Model> school_List) {
        this.school_List = school_List;
    }

    @NonNull
    @Override
    public School_View_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item_view, parent, false);

        return new School_View_Adaptor.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull School_View_Adaptor.ViewHolder holder, int position) {
        String schoolName = school_List.get(position).getSchool_Name();
        holder.setData(schoolName);


    }

    @Override
    public int getItemCount() {
        return school_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView schoolNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolNameTextView = itemView.findViewById(R.id.SchoolName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(itemView.getContext(), addStudentList.class);
                    //MarkAttPage.school= (String) schoolNameTextView.getText();
                    intent.putExtra("school",schoolNameTextView.getText());
                    itemView.getContext().startActivity(intent);
                }
            });


        }


        public void setData(String SchoolName) {
            schoolNameTextView.setText(SchoolName);
        }
    }
}
