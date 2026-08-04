package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Assignment;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder>{

    Context context;
    List<Assignment> assignmentList;

    public AssignmentAdapter(Context context,List<Assignment> assignmentList){

        this.context=context;
        this.assignmentList=assignmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view= LayoutInflater.from(context)
                .inflate(R.layout.item_assignment,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){

        Assignment assignment=assignmentList.get(position);

        holder.title.setText(assignment.getTitle());
        holder.desc.setText(assignment.getDescription());
        holder.date.setText("Deadline : "+assignment.getDeadline());
        holder.upload.setText("Marks : "+assignment.getTotalMarks());

    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,desc,date,upload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.txtTitle);
            desc=itemView.findViewById(R.id.txtDescription);
            date=itemView.findViewById(R.id.txtDate);

        }
    }
}
