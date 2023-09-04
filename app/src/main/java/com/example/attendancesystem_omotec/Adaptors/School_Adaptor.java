package com.example.attendancesystem_omotec.Adaptors;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.attendancesystem_omotec.MarkAttPage;
import com.example.attendancesystem_omotec.Models.School_Model;
import com.example.attendancesystem_omotec.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

public class School_Adaptor extends RecyclerView.Adapter<School_Adaptor.ViewHolder> {
    private List<School_Model> school_List;

    public School_Adaptor(List<School_Model> school_List) {
        this.school_List = school_List;
    }

    @NonNull
    @Override
    public School_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item_view, parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull School_Adaptor.ViewHolder holder, int position) {
        String schoolName = school_List.get(position).getSchool_Name();
        String schoollogo = school_List.get(position).getSchool_Logo();
        String schoollocation = school_List.get(position).getSchool_location();
        holder.logo=schoollogo;
        if (!schoollogo.equals("")) {
            Glide.with(holder.itemView.getContext()).load(schoollogo).apply(new RequestOptions().placeholder(R.drawable.baseline_school_24)).into(holder.school_logo);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.baseline_school_24).apply(new RequestOptions().placeholder(R.drawable.baseline_school_24)).into(holder.school_logo);
        }

        holder.setData(schoolName,position);



    }

    @Override
    public int getItemCount() {
        return school_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView schoolNameTextView;
        private ImageView school_logo;
        private String logo;
        private int index;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolNameTextView = itemView.findViewById(R.id.SchoolName);
            school_logo=itemView.findViewById(R.id.SchoolLogo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(itemView.getContext(), MarkAttPage.class);
                    intent.putExtra("logo",logo );
                    MarkAttPage.school= (String) schoolNameTextView.getText();
                    itemView.getContext().startActivity(intent);
                }
            });


        }


        public void setData(String SchoolName,int i) {
            schoolNameTextView.setText(SchoolName);
            index=i;
        }


    }
}
