package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class UoMResponse extends RealmObject {

    @PrimaryKey
    @SerializedName("uom_name")
    private String uomName;
    @SerializedName("uom_descrip")
    private String uomDescip;



    @Override
    public String toString() {
        return this.uomName;
    }
}
