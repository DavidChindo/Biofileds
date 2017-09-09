package com.hics.biofields.Network.Requests.RequisitionItem;

/**
 * Created by david.barrera on 9/5/17.
 */

public class BudgeItemRequest {

    private int idAutonumeric;
    private String notes;
    private String idProduct;
    private String descProduct;
    private String uom;
    private String price;
    private String qty;
    private String total;

    public BudgeItemRequest(int idAutonumeric, String notes, String idProduct, String descProduct, String uom, String price, String qty, String total) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
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
        return this.descProduct;
    }
}
