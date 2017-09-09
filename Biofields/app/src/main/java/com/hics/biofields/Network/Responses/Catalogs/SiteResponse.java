package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class SiteResponse extends RealmObject {

    @PrimaryKey
    @SerializedName("site_id")
    private String siteId;
    @SerializedName("site_number")
    private String siteNumber;
    @SerializedName("site_name")
    private String siteName;

    @Override
    public String toString() {
        return this.siteName;
    }
}
