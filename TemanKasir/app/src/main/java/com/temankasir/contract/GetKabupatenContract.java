package com.temankasir.contract;

import com.temankasir.ui.register.model.KabupatenModel;

import java.util.List;

public interface GetKabupatenContract {

    interface getKabupatenView {
        void getDataKabupaten(String[] idKabupaten, String[] namaKabupaten);
    }
}
