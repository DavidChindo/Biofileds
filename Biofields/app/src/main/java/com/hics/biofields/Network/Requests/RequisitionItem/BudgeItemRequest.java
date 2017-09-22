package com.hics.biofields.Network.Requests.RequisitionItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by david.barrera on 9/5/17.
 */

public class BudgeItemRequest {

    @SerializedName("req_linenumber")
    private int idAutonumeric;
    @SerializedName("reqitem_notas")
    private String notes;
    @SerializedName("reqitem_item_number")
    private String idProduct;
    @SerializedName("reqitem_cg_desc")
    private String descProduct;
    @SerializedName("reqitem_uom")
    private String uom;
    @SerializedName("reqitem_price")
    private double price;
    @SerializedName("reqitem_qty")
    private double qty;
    @Expose(serialize = false)
    private String total;

    public BudgeItemRequest(int idAutonumeric, String notes, String idProduct, String descProduct, String uom, double price, double qty, String total) {
        this.idAutonumeric = idAutonumeric;
        this.notes = notes;
        this.idProduct = idProduct;
        this.descProduct = descProduct;
        this.uom = uom;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public int getIdAutonumeric() {
        return idAutonumeric;
    }

    public void setIdAutonumeric(int idAutonumeric) {
        this.idAutonumeric = idAutonumeric;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getDescProduct() {
        return descProduct;
    }

    public void setDescProduct(String descProduct) {
        this.descProduct = descProduct;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {

        return this.idProduct != null ? this.idProduct.equals("-1") ? this.descProduct : this.idProduct : this.notes;
    }
}
