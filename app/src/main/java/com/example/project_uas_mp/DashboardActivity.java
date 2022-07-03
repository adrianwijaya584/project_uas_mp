package com.example.project_uas_mp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.project_uas_mp.class_data.Jurusan;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
  TextView tvWelcome, tvLogout, tvNavJurusan, tvNavMahasiswa;
  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);


    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    editor = sharedPreferences.edit();

    String token = sharedPreferences.getString("token", "");
    String tokenPayloadEncoded = TextUtils.split(token, "[.]")[1];
    byte[] tokenPayloadDecodedByte = Base64.decode(tokenPayloadEncoded, Base64.DEFAULT);
    String name = "";
    try {
      JSONObject tokenJson = new JSONObject(new String(tokenPayloadDecodedByte));
      name = tokenJson.getString("name");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    tvWelcome= findViewById(R.id.tvWelcome);
    tvWelcome.setText("Selamat datang, ".concat(name));
    tvLogout= findViewById(R.id.tvLogout);
    tvNavJurusan= findViewById(R.id.tvNavJurusan);
    tvNavMahasiswa= findViewById(R.id.tvNavMahasiswa);

    tvNavJurusan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ListJurusanActivity.class));
      }
    });

    tvNavMahasiswa.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ListMahasiswaActivity.class));
      }
    });

    tvLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(DashboardActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda ingin logout ?");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            editor.remove("token");

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
          }
        });

        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    });

  }
}