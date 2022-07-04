package com.example.project_uas_mp.class_data;

public class MatkulBody {
  private String major_id;
  private String name;
  private String credits;

  public MatkulBody(String major_id, String name, String credits) {
    this.major_id = major_id;
    this.name = name;
    this.credits = credits;
  }

  public String getMajor_id() {
    return major_id;
  }

  public void setMajor_id(String major_id) {
    this.major_id = major_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCredits() {
    return credits;
  }

  public void setCredits(String credits) {
    this.credits = credits;
  }


}
