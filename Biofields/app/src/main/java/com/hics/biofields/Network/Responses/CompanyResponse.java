package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;


/**
 * Created by david.barrera on 9/1/17.
 */

public class CompanyResponse   {



    @SerializedName("companybd_name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return this.companyName;
    }
}
