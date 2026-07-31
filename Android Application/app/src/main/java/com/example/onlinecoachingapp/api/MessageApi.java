package com.example.onlinecoachingapp.api;

import com.example.onlinecoachingapp.model.Message;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessageApi {

    @GET("api/messages/inbox/{userId}")
    Call<List<Message>> getInbox(
            @Path("userId") Long userId
    );
}
