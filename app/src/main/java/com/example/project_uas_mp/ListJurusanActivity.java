package com.example.project_uas_mp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_uas_mp.class_data.Jurusan;

import java.util.ArrayList;
import java.util.List;

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

    showData();

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

  private void showData() {
    ArrayAdapter<Jurusan> adapter= new ListJurusanAdapter();

    dataJurusan.clear();

    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SI", "Sistem Informasi"));
    dataJurusan.add(new Jurusan("SK", "Sistem Komputer"));
    dataJurusan.add(new Jurusan("TI", "Teknik Informasi"));

    lvJurusan.setAdapter(adapter);
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

          startActivity(intent);
        }
      });

      btnDelJurusan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder= new AlertDialog.Builder(ListJurusanActivity.this)
              .setTitle("Konfirmasi")
              .setMessage("Apakah anda ingin menghapus jurusan "+jurusan.getName()+" ?");

          builder.setPositiveButton("Hapus", null);
          builder.setNegativeButton("Kembali", null);

          builder.create().show();
        }
      });

      return convertView;
    }
  }

}

