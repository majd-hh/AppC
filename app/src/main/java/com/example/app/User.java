package com.example.app;

public class User {
    private String Firstname,Secondname,Email,Adress,City,Password,Phonenumber,Zibcode,Personalnumber,Country;
    private String Role = "User";
    private DataEncryption DE;
    public User(String firstname, String secondname, String email, String adress, String city, String password, String phonenumber, String zibcode, String personalnumber,String country) {
        Firstname = firstname;
        Secondname = secondname;
        Email = email;
        Adress = adress;
        City = city;
        Password = password;
        Phonenumber = phonenumber;
        Zibcode = zibcode;
        Personalnumber = personalnumber;
        Country = country;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getZibcode() {
        return Zibcode;
    }

    public void setZibcode(String zibcode) {
        Zibcode = zibcode;
    }

    public String getPersonalnumber() {
        return Personalnumber;
    }

    public void setPersonalnumber(String personalnumber) {
        Personalnumber = personalnumber;
    }

    public User(String firstname, String secondname, String email, String adress, String city) {
        Firstname = firstname;
        Secondname = secondname;
        Email = email;
        Adress = adress;
        City = city;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getSecondname() {
        return Secondname;
    }

    public void setSecondname(String secondname) {
        Secondname = secondname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
