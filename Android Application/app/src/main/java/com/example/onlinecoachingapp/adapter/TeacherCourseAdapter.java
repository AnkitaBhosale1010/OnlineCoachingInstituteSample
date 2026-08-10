package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Course;

import java.util.List;

public class TeacherCourseAdapter extends RecyclerView.Adapter<TeacherCourseAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courseList;
    private OnCourseActionListener listener;

    public TeacherCourseAdapter(Context context,
                                List<Course> courseList,
                                OnCourseActionListener listener) {

        this.context = context;
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_teacher_course, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        Course course = courseList.get(position);

        holder.txtTitle.setText(course.getTitle());

        holder.txtDescription.setText(course.getDescription());

        holder.txtDuration.setText("Duration : " + course.getDuration());

        holder.txtLevel.setText(course.getLevel());

        holder.txtPrice.setText("₹ " + course.getPrice());

        holder.itemView.setOnClickListener(v -> {

            if(listener!=null)
                listener.onView(course);

        });

        holder.btnMenu.setOnClickListener(v -> showPopup(v,course));

    }

    @Override
    public int getItemCount() {

        return courseList.size();

    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtDescription,txtDuration,txtLevel,txtPrice;

        ImageButton btnMenu;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle=itemView.findViewById(R.id.txtTitle);

            txtDescription=itemView.findViewById(R.id.txtDescription);

            txtDuration=itemView.findViewById(R.id.txtDuration);

            txtLevel=itemView.findViewById(R.id.txtLevel);

            txtPrice=itemView.findViewById(R.id.txtPrice);

            btnMenu=itemView.findViewById(R.id.btnMenu);

        }
    }

    private void showPopup(View view,Course course){

        PopupMenu popupMenu=new PopupMenu(context,view);

        MenuInflater inflater=popupMenu.getMenuInflater();

        inflater.inflate(R.menu.menu_course,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {

            int id=item.getItemId();

            if(id==R.id.menuView){

                listener.onView(course);

                return true;

            }

            if(id==R.id.menuEdit){

                listener.onEdit(course);

                return true;

            }

            if(id==R.id.menuDelete){

                listener.onDelete(course);

                return true;

            }

            return false;

        });

        popupMenu.show();

    }

    public interface OnCourseActionListener{

        void onView(Course course);

        void onEdit(Course course);

        void onDelete(Course course);

    }

}