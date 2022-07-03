package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Jurusan {
  @SerializedName("id")
  private String code;

  @SerializedName("name")
  private String name;

  @SerializedName("created_at")
  private String created_at;

  public Jurusan(String code, String name, String created_at) {
    this.code = code;
    this.name = name;
    this.created_at = created_at;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }
}
