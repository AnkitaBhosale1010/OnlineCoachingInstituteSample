package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.activities.QuestionActivity;
import com.example.onlinecoachingapp.model.Quiz;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private Context context;
    private List<Quiz> quizList;

    public QuizAdapter(Context context, List<Quiz> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_quiz, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Quiz quiz = quizList.get(position);

        holder.txtTitle.setText(quiz.getTitle());
        holder.txtDescription.setText(quiz.getDescription());
        holder.txtMarks.setText("Marks : " + quiz.getTotalMarks());

        holder.btnStartQuiz.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    QuestionActivity.class);

            intent.putExtra("quizId", quiz.getQuizId());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription;
        TextView txtMarks;
        Button btnStartQuiz;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtQuizTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtMarks = itemView.findViewById(R.id.txtMarks);
            btnStartQuiz = itemView.findViewById(R.id.btnStartQuiz);
        }
    }
}