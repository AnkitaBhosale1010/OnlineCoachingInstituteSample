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
import com.example.onlinecoachingapp.adapter.BatchAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.BatchApi;
import com.example.onlinecoachingapp.model.Batch;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchFragment extends Fragment {

    RecyclerView recyclerView;
    BatchAdapter adapter;
    List<Batch> batchList;

    public BatchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_batch, container, false);

        recyclerView = view.findViewById(R.id.recyclerBatch);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        batchList = new ArrayList<>();

        adapter = new BatchAdapter(getContext(), batchList);

        recyclerView.setAdapter(adapter);
        loadBatches();
        return view;
    }
    private void loadBatches() {

        BatchApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(BatchApi.class);

        api.getAllBatches().enqueue(new Callback<List<Batch>>() {

            @Override
            public void onResponse(Call<List<Batch>> call,
                                   Response<List<Batch>> response) {

                Log.d("BATCH_DEBUG", "HTTP Code : " + response.code());

                if (response.body() != null) {

                    Log.d("BATCH_DEBUG", "Batch Size : " + response.body().size());

                    for (Batch b : response.body()) {

                        Log.d("BATCH_DEBUG",
                                b.getBatchName() + " - " + b.getTrainerName());
                    }
                }

                if (response.isSuccessful() && response.body() != null) {

                    batchList.clear();

                    batchList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(getContext(),
                            "No Data",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Batch>> call, Throwable t) {

                Log.e("BATCH_DEBUG", "Error", t);

                Toast.makeText(getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}