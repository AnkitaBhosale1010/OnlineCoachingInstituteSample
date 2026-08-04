package com.example.onlinecoachingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.onlinecoachingapp.R;
import com.example.onlinecoachingapp.api.ApiClient;
import com.example.onlinecoachingapp.api.StudentApi;
import com.example.onlinecoachingapp.model.ApiResponse;
import com.example.onlinecoachingapp.model.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    TextView txtName,txtEmail,txtPhone,txtAddress,
            txtDob,txtJoinDate,txtBatch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile,
                container,false);

        txtName=view.findViewById(R.id.txtName);
        txtEmail=view.findViewById(R.id.txtEmail);
        txtPhone=view.findViewById(R.id.txtPhone);
        txtAddress=view.findViewById(R.id.txtAddress);
        txtDob=view.findViewById(R.id.txtDob);
        txtJoinDate=view.findViewById(R.id.txtJoinDate);
        txtBatch=view.findViewById(R.id.txtBatch);

        loadProfile();

        return view;
    }

    private void loadProfile(){

        StudentApi api=ApiClient
                .getRetrofitInstance(requireContext())
                .create(StudentApi.class);

        api.getProfile().enqueue(new Callback<ApiResponse<Student>>() {

            @Override
            public void onResponse(Call<ApiResponse<Student>> call,
                                   Response<ApiResponse<Student>> response) {

                Log.e("PROFILE", "Response Code : " + response.code());

                if (response.body() != null) {
                    Log.e("PROFILE", response.body().toString());
                }

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().isSuccess()) {

                    Student student = response.body().getData();

                    // Set values to TextViews
                    txtName.setText(student.getUser().getName());
                    txtEmail.setText(student.getUser().getEmail());
                    txtPhone.setText(student.getPhone());
                    txtAddress.setText(student.getAddress());
                    txtDob.setText(student.getDob());
                    txtJoinDate.setText(student.getJoinDate());
                    

                } else {

                    Toast.makeText(getContext(),
                            "Profile not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Student>> call, Throwable t) {

                Toast.makeText(getContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();

                Log.e("PROFILE", "Error", t);
            }
        });

    }
}