package com.temankasir.ui.transaksi.model;

public class PaymentTunaiModel {
    private String idTunai;
    private String nominalTunai;

    public PaymentTunaiModel(String idTunai, String nominalTunai) {
        this.idTunai = idTunai;
        this.nominalTunai = nominalTunai;
    }

    public String getIdTunai() {
        return idTunai;
    }

    public void setIdTunai(String idTunai) {
        this.idTunai = idTunai;
    }

    public String getNominalTunai() {
        return nominalTunai;
    }

    public void setNominalTunai(String nominalTunai) {
        this.nominalTunai = nominalTunai;
    }
}
