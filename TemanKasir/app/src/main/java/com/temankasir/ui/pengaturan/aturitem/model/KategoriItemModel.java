package com.temankasir.ui.pengaturan.aturitem.model;

import android.os.Parcel;
import android.os.Parcelable;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class KategoriItemModel implements Parcelable {

    private String idKategori;
    private String idOutletKategori;
    private String namaOutletKategori;
    private String namaKategori;
    private String status;

    public KategoriItemModel(String idKategori, String idOutletKategori, String namaOutletKategori, String namaKategori, String status){
        this.idOutletKategori = idOutletKategori;
        this.namaOutletKategori = namaOutletKategori;
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.status = status;
    }

    protected KategoriItemModel(Parcel in) {
        idKategori = in.readString();
        idOutletKategori = in.readString();
        namaOutletKategori = in.readString();
        namaKategori = in.readString();
        status = in.readString();
    }

    public static final Creator<KategoriItemModel> CREATOR = new Creator<KategoriItemModel>() {
        @Override
        public KategoriItemModel createFromParcel(Parcel in) {
            return new KategoriItemModel(in);
        }

        @Override
        public KategoriItemModel[] newArray(int size) {
            return new KategoriItemModel[size];
        }
    };

    public String getIdOutletKategori() {
        return idOutletKategori;
    }

    public void setIdOutletKategori(String idOutletKategori) {
        this.idOutletKategori = idOutletKategori;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getNamaOutletKategori() {
        return namaOutletKategori;
    }

    public void setNamaOutletKategori(String namaOutletKategori) {
        this.namaOutletKategori = namaOutletKategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idKategori);
        parcel.writeString(idOutletKategori);
        parcel.writeString(namaOutletKategori);
        parcel.writeString(namaKategori);
        parcel.writeString(status);
    }
}
