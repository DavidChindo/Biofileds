package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 8/30/17.
 */

public class LoginResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("email")
    private String email;
    @SerializedName("createdAt")
    private String created;
    @SerializedName("message")
    private String messsage;
    @SerializedName("array")
    private ArrayResponse array;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public ArrayResponse getArray() {
        return array;
    }

    public void setArray(ArrayResponse array) {
        this.array = array;
    }

}
