package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private final Context context;
    private final List<Teacher> teacherList;
    private final OnTeacherActionListener listener;

    public TeacherAdapter(Context context,
                          List<Teacher> teacherList,
                          OnTeacherActionListener listener) {

        this.context = context;
        this.teacherList = teacherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_teacher,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Teacher teacher = teacherList.get(position);

        if (teacher.getUser() != null) {

            holder.txtName.setText(
                    teacher.getUser().getName());

            holder.txtEmail.setText(
                    teacher.getUser().getEmail());

        } else {

            holder.txtName.setText("N/A");
            holder.txtEmail.setText("N/A");

        }

        holder.txtPhone.setText(
                "Phone : " + teacher.getPhone());

        holder.txtQualification.setText(
                "Qualification : " + teacher.getQualification());

        holder.txtExpertise.setText(
                "Expertise : " + teacher.getExpertise());

        holder.txtJoinDate.setText(
                "Join Date : " + teacher.getJoinDate());

        holder.btnEdit.setOnClickListener(v -> {

            if (listener != null) {
                listener.onEdit(teacher);
            }

        });

        holder.btnDelete.setOnClickListener(v -> {

            if (listener != null) {
                listener.onDelete(teacher);
            }

        });

    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtEmail;
        TextView txtPhone;
        TextView txtQualification;
        TextView txtExpertise;
        TextView txtJoinDate;

        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtQualification = itemView.findViewById(R.id.txtQualification);
            txtExpertise = itemView.findViewById(R.id.txtExpertise);
            txtJoinDate = itemView.findViewById(R.id.txtJoinDate);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnTeacherActionListener {

        void onEdit(Teacher teacher);

        void onDelete(Teacher teacher);

    }

}