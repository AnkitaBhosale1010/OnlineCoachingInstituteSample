package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Assignment;

import java.util.List;

import android.widget.Button;
import android.widget.TextView;

public class TeacherAssignmentAdapter extends
        RecyclerView.Adapter<TeacherAssignmentAdapter.ViewHolder> {

    private Context context;

    private List<Assignment> assignmentList;

    private OnAssignmentActionListener listener;

    public interface OnAssignmentActionListener {

        void onEdit(Assignment assignment);

        void onDelete(Assignment assignment);

    }

    public TeacherAssignmentAdapter(
            Context context,
            List<Assignment> assignmentList,
            OnAssignmentActionListener listener) {

        this.context = context;
        this.assignmentList = assignmentList;
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
                        R.layout.item_teacher_assignment,
                        parent,
                        false
                );

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Assignment assignment = assignmentList.get(position);

        holder.txtTitle.setText(
                assignment.getTitle()
        );

        holder.txtDescription.setText(
                assignment.getDescription()
        );

        holder.txtDeadline.setText(
                "Deadline : " + assignment.getDeadline()
        );

        holder.txtMarks.setText(
                "Total Marks : " + assignment.getTotalMarks()
        );

        holder.btnEdit.setOnClickListener(v -> {

            if (listener != null) {

                listener.onEdit(assignment);

            }

        });

        holder.btnDelete.setOnClickListener(v -> {

            if (listener != null) {

                listener.onDelete(assignment);

            }

        });

    }

    @Override
    public int getItemCount() {

        return assignmentList.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        TextView txtDeadline;
        TextView txtMarks;

        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDeadline = itemView.findViewById(R.id.txtDeadline);
            txtMarks = itemView.findViewById(R.id.txtMarks);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}