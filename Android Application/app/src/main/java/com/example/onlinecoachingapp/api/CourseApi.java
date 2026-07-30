package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Course;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CourseApi {
    @GET("api/courses")
    Call<ApiResponse<List<Course>>>getAllCourses();
}
