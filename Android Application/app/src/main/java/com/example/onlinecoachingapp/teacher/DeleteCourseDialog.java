package com.example.onlinecoachingapp.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCourseDialog {

    public interface RefreshListener{
        void onRefresh();
    }

    public static void show(Context context,
                            ApiService apiService,
                            Long courseId,
                            RefreshListener listener){

        new AlertDialog.Builder(context)

                .setTitle("Delete Course")

                .setMessage("Are you sure?")

                .setPositiveButton("Delete",(d,i)->{

                    apiService.deleteCourse(courseId)
                            .enqueue(new Callback<ApiResponse<Void>>() {

                                @Override
                                public void onResponse(Call<ApiResponse<Void>> call,
                                                       Response<ApiResponse<Void>> response) {

                                    Toast.makeText(context,
                                            "Course Deleted",
                                            Toast.LENGTH_SHORT).show();

                                    listener.onRefresh();

                                }

                                @Override
                                public void onFailure(Call<ApiResponse<Void>> call,
                                                      Throwable t) {

                                    Toast.makeText(context,
                                            t.getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            });

                })

                .setNegativeButton("Cancel",null)

                .show();

    }

}