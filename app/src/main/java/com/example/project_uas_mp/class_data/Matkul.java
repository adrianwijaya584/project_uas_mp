package com.example.project_uas_mp.class_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Matkul implements Parcelable {
  @SerializedName("id")
  private String id;

  @SerializedName("major_id")
  private String major_id;

  @SerializedName("name")
  private String name;

  @SerializedName("credits")
  private String credits;

  @SerializedName("created_at")
  private String created_at;

  public Matkul(String id, String major_id, String name, String credits, String created_at) {
    this.id = id;
    this.major_id = major_id;
    this.name = name;
    this.credits = credits;
    this.created_at = created_at;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.major_id);
    dest.writeString(this.name);
    dest.writeString(this.credits);
  }

  public void readFromParcel(Parcel source) {
    this.id = source.readString();
    this.major_id = source.readString();
    this.name = source.readString();
    this.credits = source.readString();
  }

  protected Matkul(Parcel in) {
    this.id = in.readString();
    this.major_id = in.readString();
    this.name = in.readString();
    this.credits = in.readString();
  }

  public static final Creator<Matkul> CREATOR = new Creator<Matkul>() {
    @Override
    public Matkul createFromParcel(Parcel source) {
      return new Matkul(source);
    }

    @Override
    public Matkul[] newArray(int size) {
      return new Matkul[size];
    }
  };
}
