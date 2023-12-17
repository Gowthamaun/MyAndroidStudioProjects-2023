package com.example.bloodapp.Model;

public class User {
    String name,phone,password,email,blood;
    public User()
    {

    }
    public User(String name, String phone, String password, String email, String blood) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.blood = blood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBlood() {
        return blood;
    }
}
