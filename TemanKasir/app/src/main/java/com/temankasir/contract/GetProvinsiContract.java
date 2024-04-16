package com.temankasir.contract;

import com.temankasir.ui.register.model.ProvinsiModel;

import java.util.List;

public interface GetProvinsiContract {

    interface getProvinsiView {
        void getDataProvinsi(String[] idProvinsi, String[] namaProvinsi);
    }
}
