package com.temankasir.ui.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterUserPostModel {

    @SerializedName("username") private String usernameRegis;
    @SerializedName("name") private String nameRegis;
    @SerializedName("email") private String emailRegis;
    @SerializedName("password") private String passwordRegis;

    public RegisterUserPostModel(String usernameRegis, String nameRegis, String emailRegis, String passwordRegis) {
        this.usernameRegis = usernameRegis;
        this.nameRegis = nameRegis;
        this.emailRegis = emailRegis;
        this.passwordRegis = passwordRegis;
    }

    public String getUsernameRegis() {
        return usernameRegis;
    }

    public void setUsernameRegis(String usernameRegis) {
        this.usernameRegis = usernameRegis;
    }

    public String getNameRegis() {
        return nameRegis;
    }

    public void setNameRegis(String nameRegis) {
        this.nameRegis = nameRegis;
    }

    public String getEmailRegis() {
        return emailRegis;
    }

    public void setEmailRegis(String emailRegis) {
        this.emailRegis = emailRegis;
    }

    public String getPasswordRegis() {
        return passwordRegis;
    }

    public void setPasswordRegis(String passwordRegis) {
        this.passwordRegis = passwordRegis;
    }
}
