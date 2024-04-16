package com.temankasir.ui.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterPostModel {

    @SerializedName("users") private RegisterUserPostModel users;
    @SerializedName("businesses") private RegisterBusinessPostModel business;

    public RegisterPostModel(RegisterUserPostModel users, RegisterBusinessPostModel business) {
        this.users = users;
        this.business = business;
    }

    public RegisterUserPostModel getUsers() {
        return users;
    }

    public void setUsers(RegisterUserPostModel users) {
        this.users = users;
    }

    public RegisterBusinessPostModel getBusiness() {
        return business;
    }

    public void setBusiness(RegisterBusinessPostModel business) {
        this.business = business;
    }
}
