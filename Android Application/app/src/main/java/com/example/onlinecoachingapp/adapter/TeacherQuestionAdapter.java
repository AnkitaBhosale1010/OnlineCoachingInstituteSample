package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.QuizQuestion;

import java.util.List;

public class TeacherQuestionAdapter extends RecyclerView.Adapter<TeacherQuestionAdapter.ViewHolder> {

    private Context context;
    private List<QuizQuestion> questionList;
    private OnQuestionActionListener listener;

    public interface OnQuestionActionListener {

        void onEdit(QuizQuestion question);

        void onDelete(QuizQuestion question);

    }

    public TeacherQuestionAdapter(
            Context context,
            List<QuizQuestion> questionList,
            OnQuestionActionListener listener) {

        this.context = context;
        this.questionList = questionList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_teacher_question,
                        parent,
                        false
                );

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        QuizQuestion question =
                questionList.get(position);

        holder.txtQuestion.setText(
                question.getQuestion()
        );

        holder.txtOptionA.setText(
                "A) " + question.getOptionA()
        );

        holder.txtOptionB.setText(
                "B) " + question.getOptionB()
        );

        holder.txtOptionC.setText(
                "C) " + question.getOptionC()
        );

        holder.txtOptionD.setText(
                "D) " + question.getOptionD()
        );

        holder.txtCorrectAnswer.setText(
                "Correct Answer : " + question.getCorrectAnswer()
        );

        holder.txtMarks.setText(
                "Marks : " + question.getMarks()
        );

        holder.btnEdit.setOnClickListener(v -> {

            if (listener != null) {

                listener.onEdit(question);

            }

        });

        holder.btnDelete.setOnClickListener(v -> {

            if (listener != null) {

                listener.onDelete(question);

            }

        });

    }

    @Override
    public int getItemCount() {

        return questionList.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        TextView txtOptionA;
        TextView txtOptionB;
        TextView txtOptionC;
        TextView txtOptionD;
        TextView txtCorrectAnswer;
        TextView txtMarks;

        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtOptionA = itemView.findViewById(R.id.txtOptionA);
            txtOptionB = itemView.findViewById(R.id.txtOptionB);
            txtOptionC = itemView.findViewById(R.id.txtOptionC);
            txtOptionD = itemView.findViewById(R.id.txtOptionD);
            txtCorrectAnswer = itemView.findViewById(R.id.txtCorrectAnswer);
            txtMarks = itemView.findViewById(R.id.txtMarks);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

    }

}