package com.hics.biofields.Network.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by david.barrera on 9/15/17.
 */

public class RequisitionDetailResponse {

    @SerializedName("req_number")
    private String numRequisition;
    @SerializedName("req_desc")
    private String descRequsition;
    @SerializedName("company_number")
    private String companyIdRequisition;
    @SerializedName("company_name")
    private String companyNameRequisition;
    @SerializedName("req_status_desc")
    private String statusRequisition;
    @SerializedName("req_monto_form")
    private String amountRequsition;
    @SerializedName("req_urgente")
    private String urgentRequsition;
    @SerializedName("req_date")
    private String dateRequisition;
    @SerializedName("req_costcenter")
    private String costCenterRequisition;
    @SerializedName("req_vend_number")
    private String salesManNumberRequisition;
    @SerializedName("req_facturado")
    private String billedRequisition;
    @SerializedName("solicitante")
    private String applicantRequisition;
    @SerializedName("titular")
    private String titularRequisition;
    @SerializedName("director")
    private String directorRequisition;
    @SerializedName("comprador")
    private String buyerRequisition;
    @SerializedName("auditor")
    private String auditorRequisition;
    @SerializedName("auth_daf")
    private String authDafRequisition;
    @SerializedName("auth_dg")
    private String authDgRequisition;
    @SerializedName("reqitems")
    private ArrayList<BudgeItemResponse> items;
    @SerializedName("reqfiles")
    private ArrayList<FilesReqResponse> files;

    public String getNumRequisition() {
        return numRequisition;
    }

    public void setNumRequisition(String numRequisition) {
        this.numRequisition = numRequisition;
    }

    public String getDescRequsition() {
        return descRequsition;
    }

    public void setDescRequsition(String descRequsition) {
        this.descRequsition = descRequsition;
    }

    public String getCompanyIdRequisition() {
        return companyIdRequisition;
    }

    public void setCompanyIdRequisition(String companyIdRequisition) {
        this.companyIdRequisition = companyIdRequisition;
    }

    public String getCompanyNameRequisition() {
        return companyNameRequisition;
    }

    public void setCompanyNameRequisition(String companyNameRequisition) {
        this.companyNameRequisition = companyNameRequisition;
    }

    public String getStatusRequisition() {
        return statusRequisition;
    }

    public void setStatusRequisition(String statusRequisition) {
        this.statusRequisition = statusRequisition;
    }

    public String getAmountRequsition() {
        return amountRequsition;
    }

    public void setAmountRequsition(String amountRequsition) {
        this.amountRequsition = amountRequsition;
    }

    public String getUrgentRequsition() {
        return urgentRequsition;
    }

    public void setUrgentRequsition(String urgentRequsition) {
        this.urgentRequsition = urgentRequsition;
    }

    public String getDateRequisition() {
        return dateRequisition;
    }

    public void setDateRequisition(String dateRequisition) {
        this.dateRequisition = dateRequisition;
    }

    public String getCostCenterRequisition() {
        return costCenterRequisition;
    }

    public void setCostCenterRequisition(String costCenterRequisition) {
        this.costCenterRequisition = costCenterRequisition;
    }

    public String getSalesManNumberRequisition() {
        return salesManNumberRequisition;
    }

    public void setSalesManNumberRequisition(String salesManNumberRequisition) {
        this.salesManNumberRequisition = salesManNumberRequisition;
    }

    public String getBilledRequisition() {
        return billedRequisition;
    }

    public void setBilledRequisition(String billedRequisition) {
        this.billedRequisition = billedRequisition;
    }

    public String getApplicantRequisition() {
        return applicantRequisition;
    }

    public void setApplicantRequisition(String applicantRequisition) {
        this.applicantRequisition = applicantRequisition;
    }

    public String getTitularRequisition() {
        return titularRequisition;
    }

    public void setTitularRequisition(String titularRequisition) {
        this.titularRequisition = titularRequisition;
    }

    public String getDirectorRequisition() {
        return directorRequisition;
    }

    public void setDirectorRequisition(String directorRequisition) {
        this.directorRequisition = directorRequisition;
    }

    public String getBuyerRequisition() {
        return buyerRequisition;
    }

    public void setBuyerRequisition(String buyerRequisition) {
        this.buyerRequisition = buyerRequisition;
    }

    public String getAuditorRequisition() {
        return auditorRequisition;
    }

    public void setAuditorRequisition(String auditorRequisition) {
        this.auditorRequisition = auditorRequisition;
    }

    public String getAuthDafRequisition() {
        return authDafRequisition;
    }

    public void setAuthDafRequisition(String authDafRequisition) {
        this.authDafRequisition = authDafRequisition;
    }

    public String getAuthDgRequisition() {
        return authDgRequisition;
    }

    public void setAuthDgRequisition(String authDgRequisition) {
        this.authDgRequisition = authDgRequisition;
    }

    public ArrayList<BudgeItemResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<BudgeItemResponse> items) {
        this.items = items;
    }

    public ArrayList<FilesReqResponse> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FilesReqResponse> files) {
        this.files = files;
    }
}
