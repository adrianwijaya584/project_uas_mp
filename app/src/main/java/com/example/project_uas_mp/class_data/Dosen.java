package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

public class Dosen {
   @SerializedName("id")
   private String id;

   @SerializedName("name")
   private String name;

   @SerializedName("phone_number")
   private String phone_number;

   @SerializedName("address")
   private String address;

   @SerializedName("gender")
   private String gender;

   @SerializedName("profile_image")
   private String profile_image;

   @SerializedName("created_at")
   private String created_at;

   public Dosen(String id, String name, String phone_number, String address, String gender, String profile_image, String created_at) {
      this.id = id;
      this.name = name;
      this.phone_number = phone_number;
      this.address = address;
      this.gender = gender;
      this.profile_image = profile_image;
      this.created_at = created_at;
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

   public String getCreated_at() {
      return created_at;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }
}
