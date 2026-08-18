package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.AssignmentRequest;
import com.example.onlinecoachingapp.model.AuthResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.CourseRequest;
import com.example.onlinecoachingapp.model.DashboardResponse;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.model.Lecture;
import com.example.onlinecoachingapp.model.LectureRequest;
import com.example.onlinecoachingapp.model.LoginRequest;
import com.example.onlinecoachingapp.model.Message;
import com.example.onlinecoachingapp.model.MessageRequest;
import com.example.onlinecoachingapp.model.Quiz;
import com.example.onlinecoachingapp.model.QuizAnswer;
import com.example.onlinecoachingapp.model.QuizAttempt;
import com.example.onlinecoachingapp.model.QuizQuestion;
import com.example.onlinecoachingapp.model.QuizResult;
import com.example.onlinecoachingapp.model.QuizResultDto;
import com.example.onlinecoachingapp.model.RegisterRequest;
import com.example.onlinecoachingapp.model.StudyMaterial;
import com.example.onlinecoachingapp.model.Submission;
import com.example.onlinecoachingapp.model.TeacherDashboardResponse;

import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("api/teachers/dashboard/{teacherId}")
    Call<TeacherDashboardResponse> getTeacherDashboard(
            @Path("teacherId") Long teacherId
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
    @POST("api/submissions/assignment/{assignmentId}/student/{studentId}")
    Call<Submission> submitAssignment(
            @Path("assignmentId") Long assignmentId,
            @Path("studentId") Long studentId,
            @Part MultipartBody.Part file,
            @Part("remarks") RequestBody remarks
    );

    // Get all quizzes of a course
    @GET("api/quizzes/course/{courseId}")
    Call<List<Quiz>> getQuizzes(
            @Path("courseId") Long courseId);

    // Get all questions of a quiz
    @GET("api/quizzes/{quizId}/questions")
    Call<List<QuizQuestion>> getQuizQuestions(
            @Path("quizId") Long quizId);

    // Submit one answer
    @POST("api/quizzes/submit")
    Call<ApiResponse<QuizAttempt>> submitAnswer(
            @Body QuizAnswer quizAnswer);

    // Save final quiz result
    @POST("api/results")
    Call<ApiResponse<QuizResult>> saveQuizResult(
            @Body QuizResultDto quizResultDto);

    @GET("api/messages/chat/{user1}/{user2}")
    Call<List<Message>> getConversation(
            @Path("user1") Long user1,
            @Path("user2") Long user2);

    @POST("api/messages")
    Call<ApiResponse<Message>> sendMessage(
            @Body MessageRequest request);

    @POST("api/courses/teacher/{teacherId}")
    Call<ApiResponse<Course>> createCourse(
            @Path("teacherId") Long teacherId,
            @Body CourseRequest request
    );

    @POST("api/teachers/lecture")
    Call<ApiResponse<String>> uploadLecture(
            @Body LectureRequest request
    );

    @GET("api/teachers/{teacherId}/courses")
    Call<ApiResponse<List<Course>>> getTeacherCourses(
            @Path("teacherId") Long teacherId
    );

    @PUT("api/courses/{id}")
    Call<Course> updateCourse(
            @Path("id") Long courseId,
            @Body Course course
    );

    @Multipart
    @POST("api/materials/upload")
    Call<String> uploadStudyMaterial(

            @Part("title") RequestBody title,

            @Part("courseId") RequestBody courseId,

            @Part MultipartBody.Part file
    );

    @GET("api/materials/course/{courseId}")
    Call<ApiResponse<List<StudyMaterial>>> getMaterials(
            @Path("courseId") Long courseId
    );

    @DELETE("api/materials/{id}")
    Call<String> deleteMaterial(
            @Path("id") Long id
    );

    @POST("api/assignments/course/{courseId}")
    Call<ApiResponse<Assignment>> createAssignment(
            @Path("courseId") Long courseId,
            @Body AssignmentRequest  request
    );

    @DELETE("api/assignments/{id}")
    Call<String> deleteAssignment(

            @Path("id") Long id
    );
}
