package com.example.project_uas_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateJurusanActivity extends AppCompatActivity {

  EditText etUpdateKodeJ, etUpdateNamaJ;
  Button btnSendUpdateJ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_jurusan);

    etUpdateKodeJ= findViewById(R.id.etUpdateKodeJ);
    etUpdateNamaJ= findViewById(R.id.etUpdateNamaJ);
    btnSendUpdateJ= findViewById(R.id.btnSendUpdateJ);

    Intent intent= getIntent();

    etUpdateKodeJ.setText(intent.getStringExtra("code"));
    etUpdateNamaJ.setText("Sistem Informasi");


    btnSendUpdateJ.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateJurusanActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin mengubah data ini ?");

        builder.setPositiveButton("Kirim", null);
        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    });
  }
}