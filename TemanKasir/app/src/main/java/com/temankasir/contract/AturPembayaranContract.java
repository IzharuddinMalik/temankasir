package com.temankasir.contract;

public interface AturPembayaranContract {

    interface aturPembayaranView{
        void showLoading();
        void hideLoading();
        void showToast(String message);
    }
}
