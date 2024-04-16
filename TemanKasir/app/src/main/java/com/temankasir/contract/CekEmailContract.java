package com.temankasir.contract;

public interface CekEmailContract {

    interface cekEmailView {
        void showToastCekEmail(String message);
        void successCekEmail(String success);
    }

    interface cekEmailPresenter {
        void sendCekEmail(String email);
    }
}
