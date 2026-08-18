package com.example.onlinecoachingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Course;

import java.util.List;

public class SelectCourseAdapter extends RecyclerView.Adapter<SelectCourseAdapter.ViewHolder>{
    private Context context;
    private List<Course> courseList;
    private OnCourseClickListener listener;

    public interface OnCourseClickListener{
        void onClick(Course course);

    }

    public SelectCourseAdapter(
            Context context,
            List<Course> courseList,
            OnCourseClickListener listener) {

        this.context = context;
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){

        View view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.item_select_course,
                                parent,
                                false
                        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position){

        Course course =
                courseList.get(position);

        holder.txtTitle
                .setText(course.getTitle());

        holder.txtDescription
                .setText(course.getDescription());

        holder.itemView.setOnClickListener(v -> {

            if(listener!=null){
                listener.onClick(course);
            }
        });

    }

    @Override
    public int getItemCount(){
        return courseList.size();
    }

    static class ViewHolder
            extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDescription;

        public ViewHolder(
                @NonNull View itemView){
            super(itemView);

            txtTitle =
                    itemView.findViewById(
                            R.id.txtTitle
                    );

            txtDescription =
                    itemView.findViewById(
                            R.id.txtDescription
                    );
        }

    }

}