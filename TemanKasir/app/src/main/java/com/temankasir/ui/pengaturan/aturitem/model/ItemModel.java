package com.temankasir.ui.pengaturan.aturitem.model;

import android.os.Parcel;
import android.os.Parcelable;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class ItemModel implements Parcelable {

    private String idItem;
    private String idOutletItem;
    private String namaOutletItem;
    private String namaKategoriItem;
    private String namaItem;
    private String hargaItem;
    private String stokItem;
    private String status;

    public ItemModel(String idItem, String idOutletItem, String namaOutletItem, String namaKategoriItem, String namaItem, String hargaItem, String stokItem, String status) {
        this.idItem = idItem;
        this.idOutletItem = idOutletItem;
        this.namaOutletItem = namaOutletItem;
        this.namaKategoriItem = namaKategoriItem;
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
        this.stokItem = stokItem;
        this.status = status;
    }

    protected ItemModel(Parcel in) {
        idItem = in.readString();
        idOutletItem = in.readString();
        namaOutletItem = in.readString();
        namaKategoriItem = in.readString();
        namaItem = in.readString();
        hargaItem = in.readString();
        stokItem = in.readString();
        status = in.readString();
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdOutletItem() {
        return idOutletItem;
    }

    public void setIdOutletItem(String idOutletItem) {
        this.idOutletItem = idOutletItem;
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

    public String getStokItem() {
        return stokItem;
    }

    public void setStokItem(String stokItem) {
        this.stokItem = stokItem;
    }

    public String getNamaKategoriItem() {
        return namaKategoriItem;
    }

    public void setNamaKategoriItem(String namaKategoriItem) {
        this.namaKategoriItem = namaKategoriItem;
    }

    public String getNamaOutletItem() {
        return namaOutletItem;
    }

    public void setNamaOutletItem(String namaOutletItem) {
        this.namaOutletItem = namaOutletItem;
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
        parcel.writeString(idItem);
        parcel.writeString(idOutletItem);
        parcel.writeString(namaOutletItem);
        parcel.writeString(namaKategoriItem);
        parcel.writeString(namaItem);
        parcel.writeString(hargaItem);
        parcel.writeString(stokItem);
        parcel.writeString(status);
    }
}
