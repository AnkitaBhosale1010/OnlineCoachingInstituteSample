package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.activities.AssignmentActivity;
import com.example.onlinecoachingapp.activities.LectureActivity;
import com.example.onlinecoachingapp.activities.MaterialActivity;
import com.example.onlinecoachingapp.activities.QuizActivity;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.Enrollment;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyViewHolder>{

    private Context context;
    private List<Enrollment> enrollmentList;

    public MyCourseAdapter(Context context, List<Enrollment> enrollmentList) {
        this.context = context;
        this.enrollmentList = enrollmentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_my_course,
                        parent,
                        false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MyViewHolder holder,
            int position) {

        Enrollment enrollment = enrollmentList.get(position);

        Course course = enrollment.getCourse();

        holder.txtCourseName.setText(course.getTitle());

        holder.txtDuration.setText(
                "Duration : " + course.getDuration());

        holder.txtLevel.setText(
                "Level : " + course.getLevel());

        holder.txtStatus.setText(
                "Status : " + enrollment.getStatus());

        holder.btnLecture.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    LectureActivity.class);

            intent.putExtra(
                    "courseId",
                    course.getCourseId());

            context.startActivity(intent);

        });

        holder.btnMaterial.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    MaterialActivity.class);

            intent.putExtra(
                    "courseId",
                    course.getCourseId());

            context.startActivity(intent);

        });

        holder.btnAssignment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    AssignmentActivity.class);

            intent.putExtra(
                    "courseId",
                    course.getCourseId());

            context.startActivity(intent);

        });

        holder.btnQuiz.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    QuizActivity.class);

            intent.putExtra(
                    "courseId",
                    course.getCourseId());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return enrollmentList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCourseName;
        TextView txtDuration;
        TextView txtLevel;
        TextView txtStatus;

        MaterialButton btnLecture;
        MaterialButton btnMaterial;
        MaterialButton btnAssignment;
        MaterialButton btnQuiz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtLevel = itemView.findViewById(R.id.txtLevel);
            txtStatus = itemView.findViewById(R.id.txtStatus);

            btnLecture = itemView.findViewById(R.id.btnLecture);
            btnMaterial = itemView.findViewById(R.id.btnMaterial);
            btnAssignment = itemView.findViewById(R.id.btnAssignment);
            btnQuiz = itemView.findViewById(R.id.btnQuiz);
        }
    }
}
