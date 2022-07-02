package com.example.project_uas_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.project_uas_mp.class_data.Jurusan;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
  TextView tvWelcome, tvLogout, tvNavJurusan;
  SharedPreferences.Editor editor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    editor= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();

    tvWelcome= findViewById(R.id.tvWelcome);
    tvLogout= findViewById(R.id.tvLogout);
    tvNavJurusan= findViewById(R.id.tvNavJurusan);

    tvNavJurusan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), ListJurusanActivity.class));
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