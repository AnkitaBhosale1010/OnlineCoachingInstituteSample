package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Student;

import java.util.List;

public class EnrolledStudentAdapter extends RecyclerView.Adapter<EnrolledStudentAdapter.ViewHolder> {

    private Context context;
    private List<Student> studentList;

    public EnrolledStudentAdapter(
            Context context,
            List<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.item_enrolled_student,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Student student = studentList.get(position);

        holder.txtStudentId.setText(
                "Student ID: " + student.getStudentId()
        );

        if (student.getUser() != null) {

            holder.txtStudentName.setText(
                    "Name: " + student.getUser().getName()
            );

            holder.txtStudentEmail.setText(
                    "Email: " + student.getUser().getEmail()
            );

        } else {

            holder.txtStudentName.setText(
                    "Name: Not Available"
            );

            holder.txtStudentEmail.setText(
                    "Email: Not Available"
            );
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentName;
        TextView txtStudentEmail;
        TextView txtStudentId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentName = itemView.findViewById(
                    R.id.txtStudentName
            );

            txtStudentEmail = itemView.findViewById(
                    R.id.txtStudentEmail
            );

            txtStudentId = itemView.findViewById(
                    R.id.txtStudentId
            );
        }
    }
}