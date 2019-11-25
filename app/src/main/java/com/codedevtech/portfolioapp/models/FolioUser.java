package com.codedevtech.portfolioapp.models;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FolioUser {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private List<String> roleFlags;


    public FolioUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getRoleFlags() {
        return roleFlags;
    }

    public void setRoleFlags(List<String> roleFlags) {
        this.roleFlags = roleFlags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean isFirstNameValid(){
        return (firstName!=null && !firstName.isEmpty());
    }

    public boolean isLastNameValid(){
        return (lastName!=null && !lastName.isEmpty());
    }

    public boolean isIdValid(){
        return (id!=null && !id.isEmpty());

    }

    public boolean isEmailValid(){
        return (email!=null && !email.isEmpty());
    }

    public boolean isRoleFlagsValid(){
        return (roleFlags!=null /*&& roleFlags.size()<4*/ && roleFlags.size()>0);
    }


}
