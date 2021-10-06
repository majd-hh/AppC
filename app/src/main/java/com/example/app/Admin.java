package com.example.app;

public class Admin {
    private String ad_username, ad_password;
    private String Role = "Admin";

    public Admin(String ad_username, String ad_password) {
        this.ad_username = ad_username;

        this.ad_password = ad_password;
    }

    public String getAd_username() {
        return ad_username;
    }

    public void setAd_username(String ad_username) {
        this.ad_username = ad_username;
    }



    public String getAd_password() {
        return ad_password;
    }

    public void setAd_password(String ad_password) {
        this.ad_password = ad_password;
    }
}
