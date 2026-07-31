package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Attendance;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttendanceApi {

    @GET("api/attendance/student/{studentId}")
    Call<ApiResponse<List<Attendance>>> getAttendance(
            @Path("studentId") Long studentId);
}
