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
import com.example.onlinecoachingapp.model.Quiz;

import java.util.List;

public class TeacherQuizAdapter extends RecyclerView.Adapter<TeacherQuizAdapter.ViewHolder> {

    private Context context;
    private List<Quiz> quizList;
    private OnQuizActionListener listener;

    public TeacherQuizAdapter(
            Context context,
            List<Quiz> quizList,
            OnQuizActionListener listener) {

        this.context = context;
        this.quizList = quizList;
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
                        R.layout.item_teacher_quiz,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Quiz quiz = quizList.get(position);

        holder.txtTitle.setText(
                quiz.getTitle()
        );

        holder.txtDescription.setText(
                quiz.getDescription()
        );

        holder.txtMarks.setText(
                "Total Marks : " + quiz.getTotalMarks()
        );

        holder.txtDate.setText(
                "Date : " + quiz.getQuizDate()
        );

        holder.btnQuestions.setOnClickListener(v -> {

            if(listener != null){
                listener.onQuestions(quiz);
            }

        });

        holder.btnEdit.setOnClickListener(v -> {

            if(listener != null){
                listener.onEdit(quiz);
            }

        });

        holder.btnDelete.setOnClickListener(v -> {

            if(listener != null){
                listener.onDelete(quiz);
            }

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
        TextView txtDate;

        Button btnQuestions;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtMarks = itemView.findViewById(R.id.txtMarks);
            txtDate = itemView.findViewById(R.id.txtDate);

            btnQuestions = itemView.findViewById(R.id.btnQuestions);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnQuizActionListener {

        void onQuestions(Quiz quiz);

        void onEdit(Quiz quiz);

        void onDelete(Quiz quiz);

    }

}