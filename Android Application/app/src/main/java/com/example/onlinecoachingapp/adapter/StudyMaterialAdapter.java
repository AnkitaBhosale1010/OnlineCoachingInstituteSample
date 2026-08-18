package com.example.onlinecoachingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.StudyMaterial;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyMaterialAdapter
        extends RecyclerView.Adapter<StudyMaterialAdapter.ViewHolder>{

    private Context context;

    private List<StudyMaterial> materialList;

    ApiService apiService;

    public StudyMaterialAdapter(
            Context context,
            List<StudyMaterial> materialList){


        this.context=context;

        this.materialList=materialList;


        apiService =
                ApiClient.getRetrofitInstance(context)
                        .create(ApiService.class);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType){


        View view =
                LayoutInflater.from(context)
                        .inflate(
                                R.layout.item_study_material,
                                parent,
                                false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position){



        StudyMaterial material =
                materialList.get(position);



        holder.txtTitle
                .setText(material.getTitle());



        holder.txtDate
                .setText(
                        "Uploaded : "
                                +material.getUploadDate()
                );



        holder.txtFile
                .setText(
                        "File : "
                                +material.getFileUrl()
                );


        holder.btnDelete
                .setOnClickListener(v->{


                    deleteMaterial(
                            material.getMaterialId(),
                            position);


                });
    }

    private void deleteMaterial(
            Long materialId,
            int position){



        apiService.deleteMaterial(materialId)
                .enqueue(new Callback<String>() {


                    @Override
                    public void onResponse(
                            Call<String> call,
                            Response<String> response){



                        if(response.isSuccessful()){



                            materialList.remove(position);


                            notifyItemRemoved(position);



                            Toast.makeText(
                                            context,
                                            "Material Deleted",
                                            Toast.LENGTH_SHORT)
                                    .show();



                        }


                    }



                    @Override
                    public void onFailure(
                            Call<String> call,
                            Throwable t){


                        Toast.makeText(
                                        context,
                                        "Delete Failed : "
                                                +t.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();


                    }
                });


    }






    @Override
    public int getItemCount(){


        return materialList.size();

    }





    public static class ViewHolder
            extends RecyclerView.ViewHolder{


        TextView txtTitle;
        TextView txtDate;
        TextView txtFile;

        Button btnDelete;



        public ViewHolder(
                @NonNull View itemView){


            super(itemView);



            txtTitle =
                    itemView.findViewById(
                            R.id.txtMaterialTitle);



            txtDate =
                    itemView.findViewById(
                            R.id.txtUploadDate);



            txtFile =
                    itemView.findViewById(
                            R.id.txtFileUrl);



            btnDelete =
                    itemView.findViewById(
                            R.id.btnDeleteMaterial);


        }

    }


}