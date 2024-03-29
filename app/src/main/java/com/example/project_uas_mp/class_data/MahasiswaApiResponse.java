package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaApiResponse {
  @SerializedName("success")
  private Boolean success;

  @SerializedName("message")
  private String message;

  @SerializedName("errors")
  private List<String> errors;

  @SerializedName("data")
  private List<Mahasiswa> listMahasiswa;

  public MahasiswaApiResponse(Boolean success, String message, List<Mahasiswa> listMahasiswa, List<String> errors) {
    this.success = success;
    this.message= message;
    this.listMahasiswa = listMahasiswa;
    this.errors= errors;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Mahasiswa> getListMahasiswa() {
    return listMahasiswa;
  }

  public void setListMahasiswa(List<Mahasiswa> listMahasiswa) {
    this.listMahasiswa = listMahasiswa;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
