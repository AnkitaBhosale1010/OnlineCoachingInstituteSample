package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Assignment;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private Context context;
    private List<Assignment> assignmentList;
    private ActivityResultLauncher<Intent> filePickerLauncher;

    private Long selectedAssignmentId;

    public AssignmentAdapter(Context context,
                             List<Assignment> assignmentList,
                             ActivityResultLauncher<Intent> filePickerLauncher) {

        this.context = context;
        this.assignmentList = assignmentList;
        this.filePickerLauncher = filePickerLauncher;
    }

    public Long getSelectedAssignmentId() {
        return selectedAssignmentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_assignment,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Assignment assignment = assignmentList.get(position);

        holder.txtTitle.setText(assignment.getTitle());

        holder.txtDescription.setText(assignment.getDescription());

        holder.txtDueDate.setText("Due : " + assignment.getDeadline());

        /*
         * Download Assignment
         */

        holder.btnDownload.setOnClickListener(v -> {

            if (assignment.getFileUrl() == null ||
                    assignment.getFileUrl().isEmpty()) {

                return;
            }

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(assignment.getFileUrl()));

            context.startActivity(intent);

        });

        /*
         * Submit Assignment
         */

        holder.btnSubmit.setOnClickListener(v -> {

            selectedAssignmentId = assignment.getAssignmentId();

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

            intent.addCategory(Intent.CATEGORY_OPENABLE);

            intent.setType("*/*");

            filePickerLauncher.launch(intent);

        });

    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        TextView txtDueDate;

        Button btnDownload;
        Button btnSubmit;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);

            btnDownload = itemView.findViewById(R.id.btnDownload);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
        }
    }
}