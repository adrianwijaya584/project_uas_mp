package com.example.project_uas_mp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.project_uas_mp.class_data.Jurusan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
  TextView tvWelcome, tvLogout, tvNavJurusan, tvNavMahasiswa, tvNavDosen, tvNavMatkul;
  SharedPreferences sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
    }

    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    tvWelcome= findViewById(R.id.tvWelcome);
    tvLogout= findViewById(R.id.tvLogout);
    tvNavJurusan= findViewById(R.id.tvNavJurusan);
    tvNavMahasiswa= findViewById(R.id.tvNavMahasiswa);
    tvNavDosen= findViewById(R.id.tvNavDosen);
    tvNavMatkul= findViewById(R.id.tvNavMatkul);

    tvWelcome.setText("Selamat datang ".concat(sp.getString("name", "")+"!"));

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

    tvNavDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ListDosenActivity.class));
      }
    });

    tvNavMatkul.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ListMatkulActivity.class));
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
            SharedPreferences.Editor editor= sp.edit();

            editor.remove("token");
            editor.remove("username");

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