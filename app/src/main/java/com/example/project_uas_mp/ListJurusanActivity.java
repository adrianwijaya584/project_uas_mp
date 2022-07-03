package com.example.project_uas_mp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.JurusanApiResponse;
import com.example.project_uas_mp.config.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListJurusanActivity extends AppCompatActivity {
  Button btnAddJurusan;
  ListView lvJurusan;
  List<Jurusan> dataJurusan= new ArrayList();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_jurusan);

    btnAddJurusan= findViewById(R.id.btnAddJurusan);
    lvJurusan= findViewById(R.id.lvJurusan);

    getData();

    btnAddJurusan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent= new Intent(getApplicationContext(), AddJurusanActivity.class);

        startActivity(intent);
      }
    });

      lvJurusan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Jurusan jurusan= dataJurusan.get(i);

        Toast.makeText(ListJurusanActivity.this, jurusan.getName(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  protected void onStart() {
    getData();
    super.onStart();
  }

  private void getData() {
    Call<JurusanApiResponse> request= AppConfig.requestConfig(getApplicationContext()).getAllJurusan();

    request.enqueue(new Callback<JurusanApiResponse>() {
      @Override
      public void onResponse(Call<JurusanApiResponse> call, Response<JurusanApiResponse> response) {
        JurusanApiResponse res= response.body();

        if (!response.isSuccessful()) {
          return;
        }

        dataJurusan= res.getListJurusan();

        setAdapter();
      }

      @Override
      public void onFailure(Call<JurusanApiResponse> call, Throwable t) {
        Log.d("jurusan", t.getLocalizedMessage());
      }
    });
  } // getData from API

  private void setAdapter() {
    ArrayAdapter<Jurusan> adapter= new ListJurusanAdapter();

    lvJurusan.setAdapter(adapter);
  } // set adapter for list view

  private void deleteData(String id) {
    Call<JurusanApiResponse> deleteRequest= AppConfig.requestConfig(getApplicationContext()).deleteJurusan(id);

    deleteRequest.enqueue(new Callback<JurusanApiResponse>() {
      @Override
      public void onResponse(Call<JurusanApiResponse> call, Response<JurusanApiResponse> response) {
        if (!response.isSuccessful()) {
          JurusanApiResponse errBody= new Gson().fromJson(response.errorBody().charStream(), JurusanApiResponse.class);

          Toast.makeText(ListJurusanActivity.this, errBody.getMessage(), Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(ListJurusanActivity.this, "Jurusan berhasil dihapus", Toast.LENGTH_SHORT).show();

        getData();
      }

      @Override
      public void onFailure(Call<JurusanApiResponse> call, Throwable t) {
        Toast.makeText(ListJurusanActivity.this, "jurusan gagal dihapus", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private class ListJurusanAdapter extends ArrayAdapter<Jurusan> {
    public ListJurusanAdapter() {
      super(ListJurusanActivity.this, R.layout.list_jurusan, dataJurusan);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView==null) {
        convertView= getLayoutInflater().inflate(R.layout.list_jurusan, parent, false);
      }

      Jurusan jurusan= dataJurusan.get(position);

      TextView tvListKJurusan= convertView.findViewById(R.id.tvListKJurusan);
      TextView tvListNJurusan= convertView.findViewById(R.id.tvListNJurusan);
      Button btnDelJurusan= convertView.findViewById(R.id.btnDelJurusan);
      Button btnUpdateJurusan= convertView.findViewById(R.id.btnUpdateJurusan);


      tvListKJurusan.setText(jurusan.getCode());
      tvListNJurusan.setText(jurusan.getName());

      btnUpdateJurusan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent= new Intent(getApplicationContext(), UpdateJurusanActivity.class);

          intent.putExtra("code", jurusan.getCode());
          intent.putExtra("name", jurusan.getName());

          startActivity(intent);
        }
      });

      btnDelJurusan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder= new AlertDialog.Builder(ListJurusanActivity.this)
              .setTitle("Konfirmasi")
              .setMessage("Apakah anda ingin menghapus jurusan "+jurusan.getName()+" ?");

          builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              deleteData(jurusan.getCode());
            }
          });

          builder.setNegativeButton("Kembali", null);

          builder.create().show();
        }
      });

      return convertView;
    }
  }

}

