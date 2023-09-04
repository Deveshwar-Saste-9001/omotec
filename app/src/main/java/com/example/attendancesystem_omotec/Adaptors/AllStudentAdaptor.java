package com.example.attendancesystem_omotec.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_omotec.Models.Student_ViewModel;
import com.example.attendancesystem_omotec.R;

import java.util.List;

public class AllStudentAdaptor extends RecyclerView.Adapter<AllStudentAdaptor.ViewHolder> {
    private List<Student_ViewModel> studentList;

    public AllStudentAdaptor(List<Student_ViewModel> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_student_itemview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String rollNo=studentList.get(position).getRollNo();
        String name=studentList.get(position).getName();
        String section=studentList.get(position).getSection();
        holder.setData(rollNo,name,section);

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rollNo,studentName,section;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rollNo=itemView.findViewById(R.id.rollNoeditStudent);
            studentName=itemView.findViewById(R.id.nameEditStudent);
            section=itemView.findViewById(R.id.sectionEditStudent);
        }
        private void setData(String roll,String name, String section1){
            rollNo.setText(roll);
            studentName.setText(name);
            section.setText(section1);
        }
    }
}
