package com.temankasir.ui.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderModel implements Parcelable {

    private String idItem;
    private String namaItem;
    private String hargaItem;
    private String jumlahItem;

    public OrderModel(String idItem, String namaItem, String hargaItem, String jumlahItem) {
        this.idItem = idItem;
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
        this.jumlahItem = jumlahItem;
    }

    protected OrderModel(Parcel in) {
        idItem = in.readString();
        namaItem = in.readString();
        hargaItem = in.readString();
        jumlahItem = in.readString();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

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
        parcel.writeString(idItem);
        parcel.writeString(namaItem);
        parcel.writeString(hargaItem);
        parcel.writeString(jumlahItem);
    }
}
