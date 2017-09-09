package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class ExpenseResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("expcat_id")
    private String expcatId;
    @SerializedName("expcat_code")
    private String expcatCode;
    @SerializedName("expcat_descrip")
    private String expcatDesc;

    @Override
    public String toString() {
        return this.expcatDesc;
    }
}
