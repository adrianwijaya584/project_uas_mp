package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JurusanApiResponse {
  @SerializedName("success")
  private Boolean success;

  @SerializedName("message")
  private String message;

  @SerializedName("data")
  List<Jurusan> listJurusan;

  public JurusanApiResponse(Boolean success, String message, List<Jurusan> listJurusan) {
    this.success = success;
    this.message= message;
    this.listJurusan = listJurusan;
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

  public List<Jurusan> getListJurusan() {
    return listJurusan;
  }

  public void setListJurusan(List<Jurusan> listJurusan) {
    this.listJurusan = listJurusan;
  }
}
