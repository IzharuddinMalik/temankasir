package com.temankasir.contract;

public interface OutletContract {

    interface outletView {
        void showLoadingOutlet();
        void hideLoadingOutlet();
        void showToastOutlet(String message);
    }
}
