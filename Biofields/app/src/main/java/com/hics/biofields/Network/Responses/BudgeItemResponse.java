package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david.barrera on 8/31/17.
 */

public class BudgeItemResponse {

    @SerializedName("reqitem_linenumber")
    private String lineNumberBudge;
    @SerializedName("reqitem_cg_desc")
    private String descBudge;
    @SerializedName("reqitem_item_number")
    private String itemIdBudge;
    @SerializedName("reqitem_qty")
    private String qtyBudge;
    @SerializedName("reqitem_price")
    private String priceBudge;
    @SerializedName("reqitem_notas")
    private String notes;

    public String getLineNumberBudge() {
        return lineNumberBudge;
    }

    public void setLineNumberBudge(String lineNumberBudge) {
        this.lineNumberBudge = lineNumberBudge;
    }

    public String getDescBudge() {
        return descBudge;
    }

    public void setDescBudge(String descBudge) {
        this.descBudge = descBudge;
    }

    public String getItemIdBudge() {
        return itemIdBudge;
    }

    public void setItemIdBudge(String itemIdBudge) {
        this.itemIdBudge = itemIdBudge;
    }

    public String getQtyBudge() {
        return qtyBudge;
    }

    public void setQtyBudge(String qtyBudge) {
        this.qtyBudge = qtyBudge;
    }

    public String getPriceBudge() {
        return priceBudge;
    }

    public void setPriceBudge(String priceBudge) {
        this.priceBudge = priceBudge;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
