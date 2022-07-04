package com.example.project_uas_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.JurusanBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateJurusanActivity extends AppCompatActivity {

  EditText etUpdateKodeJ, etUpdateNamaJ;
  Button btnSendUpdateJ;
  String id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_jurusan);

    etUpdateKodeJ= findViewById(R.id.etUpdateKodeJ);
    etUpdateNamaJ= findViewById(R.id.etUpdateNamaJ);
    btnSendUpdateJ= findViewById(R.id.btnSendUpdateJ);

    Intent intent= getIntent();
    id= intent.getStringExtra("code");

    etUpdateKodeJ.setText(id);
    etUpdateNamaJ.setText(intent.getStringExtra("name"));

    btnSendUpdateJ.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateJurusanActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin mengubah data ini ?");

        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            updateJurusan();
          }
        });
        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    });
  }

  private void updateJurusan() {
    String namaJurusan= etUpdateNamaJ.getText().toString();

    if (namaJurusan.equals("")) {
      Toast.makeText(UpdateJurusanActivity.this, "Harap memasukan nama jurusan", Toast.LENGTH_SHORT).show();
      etUpdateNamaJ.requestFocus();
      return;
    }

    if (namaJurusan.equals("")) {
      Toast.makeText(this, "Harap isi nama jurusan", Toast.LENGTH_SHORT).show();
      etUpdateNamaJ.requestFocus();
      return;
    }

    Call<JurusanApiResponse> responseCall= AppConfig.requestConfig(getApplicationContext())
        .updateJurusan(id, new JurusanBody(id, namaJurusan));

    responseCall.enqueue(new Callback<JurusanApiResponse>() {
      @Override
      public void onResponse(Call<JurusanApiResponse> call, Response<JurusanApiResponse> response) {
        if (!response.isSuccessful()) {
          JurusanApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), JurusanApiResponse.class);

          Toast.makeText(UpdateJurusanActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(UpdateJurusanActivity.this, "jurusan berhasil diupdate", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<JurusanApiResponse> call, Throwable t) {
        Log.d("updateJurusan", t.getLocalizedMessage());

        Toast.makeText(UpdateJurusanActivity.this, "Jurusan gagal diubah", Toast.LENGTH_SHORT).show();
      }
    });
  }
}