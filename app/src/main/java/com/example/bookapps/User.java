package com.example.bookapps;

public class User {
    public String fullName, age, email, password, phoneNo;

    public User(){

    }

    public User(String fullName, String age, String email, String password, String phoneNo){
        this.fullName = fullName;
        this.age = age;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
    }


}
