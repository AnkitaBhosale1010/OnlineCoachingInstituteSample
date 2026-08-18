package com.example.onlinecoachingapp.adapter;

import android.app.AlertDialog;
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

public class TeacherCourseAdapter extends RecyclerView.Adapter<TeacherCourseAdapter.ViewHolder> {

    public void updateList(List<Course> list) {
        this.courseList = list;
        notifyDataSetChanged();
    }

    public interface OnCourseActionListener{

        void onEdit(Course course);

        void onDelete(Course course);

    }

    private Context context;
    private List<Course> courseList;
    private OnCourseActionListener listener;

    public TeacherCourseAdapter(Context context,
                                List<Course> courseList,
                                OnCourseActionListener listener){

        this.context=context;
        this.courseList=courseList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view= LayoutInflater.from(context)
                .inflate(R.layout.item_teacher_course,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Course course=courseList.get(position);

        holder.txtTitle.setText(course.getTitle());

        holder.txtDescription.setText(course.getDescription());

        holder.txtDuration.setText(course.getDuration());

        holder.txtLevel.setText(course.getLevel());

        holder.txtPrice.setText("₹ "+course.getPrice());

        holder.btnEdit.setOnClickListener(v->{

            listener.onEdit(course);

        });

        holder.btnDelete.setOnClickListener(v->{

            new AlertDialog.Builder(context)
                    .setTitle("Delete Course")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes",(dialog,which)->{

                        listener.onDelete(course);

                    })
                    .setNegativeButton("No",null)
                    .show();

        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtDescription,
                txtDuration,txtLevel,txtPrice;

        Button btnEdit,btnDelete;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtDescription=itemView.findViewById(R.id.txtDescription);
            txtDuration=itemView.findViewById(R.id.txtDuration);
            txtLevel=itemView.findViewById(R.id.txtLevel);
            txtPrice=itemView.findViewById(R.id.txtPrice);

            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDelete=itemView.findViewById(R.id.btnDelete);

        }

    }

}