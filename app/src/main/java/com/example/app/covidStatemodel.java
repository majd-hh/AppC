package com.example.app;

public class covidStatemodel {

    private String city;
    private String province;
    private String country;
    private String lastUpdate;
    private String keyId;
    private String confirmed;
    private String deaths;
    private String recovered;
    public covidStatemodel(){

    }
    public covidStatemodel(String city, String province, String country, String lastUpdate, String keyId, String confirmed, String deaths, String recovered) {
        this.city = city;
        this.province = province;
        this.country = country;
        this.lastUpdate = lastUpdate;
        this.keyId = keyId;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    @Override
    public String toString() {
        return  "\n"+"region:  " +province +"\n"+
                "country:  " + country +"\n"+
                "lastUpdate:  " + lastUpdate +"\n"+
                "confirmed:  " + confirmed +"\n"+
                "deaths:  " + deaths +"\n";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }
}
