package com.example.onlinecoachingapp.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.model.StudyMaterial;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {

    private Context context;
    private List<StudyMaterial> materialList;

    public MaterialAdapter(Context context, List<StudyMaterial> materialList) {
        this.context = context;
        this.materialList = materialList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_material,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        StudyMaterial material = materialList.get(position);

        holder.txtTitle.setText(material.getTitle());

        holder.txtDate.setText(
                "Uploaded : " + material.getUploadDate()
        );

        // --------------------------
        // View PDF
        // --------------------------

        holder.btnView.setOnClickListener(v -> {

            if (material.getFileUrl() == null ||
                    material.getFileUrl().isEmpty()) {

                Toast.makeText(
                        context,
                        "PDF not available",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(material.getFileUrl())
            );

            context.startActivity(intent);

        });

        // --------------------------
        // Download PDF
        // --------------------------

        holder.btnDownload.setOnClickListener(v -> {

            if (material.getFileUrl() == null ||
                    material.getFileUrl().isEmpty()) {

                Toast.makeText(
                        context,
                        "File not found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            DownloadManager.Request request =
                    new DownloadManager.Request(
                            Uri.parse(material.getFileUrl())
                    );

            request.setTitle(material.getTitle());

            request.setDescription(
                    "Downloading Study Material..."
            );

            request.setNotificationVisibility(
                    DownloadManager.Request
                            .VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            );

            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    material.getTitle() + ".pdf"
            );

            DownloadManager manager =
                    (DownloadManager)
                            context.getSystemService(
                                    Context.DOWNLOAD_SERVICE
                            );

            if (manager != null) {

                manager.enqueue(request);

                Toast.makeText(
                        context,
                        "Download Started",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;

        MaterialButton btnView;
        MaterialButton btnDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle =
                    itemView.findViewById(R.id.txtTitle);

            txtDate =
                    itemView.findViewById(R.id.txtDate);

            btnView =
                    itemView.findViewById(R.id.btnView);

            btnDownload =
                    itemView.findViewById(R.id.btnDownload);
        }
    }
}