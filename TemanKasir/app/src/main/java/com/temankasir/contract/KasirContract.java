package com.temankasir.contract;

public interface KasirContract {

    interface kasirView {
        void showLoadingKasir();
        void hideLoadingKasir();
        void showToastKasir(String message);
    }
}
