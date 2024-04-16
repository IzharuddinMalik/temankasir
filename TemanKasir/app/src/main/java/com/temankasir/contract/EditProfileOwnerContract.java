package com.temankasir.contract;

public interface EditProfileOwnerContract {

    interface editProfileOwnerView {
        void showLoadingEditProfileOwner();
        void hideLoadingEditProfileOwner();
        void showToastEditProfileOwner(String message);
        void successEditProfileOwner();
    }

    interface editProfileOwnerPresenter {
        void sendEditProfileOwner(String idOwner, String username, String password);
    }
}
