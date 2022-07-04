package com.example.project_uas_mp.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;

import com.example.project_uas_mp.class_data.Dosen;
import com.example.project_uas_mp.class_data.Jurusan;
import com.example.project_uas_mp.class_data.Mahasiswa;
import com.example.project_uas_mp.class_data.Matkul;

import java.util.ArrayList;
import java.util.List;

public class Sqlite extends SQLiteOpenHelper {
  private static final int DB_VERSION= 1;
  private static final String DB_NAME= "project_uas";
  private static final String TAG= "sqlite";

  public Sqlite(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // create tables

    db.execSQL("CREATE TABLE jurusan (id String PRIMARY KEY, name String, created_at String)");

    db.execSQL("CREATE TABLE mahasiswa (id String PRIMARY KEY, name String, phone_number String, " +
        "address String, gender String, profile_image String, created_at String)");

    db.execSQL("CREATE TABLE dosen (id String PRIMARY KEY, name String, phone_number String, " +
        "address String, gender String, profile_image String, created_at String)");

    db.execSQL("CREATE TABLE matkul (id String PRIMARY KEY, major_id String, name String, " +
        "credits String, created_at String)");

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // delete table if upgrade

    db.execSQL("DROP TABLE IF EXISTS jurusan");
    db.execSQL("DROP TABLE IF EXISTS mahasiswa");
    db.execSQL("DROP TABLE IF EXISTS dosen");
    db.execSQL("DROP TABLE IF EXISTS matkul");
  }

