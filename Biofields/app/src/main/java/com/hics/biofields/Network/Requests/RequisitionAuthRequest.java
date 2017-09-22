package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/14/17.
 */

public class RequisitionAuthRequest {

    @SerializedName("req_isAccepted")
    private boolean isAccepted;
    @SerializedName("req_reason_reject")
    private String reasonReject;
    @SerializedName("usrw_id")
    private int usrwId;
    @SerializedName("req_number")
    private int reqNumber;

    public RequisitionAuthRequest(int reqNumber, boolean isAccepted, String reasonReject, int usrwId) {
        this.reqNumber = reqNumber;
        this.isAccepted = isAccepted;
        this.reasonReject = reasonReject;
        this.usrwId = usrwId;
    }
}
