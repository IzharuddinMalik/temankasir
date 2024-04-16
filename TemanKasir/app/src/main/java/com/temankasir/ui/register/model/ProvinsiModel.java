package com.temankasir.ui.register.model;

import androidx.versionedparcelable.ParcelField;

public class ProvinsiModel {

    private String idProvinsi;
    private String namaProvinsi;

    public ProvinsiModel(String idProvinsi, String namaProvinsi) {
        this.idProvinsi = idProvinsi;
        this.namaProvinsi = namaProvinsi;
    }

    public String getIdProvinsi() {
        return idProvinsi;
    }

    public void setIdProvinsi(String idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }
}
