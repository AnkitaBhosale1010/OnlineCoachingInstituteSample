package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Quiz;
import com.example.onlinecoachingapp.model.QuizAnswer;
import com.example.onlinecoachingapp.model.QuizAttempt;
import com.example.onlinecoachingapp.model.QuizQuestion;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuizApi {
    // Get quizzes of a course
    @GET("api/quizzes/course/{courseId}")
    Call<List<Quiz>> getCourseQuiz(
            @Path("courseId") Long courseId);

    // Get questions of a quiz
    @GET("api/quizzes/{quizId}/questions")
    Call<List<QuizQuestion>> getQuizQuestions(
            @Path("quizId") Long quizId);

    // Submit answer
    @POST("api/quizzes/submit")
    Call<ApiResponse<QuizAttempt>> submitAnswer(
            @Body QuizAnswer answer);
}
