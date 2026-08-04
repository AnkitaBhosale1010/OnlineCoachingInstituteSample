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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context context;
    private List<Course> courseList;
    private OnEnrollClickListener listener;

    public interface OnEnrollClickListener {
        void onEnrollClick(Course course);
    }
    public CourseAdapter(
            Context context,
            List<Course> courseList,
            OnEnrollClickListener listener)
    {
        this.context = context;
        this.courseList = courseList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {


        View view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.item_course,
                                parent,
                                false
                        );


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {


        Course course =
                courseList.get(position);



        holder.txtTitle.setText(
                course.getTitle()
        );


        holder.txtDescription.setText(
                course.getDescription()
        );


        holder.txtDuration.setText(
                "Duration : "
                        +
                        course.getDuration()
        );


        holder.txtLevel.setText(
                "Level : "
                        +
                        course.getLevel()
        );


        holder.txtPrice.setText(
                "Price : ₹"
                        +
                        course.getPrice()
        );



        holder.btnEnroll.setOnClickListener(v -> {


            listener.onEnrollClick(course);


        });



    }





    @Override
    public int getItemCount() {

        return courseList.size();

    }







    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle;
        TextView txtDescription;
        TextView txtDuration;
        TextView txtLevel;
        TextView txtPrice;

        Button btnEnroll;



        public ViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);


            txtTitle =
                    itemView.findViewById(
                            R.id.txtCourseTitle
                    );


            txtDescription =
                    itemView.findViewById(
                            R.id.txtCourseDescription
                    );


            txtDuration =
                    itemView.findViewById(
                            R.id.txtDuration
                    );


            txtLevel =
                    itemView.findViewById(
                            R.id.txtLevel
                    );


            txtPrice =
                    itemView.findViewById(
                            R.id.txtPrice
                    );


            btnEnroll =
                    itemView.findViewById(
                            R.id.btnEnroll
                    );

        }

    }

}