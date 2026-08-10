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
import com.example.onlinecoachingapp.model.Course;

import java.util.List;

public class AdminCourseAdapter extends RecyclerView.Adapter<AdminCourseAdapter.ViewHolder> {

    private final Context context;
    private final List<Course> courseList;
    private final OnCourseActionListener listener;

    public interface OnCourseActionListener {
        void onEdit(Course course);
        void onDelete(Course course);
    }

    public AdminCourseAdapter(Context context,
                              List<Course> courseList,
                              OnCourseActionListener listener) {

        this.context = context;
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_admin_course,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Course course = courseList.get(position);

        holder.txtTitle.setText(course.getTitle());

        holder.txtDescription.setText(course.getDescription());

        holder.txtDuration.setText(
                "Duration : " + course.getDuration());

        holder.txtLevel.setText(
                "Level : " + course.getLevel());

        holder.txtPrice.setText(
                "Price : ₹" + course.getPrice());

        holder.btnEdit.setOnClickListener(v ->
                listener.onEdit(course));

        holder.btnDelete.setOnClickListener(v ->
                listener.onDelete(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        TextView txtDuration;
        TextView txtLevel;
        TextView txtPrice;

        Button btnEdit;
        Button btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtLevel = itemView.findViewById(R.id.txtLevel);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}