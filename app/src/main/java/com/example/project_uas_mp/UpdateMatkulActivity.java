package com.example.project_uas_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.Matkul;
import com.example.project_uas_mp.class_data.MatkulApiResponse;
import com.example.project_uas_mp.class_data.MatkulBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMatkulActivity extends AppCompatActivity {

  EditText etEditNamaMatkul;
  Spinner spEditSks, spEditJurusan;
  Button btnSendEditMatkul;
  String[] sks= {"1", "2", "4"};
  List<Jurusan> jurusan= new ArrayList<>();
  Matkul matkul;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_matkul);

    etEditNamaMatkul= findViewById(R.id.etEditNamaMatkul);
    spEditSks= findViewById(R.id.spEditSks);
    spEditJurusan= findViewById(R.id.spEditJurusan);
    btnSendEditMatkul= findViewById(R.id.btnSendEditMatkul);

    getJurusan();

    Intent intent= getIntent();

    matkul= intent.getParcelableExtra("matkul");

    etEditNamaMatkul.setText(matkul.getName());

    spEditSks.setAdapter(new ArrayAdapter<String>(
        UpdateMatkulActivity.this,
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        sks
    ));

    spEditSks.setSelection(Arrays.asList(sks).indexOf(matkul.getCredits()));

    btnSendEditMatkul.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateMatkulActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin mengirim data ini ?");

        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            updateMatkul();
          }
        });

        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    });
  } // onCreate

  private int indexOfJurusan() {
    int pos= 0;

    for (int i = 0; i < jurusan.size(); i++) {
      if (jurusan.get(i).getCode().equals(matkul.getMajor_id())) {
        pos= i;

        break;
      }
    }

    return pos;
  }

  private ArrayAdapter jurusanAdapter() {

    if (jurusan.size()==0) return null;

    List<String> list_jurusan= new ArrayList<>();

    for (int i = 0; i < jurusan.size(); i++) {
      list_jurusan.add(jurusan.get(i).getName());
    }

    ArrayAdapter<String> adapter= new ArrayAdapter<>(UpdateMatkulActivity.this,
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        list_jurusan);

    return adapter;
  }

  private void getJurusan() {
    Call<JurusanApiResponse> call= AppConfig.requestConfig(getApplicationContext()).getAllJurusan();

    call.enqueue(new Callback<JurusanApiResponse>() {
      @Override
      public void onResponse(Call<JurusanApiResponse> call, Response<JurusanApiResponse> response) {
        if (!response.isSuccessful()) return;

        jurusan= response.body().getListJurusan();

        spEditJurusan.setAdapter(jurusanAdapter());
        spEditJurusan.setSelection(indexOfJurusan());
      }

      @Override
      public void onFailure(Call<JurusanApiResponse> call, Throwable t) {

      }
    });
  } // getJurusan

  private void updateMatkul() {
    String name= etEditNamaMatkul.getText().toString();
    String credits= sks[spEditSks.getSelectedItemPosition()];
    String major_id= jurusan.get(spEditJurusan.getSelectedItemPosition()).getCode();

    if (name.equals("")) {
      Toast.makeText(this, "Harap masukan nama mata kuliah", Toast.LENGTH_SHORT).show();
      etEditNamaMatkul.requestFocus();
      return;
    }

    MatkulBody body= new MatkulBody(major_id, name, credits);

    Call<MatkulApiResponse> call= AppConfig.requestConfig(getApplicationContext()).updateMatkul(matkul.getId(), body);

    call.enqueue(new Callback<MatkulApiResponse>() {
      @Override
      public void onResponse(Call<MatkulApiResponse> call, Response<MatkulApiResponse> response) {
        if (!response.isSuccessful()) {
          Toast.makeText(UpdateMatkulActivity.this, "mata kuliah gagal diubah", Toast.LENGTH_SHORT).show();
          return;
        }

        Toast.makeText(UpdateMatkulActivity.this, "mata kuliah berhasil diubah", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<MatkulApiResponse> call, Throwable t) {
        Toast.makeText(UpdateMatkulActivity.this, "Data mata kuliah gagal diubah", Toast.LENGTH_SHORT).show();
      }
    });
  }
}