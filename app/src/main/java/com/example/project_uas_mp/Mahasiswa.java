package com.example.project_uas_mp;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable {
    private String nim, nama, jurusan, telepon, alamat, path;
    private boolean kelamin;

    public Mahasiswa(String nim, String nama, String jurusan, String telepon, String alamat, String path, boolean kelamin) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.telepon = telepon;
        this.alamat = alamat;
        this.path = path;
        this.kelamin = kelamin;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isKelamin() {
        return kelamin;
    }

    public void setKelamin(boolean kelamin) {
        this.kelamin = kelamin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nim);
        dest.writeString(this.nama);
        dest.writeString(this.jurusan);
        dest.writeString(this.telepon);
        dest.writeString(this.alamat);
        dest.writeString(this.path);
        dest.writeByte(this.kelamin ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.nim = source.readString();
        this.nama = source.readString();
        this.jurusan = source.readString();
        this.telepon = source.readString();
        this.alamat = source.readString();
        this.path = source.readString();
        this.kelamin = source.readByte() != 0;
    }

    protected Mahasiswa(Parcel in) {
        this.nim = in.readString();
        this.nama = in.readString();
        this.jurusan = in.readString();
        this.telepon = in.readString();
        this.alamat = in.readString();
        this.path = in.readString();
        this.kelamin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Mahasiswa> CREATOR = new Parcelable.Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel source) {
            return new Mahasiswa(source);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };
}
