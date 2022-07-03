package com.example.project_uas_mp.class_data;

import com.google.gson.annotations.SerializedName;

public class AuthApiResponse {
  @SerializedName("success")
  private Boolean success;

  @SerializedName("message")
  private String message;

  @SerializedName("data")
  private Token data;

  public AuthApiResponse(Boolean success, String message, Token data) {
    this.success = success;
    this.message = message;
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

  public Token getData() {
    return data;
  }

  public void setData(Token data) {
    this.data = data;
  }

  public static class Token {
    @SerializedName("access_token")
    private String access_token;

    public Token(String access_token) {
      this.access_token = access_token;
    }

    public String getAccess_token() {
      return access_token;
    }

    public void setAccess_token(String access_token) {
      this.access_token = access_token;
    }
  }
}
