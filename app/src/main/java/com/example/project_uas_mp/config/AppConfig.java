package com.example.project_uas_mp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {
  public static String BASE_URL= "http://192.168.1.14:3000/";

  public static ApiRequest requestConfig(Context ctx) {
    SharedPreferences editor= PreferenceManager.getDefaultSharedPreferences(ctx);

    OkHttpClient.Builder httpClient= new OkHttpClient.Builder();

    httpClient.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request request= chain.request().newBuilder()
            .addHeader("Authorization", "Bearer "+editor.getString("token", "")).build();
        return chain.proceed(request);
      }
    });

    Retrofit retrofit= new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    return retrofit.create(ApiRequest.class);
  }
}
