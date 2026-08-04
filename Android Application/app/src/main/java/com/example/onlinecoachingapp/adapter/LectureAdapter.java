package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Lecture;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {

    private Context context;
    private List<Lecture> lectureList;

    public LectureAdapter(Context context, List<Lecture> lectureList) {
        this.context = context;
        this.lectureList = lectureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_lecture,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Lecture lecture = lectureList.get(position);

        holder.txtTitle.setText(lecture.getTitle());

        holder.txtDescription.setText(lecture.getDescription());

        holder.txtOrder.setText(
                "Lecture " + lecture.getLectureOrder());

        holder.txtDate.setText(
                "Uploaded : " + lecture.getUploadDate());

//        holder.btnWatch.setOnClickListener(v -> {
//
//            String videoUrl = lecture.getVideoUrl();
//
//            if (videoUrl == null || videoUrl.isEmpty()) {
//
//                Toast.makeText(
//                        context,
//                        "Video not available",
//                        Toast.LENGTH_SHORT
//                ).show();
//
//                return;
//            }
//
//            try {
//
//                Intent intent = new Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(videoUrl));
//
//                context.startActivity(intent);
//
//            } catch (Exception e) {
//
//                Toast.makeText(
//                        context,
//                        "Unable to open video",
//                        Toast.LENGTH_SHORT
//                ).show();
//            }
//        });

        holder.btnWatch.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(lecture.getVideoUrl())
            );

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        TextView txtOrder;
        TextView txtDate;

        MaterialButton btnWatch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtLectureTitle);

            txtDescription = itemView.findViewById(R.id.txtLectureDescription);

            txtOrder = itemView.findViewById(R.id.txtLectureOrder);

            txtDate = itemView.findViewById(R.id.txtUploadDate);

            btnWatch = itemView.findViewById(R.id.btnWatchLecture);
        }
    }
}