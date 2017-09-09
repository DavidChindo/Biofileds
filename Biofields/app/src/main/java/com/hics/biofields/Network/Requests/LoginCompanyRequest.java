package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/1/17.
 */

public class LoginCompanyRequest {


    @SerializedName("email")
    private String email;
    @SerializedName("passwd")
    private String password;
    @SerializedName("company")
    private String company;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


}
