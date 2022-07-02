package com.example.project_uas_mp;

import com.example.project_uas_mp.class_data.Jurusan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRequest {
  @GET("/jurusan")
  Call<List<Jurusan>> getAllJurusan();

  @POST("/jurusan")
  Call<Jurusan> addJurusan();

  @GET("/jurusan/{id}")
  Call<Jurusan> detailJurusan(@Path("id") String id);

  @PUT("/jurusan")
  Call<Jurusan> updateJurusan();

  @DELETE("/jurusan/{id}")
  Call<Jurusan> deleteJurusan(@Path("id") String id);
}