  public List<Jurusan> getAllJurusan() {
    List<Jurusan> jurusan= new ArrayList<>();

    SQLiteDatabase db= getReadableDatabase();

    Cursor cursor= db.rawQuery("SELECT * FROM jurusan", null);

    if (cursor != null) {
      cursor.moveToFirst();

      do {
        jurusan.add(new Jurusan(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
      } while (cursor.moveToNext());
    }

    db.close();

    return  jurusan;
  }

  public void insertJurusan(List<Jurusan> jurusan) {
    SQLiteDatabase db= getWritableDatabase();

    try {
      for (int i = 0; i < jurusan.size() ; i++) {
        ContentValues cv= new ContentValues();

        cv.put("id", jurusan.get(i).getCode());
        cv.put("name", jurusan.get(i).getName());
        cv.put("created_at", jurusan.get(i).getCreated_at());

        db.insertOrThrow("jurusan",null, cv);
      }

      db.close();
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
  } // insertJurusan

  public void deleteJurusan() {
    SQLiteDatabase db= getWritableDatabase();

    db.delete("jurusan", null, null);

    db.close();
  }

  public List<Mahasiswa> getAllMahasiswa() {
    List<Mahasiswa> mahasiswaList= new ArrayList<>();

    SQLiteDatabase db= getWritableDatabase();

    Cursor cursor= db.rawQuery("SELECT * FROM mahasiswa",null, null);

    if (cursor!=null) {
      try {
        cursor.moveToFirst();

        do {
          String id= cursor.getString(cursor.getColumnIndexOrThrow("id"));
          String name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
          String phone_number= cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
          String address= cursor.getString(cursor.getColumnIndexOrThrow("address"));
          String gender= cursor.getString(cursor.getColumnIndexOrThrow("gender"));
          String profile_image= cursor.getString(cursor.getColumnIndexOrThrow("profile_image"));
          String created_at= cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

          Mahasiswa mahasiswa= new Mahasiswa(id, name, phone_number,
              address, gender, profile_image, created_at);

          mahasiswaList.add(mahasiswa);
        } while (cursor.moveToNext());
      } catch (Exception e) {
        Log.e(TAG, e.getLocalizedMessage());
      }
    }

    db.close();

    return mahasiswaList;
  }

  public void insertMahasiswa(List<Mahasiswa> mahasiswaList) {
    SQLiteDatabase db= getWritableDatabase();

    try {
      for (int i = 0; i < mahasiswaList.size(); i++) {
        Mahasiswa mahasiswa= mahasiswaList.get(i);

        ContentValues contentValues= new ContentValues();

        contentValues.put("id", mahasiswa.getId());
        contentValues.put("name", mahasiswa.getName());
        contentValues.put("phone_number", mahasiswa.getPhone_number());
        contentValues.put("address", mahasiswa.getAddress());
        contentValues.put("gender", mahasiswa.getGender());
        contentValues.put("profile_image", mahasiswa.getProfile_image());
        contentValues.put("created_at", mahasiswa.getCreated_at());

        db.insertOrThrow("mahasiswa", null, contentValues);
      }

    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage());
    }

    db.close();
  }

  public void deleteMahasiswa() {
    SQLiteDatabase db= getWritableDatabase();

    db.delete("mahasiswa", null, null);

    db.close();
  }

  public List<Dosen> getAllDosen() {
    List<Dosen> dosenList= new ArrayList<>();

    SQLiteDatabase db= getReadableDatabase();

    Cursor cursor= db.rawQuery("SELECT * FROM dosen", null, null);

    if (cursor != null) {
      cursor.moveToFirst();

      do {
        String id= cursor.getString(cursor.getColumnIndexOrThrow("id"));
        String name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String phone_number= cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
        String address= cursor.getString(cursor.getColumnIndexOrThrow("address"));
        String gender= cursor.getString(cursor.getColumnIndexOrThrow("gender"));
        String profile_image= cursor.getString(cursor.getColumnIndexOrThrow("profile_image"));
        String created_at= cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

        Dosen dosen= new Dosen(id, name, phone_number,
            address, gender, profile_image, created_at);

        dosenList.add(dosen);
      } while (cursor.moveToNext());
    }

    db.close();

    return dosenList;
  }

  public void insertDosen(List<Dosen> dosenList) {
    SQLiteDatabase db= getWritableDatabase();

    try {
      for (int i = 0; i < dosenList.size() ; i++) {
        ContentValues contentValues= new ContentValues();

        Dosen dosen= dosenList.get(i);

        contentValues.put("id", dosen.getId());
        contentValues.put("name", dosen.getName());
        contentValues.put("phone_number", dosen.getPhone_number());
        contentValues.put("address", dosen.getAddress());
        contentValues.put("gender", dosen.getGender());
        contentValues.put("profile_image", dosen.getProfile_image());
        contentValues.put("created_at", dosen.getCreated_at());

        db.insertOrThrow("dosen", null, contentValues);
      }
    } catch (Exception e) {
      Log.e(TAG, e.getLocalizedMessage());
    }

    db.close();
  }

  public void deleteDosen() {
    SQLiteDatabase db= getWritableDatabase();

    db.delete("dosen", null, null);

    db.close();
  }

  public List<Matkul> getAllMatkul() {
    List<Matkul> matkulList= new ArrayList<>();

    SQLiteDatabase db= getReadableDatabase();

    Cursor cursor= db.rawQuery("SELECT * FROM matkul", null, null);

    if (cursor!=null) {
      cursor.moveToFirst();

      try {
        do {
          String id= cursor.getString(cursor.getColumnIndexOrThrow("id"));
          String name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
          String major_id= cursor.getString(cursor.getColumnIndexOrThrow("major_id"));
          String credits= cursor.getString(cursor.getColumnIndexOrThrow("credits"));
          String created_at= cursor.getString(cursor.getColumnIndexOrThrow("created_at"));

          Matkul matkul= new Matkul(id, major_id, name, credits, created_at);

          matkulList.add(matkul);
        } while (cursor.moveToNext());

      } catch (Exception e) {
        Log.e("getAllMatkul", e.getLocalizedMessage());
      }

      db.close();
    } // getAllMatkul

    return  matkulList;
  }

  public void insertMatkul(List<Matkul> matkulList) {
    SQLiteDatabase db= getWritableDatabase();

    try {
      for (int i = 0; i < matkulList.size(); i++) {
        ContentValues contentValues= new ContentValues();

        Matkul matkul= matkulList.get(i);

        contentValues.put("id", matkul.getId());
        contentValues.put("major_id", matkul.getMajor_id());
        contentValues.put("name", matkul.getName());
        contentValues.put("credits", matkul.getCredits());
        contentValues.put("created_at", matkul.getCreated_at());

        db.insertOrThrow("matkul", null, contentValues);
      }
    } catch (Exception e) {
      Log.e("insertMatkul", e.getLocalizedMessage());
    }

    db.close();
  } // insertMatkul

  public void deleteMatkul() {
    SQLiteDatabase db= getWritableDatabase();

    db.delete("matkul", null, null);

    db.close();
  } // deleteMatkul
}
