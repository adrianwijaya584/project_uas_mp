package com.example.project_uas_mp.config;

import com.example.project_uas_mp.class_data.AuthApiResponse;
import com.example.project_uas_mp.class_data.AuthBody;
import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.JurusanBody;
import com.example.project_uas_mp.class_data.Mahasiswa;
import com.example.project_uas_mp.class_data.MahasiswaApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaBody;

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

  @GET("/students")
  Call<MahasiswaApiResponse> getAllMahasiswa();

  @POST("/students")
  Call<MahasiswaApiResponse> addMahasiswa(@Body MahasiswaBody body);

  @DELETE("/students/{id}")
  Call<MahasiswaApiResponse> deleteMahasiswa(@Path("id") String id);
}
