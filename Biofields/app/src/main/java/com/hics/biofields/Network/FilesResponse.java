package com.hics.biofields.Network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/13/17.
 */

public class FilesResponse {

    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("files")
    private ArrayList<ArrayList<String>> files;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
