package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 9/14/17.
 */

public class FileRequest {

    @SerializedName("req_number" )
    private int reqNumber;

    public FileRequest(int reqNumber) {
        this.reqNumber = reqNumber;
    }


}
