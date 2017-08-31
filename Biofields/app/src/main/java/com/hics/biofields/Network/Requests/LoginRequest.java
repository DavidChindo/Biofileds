package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 8/30/17.
 */

public class LoginRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("passwd")
    private String password;

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

}
