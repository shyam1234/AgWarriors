package com.aiml.agwarriors.model;

public class TableUserInfoDataModel {
    public static  String TYPE_USER_FARMER = "Farmer" ;
    public static  String TYPE_USER_BUYER = "Buyer" ;
    private String ID = null;
    private String USERNAME = null;
    private String PASS = null;
    private String MOBILENO = null;
    private String ADDRESS = null;
    private String LAT = null;
    private String LONG = null;
    private String TYPE = null;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getLONG() {
        return LONG;
    }

    public void setLONG(String LONG) {
        this.LONG = LONG;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
}
