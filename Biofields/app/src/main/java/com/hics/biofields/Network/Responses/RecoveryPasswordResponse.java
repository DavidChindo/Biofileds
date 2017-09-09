package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/7/17.
 */

public class RecoveryPasswordResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private String user;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
