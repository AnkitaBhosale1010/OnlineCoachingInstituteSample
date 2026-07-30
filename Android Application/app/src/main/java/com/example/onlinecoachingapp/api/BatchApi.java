package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.Batch;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BatchApi {
    @GET("api/batches")
    Call<List<Batch>> getAllBatches();
}
