package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/7/17.
 */

public class RecoveryPasswordRequest {

    @SerializedName("email")
    private String email;

    public RecoveryPasswordRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
