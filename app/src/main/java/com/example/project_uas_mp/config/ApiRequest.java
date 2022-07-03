package com.example.project_uas_mp.config;

import com.example.project_uas_mp.class_data.AuthApiResponse;
import com.example.project_uas_mp.class_data.AuthBody;
import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.JurusanBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRequest {
  @POST("auth/login")
  Call<AuthApiResponse> login(@Body AuthBody body);

  @GET("/majors")
  Call<JurusanApiResponse> getAllJurusan();

  @POST("/majors")
  Call<JurusanApiResponse> addJurusan(@Body JurusanBody body);

  @PUT("/majors/{id}")
  Call<JurusanApiResponse> updateJurusan(@Path("id") String id, @Body JurusanBody body);

  @DELETE("/majors/{id}")
  Call<JurusanApiResponse> deleteJurusan(@Path("id") String id);
}
