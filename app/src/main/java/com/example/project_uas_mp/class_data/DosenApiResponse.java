package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DosenApiResponse {
  @SerializedName("success")
  private Boolean success;

  @SerializedName("message")
  private String message;

  @SerializedName("errors")
  private List<String> errors;

  @SerializedName("data")
  private List<Dosen> data;

  public DosenApiResponse(Boolean success, String message, List<String> errors, List<Dosen> data) {
    this.success = success;
    this.message = message;
    this.errors = errors;
    this.data = data;
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

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public List<Dosen> getData() {
    return data;
  }

  public void setData(List<Dosen> data) {
    this.data = data;
  }
}
