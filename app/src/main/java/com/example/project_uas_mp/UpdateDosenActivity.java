package com.example.project_uas_mp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.project_uas_mp.class_data.DosenApiResponse;
import com.example.project_uas_mp.class_data.DosenBody;
import com.example.project_uas_mp.class_data.FilesApiResponse;
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

public class UpdateDosenActivity extends AppCompatActivity {
  EditText etEditIdDosen, etEditNamaDosen, etEditTelpDosen, etEditAlamatDosen;
  RadioGroup rgGenderDosen;
  Button btnSendEditDosen, btnUpdateFileDosen;
  ImageView imvUpdateDosen;
  RadioButton dosenLakiLaki, dosenPerempuan;
  DosenBody dosen;
  String filename;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_dosen);

    etEditIdDosen= findViewById(R.id.etEditIdDosen);
    etEditNamaDosen= findViewById(R.id.etEditNamaDosen);
    etEditTelpDosen= findViewById(R.id.etEditTelpDosen);
    etEditAlamatDosen= findViewById(R.id.etEditAlamatDosen);
    rgGenderDosen= findViewById(R.id.rgGenderDosen);
    dosenLakiLaki= findViewById(R.id.dosenLakiLaki);
    dosenPerempuan= findViewById(R.id.dosenPerempuan);
    imvUpdateDosen= findViewById(R.id.imvUpdateDosen);
    btnUpdateFileDosen= findViewById(R.id.btnUpdateFileDosen);
    btnSendEditDosen= findViewById(R.id.btnSendEditDosen);

    Intent intent= getIntent();

    dosen= intent.getParcelableExtra("dosen");

    etEditIdDosen.setText(dosen.getId());
    etEditNamaDosen.setText(dosen.getName());
    etEditTelpDosen.setText(dosen.getPhone_number());
    etEditAlamatDosen.setText(dosen.getAddress());
    filename= dosen.getProfile_image();

    if (dosen.getGender().equals("male")) {
      dosenLakiLaki.setChecked(true);
    } else {
      dosenPerempuan.setChecked(true);
    }

    btnUpdateFileDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        i.setType("image/*");

        startActivityForResult(i, 1);
      }
    });

    btnSendEditDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateDosenActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin dengan data ini ?");

        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            updateDosen();
          }
        });

        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    }); // onClick

  } // onCreate

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data==null) {
      filename= dosen.getProfile_image();

      imvUpdateDosen.setImageResource(R.mipmap.ic_launcher);
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

        ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Gambar sedang diupload");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<FilesApiResponse> call= AppConfig.requestConfig(getApplicationContext()).uploadFile(body);

        call.enqueue(new Callback<FilesApiResponse>() {
          @Override
          public void onResponse(Call<FilesApiResponse> call, Response<FilesApiResponse> response) {
            if (!response.isSuccessful()) {
              filename= dosen.getProfile_image();

              Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();
            } else {
              filename = response.body().getData().getName();

              imvUpdateDosen.setImageURI(data.getData());
            }

            progressDialog.dismiss();
          }

          @Override
          public void onFailure(Call<FilesApiResponse> call, Throwable t) {
            filename= dosen.getProfile_image();

            Log.e("uploadFile", t.getLocalizedMessage());

            Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
          }
        });

      }
    }
  }
  
  private void updateDosen() {
    RadioButton activeGender= findViewById(rgGenderDosen.getCheckedRadioButtonId());
    String id= etEditIdDosen.getText().toString();
    String name= etEditNamaDosen.getText().toString();
    String phone= etEditTelpDosen.getText().toString();
    String address= etEditAlamatDosen.getText().toString();
    String gender= activeGender.getText().toString().equals("Laki-laki")?"male":"female";

    if (id.equals("")) {
      Toast.makeText(this, "Harap mengisi NIM", Toast.LENGTH_SHORT).show();
      etEditIdDosen.requestFocus();
      return;
    }

    if (name.equals("")) {
      Toast.makeText(this, "Harap mengisi Nama", Toast.LENGTH_SHORT).show();
      etEditNamaDosen.requestFocus();
      return;
    }

    if (phone.equals("")) {
      Toast.makeText(this, "Harap mengisi No Telp", Toast.LENGTH_SHORT).show();
      etEditTelpDosen.requestFocus();
      return;
    }

    if (address.equals("")) {
      Toast.makeText(this, "Harap mengisi Alamat", Toast.LENGTH_SHORT).show();
      etEditAlamatDosen.requestFocus();
      return;
    }

    DosenBody body= new DosenBody(id, name, phone, address, gender, filename);

    Call<DosenApiResponse> call= AppConfig.requestConfig(getApplicationContext()).updateDosen(dosen.getId(), body);

    call.enqueue(new Callback<DosenApiResponse>() {
      @Override
      public void onResponse(Call<DosenApiResponse> call, Response<DosenApiResponse> response) {
        if (!response.isSuccessful()) {
          DosenApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), DosenApiResponse.class);

          Toast.makeText(UpdateDosenActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(UpdateDosenActivity.this, "Data dosen berhasil diupdate", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<DosenApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(UpdateDosenActivity.this, "Dosen gagal ditambahkan", Toast.LENGTH_SHORT).show();
      }
    });

  } // updateDosen
  
}