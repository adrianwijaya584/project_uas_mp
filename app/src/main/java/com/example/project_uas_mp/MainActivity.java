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
        SharedPreferences.Editor editor= sp.edit();

        editor.putString("token", "123");

        editor.apply();

        Intent intent= new Intent(getApplicationContext(), DashboardActivity.class);

        startActivity(intent);
      }
    });
  }
}