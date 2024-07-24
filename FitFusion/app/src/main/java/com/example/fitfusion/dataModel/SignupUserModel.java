package com.example.fitfusion.dataModel;

public class SignupUserModel {

    String Firstname;
    String Lastname;
    String Email;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public SignupUserModel(String firstname, String lastname, String email, String password) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Password = password;
    }

    String Password;

    public SignupUserModel() {
    }
}
