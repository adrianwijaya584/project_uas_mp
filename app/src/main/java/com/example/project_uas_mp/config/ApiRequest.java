package com.example.project_uas_mp.config;

import com.example.project_uas_mp.class_data.AuthApiResponse;
import com.example.project_uas_mp.class_data.AuthBody;
import com.example.project_uas_mp.class_data.DosenApiResponse;
import com.example.project_uas_mp.class_data.DosenBody;
import com.example.project_uas_mp.class_data.FilesApiResponse;
import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.JurusanBody;
import com.example.project_uas_mp.class_data.Mahasiswa;
import com.example.project_uas_mp.class_data.MahasiswaApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaBody;
import com.example.project_uas_mp.class_data.Matkul;
import com.example.project_uas_mp.class_data.MatkulApiResponse;
import com.example.project_uas_mp.class_data.MatkulBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

  @POST("files")
  Call<FilesApiResponse> uploadFile(@Body RequestBody body);

  // API Jurusan
  @GET("/majors")
  Call<JurusanApiResponse> getAllJurusan();

  @POST("/majors")
  Call<JurusanApiResponse> addJurusan(@Body JurusanBody body);

  @PUT("/majors/{id}")
  Call<JurusanApiResponse> updateJurusan(@Path("id") String id, @Body JurusanBody body);

  @DELETE("/majors/{id}")
  Call<JurusanApiResponse> deleteJurusan(@Path("id") String id);
  // API Jurusan

  // API Mahasiswa
  @GET("/students")
  Call<MahasiswaApiResponse> getAllMahasiswa();

  @POST("/students")
  Call<MahasiswaApiResponse> addMahasiswa(@Body MahasiswaBody body);

  @PUT("/students/{id}")
  Call<MahasiswaApiResponse> updateMahasiswa(@Path("id") String id, @Body MahasiswaBody body);

  @DELETE("/students/{id}")
  Call<MahasiswaApiResponse> deleteMahasiswa(@Path("id") String id);
  // API Mahasiswa

  // API Dosen
  @GET("/lecturers")
  Call<DosenApiResponse> getAllDosen();

  @POST("/lecturers")
  Call<DosenApiResponse> addDosen(@Body DosenBody body);

  @PUT("/lecturers/{id}")
  Call<DosenApiResponse> updateDosen(@Path("id") String id, @Body DosenBody body);

  @DELETE("/lecturers/{id}")
  Call<DosenApiResponse> deleteDosen(@Path("id") String id);
  // API Dosen

  // API Matkul
  @GET("/subjects")
  Call<MatkulApiResponse> getAllMatkul();

  @POST("/subjects")
  Call<MatkulApiResponse> addMatkul(@Body MatkulBody body);

  @PUT("/subjects/{id}")
  Call<MatkulApiResponse> updateMatkul(@Path("id") String id, @Body MatkulBody body);

  @DELETE("/subjects/{id}")
  Call<MatkulApiResponse> deleteMatkul(@Path("id") String id);

  // API Matkul

}
