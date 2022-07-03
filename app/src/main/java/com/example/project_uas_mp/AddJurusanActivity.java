package com.example.project_uas_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.JurusanBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJurusanActivity extends AppCompatActivity {
  TextView etAddKodeJ, etAddNamaJ;
  Button btnSendAddJ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_jurusan);

    etAddKodeJ= findViewById(R.id.etAddKodeJ);
    etAddNamaJ= findViewById(R.id.etAddNamaJ);
    btnSendAddJ= findViewById(R.id.btnSendAddJ);

    btnSendAddJ.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String kodeJurusan= etAddKodeJ.getText().toString();
        String namaJurusan= etAddNamaJ.getText().toString();

        Call<JurusanApiResponse> request= AppConfig.requestConfig(getApplicationContext())
            .addJurusan(new JurusanBody(kodeJurusan, namaJurusan));

        request.enqueue(new Callback<JurusanApiResponse>() {
          @Override
          public void onResponse(Call<JurusanApiResponse> call, Response<JurusanApiResponse> response) {
            if (!response.isSuccessful()) {
              JurusanApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), JurusanApiResponse.class);

              Toast.makeText(AddJurusanActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

              return;
            }

            JurusanApiResponse body= response.body();

            Toast.makeText(AddJurusanActivity.this, "Jurusan berhasil ditambah", Toast.LENGTH_SHORT).show();

            finish();
          }

          @Override
          public void onFailure(Call<JurusanApiResponse> call, Throwable t) {
            Toast.makeText(AddJurusanActivity.this, "jurusan gagal ditambahkan", Toast.LENGTH_SHORT).show();
          }
        });

      } // enqueue
    });

  } // oncreate
}