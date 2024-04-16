package com.temankasir.ui.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class ItemTransaksiModel implements Parcelable {

    private String namaItem;
    private String hargaItem;
    private String jumlahItem;

    public ItemTransaksiModel(String namaItem, String hargaItem, String jumlahItem) {
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
        this.jumlahItem = jumlahItem;
    }

    protected ItemTransaksiModel(Parcel in) {
        namaItem = in.readString();
        hargaItem = in.readString();
        jumlahItem = in.readString();
    }

    public static final Creator<ItemTransaksiModel> CREATOR = new Creator<ItemTransaksiModel>() {
        @Override
        public ItemTransaksiModel createFromParcel(Parcel in) {
            return new ItemTransaksiModel(in);
        }

        @Override
        public ItemTransaksiModel[] newArray(int size) {
            return new ItemTransaksiModel[size];
        }
    };

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getHargaItem() {
        return hargaItem;
    }

    public void setHargaItem(String hargaItem) {
        this.hargaItem = hargaItem;
    }

    public String getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(String jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(namaItem);
        parcel.writeString(hargaItem);
        parcel.writeString(jumlahItem);
    }
}
