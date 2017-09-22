package com.hics.biofields.Network.Responses.Catalogs;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david.barrera on 9/1/17.
 */

public class BudgetlistResponse extends RealmObject{

    @PrimaryKey
    @SerializedName("rubro_id")
    private String rubroId;
    @SerializedName("rubro_desc")
    private String rubroDesc;
    @SerializedName("rubro_empresa_id")
    private String rubroEmpresaId;

    public String getRubroId() {
        return rubroId;
    }

    public void setRubroId(String rubroId) {
        this.rubroId = rubroId;
    }

    public String getRubroDesc() {
        return rubroDesc;
    }

    public void setRubroDesc(String rubroDesc) {
        this.rubroDesc = rubroDesc;
    }

    public String getRubroEmpresaId() {
        return rubroEmpresaId;
    }

    public void setRubroEmpresaId(String rubroEmpresaId) {
        this.rubroEmpresaId = rubroEmpresaId;
    }

    @Override
    public String toString() {
        return this.rubroDesc;
    }
}
