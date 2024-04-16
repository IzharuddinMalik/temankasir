package com.temankasir.contract;

import com.temankasir.ui.register.model.KecamatanModel;

import java.util.List;

public interface GetKecamatanContract {

    interface getKecamatanView {
        void getDataKecamatan(String[] idKecamatan, String[] namaKecamatan);
    }
}
