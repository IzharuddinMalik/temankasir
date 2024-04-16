package com.temankasir.contract;

import com.temankasir.ui.register.model.RegisterPostModel;

import okhttp3.RequestBody;

public interface RegisterAkunContract {

    interface registerAkunView{
        void showLoadingRegister();
        void hideLoadingRegister();
        void showToastRegister(String message);
    }
}
