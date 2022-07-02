package com.example.project_uas_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddJurusanActivity extends AppCompatActivity {
  TextView etAddKodeJ, etAddNamaJ;
  Button btnSendAddJ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_jurusan);

    etAddKodeJ= findViewById(R.id.etAddKodeJ);
    etAddKodeJ= findViewById(R.id.etAddKodeJ);
    btnSendAddJ= findViewById(R.id.btnSendAddJ);

    btnSendAddJ.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }
}