package com.example.onlinecoachingapp.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.Course;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCourseDialog {

    public interface RefreshListener{
        void onRefresh();
    }

    public static void show(Context context,
                            Course course,
                            ApiService apiService,
                            RefreshListener listener){

        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_edit_course,null);

        EditText etTitle=view.findViewById(R.id.etTitle);
        EditText etDescription=view.findViewById(R.id.etDescription);
        EditText etDuration=view.findViewById(R.id.etDuration);
        EditText etLevel=view.findViewById(R.id.etLevel);
        EditText etPrice=view.findViewById(R.id.etPrice);

        etTitle.setText(course.getTitle());
        etDescription.setText(course.getDescription());
        etDuration.setText(course.getDuration());
        etLevel.setText(course.getLevel());
        etPrice.setText(course.getPrice().toString());

        AlertDialog dialog=new AlertDialog.Builder(context)
                .setView(view)
                .create();

        view.findViewById(R.id.btnUpdate).setOnClickListener(v->{

            course.setTitle(etTitle.getText().toString());
            course.setDescription(etDescription.getText().toString());
            course.setDuration(etDuration.getText().toString());
            course.setLevel(etLevel.getText().toString());
            course.setPrice(new BigDecimal(
                    etPrice.getText().toString()));

            apiService.updateCourse(course.getCourseId(),course)
                    .enqueue(new Callback<Course>() {

                        @Override
                        public void onResponse(Call<Course> call,
                                               Response<Course> response) {

                            Toast.makeText(context,
                                    "Course Updated",
                                    Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                            listener.onRefresh();

                        }

                        @Override
                        public void onFailure(Call<Course> call,
                                              Throwable t) {

                            Toast.makeText(context,
                                    t.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

        });

        dialog.show();

    }

}