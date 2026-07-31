package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.AuthResponse;
import com.example.onlinecoachingapp.model.Batch;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.LoginRequest;
import com.example.onlinecoachingapp.model.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse<AuthResponse>> register(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse<AuthResponse>> login(@Body LoginRequest request);

}
