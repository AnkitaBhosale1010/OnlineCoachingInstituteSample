package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Lecture;
import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder>{
    Context context;
    List<Lecture> lectureList;

    public LectureAdapter(Context context,
                          List<Lecture> lectureList) {

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

        holder.title.setText(lecture.getTitle());

        holder.description.setText(
                lecture.getDescription());

        holder.uploadDate.setText(
                "Uploaded : " + lecture.getUploadDate());

        holder.watch.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(lecture.getVideoUrl()));

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView title, description, uploadDate;
        Button watch;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            description = itemView.findViewById(R.id.txtDescription);
            uploadDate = itemView.findViewById(R.id.txtUploadDate);
            watch = itemView.findViewById(R.id.btnWatch);
        }
    }
}
