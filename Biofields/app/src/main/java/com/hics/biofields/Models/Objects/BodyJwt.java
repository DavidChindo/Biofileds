package com.hics.biofields.Models.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/14/17.
 */

public class BodyJwt {

    @SerializedName("iat")
    private double iat;
    @SerializedName("jti")
    private String jti;
    @SerializedName("iss")
    private String iss;
    @SerializedName("nbf")
    private double nbf;
    @SerializedName("data")
    private UserData userData;

    public double getIat() {
        return iat;
    }

    public void setIat(double iat) {
        this.iat = iat;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public double getNbf() {
        return nbf;
    }

    public void setNbf(double nbf) {
        this.nbf = nbf;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

