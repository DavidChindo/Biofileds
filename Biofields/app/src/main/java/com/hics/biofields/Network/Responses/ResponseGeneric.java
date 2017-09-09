package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/7/17.
 */

public class ResponseGeneric {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public ResponseGeneric(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean getError() {
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
}
