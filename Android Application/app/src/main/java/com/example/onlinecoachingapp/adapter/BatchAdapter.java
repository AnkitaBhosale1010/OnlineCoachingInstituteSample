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
import com.example.onlinecoachingapp.model.Batch;

import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> {

    private final Context context;
    private final List<Batch> batchList;
    private final OnBatchActionListener listener;


    public BatchAdapter(
            Context context,
            List<Batch> batchList,
            OnBatchActionListener listener) {

        this.context = context;
        this.batchList = batchList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {


        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_admin_batch,
                        parent,
                        false
                );


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {


        Batch batch = batchList.get(position);


        holder.txtBatchName.setText(
                "Batch : " + batch.getBatchName()
        );


        holder.txtTrainerName.setText(
                "Trainer : " + batch.getTrainerName()
        );


        holder.txtStartDate.setText(
                "Start Date : " + batch.getStartDate()
        );


        holder.txtEndDate.setText(
                "End Date : " + batch.getEndDate()
        );


        holder.txtStatus.setText(
                "Status : " + batch.getStatus()
        );


        holder.btnEdit.setOnClickListener(v -> {

            if(listener != null){
                listener.onEdit(batch);
            }

        });


        holder.btnDelete.setOnClickListener(v -> {

            if(listener != null){
                listener.onDelete(batch);
            }

        });


        holder.btnStudents.setOnClickListener(v -> {

            if(listener != null){
                listener.onViewStudents(batch);
            }

        });

    }

    @Override
    public int getItemCount() {

        return batchList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtBatchName;
        TextView txtTrainerName;
        TextView txtStartDate;
        TextView txtEndDate;
        TextView txtStatus;


        Button btnEdit;
        Button btnDelete;
        Button btnStudents;



        public ViewHolder(
                @NonNull View itemView) {

            super(itemView);


            txtBatchName =
                    itemView.findViewById(
                            R.id.txtBatchName
                    );


            txtTrainerName =
                    itemView.findViewById(
                            R.id.txtTrainerName
                    );


            txtStartDate =
                    itemView.findViewById(
                            R.id.txtStartDate
                    );


            txtEndDate =
                    itemView.findViewById(
                            R.id.txtEndDate
                    );


            txtStatus =
                    itemView.findViewById(
                            R.id.txtStatus
                    );


            btnEdit =
                    itemView.findViewById(
                            R.id.btnEditBatch
                    );


            btnDelete =
                    itemView.findViewById(
                            R.id.btnDeleteBatch
                    );


            btnStudents =
                    itemView.findViewById(
                            R.id.btnViewStudents
                    );

        }
    }


    public interface OnBatchActionListener {

        void onEdit(Batch batch);

        void onDelete(Batch batch);

        void onViewStudents(Batch batch);

    }

}