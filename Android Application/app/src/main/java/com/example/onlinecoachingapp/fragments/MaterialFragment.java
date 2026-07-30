package com.example.onlinecoachingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.MaterialAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.MaterialApi;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.StudyMaterial;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialFragment extends Fragment {

    RecyclerView recyclerView;
    MaterialAdapter adapter;
    List<StudyMaterial> materialList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_material,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerMaterial);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        materialList = new ArrayList<>();

        adapter = new MaterialAdapter(
                getContext(),
                materialList);

        recyclerView.setAdapter(adapter);

        loadMaterials();

        return view;
    }

    private void loadMaterials() {

        Long courseId = 1L;

        MaterialApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(MaterialApi.class);

        api.getMaterials(courseId)
                .enqueue(new Callback<ApiResponse<List<StudyMaterial>>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Response<ApiResponse<List<StudyMaterial>>> response) {

                        Log.e("MATERIAL", "Response Code = " + response.code());

                        if(response.body() != null){
                            Log.e("MATERIAL", "Success = " + response.body().isSuccess());
                            Log.e("MATERIAL", "Message = " + response.body().getMessage());

                            if(response.body().getData() != null){
                                Log.e("MATERIAL", "Size = " + response.body().getData().size());
                            }else{
                                Log.e("MATERIAL", "Data is NULL");
                            }
                        }

                        if(response.isSuccessful()
                                && response.body()!=null
                                && response.body().isSuccess()){

                            materialList.clear();
                            materialList.addAll(response.body().getData());

                            Log.e("MATERIAL","Recycler Size = "+materialList.size());

                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(
                            Call<ApiResponse<List<StudyMaterial>>> call,
                            Throwable t) {

                        Log.e("MATERIAL",
                                t.getMessage(),
                                t);
                    }
                });
    }
}