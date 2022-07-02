package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

public class Jurusan {
  @SerializedName("code")
  private String code;

  @SerializedName("name")
  private String name;

  public Jurusan(String code, String name) {
    this.code = code;
    this.name = name;
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
}
