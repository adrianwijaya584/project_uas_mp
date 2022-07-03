package com.example.project_uas_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.MahasiswaApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaBody;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMahasiswaActivity extends AppCompatActivity {
  EditText etAddNimMhs, etAddNamaMhs, etAddTelpMhs, etAddAlamatMhs;
  RadioGroup rgAddGenderMhs;
  Button btnSendAddMhs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_mahasiswa);

    etAddNimMhs= findViewById(R.id.etAddNimMhs);
    etAddNamaMhs= findViewById(R.id.etAddNamaMhs);
    etAddTelpMhs= findViewById(R.id.etAddTelpMhs);
    etAddAlamatMhs= findViewById(R.id.etAddAlamatMhs);
    rgAddGenderMhs= findViewById(R.id.rgAddGenderMhs);
    btnSendAddMhs= findViewById(R.id.btnSendAddMhs);

    etAddNimMhs.setText("200001");
    etAddNamaMhs.setText("nama");
    etAddTelpMhs.setText("0101010");
    etAddAlamatMhs.setText("Jln");

    btnSendAddMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addMahasiswa();
      }
    });

  }

  private void addMahasiswa() {
    String id= etAddNimMhs.getText().toString();
    String name= etAddNamaMhs.getText().toString();
    String phone= String.valueOf(etAddTelpMhs.getText().toString());
    String address= etAddAlamatMhs.getText().toString();
    RadioButton rb= findViewById(rgAddGenderMhs.getCheckedRadioButtonId());
    String gender= rb.getText().toString().equals("Laki-laki")?"male":"female";

    MahasiswaBody body= new MahasiswaBody(id, name, phone, gender, address,"1");

    Call<MahasiswaApiResponse> call= AppConfig.requestConfig(AddMahasiswaActivity.this).addMahasiswa(body);

    call.enqueue(new Callback<MahasiswaApiResponse>() {
      @Override
      public void onResponse(Call<MahasiswaApiResponse> call, Response<MahasiswaApiResponse> response) {
        if (!response.isSuccessful()) {
          MahasiswaApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), MahasiswaApiResponse.class);

          Toast.makeText(AddMahasiswaActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();
          
          return;
        }

        Toast.makeText(AddMahasiswaActivity.this, "Berhasil menambahkan mahasiswa", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<MahasiswaApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(AddMahasiswaActivity.this, "Gagal menambahkan mahasiswa", Toast.LENGTH_SHORT).show();
      }
    });
  }
}