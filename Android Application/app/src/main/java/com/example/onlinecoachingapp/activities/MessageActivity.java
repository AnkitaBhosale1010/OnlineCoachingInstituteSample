package com.example.onlinecoachingapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.adapter.MessageAdapter;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.ApiService;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Message;
import com.example.onlinecoachingapp.model.MessageRequest;
import com.example.onlinecoachingapp.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edtMessage;
    private Button btnSend;

    private MessageAdapter adapter;
    private ApiService apiService;

    private SessionManager sessionManager;

    private Long userId;

    private Long receiverId = 3L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recyclerMessages);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        sessionManager = new SessionManager(this);

        apiService = ApiClient
                .getRetrofitInstance(this)
                .create(ApiService.class);

        userId = sessionManager.getUserId();

        loadMessages();

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void loadMessages() {

        apiService.getConversation(userId, receiverId)
                .enqueue(new Callback<List<Message>>() {

                    @Override
                    public void onResponse(Call<List<Message>> call,
                                           Response<List<Message>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            adapter = new MessageAdapter(
                                    MessageActivity.this,
                                    response.body());

                            recyclerView.setAdapter(adapter);

                            recyclerView.scrollToPosition(
                                    response.body().size() - 1);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Message>> call,
                                          Throwable t) {

                        Toast.makeText(
                                MessageActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                });

    }

    private void sendMessage() {

        String text = edtMessage.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {

            edtMessage.setError("Enter message");

            return;
        }

        MessageRequest request = new MessageRequest();

        request.setSenderId(userId);
        request.setReceiverId(receiverId);
        request.setMessage(text);

        apiService.sendMessage(request)
                .enqueue(new Callback<ApiResponse<Message>>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse<Message>> call,
                            Response<ApiResponse<Message>> response) {

                        if (response.isSuccessful()) {

                            edtMessage.setText("");

                            Toast.makeText(
                                    MessageActivity.this,
                                    "Message Sent",
                                    Toast.LENGTH_SHORT).show();

                            loadMessages();

                        } else {

                            Toast.makeText(
                                    MessageActivity.this,
                                    "Unable to Send",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse<Message>> call,
                            Throwable t) {

                        Toast.makeText(
                                MessageActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                });

    }

}