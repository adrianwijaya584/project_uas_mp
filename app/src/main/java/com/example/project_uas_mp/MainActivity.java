package com.example.project_uas_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.AuthApiResponse;
import com.example.project_uas_mp.class_data.AuthBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  EditText etUsername, etPassword;
  Button btnLogin;
  SharedPreferences sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    if (!sp.getString("token", "").equals("")) {
      finish();
      Intent intent= new Intent(getApplicationContext(), DashboardActivity.class);

      startActivity(intent);
    }

    etUsername= findViewById(R.id.etUsername);
    etPassword= findViewById(R.id.etPassword);
    btnLogin= findViewById(R.id.btnLogin);

    etUsername.requestFocus();

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        loginAction();
      }
    });
  }

  private void loginAction() {
    String username= etUsername.getText().toString();
    String password= etPassword.getText().toString();

    if (username.equals("")) {
      Toast.makeText(this, "Harap masukan username", Toast.LENGTH_SHORT).show();
      etUsername.requestFocus();
      return;
    }
    if (password.equals("")) {
      Toast.makeText(this, "Harap masukan password", Toast.LENGTH_SHORT).show();
      etPassword.requestFocus();
      return;
    }

    Call<AuthApiResponse> request= AppConfig.requestConfig(getApplicationContext()).login(new AuthBody(username, password));

    request.enqueue(new Callback<AuthApiResponse>() {
      @Override
      public void onResponse(Call<AuthApiResponse> call, Response<AuthApiResponse> response) {
        AuthApiResponse res= response.body();

        if (!response.isSuccessful()) {
          Gson gson= new Gson();

          AuthApiResponse errBody= gson.fromJson(response.errorBody().charStream(), AuthApiResponse.class);

          Toast.makeText(MainActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT)
              .show();

          return;
        }

        SharedPreferences.Editor editor= sp.edit();

        editor.putString("token", res.getData().getAccess_token());

        editor.apply();

        Toast.makeText(MainActivity.this, "Selamat datang "+username, Toast.LENGTH_SHORT).show();

        Intent intent= new Intent(getApplicationContext(), DashboardActivity.class);

        startActivity(intent);
      }

      @Override
      public void onFailure(Call<AuthApiResponse> call, Throwable t) {
        Toast.makeText(MainActivity.this, "Login gagal dilakukan", Toast.LENGTH_SHORT).show();
      }
    });
  }
}