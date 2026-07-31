package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Lecture;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LectureApi {

    @GET("api/lectures/course/{courseId}")
    Call<ApiResponse<List<Lecture>>> getLectures(
            @Path("courseId") Long courseId
    );
}
