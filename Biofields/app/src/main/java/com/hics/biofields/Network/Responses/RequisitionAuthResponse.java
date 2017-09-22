package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/14/17.
 */

public class RequisitionAuthResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("incidente")
    private int incident;
    @SerializedName("req_number")
    private int reqNumber;

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

    public int getIncident() {
        return incident;
    }

    public void setIncident(int incident) {
        this.incident = incident;
    }

    public int getReqNumber() {
        return reqNumber;
    }

    public void setReqNumber(int reqNumber) {
        this.reqNumber = reqNumber;
    }
}
