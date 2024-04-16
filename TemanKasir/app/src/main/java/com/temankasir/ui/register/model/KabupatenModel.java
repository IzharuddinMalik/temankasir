package com.temankasir.ui.register.model;

public class KabupatenModel {

    private String idKabupaten;
    private String namaKabupaten;

    public KabupatenModel(String idKabupaten, String namaKabupaten) {
        this.idKabupaten = idKabupaten;
        this.namaKabupaten = namaKabupaten;
    }

    public String getIdKabupaten() {
        return idKabupaten;
    }

    public void setIdKabupaten(String idKabupaten) {
        this.idKabupaten = idKabupaten;
    }

    public String getNamaKabupaten() {
        return namaKabupaten;
    }

    public void setNamaKabupaten(String namaKabupaten) {
        this.namaKabupaten = namaKabupaten;
    }
}
