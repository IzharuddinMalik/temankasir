package com.temankasir.contract;

public interface ProfileBisnisContract {

    interface profileBisnisView{
        void showLoadingProfileBisnis();
        void hideLoadingProfileBisnis();
        void showToastProfileBisnis(String message);
        void getDataBisnis(String emailBisnis, String namaBisnis, String noTeleponBisnis, String idProvinsi, String idKabupaten,
                           String idKecamatan, String logoBisnis);
    }
}
