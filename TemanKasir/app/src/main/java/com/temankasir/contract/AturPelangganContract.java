package com.temankasir.contract;

public interface AturPelangganContract {

    interface aturPelangganView {
        void showLoading();
        void hideLoading();
        void showToast(String message);
    }
}
