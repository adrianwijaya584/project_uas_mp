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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Matkul;
import com.example.project_uas_mp.class_data.MatkulApiResponse;
import com.example.project_uas_mp.config.AppConfig;
import com.example.project_uas_mp.config.Sqlite;
import com.example.project_uas_mp.config.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMatkulActivity extends AppCompatActivity {
  Button btnAddMatkul;
  ListView lvMatkul;

  List<Matkul> dataMatkul= new ArrayList<>();

  Sqlite db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_matkul);

    db= new Sqlite(ListMatkulActivity.this);

    btnAddMatkul= findViewById(R.id.btnAddMatkul);
    lvMatkul= findViewById(R.id.lvMatkul);

    getMatktul();

    btnAddMatkul.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(ListMatkulActivity.this, AddMatkulActivity.class));
      }
    });
  } // oncreate

  @Override
  protected void onResume() {
    super.onResume();

    getMatktul();
  }

  private void setAdapter() {
    ListAdapterMatkul adapter= new ListAdapterMatkul();

    lvMatkul.setAdapter(adapter);
  } // setAdapter

  private void deleteMatkul(String id) {
    Call<MatkulApiResponse> call= AppConfig.requestConfig(ListMatkulActivity.this).deleteMatkul(id);

    call.enqueue(new Callback<MatkulApiResponse>() {
      @Override
      public void onResponse(Call<MatkulApiResponse> call, Response<MatkulApiResponse> response) {
        if (!response.isSuccessful()) {
          Toast.makeText(ListMatkulActivity.this, "Matkul gagal dihapus", Toast.LENGTH_SHORT).show();

          return;
        }

        Toast.makeText(ListMatkulActivity.this, "Matkul berhasil dihapus", Toast.LENGTH_SHORT).show();

        getMatktul();
      }

      @Override
      public void onFailure(Call<MatkulApiResponse> call, Throwable t) {
        Toast.makeText(ListMatkulActivity.this, "Matkul gagal dihapus", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void getMatktul() {
    dataMatkul.clear();

    if (!Utils.isNetworkAvailable(this)) {
      dataMatkul= db.getAllMatkul();
      Toast.makeText(this, "Tidak ada internet.", Toast.LENGTH_SHORT).show();
      setAdapter();

      return;
    }

    Call<MatkulApiResponse> call= AppConfig.requestConfig(getApplicationContext()).getAllMatkul();

    call.enqueue(new Callback<MatkulApiResponse>() {
      @Override
      public void onResponse(Call<MatkulApiResponse> call, Response<MatkulApiResponse> response) {
        if (!response.isSuccessful()) {
          return;
        }

        dataMatkul= response.body().getListMatkul();


        db.deleteMatkul();
        db.insertMatkul(dataMatkul);

        setAdapter();
      }

      @Override
      public void onFailure(Call<MatkulApiResponse> call, Throwable t) {
        System.out.println(t.getLocalizedMessage());
      }
    });
  } // getMatkul

  private class ListAdapterMatkul extends ArrayAdapter<Matkul> {

    public ListAdapterMatkul() {
      super(ListMatkulActivity.this, R.layout.list_matkul, dataMatkul);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      if (convertView==null) {
        convertView= getLayoutInflater().inflate(R.layout.list_matkul, parent, false);
      }

      Matkul matkul= dataMatkul.get(position);

      TextView tvListIdMatkul= convertView.findViewById(R.id.tvListIdMatkul);
      TextView tvListNamaMatkul= convertView.findViewById(R.id.tvListNamaMatkul);
      Button btnUpdateMatkul= convertView.findViewById(R.id.btnUpdateMatkul);
      Button btnDelMatkul= convertView.findViewById(R.id.btnDelMatkul);

      tvListIdMatkul.setText(matkul.getId());
      tvListNamaMatkul.setText(matkul.getName());

      btnUpdateMatkul.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent= new Intent(ListMatkulActivity.this, UpdateMatkulActivity.class);

          intent.putExtra("matkul",new Matkul(matkul.getId(), matkul.getMajor_id(),
              matkul.getName(), matkul.getCredits(), ""));

          startActivity(intent);
        }
      });

      btnDelMatkul.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder= new AlertDialog.Builder(ListMatkulActivity.this)
              .setTitle("Konfirmasi")
              .setMessage("Apakah anda ingin menghapus matkul ini ?");

          builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              deleteMatkul(matkul.getId());
            }
          });

          builder.setNegativeButton("Kembali", null);

          builder.create().show();
        }
      });

      return convertView;
    }
  } // ListAdapterMatkul
}