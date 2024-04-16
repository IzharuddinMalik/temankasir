package com.temankasir.contract;

public interface ProfileOwnerContract {

    interface profileOwnerView{
        void showLoadingProfileOwner();
        void hideLoadingProfileOwner();
        void showToastProfileOwner(String message);
        void getDataProfileOwner(String username, String password);
    }

    interface profileOwnerPresenter {
        void sendProfileOwner(String idOwner);
    }
}
