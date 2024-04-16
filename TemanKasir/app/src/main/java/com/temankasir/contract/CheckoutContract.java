package com.temankasir.contract;

public interface CheckoutContract {

    interface checkoutView{
        void showLoading();
        void hideLoading();
        void showToast(String message);
    }
}
