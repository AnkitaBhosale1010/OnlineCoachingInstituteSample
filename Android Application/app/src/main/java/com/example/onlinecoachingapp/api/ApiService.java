package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.AuthResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.DashboardResponse;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.model.Lecture;
import com.example.onlinecoachingapp.model.LoginRequest;
import com.example.onlinecoachingapp.model.RegisterRequest;
import com.example.onlinecoachingapp.model.StudyMaterial;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/register")
    Call<ApiResponse<AuthResponse>> register(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse<AuthResponse>> login(@Body LoginRequest request);

    @GET("api/students/dashboard/{studentId}")
    Call<DashboardResponse> getDashboard(
            @Path("studentId") Long studentId
    );

    @GET("api/courses")
    Call<ApiResponse<List<Course>>> getAllCourses();

    @GET("api/courses/{id}")
    Call<Course> getCourse(@Path("id") Long id);

    @POST("api/enrollments/student/{studentId}/course/{courseId}")
    Call<ApiResponse<Enrollment>> enrollCourse(
            @Path("studentId") Long studentId,
            @Path("courseId") Long courseId
    );

    @GET("api/enrollments/student/{studentId}")
    Call<List<Enrollment>> getMyCourses(
            @Path("studentId") Long studentId
    );

    @GET("api/lectures/course/{courseId}")
    Call<ApiResponse<List<Lecture>>> getCourseLectures(
            @Path("courseId") Long courseId
    );

    @GET("api/materials/course/{courseId}")
    Call<ApiResponse<List<StudyMaterial>>> getCourseMaterials(
            @Path("courseId") Long courseId
    );

    @GET("api/student/lectures")
    Call<List<Lecture>> getStudentLectures(
            );

    // Get assignments by course
    @GET("api/assignments/course/{courseId}")
    Call<List<Assignment>> getAssignments(
            @Path("courseId") Long courseId
    );

    // Submit Assignment
    @Multipart
    @POST("api/submissions/upload")
    Call<ApiResponse<Object>> submitAssignment(
            @Part MultipartBody.Part file,
            @Part("studentId") RequestBody studentId,
            @Part("assignmentId") RequestBody assignmentId
    );
}
