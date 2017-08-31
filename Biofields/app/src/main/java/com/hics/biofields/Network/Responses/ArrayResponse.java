package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 8/30/17.
 */

public class ArrayResponse {

    @SerializedName("jwt")
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
