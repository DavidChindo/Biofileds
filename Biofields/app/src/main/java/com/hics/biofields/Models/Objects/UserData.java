package com.hics.biofields.Models.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/14/17.
 */

public class UserData {

    @SerializedName("usr_id")
    private String id;
    @SerializedName("usr_email")
    private String email;
    @SerializedName("role")
    private String role;
    @SerializedName("company")
    private String company;
    @SerializedName("login")
    private boolean login;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
