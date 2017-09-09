package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class ItemResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("company_id")
    private String companyId;
    @SerializedName("item_desc")
    private String itemDesc;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Override
    public String toString() {
        return this.itemDesc;
    }
}