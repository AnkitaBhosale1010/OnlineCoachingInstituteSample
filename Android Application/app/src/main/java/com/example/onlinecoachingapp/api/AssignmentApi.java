package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.Assignment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AssignmentApi {

    @GET("api/assignments/course/{courseId}")
    Call<List<Assignment>> getAssignments(
            @Path("courseId") Long courseId
    );
}
