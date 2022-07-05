package com.example.project_uas_mp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class AddDosenActivity extends AppCompatActivity {
  EditText etAddIdDosen, etAddNamaDosen, etAddTelpDosen, etAddAlamatDosen;
  RadioGroup rgGenderDosen;
  String filename= null;
  ImageView imvAddDosen;
  Button btnSendAddDosen, btnFileAddDosen;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_dosen);

    etAddIdDosen= findViewById(R.id.etAddIdDosen);
    etAddNamaDosen= findViewById(R.id.etAddNamaDosen);
    etAddTelpDosen= findViewById(R.id.etAddTelpDosen);
    etAddAlamatDosen= findViewById(R.id.etAddAlamatDosen);
    rgGenderDosen= findViewById(R.id.rgGenderDosen);
    imvAddDosen= findViewById(R.id.imvAddDosen);
    btnFileAddDosen= findViewById(R.id.btnFileAddDosen);
    btnSendAddDosen= findViewById(R.id.btnSendAddDosen);

//    etAddIdDosen.setText("1111");
//    etAddNamaDosen.setText("Dosen 1");
//    etAddTelpDosen.setText("088118");
//    etAddAlamatDosen.setText("Jln");

    btnFileAddDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult(intent, 1);
      }
    });

    btnSendAddDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addDosen();
      }
    });
  } // onCreate

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (data==null) {
      filename= null;

      imvAddDosen.setImageResource(R.mipmap.ic_launcher);
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
              Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();
            } else {
              filename = response.body().getData().getName();

              imvAddDosen.setImageURI(data.getData());
            }

           progressDialog.dismiss();
          }

          @Override
          public void onFailure(Call<FilesApiResponse> call, Throwable t) {
            Log.d("uploadFile", t.getLocalizedMessage());

            progressDialog.dismiss();

            Toast.makeText(getApplicationContext(), "File gagal diupload", Toast.LENGTH_SHORT).show();
          }
        });

      } // enqueue
    } // else
  } // onActivityRes

  private void addDosen() {
    RadioButton activeGender= findViewById(rgGenderDosen.getCheckedRadioButtonId());
    String id= etAddIdDosen.getText().toString();
    String name= etAddNamaDosen.getText().toString();
    String phone= etAddTelpDosen.getText().toString();
    String address= etAddAlamatDosen.getText().toString();
    String gender= activeGender.getText().toString().equals("Laki-laki")?"male":"female";

    if (filename == null) {
      Toast.makeText(this, "Harap memilih gambar", Toast.LENGTH_SHORT).show();
      return;
    }

    if (id.equals("")) {
      Toast.makeText(this, "Harap mengisi NIM", Toast.LENGTH_SHORT).show();
      etAddIdDosen.requestFocus();
      return;
    }

    if (name.equals("")) {
      Toast.makeText(this, "Harap mengisi Nama", Toast.LENGTH_SHORT).show();
      etAddNamaDosen.requestFocus();
      return;
    }

    if (phone.equals("")) {
      Toast.makeText(this, "Harap mengisi No Telp", Toast.LENGTH_SHORT).show();
      etAddTelpDosen.requestFocus();
      return;
    }

    if (address.equals("")) {
      Toast.makeText(this, "Harap mengisi Alamat", Toast.LENGTH_SHORT).show();
      etAddAlamatDosen.requestFocus();
      return;
    }

    DosenBody body= new DosenBody(id, name, phone, address, gender, filename);

    Call<DosenApiResponse> call= AppConfig.requestConfig(AddDosenActivity.this).addDosen(body);

    call.enqueue(new Callback<DosenApiResponse>() {
      @Override
      public void onResponse(Call<DosenApiResponse> call, Response<DosenApiResponse> response) {
        if (!response.isSuccessful()) {
          DosenApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), DosenApiResponse.class);

          Toast.makeText(AddDosenActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(AddDosenActivity.this, "Data dosen berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        finish();
      }

      @Override
      public void onFailure(Call<DosenApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(AddDosenActivity.this, "Data dosen gagal ditambahkan", Toast.LENGTH_SHORT).show();
      }
    });
  } // addDosen

} // class