package com.hics.biofields.Network.Requests;

import com.google.gson.annotations.SerializedName;
import com.hics.biofields.Network.Requests.RequisitionItem.BudgeItemRequest;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/13/17.
 */

public class RequisitionRequest {

    @SerializedName("req_company_id")
    private int reqCompanyId;
    @SerializedName("req_costcenter_id")
    private int reqCostCenterId;
    @SerializedName("req_rubro_id")
    private int reqRubroId;
    @SerializedName("req_vend_number")
    private String reqVendedorNumber;
    @SerializedName("req_desc")
    private String reqDesc;
    @SerializedName("req_site")
    private int reqSite;
    @SerializedName("req_notes")
    private String reqNotes;
    @SerializedName("req_moneda_id")
    private int reqMonedaId;
    @SerializedName("req_facturado")
    private boolean reqFacturado;
    @SerializedName("req_urgente")
    private int reqUrgente;
    @SerializedName("req_poa_a")
    private boolean reqPOAa;
    @SerializedName("req_incluirpoa_b")
    private boolean reqIncluirPOAb;
    @SerializedName("req_deletepoa_c")
    private boolean reqDeletePOAc;
    @SerializedName("req_operacion_d")
    private boolean reqOperaciond;
    private ArrayList<BudgeItemRequest> reqitem;

    public RequisitionRequest(int reqCompanyId, int reqCostCenterId, int reqRubroId, String reqVendedorNumber,
                              String reqDesc, int reqSite, String reqNotes, int reqMonedaId,
                              boolean reqFacturado, int reqUrgente, boolean reqPOAa, boolean reqIncluirPOAb,
                              boolean reqDeletePOAc, boolean reqOperaciond,ArrayList<BudgeItemRequest> reqitems) {
        this.reqCompanyId = reqCompanyId;
        this.reqCostCenterId = reqCostCenterId;
        this.reqRubroId = reqRubroId;
        this.reqVendedorNumber = reqVendedorNumber;
        this.reqDesc = reqDesc;
        this.reqSite = reqSite;
        this.reqNotes = reqNotes;
        this.reqMonedaId = reqMonedaId;
        this.reqFacturado = reqFacturado;
        this.reqUrgente = reqUrgente;
        this.reqPOAa = reqPOAa;
        this.reqIncluirPOAb = reqIncluirPOAb;
        this.reqDeletePOAc = reqDeletePOAc;
        this.reqOperaciond = reqOperaciond;
        this.reqitem = reqitems;
    }

    public int getReqCompanyId() {
        return reqCompanyId;
    }

    public void setReqCompanyId(int reqCompanyId) {
        this.reqCompanyId = reqCompanyId;
    }

    public int getReqCostCenterId() {
        return reqCostCenterId;
    }

    public void setReqCostCenterId(int reqCostCenterId) {
        this.reqCostCenterId = reqCostCenterId;
    }

    public int getReqRubroId() {
        return reqRubroId;
    }

    public void setReqRubroId(int reqRubroId) {
        this.reqRubroId = reqRubroId;
    }

    public String getReqVendedorNumber() {
        return reqVendedorNumber;
    }

    public void setReqVendedorNumber(String reqVendedorNumber) {
        this.reqVendedorNumber = reqVendedorNumber;
    }

    public String getReqDesc() {
        return reqDesc;
    }

    public void setReqDesc(String reqDesc) {
        this.reqDesc = reqDesc;
    }

    public int getReqSite() {
        return reqSite;
    }

    public void setReqSite(int reqSite) {
        this.reqSite = reqSite;
    }

    public String getReqNotes() {
        return reqNotes;
    }

    public void setReqNotes(String reqNotes) {
        this.reqNotes = reqNotes;
    }

    public int getReqMonedaId() {
        return reqMonedaId;
    }

    public void setReqMonedaId(int reqMonedaId) {
        this.reqMonedaId = reqMonedaId;
    }

    public boolean isReqFacturado() {
        return reqFacturado;
    }

    public void setReqFacturado(boolean reqFacturado) {
        this.reqFacturado = reqFacturado;
    }

    public int getReqUrgente() {
        return reqUrgente;
    }

    public void setReqUrgente(int reqUrgente) {
        this.reqUrgente = reqUrgente;
    }

    public boolean isReqPOAa() {
        return reqPOAa;
    }

    public void setReqPOAa(boolean reqPOAa) {
        this.reqPOAa = reqPOAa;
    }

    public boolean isReqIncluirPOAb() {
        return reqIncluirPOAb;
    }

    public void setReqIncluirPOAb(boolean reqIncluirPOAb) {
        this.reqIncluirPOAb = reqIncluirPOAb;
    }

    public boolean isReqDeletePOAc() {
        return reqDeletePOAc;
    }

    public void setReqDeletePOAc(boolean reqDeletePOAc) {
        this.reqDeletePOAc = reqDeletePOAc;
    }

    public boolean isReqOperaciond() {
        return reqOperaciond;
    }

    public void setReqOperaciond(boolean reqOperaciond) {
        this.reqOperaciond = reqOperaciond;
    }

    public ArrayList<BudgeItemRequest> getReqitem() {
        return reqitem;
    }

    public void setReqitem(ArrayList<BudgeItemRequest> reqitem) {
        this.reqitem = reqitem;
    }
}
