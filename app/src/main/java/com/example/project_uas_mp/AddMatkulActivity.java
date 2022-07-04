package com.example.project_uas_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.class_data.MatkulApiResponse;
import com.example.project_uas_mp.class_data.MatkulBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMatkulActivity extends AppCompatActivity {
  EditText etAddNamaMatkul;
  Spinner spSks, spJurusan;
  Button btnSendAddMatkul;
  String[] sks= {"1", "2", "4"};
  List<Jurusan> jurusan= new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_matkul);

    etAddNamaMatkul= findViewById(R.id.etAddNamaMatkul);
    spSks= findViewById(R.id.spSks);
    spJurusan= findViewById(R.id.spJurusan);
    btnSendAddMatkul= findViewById(R.id.btnSendAddMatkul);

    spSks.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        sks));

    getJurusan();


    btnSendAddMatkul.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addMatkul();
      }
    });
  } // onCreate

  private ArrayAdapter jurusanAdapter() {

    if (jurusan.size()==0) return null;

    List<String> list_jurusan= new ArrayList<>();

    for (int i = 0; i < jurusan.size(); i++) {
      list_jurusan.add(jurusan.get(i).getName());
    }

    ArrayAdapter<String> adapter= new ArrayAdapter<>(AddMatkulActivity.this,
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

        spJurusan.setAdapter(jurusanAdapter());
      }

      @Override
      public void onFailure(Call<JurusanApiResponse> call, Throwable t) {

      }
    });
  } // getJurusan

  private void addMatkul() {
    String name= etAddNamaMatkul.getText().toString();
    String credits= sks[spSks.getSelectedItemPosition()];
    String major_id= jurusan.get(spJurusan.getSelectedItemPosition()).getCode();

    MatkulBody body= new MatkulBody(major_id, name, credits);

    Call<MatkulApiResponse> call= AppConfig.requestConfig(getApplicationContext()).addMatkul(body);

    call.enqueue(new Callback<MatkulApiResponse>() {
      @Override
      public void onResponse(Call<MatkulApiResponse> call, Response<MatkulApiResponse> response) {
        if (!response.isSuccessful()) {
          MatkulApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), MatkulApiResponse.class);

          Toast.makeText(AddMatkulActivity.this, errBody.getErrors().get(0), Toast.LENGTH_SHORT).show();

          return;
        }

        PreferenceManager.getDefaultSharedPreferences(AddMatkulActivity.this)
                .edit().remove("matkulCache").apply();

        Toast.makeText(AddMatkulActivity.this, "Matkul berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<MatkulApiResponse> call, Throwable t) {
        Toast.makeText(AddMatkulActivity.this, "Matkul gagal ditambahkan", Toast.LENGTH_SHORT).show();
      }
    });
  }

}