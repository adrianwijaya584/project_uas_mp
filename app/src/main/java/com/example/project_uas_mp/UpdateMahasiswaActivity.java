package com.example.project_uas_mp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class UpdateMahasiswaActivity extends AppCompatActivity {
  EditText etEditNimMhs, etEditNamaMhs, etEditTelpMhs, etEditAlamatMhs;
  RadioGroup rgEditGenderMhs;
  Button btnSendEditMhs, btnFileEditMhs;
  ImageView imvUpdateMhs;
  RadioButton mhsLakiLaki, mhsPerempuan;
  MahasiswaBody mahasiswaBody;
  String filename= "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_mahasiswa);

    etEditNimMhs= findViewById(R.id.etEditNimMhs);
    etEditNamaMhs= findViewById(R.id.etEditNamaMhs);
    etEditTelpMhs= findViewById(R.id.etEditTelpMhs);
    etEditAlamatMhs= findViewById(R.id.etEditAlamatMhs);
    rgEditGenderMhs= findViewById(R.id.rgGenderDosen);
    btnSendEditMhs= findViewById(R.id.btnSendEditMhs);
    btnFileEditMhs= findViewById(R.id.btnFileEditMhs);
    imvUpdateMhs= findViewById(R.id.imvUpdateMhs);
    mhsLakiLaki= findViewById(R.id.dosenLakiLaki);
    mhsPerempuan= findViewById(R.id.dosenPerempuan);

    Intent intent= getIntent();

    mahasiswaBody= intent.getParcelableExtra("mahasiswa");

    etEditNimMhs.setText(mahasiswaBody.getId());
    etEditNamaMhs.setText(mahasiswaBody.getName());
    etEditTelpMhs.setText(mahasiswaBody.getPhone_number());
    etEditAlamatMhs.setText(mahasiswaBody.getAddress());
    filename= mahasiswaBody.getProfile_image();

    if (mahasiswaBody.getGender() == "male") {
      mhsLakiLaki.setChecked(true);
    } else {
      mhsPerempuan.setChecked(true);
    }

    btnFileEditMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        i.setType("image/*");

        startActivityForResult(i, 1);
      }
    });

    btnSendEditMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateMahasiswaActivity.this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin dengan data ini ?");

        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            updateMahasiswa();
          }
        });

        builder.setNegativeButton("Kembali", null);

        builder.create().show();
      }
    });
  } // onCreate

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data==null) {
      filename= mahasiswaBody.getProfile_image();

      imvUpdateMhs.setImageResource(R.mipmap.ic_launcher);

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
              filename= mahasiswaBody.getProfile_image();
              Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();

              return;
            }

            filename = response.body().getData().getName();

            imvUpdateMhs.setImageURI(data.getData());
          }

          @Override
          public void onFailure(Call<FilesApiResponse> call, Throwable t) {
            Log.d("uploadFile", t.getLocalizedMessage());

            filename= mahasiswaBody.getProfile_image();

            Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();
          }
        }); // onFail
      } // enqueue
    } // else
  } // onActivityRes

  private void updateMahasiswa() {
    String id= etEditNimMhs.getText().toString();
    String name= etEditNamaMhs.getText().toString();
    String phone= String.valueOf(etEditTelpMhs.getText().toString());
    String address= etEditAlamatMhs.getText().toString();
    RadioButton rb= findViewById(rgEditGenderMhs.getCheckedRadioButtonId());
    String gender= rb.getText().toString().equals("Laki-laki")?"male":"female";

    if (id.equals("")) {
      Toast.makeText(this, "Harap mengisi NIM", Toast.LENGTH_SHORT).show();
      etEditNimMhs.requestFocus();
      return;
    }

    if (name.equals("")) {
      Toast.makeText(this, "Harap mengisi Nama", Toast.LENGTH_SHORT).show();
      etEditNamaMhs.requestFocus();
      return;
    }

    if (phone.equals("")) {
      Toast.makeText(this, "Harap mengisi No Telp", Toast.LENGTH_SHORT).show();
      etEditTelpMhs.requestFocus();
      return;
    }

    if (address.equals("")) {
      Toast.makeText(this, "Harap mengisi Alamat", Toast.LENGTH_SHORT).show();
      etEditAlamatMhs.requestFocus();
      return;
    }

    Call<MahasiswaApiResponse> call= AppConfig.requestConfig(getApplicationContext()).updateMahasiswa(
        mahasiswaBody.getId(),
        new MahasiswaBody(
            id,
            name,
            phone,
            address,
            gender,
            filename
        )
    );

    call.enqueue(new Callback<MahasiswaApiResponse>() {
      @Override
      public void onResponse(Call<MahasiswaApiResponse> call, Response<MahasiswaApiResponse> response) {
        if (!response.isSuccessful()) {
          MahasiswaApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), MahasiswaApiResponse.class);

          Toast.makeText(UpdateMahasiswaActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(UpdateMahasiswaActivity.this, "Data mahasiswa berhasil diubah", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<MahasiswaApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(UpdateMahasiswaActivity.this, "Data mahasiswa gagal diubah", Toast.LENGTH_SHORT).show();
      }
    });
  }
} // class