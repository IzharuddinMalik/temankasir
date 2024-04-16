package com.temankasir.ui.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class HistoriTransaksiModel implements Parcelable {

    private String idTransaksi;
    private String kodeTransaksi;
    private String namaOutletTransaksi;
    private String namaKasirTransaksi;
    private String namaPelangganTransaksi;
    private String dateTransaksi;
    private String totalTransaksi;
    private String jumlahdibayarTransaksi;
    private String statusRefundTransaksi;
    private String statusDibayarTransaksi;
    private String tipePembayaranTransaksi;
    private ArrayList<ItemTransaksiModel> itemListTransaksi;

    public HistoriTransaksiModel(String idTransaksi, String kodeTransaksi, String namaOutletTransaksi, String namaKasirTransaksi, String namaPelangganTransaksi,
                                 String dateTransaksi, String totalTransaksi, String jumlahdibayarTransaksi, String statusRefundTransaksi, String statusDibayarTransaksi,
                                 String tipePembayaranTransaksi, ArrayList<ItemTransaksiModel> itemListTransaksi) {
        this.idTransaksi = idTransaksi;
        this.kodeTransaksi = kodeTransaksi;
        this.namaOutletTransaksi = namaOutletTransaksi;
        this.namaKasirTransaksi = namaKasirTransaksi;
        this.namaPelangganTransaksi = namaPelangganTransaksi;
        this.dateTransaksi = dateTransaksi;
        this.totalTransaksi = totalTransaksi;
        this.jumlahdibayarTransaksi = jumlahdibayarTransaksi;
        this.statusRefundTransaksi = statusRefundTransaksi;
        this.statusDibayarTransaksi = statusDibayarTransaksi;
        this.tipePembayaranTransaksi = tipePembayaranTransaksi;
        this.itemListTransaksi = itemListTransaksi;
    }

    protected HistoriTransaksiModel(Parcel in) {
        idTransaksi = in.readString();
        kodeTransaksi = in.readString();
        namaOutletTransaksi = in.readString();
        namaKasirTransaksi = in.readString();
        namaPelangganTransaksi = in.readString();
        dateTransaksi = in.readString();
        totalTransaksi = in.readString();
        jumlahdibayarTransaksi = in.readString();
        statusRefundTransaksi = in.readString();
        statusDibayarTransaksi = in.readString();
        tipePembayaranTransaksi = in.readString();
        itemListTransaksi = in.createTypedArrayList(ItemTransaksiModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTransaksi);
        dest.writeString(kodeTransaksi);
        dest.writeString(namaOutletTransaksi);
        dest.writeString(namaKasirTransaksi);
        dest.writeString(namaPelangganTransaksi);
        dest.writeString(dateTransaksi);
        dest.writeString(totalTransaksi);
        dest.writeString(jumlahdibayarTransaksi);
        dest.writeString(statusRefundTransaksi);
        dest.writeString(statusDibayarTransaksi);
        dest.writeString(tipePembayaranTransaksi);
        dest.writeTypedList(itemListTransaksi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistoriTransaksiModel> CREATOR = new Creator<HistoriTransaksiModel>() {
        @Override
        public HistoriTransaksiModel createFromParcel(Parcel in) {
            return new HistoriTransaksiModel(in);
        }

        @Override
        public HistoriTransaksiModel[] newArray(int size) {
            return new HistoriTransaksiModel[size];
        }
    };

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getNamaOutletTransaksi() {
        return namaOutletTransaksi;
    }

    public void setNamaOutletTransaksi(String namaOutletTransaksi) {
        this.namaOutletTransaksi = namaOutletTransaksi;
    }

    public String getNamaKasirTransaksi() {
        return namaKasirTransaksi;
    }

    public void setNamaKasirTransaksi(String namaKasirTransaksi) {
        this.namaKasirTransaksi = namaKasirTransaksi;
    }

    public String getNamaPelangganTransaksi() {
        return namaPelangganTransaksi;
    }

    public void setNamaPelangganTransaksi(String namaPelangganTransaksi) {
        this.namaPelangganTransaksi = namaPelangganTransaksi;
    }

    public String getDateTransaksi() {
        return dateTransaksi;
    }

    public void setDateTransaksi(String dateTransaksi) {
        this.dateTransaksi = dateTransaksi;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public String getJumlahdibayarTransaksi() {
        return jumlahdibayarTransaksi;
    }

    public void setJumlahdibayarTransaksi(String jumlahdibayarTransaksi) {
        this.jumlahdibayarTransaksi = jumlahdibayarTransaksi;
    }

    public String getStatusRefundTransaksi() {
        return statusRefundTransaksi;
    }

    public void setStatusRefundTransaksi(String statusRefundTransaksi) {
        this.statusRefundTransaksi = statusRefundTransaksi;
    }

    public String getStatusDibayarTransaksi() {
        return statusDibayarTransaksi;
    }

    public void setStatusDibayarTransaksi(String statusDibayarTransaksi) {
        this.statusDibayarTransaksi = statusDibayarTransaksi;
    }

    public String getTipePembayaranTransaksi() {
        return tipePembayaranTransaksi;
    }

    public void setTipePembayaranTransaksi(String tipePembayaranTransaksi) {
        this.tipePembayaranTransaksi = tipePembayaranTransaksi;
    }

    public ArrayList<ItemTransaksiModel> getItemListTransaksi() {
        return itemListTransaksi;
    }

    public void setItemListTransaksi(ArrayList<ItemTransaksiModel> itemListTransaksi) {
        this.itemListTransaksi = itemListTransaksi;
    }
}
