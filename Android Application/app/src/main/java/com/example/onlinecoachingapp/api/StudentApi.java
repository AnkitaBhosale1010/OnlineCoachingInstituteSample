package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Student;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentApi {

    @GET("api/students/profile")
    Call<ApiResponse<Student>> getProfile();
}
