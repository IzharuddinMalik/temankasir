package com.temankasir.ui.register.model;

import com.google.gson.annotations.SerializedName;

public class RegisterBusinessPostModel {

    @SerializedName("email") private String emailBusinessRegis;
    @SerializedName("name") private String nameBusinessRegis;
    @SerializedName("phone") private String phoneBusinessRegis;
    @SerializedName("province_id") private String provinceIDBusinessRegis;
    @SerializedName("city_id") private String cityIDBusinessRegis;
    @SerializedName("subdistrict_id") private String subdistrictIDBusinessRegis;
    @SerializedName("address") private String addressBusinessRegis;
    @SerializedName("logo") private String logoBusinessRegis;

    public RegisterBusinessPostModel(String emailBusinessRegis, String nameBusinessRegis, String phoneBusinessRegis, String provinceIDBusinessRegis,
                                     String cityIDBusinessRegis, String subdistrictIDBusinessRegis, String addressBusinessRegis, String logoBusinessRegis) {
        this.emailBusinessRegis = emailBusinessRegis;
        this.nameBusinessRegis = nameBusinessRegis;
        this.phoneBusinessRegis = phoneBusinessRegis;
        this.provinceIDBusinessRegis = provinceIDBusinessRegis;
        this.cityIDBusinessRegis = cityIDBusinessRegis;
        this.subdistrictIDBusinessRegis = subdistrictIDBusinessRegis;
        this.addressBusinessRegis = addressBusinessRegis;
        this.logoBusinessRegis = logoBusinessRegis;
    }

    public String getEmailBusinessRegis() {
        return emailBusinessRegis;
    }

    public void setEmailBusinessRegis(String emailBusinessRegis) {
        this.emailBusinessRegis = emailBusinessRegis;
    }

    public String getNameBusinessRegis() {
        return nameBusinessRegis;
    }

    public void setNameBusinessRegis(String nameBusinessRegis) {
        this.nameBusinessRegis = nameBusinessRegis;
    }

    public String getPhoneBusinessRegis() {
        return phoneBusinessRegis;
    }

    public void setPhoneBusinessRegis(String phoneBusinessRegis) {
        this.phoneBusinessRegis = phoneBusinessRegis;
    }

    public String getProvinceIDBusinessRegis() {
        return provinceIDBusinessRegis;
    }

    public void setProvinceIDBusinessRegis(String provinceIDBusinessRegis) {
        this.provinceIDBusinessRegis = provinceIDBusinessRegis;
    }

    public String getCityIDBusinessRegis() {
        return cityIDBusinessRegis;
    }

    public void setCityIDBusinessRegis(String cityIDBusinessRegis) {
        this.cityIDBusinessRegis = cityIDBusinessRegis;
    }

    public String getSubdistrictIDBusinessRegis() {
        return subdistrictIDBusinessRegis;
    }

    public void setSubdistrictIDBusinessRegis(String subdistrictIDBusinessRegis) {
        this.subdistrictIDBusinessRegis = subdistrictIDBusinessRegis;
    }

    public String getAddressBusinessRegis() {
        return addressBusinessRegis;
    }

    public void setAddressBusinessRegis(String addressBusinessRegis) {
        this.addressBusinessRegis = addressBusinessRegis;
    }

    public String getLogoBusinessRegis() {
        return logoBusinessRegis;
    }

    public void setLogoBusinessRegis(String logoBusinessRegis) {
        this.logoBusinessRegis = logoBusinessRegis;
    }
}
