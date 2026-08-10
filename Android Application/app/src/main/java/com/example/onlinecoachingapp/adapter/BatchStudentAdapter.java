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

public class BatchStudentAdapter extends RecyclerView.Adapter<BatchStudentAdapter.ViewHolder> {

    private Context context;
    private List<Student> studentList;

    public BatchStudentAdapter(
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

        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_batch_student,
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


        if(student.getUser() != null){

            holder.txtStudentName.setText(
                    "Name : " + student.getUser().getName()
            );

            holder.txtEmail.setText(
                    "Email : " + student.getUser().getEmail()
            );

        }
        else{

            holder.txtStudentName.setText(
                    "Name : N/A"
            );

            holder.txtEmail.setText(
                    "Email : N/A"
            );
        }


        holder.txtPhone.setText(
                "Phone : " + student.getPhone()
        );


    }


    @Override
    public int getItemCount() {

        return studentList.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtStudentName;
        TextView txtEmail;
        TextView txtPhone;


        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);


            txtStudentName =
                    itemView.findViewById(
                            R.id.txtStudentName
                    );


            txtEmail =
                    itemView.findViewById(
                            R.id.txtStudentEmail
                    );


            txtPhone =
                    itemView.findViewById(
                            R.id.txtStudentPhone
                    );

        }
    }
}