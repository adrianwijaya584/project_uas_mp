package com.example.project_uas_mp;

import android.os.Parcel;
import android.os.Parcelable;

public class Dosen implements Parcelable {
    private String nama, nidn, telepon, alamat;
    private boolean kelamin;

    public Dosen(String nama, String nidn, String telepon, String alamat, boolean kelamin) {
        this.nama = nama;
        this.nidn = nidn;
        this.telepon = telepon;
        this.alamat = alamat;
        this.kelamin = kelamin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
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
        dest.writeString(this.nama);
        dest.writeString(this.nidn);
        dest.writeString(this.telepon);
        dest.writeString(this.alamat);
        dest.writeByte(this.kelamin ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.nama = source.readString();
        this.nidn = source.readString();
        this.telepon = source.readString();
        this.alamat = source.readString();
        this.kelamin = source.readByte() != 0;
    }

    protected Dosen(Parcel in) {
        this.nama = in.readString();
        this.nidn = in.readString();
        this.telepon = in.readString();
        this.alamat = in.readString();
        this.kelamin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Dosen> CREATOR = new Parcelable.Creator<Dosen>() {
        @Override
        public Dosen createFromParcel(Parcel source) {
            return new Dosen(source);
        }

        @Override
        public Dosen[] newArray(int size) {
            return new Dosen[size];
        }
    };
}
