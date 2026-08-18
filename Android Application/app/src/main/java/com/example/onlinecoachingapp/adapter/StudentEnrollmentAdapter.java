package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Enrollment;

import java.util.List;

public class StudentEnrollmentAdapter extends RecyclerView.Adapter<StudentEnrollmentAdapter.ViewHolder> {

    private Context context;
    private List<Enrollment> enrollmentList;

    public StudentEnrollmentAdapter(
            Context context,
            List<Enrollment> enrollmentList) {

        this.context = context;
        this.enrollmentList = enrollmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_student,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Enrollment enrollment = enrollmentList.get(position);

        if (enrollment.getStudent() != null &&
                enrollment.getStudent().getUser() != null) {

            holder.txtStudentName.setText(
                    enrollment.getStudent()
                            .getUser()
                            .getName()
            );

            holder.txtEmail.setText(
                    "Email : " +
                            enrollment.getStudent()
                                    .getUser()
                                    .getEmail()
            );

            holder.txtPhone.setText(
                    "Phone : " +
                            enrollment.getStudent()
                                    .getPhone()
            );

        } else {

            holder.txtStudentName.setText("Student");
            holder.txtEmail.setText("Email : -");
            holder.txtPhone.setText("Phone : -");

        }

        holder.txtStatus.setText(
                "Status : " + enrollment.getStatus()
        );

        holder.txtEnrollDate.setText(
                "Enrolled : " + enrollment.getEnrollDate()
        );

    }

    @Override
    public int getItemCount() {
        return enrollmentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentName;
        TextView txtEmail;
        TextView txtPhone;
        TextView txtStatus;
        TextView txtEnrollDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentName = itemView.findViewById(R.id.txtStudentName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtEnrollDate = itemView.findViewById(R.id.txtEnrollDate);
        }
    }
}