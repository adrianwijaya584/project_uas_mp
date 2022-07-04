package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

public class FilesApiResponse {
  @SerializedName("success")
  private Boolean success;

  @SerializedName("data")
  private FileData data;

  public FilesApiResponse(Boolean success, FileData data) {
    this.success = success;
    this.data = data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public FileData getData() {
    return data;
  }

  public void setData(FileData data) {
    this.data = data;
  }

  public static class FileData {
    @SerializedName("name")
    private String name;

    public FileData(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
