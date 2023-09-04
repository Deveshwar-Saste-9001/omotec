package com.example.attendancesystem_omotec.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_omotec.Models.DailyScheduleModel;
import com.example.attendancesystem_omotec.R;

import java.util.List;

public class DailyScheduleAdaptor extends RecyclerView.Adapter<DailyScheduleAdaptor.viewHolder> {
    private List<DailyScheduleModel> dailyScheduleModelList;

    public DailyScheduleAdaptor(List<DailyScheduleModel> dailyScheduleModelList) {
        this.dailyScheduleModelList = dailyScheduleModelList;
    }

    @NonNull
    @Override
    public DailyScheduleAdaptor.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_schedule_itemview, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyScheduleAdaptor.viewHolder holder, int position) {

        String School = dailyScheduleModelList.get(position).getSchool();
        String Trainer1 = dailyScheduleModelList.get(position).getTrainer1();
        String Trainer2 = dailyScheduleModelList.get(position).getTrainer2();
        holder.setData(School, Trainer1, Trainer2);

    }

    @Override
    public int getItemCount() {
        return dailyScheduleModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private TextView schoolTextView;
        private TextView Trainer;
        private Button editBtn, viewMoreBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            schoolTextView = itemView.findViewById(R.id.schoolnameDaily);
            Trainer = itemView.findViewById(R.id.TrainersnameDaily);
            editBtn = itemView.findViewById(R.id.editMorebtnDaily);
            viewMoreBtn = itemView.findViewById(R.id.viewMoreBtnDaily);
        }

        public void setData(String School, String Trainer1, String Trainer2) {

            schoolTextView.setText(School);
            Trainer.setText(Trainer1 + " , " + Trainer2);
        }
    }
}
