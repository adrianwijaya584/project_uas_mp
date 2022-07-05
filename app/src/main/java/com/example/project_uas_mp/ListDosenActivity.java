package com.example.project_uas_mp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.example.project_uas_mp.class_data.Dosen;
import com.example.project_uas_mp.class_data.DosenApiResponse;
import com.example.project_uas_mp.class_data.DosenBody;
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

public class ListDosenActivity extends AppCompatActivity {
  Button btnAddDosen;
  ListView lvDosen;
  List<Dosen> dataDosen= new ArrayList<>();
  Sqlite db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_dosen);

    db= new Sqlite(ListDosenActivity.this);

    lvDosen= findViewById(R.id.lvDosen);
    btnAddDosen= findViewById(R.id.btnAddDosen);

    btnAddDosen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), AddDosenActivity.class));
      }
    });

    getDosen();
  } // onCreate

  @Override
  protected void onResume() {
    super.onResume();

    getDosen();
  }

  private void setAdapter() {
    ArrayAdapter<Dosen> adapter= new ListAdapterDosen();

    lvDosen.setAdapter(adapter);
  }

  private void deleteDosen(String id) {
    Call<DosenApiResponse> call= AppConfig.requestConfig(getApplicationContext()).deleteDosen(id);

    call.enqueue(new Callback<DosenApiResponse>() {
      @Override
      public void onResponse(Call<DosenApiResponse> call, Response<DosenApiResponse> response) {
        if (!response.isSuccessful()) {
          DosenApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), DosenApiResponse.class);

          Toast.makeText(ListDosenActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(ListDosenActivity.this, "Data dosen berhasil dihapus", Toast.LENGTH_SHORT).show();

        getDosen();
      }

      @Override
      public void onFailure(Call<DosenApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());

        Toast.makeText(ListDosenActivity.this, "Data dosen gagal dihapus", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void getDosen() {
    dataDosen.clear();

    if (!Utils.isNetworkAvailable(this)) {
      dataDosen= db.getAllDosen();
      setAdapter();

      Toast.makeText(this, "Tidak ada internet.", Toast.LENGTH_SHORT).show();

      return;
    }

    Call<DosenApiResponse> call= AppConfig.requestConfig(getApplicationContext()).getAllDosen();

    call.enqueue(new Callback<DosenApiResponse>() {
      @Override
      public void onResponse(Call<DosenApiResponse> call, Response<DosenApiResponse> response) {
        if (!response.isSuccessful()) {
          return;
        }

        DosenApiResponse apiResponse= response.body();

        dataDosen= apiResponse.getData();

        db.deleteDosen();
        db.insertDosen(dataDosen);

        setAdapter();
      }

      @Override
      public void onFailure(Call<DosenApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());
      }
    });
  } // getDosen

  private class ListAdapterDosen extends ArrayAdapter<Dosen> {

    public ListAdapterDosen() {
      super(ListDosenActivity.this, R.layout.list_dosen, dataDosen);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView==null) {
        convertView= getLayoutInflater().inflate(R.layout.list_dosen, parent, false);
      }

      Dosen dosen= dataDosen.get(position);

      ImageView imvListDosen= convertView.findViewById(R.id.imvListDosen);
      TextView tvListIdDosen= convertView.findViewById(R.id.tvListIdDosen);
      TextView tvListNamaDosen= convertView.findViewById(R.id.tvListNamaDosen);
      Button btnListUpdateDosen= convertView.findViewById(R.id.btnListUpdateDosen);
      Button btnListDeleteDosen= convertView.findViewById(R.id.btnListDeleteDosen);


      tvListIdDosen.setText(dosen.getId());
      tvListNamaDosen.setText(dosen.getName());

      Picasso.get()
          .load(AppConfig.BASE_URL+"files/"+dosen.getProfile_image())
          .error(R.mipmap.ic_launcher)
          .placeholder(R.mipmap.ic_launcher)
          .centerCrop()
          .fit()
          .into(imvListDosen);

      btnListUpdateDosen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent= new Intent(ListDosenActivity.this, UpdateDosenActivity.class);

            intent.putExtra("dosen",new DosenBody(
              dosen.getId(), dosen.getName(), dosen.getPhone_number(),
              dosen.getAddress(), dosen.getGender(),dosen.getProfile_image()
          ));

          startActivity(intent);
        }
      });

      btnListDeleteDosen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder= new AlertDialog.Builder(ListDosenActivity.this)
              .setTitle("Konfirmasi")
              .setMessage("Apakah anda ingin menghapus data ini ?");

          builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              deleteDosen(dosen.getId());
            }
          });

          builder.setNegativeButton("Kembali", null);

          builder.create().show();
        }
      });

      return convertView;
    }
  } // ListAdapterDosen
}