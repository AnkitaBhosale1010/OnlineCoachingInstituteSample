package com.example.onlinecoachingapp.api;

import android.content.Context;

import com.example.onlinecoachingapp.session.SessionManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.15:8080/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context) {

        if (retrofit == null) {

            SessionManager sessionManager = new SessionManager(context);

            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()

                    .addInterceptor((Interceptor) chain -> {

                        Request request = chain.request();

                        String token = sessionManager.getToken();

                        if (token != null && !token.isEmpty()) {

                            request = request.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }

                        return chain.proceed(request);

                    })

                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
