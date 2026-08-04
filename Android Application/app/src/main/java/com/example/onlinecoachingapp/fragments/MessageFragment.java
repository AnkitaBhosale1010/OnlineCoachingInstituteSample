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
import com.example.onlinecoachingapp.adapter.MessageAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.MessageApi;
import com.example.onlinecoachingapp.model.Message;
import com.example.onlinecoachingapp.session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageFragment extends Fragment {

    RecyclerView recyclerView;
    MessageAdapter adapter;
    List<Message> messageList;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_message,
                container,
                false);

        recyclerView = view.findViewById(R.id.recyclerMessage);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        messageList = new ArrayList<>();

        adapter = new MessageAdapter(
                getContext(),
                messageList);

        recyclerView.setAdapter(adapter);

        sessionManager = new SessionManager(requireContext());

        loadMessages();

        return view;
    }

    private void loadMessages() {

        Long userId = sessionManager.getUserId();

        MessageApi api = ApiClient
                .getRetrofitInstance(requireContext())
                .create(MessageApi.class);

        api.getInbox(userId)
                .enqueue(new Callback<List<Message>>() {

                    @Override
                    public void onResponse(
                            Call<List<Message>> call,
                            Response<List<Message>> response) {

                        Log.e("MESSAGE", "Code = " + response.code());

                        if(response.body()!=null){
                            Log.e("MESSAGE", "Size = " + response.body().size());

                            for(Message m : response.body()){
                                Log.e("MESSAGE",
                                        m.getMessage() + " From "
                                                + m.getSender().getName());

                                messageList.clear();
                                messageList.addAll(response.body());
                                adapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(
                                            getContext(),
                                            "No Messages Found",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Message>> call,
                            Throwable t) {

                        Toast.makeText(
                                        getContext(),
                                        t.getMessage(),
                                        Toast.LENGTH_LONG)
                                .show();

                        Log.e("MESSAGE",
                                t.getMessage(),
                                t);
                    }
                });
    }
}