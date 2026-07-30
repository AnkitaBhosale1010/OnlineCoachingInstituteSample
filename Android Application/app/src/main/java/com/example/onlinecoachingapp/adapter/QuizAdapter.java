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

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder>{
    Context context;
    List<Quiz> quizList;

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
                .inflate(R.layout.item_quiz,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Quiz quiz = quizList.get(position);

        holder.title.setText(quiz.getTitle());

        holder.marks.setText(
                "Total Marks : " + quiz.getTotalMarks());

        holder.date.setText(
                "Quiz Date : " + quiz.getQuizDate());

        holder.start.setOnClickListener(v -> {

            Intent intent =
                    new Intent(context,
                            QuestionActivity.class);

            intent.putExtra(
                    "quizId",
                    quiz.getQuizId());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, marks, date;
        Button start;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.txtQuizTitle);
            marks = itemView.findViewById(R.id.txtMarks);
            date = itemView.findViewById(R.id.txtDate);
            start = itemView.findViewById(R.id.btnStartQuiz);
        }
    }
}
