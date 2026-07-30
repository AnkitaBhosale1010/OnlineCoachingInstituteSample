package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.StudyMaterial;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MaterialApi {
    @GET("api/materials/course/{courseId}")
    Call<ApiResponse<List<StudyMaterial>>> getMaterials(
            @Path("courseId") Long courseId
    );
}
