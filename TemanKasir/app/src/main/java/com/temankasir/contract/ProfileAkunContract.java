package com.temankasir.contract;

public interface ProfileAkunContract {

    interface profileAkunView {
        void showLoadingProfileAkun();
        void hideLoadingProfileAkun();
        void showToastProfileAkun(String message);
        void getDataProfile(String username, String namaBisnis, String logoBisnis);
    }

    interface profileAkunPresenter {
        void sendProfileAkun(String idOwner);
    }
}
