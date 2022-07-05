package com.example.project_uas_mp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Mahasiswa;
import com.example.project_uas_mp.class_data.MahasiswaApiResponse;
import com.example.project_uas_mp.class_data.MahasiswaBody;
import com.example.project_uas_mp.config.AppConfig;
import com.example.project_uas_mp.config.Sqlite;
import com.example.project_uas_mp.config.Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMahasiswaActivity extends AppCompatActivity {
  List<Mahasiswa> dataMhs= new ArrayList<>();
  Button btnAddMhs;
  ListView lvMhs;
  Sqlite db;
  SharedPreferences sp;
  SharedPreferences.Editor editor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_mahasiswa);

    db= new Sqlite(ListMahasiswaActivity.this);
    sp= PreferenceManager.getDefaultSharedPreferences(ListMahasiswaActivity.this);
    editor= sp.edit();

    btnAddMhs= findViewById(R.id.btnAddMhs);
    lvMhs= findViewById(R.id.lvMhs);

    btnAddMhs.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), AddMahasiswaActivity.class));
      }
    });

    getData();

  } // onCreate

  @Override
  protected void onResume() {
    super.onResume();

    getData();
  }

  private void setAdapter() {
    ArrayAdapter<Mahasiswa> adapter= new ListMahasiswaAdapter();

    lvMhs.setAdapter(adapter);
  } // setAdapter

  private void deleteData(String id) {
    Call<MahasiswaApiResponse> responseCall= AppConfig.requestConfig(getApplicationContext()).deleteMahasiswa(id);

    responseCall.enqueue(new Callback<MahasiswaApiResponse>() {
      @Override
      public void onResponse(Call<MahasiswaApiResponse> call, Response<MahasiswaApiResponse> response) {
        if (!response.isSuccessful()) {
          MahasiswaApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), MahasiswaApiResponse.class);

          Toast.makeText(ListMahasiswaActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(ListMahasiswaActivity.this, "Data mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();

        getData();
      }

      @Override
      public void onFailure(Call<MahasiswaApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(ListMahasiswaActivity.this, "Data mahasiswa gagal dihapus", Toast.LENGTH_SHORT).show();
      }
    });
  } // deleteData

  private void getData() {
    dataMhs.clear();

    if (!Utils.isNetworkAvailable(this)) {
      dataMhs= db.getAllMahasiswa();
      Toast.makeText(this, "Tidak ada internet.", Toast.LENGTH_SHORT).show();
      setAdapter();

      return;
    }

    Call<MahasiswaApiResponse> responseCall= AppConfig.requestConfig(getApplicationContext()).getAllMahasiswa();

    responseCall.enqueue(new Callback<MahasiswaApiResponse>() {
      @Override
      public void onResponse(Call<MahasiswaApiResponse> call, Response<MahasiswaApiResponse> response) {
        if (!response.isSuccessful()) {
          return;
        }

        dataMhs= response.body().getListMahasiswa();

        db.deleteMahasiswa();
        db.insertMahasiswa(dataMhs);

        setAdapter();

      }

      @Override
      public void onFailure(Call<MahasiswaApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());
      }
    });
  } // getData

  private class ListMahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
    public ListMahasiswaAdapter() {
      super(ListMahasiswaActivity.this, R.layout.list_mahasiswa, dataMhs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView==null) {
        convertView= getLayoutInflater().inflate(R.layout.list_mahasiswa, parent, false);
      }

      Mahasiswa mhs= dataMhs.get(position);

      ImageView imvListMhs= convertView.findViewById(R.id.imvListDosen);
      TextView tvListNimMhs= convertView.findViewById(R.id.tvListIdDosen);
      TextView tvListNamaMhs= convertView.findViewById(R.id.tvListNamaDosen);
      Button btnListUpdateMhs= convertView.findViewById(R.id.btnListUpdateDosen);
      Button btnListDeleteMhs= convertView.findViewById(R.id.btnListDeleteDosen);

      tvListNimMhs.setText(mhs.getId());
      tvListNamaMhs.setText(mhs.getName());

      Picasso.get().load(AppConfig.BASE_URL+"files/"+mhs.getProfile_image())
          .error(R.mipmap.ic_launcher)
          .placeholder(R.mipmap.ic_launcher)
          .centerCrop()
          .fit()
          .into(imvListMhs);

      btnListUpdateMhs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent= new Intent(ListMahasiswaActivity.this, UpdateMahasiswaActivity.class);

          intent.putExtra("mahasiswa",
              new MahasiswaBody(mhs.getId(), mhs.getName(), mhs.getPhone_number(), mhs.getAddress(),
                  mhs.getGender(), mhs.getProfile_image()));

          startActivity(intent);
        }
      }); // btnUpdateMhs

      btnListDeleteMhs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder= new AlertDialog.Builder(ListMahasiswaActivity.this)
              .setTitle("Konfirmasi")
              .setMessage("Apakah anda ingin menghapus data ini ?");

          builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              deleteData(mhs.getId());
            }
          });

          builder.setNegativeButton("Kembali", null);

          builder.create().show();
        }
      }); // btnDeleteMhs

      return convertView;
    }
  } // ListMahasiswaAdapter
}