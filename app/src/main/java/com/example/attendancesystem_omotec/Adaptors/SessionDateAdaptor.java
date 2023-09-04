package com.example.attendancesystem_omotec.Adaptors;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem_omotec.Models.SessionDateModel;
import com.example.attendancesystem_omotec.R;
import com.example.attendancesystem_omotec.ViewPresentStudentActivity;

import java.util.List;

public class SessionDateAdaptor extends RecyclerView.Adapter<SessionDateAdaptor.viewHolder> {
    private List<SessionDateModel> sessionDateModelList;
private String school="";

    public SessionDateAdaptor(List<SessionDateModel> sessionDateModelList,String school) {
        this.sessionDateModelList = sessionDateModelList;
        this.school=school;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_date_view, parent, false);

        return new SessionDateAdaptor.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String date = sessionDateModelList.get(position).getDate();
        holder.dateTextView.setText(date);


    }

    @Override
    public int getItemCount() {
        return sessionDateModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView=itemView.findViewById(R.id.attendanceDateSelector);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(itemView.getContext(), ViewPresentStudentActivity.class);
                    intent.putExtra("school",school);
                    intent.putExtra("date",dateTextView.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
