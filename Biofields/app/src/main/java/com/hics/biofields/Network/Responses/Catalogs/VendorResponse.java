package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class VendorResponse extends RealmObject {

    @PrimaryKey
    @SerializedName("vend_id")
    private String id;

    @SerializedName("vend_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
