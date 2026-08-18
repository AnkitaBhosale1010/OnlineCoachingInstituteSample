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
import com.example.onlinecoachingapp.model.Student;

import java.util.List;

public class AdminStudentAdapter extends RecyclerView.Adapter<AdminStudentAdapter.ViewHolder> {

    private final Context context;
    private final List<Student> studentList;
    private final OnStudentActionListener listener;

    public AdminStudentAdapter(Context context,
                               List<Student> studentList,
                               OnStudentActionListener listener) {

        this.context = context;
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_admin_student,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Student student = studentList.get(position);

        if (student.getUser() != null) {

            holder.txtStudentName.setText(
                    student.getUser().getName());

            holder.txtStudentEmail.setText(
                    student.getUser().getEmail());

        } else {

            holder.txtStudentName.setText("N/A");
            holder.txtStudentEmail.setText("N/A");

        }

        holder.txtStudentPhone.setText(
                "Phone : " + student.getPhone());

        holder.txtStudentJoinDate.setText(
                "Join Date : " + student.getJoinDate());

        holder.btnDeleteStudent.setOnClickListener(v -> {

            if (listener != null) {
                listener.onDelete(student);
            }

        });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentName;
        TextView txtStudentEmail;
        TextView txtStudentPhone;
        TextView txtStudentJoinDate;

        Button btnDeleteStudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentName = itemView.findViewById(R.id.txtStudentName);
            txtStudentEmail = itemView.findViewById(R.id.txtStudentEmail);
            txtStudentPhone = itemView.findViewById(R.id.txtStudentPhone);
            txtStudentJoinDate = itemView.findViewById(R.id.txtStudentJoinDate);

            btnDeleteStudent = itemView.findViewById(R.id.btnDeleteStudent);
        }
    }

    public interface OnStudentActionListener {
        void onDelete(Student student);
    }

}