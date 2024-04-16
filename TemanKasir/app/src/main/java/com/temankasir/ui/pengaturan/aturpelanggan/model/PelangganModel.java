package com.temankasir.ui.pengaturan.aturpelanggan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import kotlinx.android.parcel.Parcelize;

@Parcelize
public class PelangganModel implements Parcelable {

    @SerializedName("idpelanggan") private String idPelanggan;
    @SerializedName("namapelanggan") private String namaPelanggan;
    @SerializedName("email") private String emailPelanggan;
    @SerializedName("notelepon") private String noTeleponPelanggan;
    @SerializedName("tanggallahir") private String tanggalLahirPelanggan;

    public PelangganModel(String idPelanggan, String namaPelanggan, String emailPelanggan, String noTeleponPelanggan, String tanggalLahirPelanggan){
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.noTeleponPelanggan = noTeleponPelanggan;
        this.tanggalLahirPelanggan = tanggalLahirPelanggan;
    }

    protected PelangganModel(Parcel in) {
        idPelanggan = in.readString();
        namaPelanggan = in.readString();
        emailPelanggan = in.readString();
        noTeleponPelanggan = in.readString();
        tanggalLahirPelanggan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPelanggan);
        dest.writeString(namaPelanggan);
        dest.writeString(emailPelanggan);
        dest.writeString(noTeleponPelanggan);
        dest.writeString(tanggalLahirPelanggan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PelangganModel> CREATOR = new Creator<PelangganModel>() {
        @Override
        public PelangganModel createFromParcel(Parcel in) {
            return new PelangganModel(in);
        }

        @Override
        public PelangganModel[] newArray(int size) {
            return new PelangganModel[size];
        }
    };

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getEmailPelanggan() {
        return emailPelanggan;
    }

    public void setEmailPelanggan(String emailPelanggan) {
        this.emailPelanggan = emailPelanggan;
    }

    public String getNoTeleponPelanggan() {
        return noTeleponPelanggan;
    }

    public void setNoTeleponPelanggan(String noTeleponPelanggan) {
        this.noTeleponPelanggan = noTeleponPelanggan;
    }

    public String getTanggalLahirPelanggan() {
        return tanggalLahirPelanggan;
    }

    public void setTanggalLahirPelanggan(String tanggalLahirPelanggan) {
        this.tanggalLahirPelanggan = tanggalLahirPelanggan;
    }
}
