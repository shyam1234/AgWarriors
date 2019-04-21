package com.aiml.agwarriors.model;

import java.io.Serializable;

public class YieldListModel implements Serializable {
    public static final int FROM_NOTIFICATION = 1;
    public static final int FROM_REG_YIELD = 2;
    public static final int STATUS_SENT_BRAODCAST_TO_BUYER = 1;
    public static final int STATUS_ACCEPT_PROPOSAL = 2;
    public static final int STATUS_REJECT_PROPOSAL = 3;
    public static final int STATUS_NOTIFY_TO_SELLER = 4;
    public static final int STATUS_DISMISS_BY_BUYER = 5;
    public static final int STATUS_DISMISS_BY_SELLER = 6;
    private String lotnumber = "";
    private String yield = "";
    private String yieldType = "";
    private String date = "";
    private String costPerUnit = "";
    private String costUnit = "";
    private String status = "";
    private String QTY = "";
    private String QTYType = "";
    private String placeToSell = "";
    private String proposedCostPerUnit = "";
    private int from;
    private int statusValue;


    public String getLotnumber() {
        return lotnumber;
    }

    public void setLotnumber(String lotnumber) {
        this.lotnumber = lotnumber;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getYieldType() {
        return yieldType;
    }

    public void setYieldType(String yieldType) {
        this.yieldType = yieldType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(String costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getQTYType() {
        return QTYType;
    }

    public void setQTYType(String QTYType) {
        this.QTYType = QTYType;
    }

    public String getPlaceToSell() {
        return placeToSell;
    }

    public void setPlaceToSell(String placeToSell) {
        this.placeToSell = placeToSell;
    }

    public String getCostUnit() {
        return costUnit;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    public String getProposedCostPerUnit() {
        return proposedCostPerUnit;
    }

    public void setProposedCostPerUnit(String proposedCostPerUnit) {
        this.proposedCostPerUnit = proposedCostPerUnit;
    }


    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
