package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

public class MyReportMarketLoginDetailsTO {

    private String userToken;
    private String fromDate;
    private String toDate;
    private Integer sportId;


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

}
