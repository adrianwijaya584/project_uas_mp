package com.example.project_uas_mp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.FilesApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaBody;
import com.example.project_uas_mp.config.AppConfig;
import com.example.project_uas_mp.config.Utils;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMahasiswaActivity extends AppCompatActivity {
  EditText etAddNimMhs, etAddNamaMhs, etAddTelpMhs, etAddAlamatMhs;
  RadioGroup rgAddGenderMhs;
  String filename;
  ImageView imvAddMhs;
  Button btnSendAddMhs, btnFileAddMhs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_mahasiswa);

    etAddNimMhs= findViewById(R.id.etAddNimMhs);
    etAddNamaMhs= findViewById(R.id.etAddNamaMhs);
    etAddTelpMhs= findViewById(R.id.etAddTelpMhs);
    etAddAlamatMhs= findViewById(R.id.etAddAlamatMhs);
    rgAddGenderMhs= findViewById(R.id.rgGenderDosen);
    imvAddMhs= findViewById(R.id.imvAddMhs);
    btnSendAddMhs= findViewById(R.id.btnSendAddMhs);
    btnFileAddMhs= findViewById(R.id.btnFileAddMhs);

    etAddNimMhs.setText("200001");
    etAddNamaMhs.setText("nama");
    etAddTelpMhs.setText("0101010");
    etAddAlamatMhs.setText("Jln");

    btnFileAddMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        i.setType("image/*");

        startActivityForResult(i, 1);
      }
    });

    btnSendAddMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addMahasiswa();
      }
    });
  } // onCreate

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data==null) {
      filename= null;

      imvAddMhs.setImageResource(R.mipmap.ic_launcher);
    } else {
      if (requestCode==1 && resultCode== Activity.RESULT_OK) {
        File sourceFile= new File(Utils.getRealPathFromURI(getApplicationContext(), data.getData()));

        RequestBody file=RequestBody.create(
            MediaType.parse("*/*"),
            sourceFile
        );

        RequestBody body= new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", sourceFile.getName(), file)
            .build();

        Call<FilesApiResponse> call= AppConfig.requestConfig(getApplicationContext()).uploadFile(body);

        call.enqueue(new Callback<FilesApiResponse>() {
          @Override
          public void onResponse(Call<FilesApiResponse> call, Response<FilesApiResponse> response) {
            if (!response.isSuccessful()) {
              Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();

              return;
            }

            filename = response.body().getData().getName();

            imvAddMhs.setImageURI(data.getData());
          }

          @Override
          public void onFailure(Call<FilesApiResponse> call, Throwable t) {
            Log.d("uploadFile", t.getLocalizedMessage());

            Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();
          }
        });

      }
    }
  }


  private void addMahasiswa() {
    String id= etAddNimMhs.getText().toString();
    String name= etAddNamaMhs.getText().toString();
    String phone= String.valueOf(etAddTelpMhs.getText().toString());
    String address= etAddAlamatMhs.getText().toString();
    RadioButton rb= findViewById(rgAddGenderMhs.getCheckedRadioButtonId());
    String gender= rb.getText().toString().equals("Laki-laki")?"male":"female";

    if (filename == null) {
      Toast.makeText(this, "Harap memilih gambar", Toast.LENGTH_SHORT).show();
      return;
    }

    if (id.equals("")) {
      Toast.makeText(this, "Harap mengisi NIM", Toast.LENGTH_SHORT).show();
      etAddNimMhs.requestFocus();
      return;
    }

    if (name.equals("")) {
      Toast.makeText(this, "Harap mengisi Nama", Toast.LENGTH_SHORT).show();
      etAddNamaMhs.requestFocus();
      return;
    }

    if (phone.equals("")) {
      Toast.makeText(this, "Harap mengisi No Telp", Toast.LENGTH_SHORT).show();
      etAddTelpMhs.requestFocus();
      return;
    }

    if (address.equals("")) {
      Toast.makeText(this, "Harap mengisi Alamat", Toast.LENGTH_SHORT).show();
      etAddAlamatMhs.requestFocus();
      return;
    }


    MahasiswaBody body= new MahasiswaBody(id, name, phone, address, gender, filename);

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

        PreferenceManager.getDefaultSharedPreferences(AddMahasiswaActivity.this).edit()
                .remove("mahasiswaCache").apply();

        finish();
      }

      @Override
      public void onFailure(Call<MahasiswaApiResponse> call, Throwable t) {
        filename= null;
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(AddMahasiswaActivity.this, "Gagal menambahkan mahasiswa", Toast.LENGTH_SHORT).show();
      }
    });
  }
}