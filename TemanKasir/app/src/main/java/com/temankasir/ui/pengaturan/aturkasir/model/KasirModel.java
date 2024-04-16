package com.temankasir.ui.pengaturan.aturkasir.model;

public class KasirModel {

    private String idKasir;
    private String namaKasir;
    private String namaOutletKasir;
    private String passwordKasir;
    private String idOutletKasir;

    public KasirModel(String idKasir, String namaKasir, String namaOutletKasir, String passwordKasir, String idOutletKasir){
        this.idKasir = idKasir;
        this.namaKasir = namaKasir;
        this.namaOutletKasir = namaOutletKasir;
        this.passwordKasir = passwordKasir;
        this.idOutletKasir = idOutletKasir;
    }

    public String getIdKasir() {
        return idKasir;
    }

    public void setIdKasir(String idKasir) {
        this.idKasir = idKasir;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public String getNamaOutletKasir() {
        return namaOutletKasir;
    }

    public void setNamaOutletKasir(String namaOutletKasir) {
        this.namaOutletKasir = namaOutletKasir;
    }

    public String getIdOutletKasir() {
        return idOutletKasir;
    }

    public void setIdOutletKasir(String idOutletKasir) {
        this.idOutletKasir = idOutletKasir;
    }

    public String getPasswordKasir() {
        return passwordKasir;
    }

    public void setPasswordKasir(String passwordKasir) {
        this.passwordKasir = passwordKasir;
    }
}
