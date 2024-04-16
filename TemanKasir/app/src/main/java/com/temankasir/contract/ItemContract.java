package com.temankasir.contract;

public interface ItemContract {

    interface itemView {
        void showLoadingItem();
        void hideLoadingItem();
        void showToastItem(String message);
    }
}
