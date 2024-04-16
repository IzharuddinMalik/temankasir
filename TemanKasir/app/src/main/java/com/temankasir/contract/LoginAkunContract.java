package com.temankasir.contract;

public interface LoginAkunContract {

    interface loginAkunView{
        void showLoadingLogin();
        void hideLoadingLogin();
        void showToastLogin(String message);
        void successLogin(String idUser, String idBisnis, String statusUser, String idOutlet);
    }
}
