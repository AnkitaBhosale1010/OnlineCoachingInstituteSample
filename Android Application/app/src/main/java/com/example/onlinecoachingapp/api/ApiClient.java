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

    private static final String BASE_URL = "http://192.168.1.39:8080/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context) {


        if (retrofit == null) {


            SessionManager sessionManager =
                    new SessionManager(context);



            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor();


            logging.setLevel(
                    HttpLoggingInterceptor.Level.BODY
            );


            OkHttpClient client =
                    new OkHttpClient.Builder()


                            .addInterceptor(
                                    new Interceptor() {

                                        @Override
                                        public okhttp3.Response intercept(
                                                Chain chain)
                                                throws java.io.IOException {


                                            Request original =
                                                    chain.request();



                                            String token =
                                                    sessionManager.getToken();



                                            Request.Builder requestBuilder =
                                                    original.newBuilder();



                                            if(token != null
                                                    &&
                                                    !token.isEmpty()) {


                                                requestBuilder.addHeader(
                                                        "Authorization",
                                                        "Bearer " + token
                                                );

                                            }



                                            Request request =
                                                    requestBuilder.build();



                                            return chain.proceed(request);

                                        }
                                    }
                            )



                            .addInterceptor(logging)


                            .build();





            retrofit =
                    new Retrofit.Builder()

                            .baseUrl(BASE_URL)

                            .client(client)

                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            )

                            .build();


        }
        return retrofit;

    }

}
