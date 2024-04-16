package com.temankasir.ui.transaksi.model;

import android.os.Parcel;
import android.os.Parcelable;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class ResponseTransaksiModel implements Parcelable {

    private String noTransaksi;

    public ResponseTransaksiModel(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }

    protected ResponseTransaksiModel(Parcel in) {
        noTransaksi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noTransaksi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseTransaksiModel> CREATOR = new Creator<ResponseTransaksiModel>() {
        @Override
        public ResponseTransaksiModel createFromParcel(Parcel in) {
            return new ResponseTransaksiModel(in);
        }

        @Override
        public ResponseTransaksiModel[] newArray(int size) {
            return new ResponseTransaksiModel[size];
        }
    };

    public String getNoTransaksi() {
        return noTransaksi;
    }

    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }
}
