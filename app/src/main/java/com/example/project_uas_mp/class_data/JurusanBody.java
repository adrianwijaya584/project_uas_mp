package com.example.project_uas_mp.class_data;

public class JurusanBody {
  private String id;
  private String name;

  public JurusanBody(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
