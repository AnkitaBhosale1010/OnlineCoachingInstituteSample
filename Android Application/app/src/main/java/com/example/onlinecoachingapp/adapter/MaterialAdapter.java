package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.StudyMaterial;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder>{

    Context context;
    List<StudyMaterial> materialList;

    public MaterialAdapter(Context context, List<StudyMaterial> materialList) {

        this.context = context;
        this.materialList = materialList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_material,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        StudyMaterial material = materialList.get(position);

        holder.title.setText(material.getTitle());

        holder.uploadDate.setText(
                "Uploaded : " + material.getUploadDate());

        holder.open.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse(material.getFileUrl()));

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {

        return materialList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, uploadDate;

        Button open;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            uploadDate = itemView.findViewById(R.id.txtUploadDate);
            open = itemView.findViewById(R.id.btnOpen);
        }
    }
}
