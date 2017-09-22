package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class CostcenterResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("costcenter_id")
    private String costCenterId;
    @SerializedName("costcenter_name")
    private String costCenterName;
    @SerializedName("costcenter_number")
    private String constCenterNumber;

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getConstCenterNumber() {
        return constCenterNumber;
    }

    public void setConstCenterNumber(String constCenterNumber) {
        this.constCenterNumber = constCenterNumber;
    }

    @Override
    public String toString() {
        return this.costCenterName;
    }
}
