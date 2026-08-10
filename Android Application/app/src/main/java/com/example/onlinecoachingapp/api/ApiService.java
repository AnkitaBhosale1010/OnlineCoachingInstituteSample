package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.AdminReport;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.AssignStudentBatchRequest;
import com.example.onlinecoachingapp.model.Assignment;
import com.example.onlinecoachingapp.model.AuthResponse;
import com.example.onlinecoachingapp.model.Course;
import com.example.onlinecoachingapp.model.DashboardResponse;
import com.example.onlinecoachingapp.model.Enrollment;
import com.example.onlinecoachingapp.model.Lecture;
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
import com.example.onlinecoachingapp.model.Student;
import com.example.onlinecoachingapp.model.StudyMaterial;
import com.example.onlinecoachingapp.model.Submission;
import com.example.onlinecoachingapp.model.Teacher;
import com.example.onlinecoachingapp.model.TeacherDashboardResponse;
import com.example.onlinecoachingapp.model.TeacherRequest;
import com.example.onlinecoachingapp.model.Batch;
import com.example.onlinecoachingapp.model.BatchRequest;
import com.example.onlinecoachingapp.model.BatchDto;
import com.example.onlinecoachingapp.model.BatchResponseDto;
import com.example.onlinecoachingapp.model.User;

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
    Call<ApiResponse<AuthResponse>> register(
            @Body RegisterRequest request);

    @POST("api/auth/login")
    Call<ApiResponse<AuthResponse>> login(
            @Body LoginRequest request);

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
            @Body Course course
    );

    @GET("api/courses/teacher/{teacherId}")
    Call<List<Course>> getTeacherCourses(
            @Path("teacherId") Long teacherId
    );

    @DELETE("api/courses/{id}")
    Call<ApiResponse<Void>> deleteCourse(
            @Path("id") Long courseId
    );

    @PUT("api/courses/{id}")
    Call<Course> updateCourse(
            @Path("id") Long courseId,
            @Body Course course
    );

    @GET("api/courses/{id}")
    Call<Course> getCourse(
            @Path("id") Long courseId
    );

    @POST("api/lectures/course/{courseId}")
    Call<Lecture> addLecture(
            @Path("courseId") Long courseId,
            @Body Lecture lecture
    );

    @POST("api/materials/course/{courseId}")
    Call<ApiResponse<StudyMaterial>> uploadStudyMaterial(
            @Path("courseId") Long courseId,
            @Body StudyMaterial studyMaterial
    );

    @Multipart
    @POST("api/materials/upload")
    Call<ApiResponse<StudyMaterial>> uploadStudyMaterial(

            @Part("title") RequestBody title,

            @Part("courseId") RequestBody courseId,

            @Part MultipartBody.Part file

    );

    @POST("api/assignments/course/{courseId}")
    Call<ApiResponse<Assignment>> createAssignment(
            @Path("courseId") Long courseId,
            @Body Assignment assignment
    );

    @PUT("api/assignments/{id}")
    Call<Assignment> updateAssignment(
            @Path("id") Long id,
            @Body Assignment assignment
    );

    @DELETE("api/assignments/{id}")
    Call<String> deleteAssignment(
            @Path("id") Long id
    );

    @POST("api/quizzes/course/{courseId}")
    Call<Quiz> createQuiz(
            @Path("courseId") Long courseId,
            @Body Quiz quiz
    );


// Add Question

    @POST("api/quizzes/{quizId}/questions")
    Call<ApiResponse<QuizQuestion>> addQuestion(
            @Path("quizId") Long quizId,
            @Body QuizQuestion question
    );


// Update Question

    @PUT("api/quizzes/questions/{questionId}")
    Call<QuizQuestion> updateQuestion(
            @Path("questionId") Long questionId,
            @Body QuizQuestion question
    );


// Delete Question

    @DELETE("api/quizzes/questions/{questionId}")
    Call<String> deleteQuestion(
            @Path("questionId") Long questionId);

    @PUT("api/quizzes/{quizId}")
    Call<Quiz> updateQuiz(
            @Path("quizId") Long quizId,
            @Body Quiz quiz
    );

    @DELETE("api/quizzes/{quizId}")
    Call<Void> deleteQuiz(
            @Path("quizId") Long quizId
    );

    @GET("api/enrollments/course/{courseId}")
    Call<List<Student>> getCourseStudents(
            @Path("courseId") Long courseId
    );

    @GET("api/teachers/{id}")
    Call<Teacher> getTeacherById(
            @Path("id") Long teacherId
    );

    @PUT("api/teachers/{id}")
    Call<Teacher> updateTeacher(
            @Path("id") Long teacherId,
            @Body Teacher teacher
    );

    // Get All Students
    @GET("api/students")
    Call<ApiResponse<List<Student>>> getAllStudents();

    // Delete Student
    @DELETE("api/students/{studentId}")
    Call<String> deleteStudent(
            @Path("studentId") Long studentId
    );

// ==================== TEACHERS ====================

    @GET("api/teachers")
    Call<List<Teacher>> getAllTeachers();

    @GET("api/teachers/{id}")
    Call<Teacher> getTeacher(
            @Path("id") Long teacherId
    );

    @POST("api/teachers")
    Call<ApiResponse<Teacher>> createTeacher(
            @Body TeacherRequest request
    );

    @DELETE("api/teachers/{id}")
    Call<Void> deleteTeacher(
            @Path("id") Long teacherId
    );

    // ==================== BATCH MANAGEMENT ====================

    // Get All Batches
    @GET("api/batches")
    Call<List<Batch>> getAllBatches();

    // Get Batch By ID
    @GET("api/batches/{id}")
    Call<Batch> getBatchById(
            @Path("id") Long batchId
    );

    // Create Batch
    @POST("api/batches")
    Call<Batch> createBatch(
            @Body BatchRequest request
    );

    // Update Batch
    @PUT("api/batches/{id}")
    Call<Batch> updateBatch(
            @Path("id") Long batchId,
            @Body BatchRequest request
    );

    // Delete Batch
    @DELETE("api/batches/{id}")
    Call<String> deleteBatch(
            @Path("id") Long batchId
    );

    // Assign Student To Batch
    @POST("api/batches/assign")
    Call<Student> assignStudentToBatch(
            @Body AssignStudentBatchRequest request
    );

    // Get Students Of A Batch
    @GET("api/batches/{batchId}/students")
    Call<List<Student>> getStudentsByBatch(
            @Path("batchId") Long batchId
    );

    // ==================== BATCH ====================


    @POST("api/batches")
    Call<Batch> createBatch(
            @Body Batch batch
    );

    @PUT("api/batches/{id}")
    Call<Batch> updateBatch(
            @Path("id") Long batchId,
            @Body Batch batch
    );
    @GET("api/batches/{batchId}/students")
    Call<List<Student>> getBatchStudents(
            @Path("batchId") Long batchId
    );
    @PUT("api/batches/{id}")
    Call<BatchResponseDto> updateBatch(
            @Path("id") Long id,
            @Body BatchDto batchDto
    );

    @POST("api/batches")
    Call<BatchResponseDto> createBatch(
            @Body BatchDto batchDto
    );

    @GET("api/admin/reports")
    Call<AdminReport> getAdminReport();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") Long userId);

}
