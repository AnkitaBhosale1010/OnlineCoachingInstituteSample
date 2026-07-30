package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.Batch;
import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.BatchViewHolder> {

    private Context context;
    private List<Batch> batchList;

    public BatchAdapter(Context context, List<Batch> batchList) {
        this.context = context;
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_batch, parent, false);

        return new BatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {

        Batch batch = batchList.get(position);

        holder.txtBatchName.setText(batch.getBatchName());
        holder.txtTrainer.setText(batch.getTrainerName());
        holder.txtStart.setText(batch.getStartDate());
        holder.txtEnd.setText(batch.getEndDate());
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    static class BatchViewHolder extends RecyclerView.ViewHolder {

        TextView txtBatchName, txtTrainer, txtStart, txtEnd;

        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBatchName = itemView.findViewById(R.id.txtBatchName);
            txtTrainer = itemView.findViewById(R.id.txtTrainer);
            txtStart = itemView.findViewById(R.id.txtStartDate);
            txtEnd = itemView.findViewById(R.id.txtEndDate);
        }
    }
}
