package com.example.project_uas_mp.class_data;

import android.os.Parcel;
import android.os.Parcelable;

public class DosenBody implements Parcelable {
  private String id;
  private String name;
  private String phone_number;
  private String address;
  private String gender;
  private String profile_image;

  public DosenBody(String id, String name, String phone_number, String address, String gender, String profile_image) {
    this.id = id;
    this.name = name;
    this.phone_number = phone_number;
    this.address = address;
    this.gender = gender;
    this.profile_image = profile_image;
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

  public String getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getProfile_image() {
    return profile_image;
  }

  public void setProfile_image(String profile_image) {
    this.profile_image = profile_image;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.name);
    dest.writeString(this.phone_number);
    dest.writeString(this.address);
    dest.writeString(this.gender);
    dest.writeString(this.profile_image);
  }

  public void readFromParcel(Parcel source) {
    this.id = source.readString();
    this.name = source.readString();
    this.phone_number = source.readString();
    this.address = source.readString();
    this.gender = source.readString();
    this.profile_image = source.readString();
  }

  protected DosenBody(Parcel in) {
    this.id = in.readString();
    this.name = in.readString();
    this.phone_number = in.readString();
    this.address = in.readString();
    this.gender = in.readString();
    this.profile_image = in.readString();
  }

  public static final Parcelable.Creator<DosenBody> CREATOR = new Parcelable.Creator<DosenBody>() {
    @Override
    public DosenBody createFromParcel(Parcel source) {
      return new DosenBody(source);
    }

    @Override
    public DosenBody[] newArray(int size) {
      return new DosenBody[size];
    }
  };
}
