package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyReportEventDetailsTO {
    private String sportName;
    private String sportId;
    private String eventName;
    @NotNull
    private BigDecimal totalStake;
    @NotNull
    private BigDecimal totalPlyrProfitlossSport;
    @NotNull
    private BigDecimal totalDwnProfitlossSport;
    @NotNull
    private BigDecimal totalCommisonSport;
    @NotNull
    private BigDecimal totalUpProfitlosssport;
    private List<MyReportMarketTO> marketDetails = new ArrayList<>();

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public List<MyReportMarketTO> getMarketDetails() {
        return marketDetails;
    }

    public void setMarketDetails(List<MyReportMarketTO> marketDetails) {
        this.marketDetails = marketDetails;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(BigDecimal totalStake) {
        this.totalStake = totalStake;
    }

    public BigDecimal getTotalPlyrProfitlossSport() {
        return totalPlyrProfitlossSport;
    }

    public void setTotalPlyrProfitlossSport(BigDecimal totalPlyrProfitlossSport) {
        this.totalPlyrProfitlossSport = totalPlyrProfitlossSport;
    }

    public BigDecimal getTotalDwnProfitlossSport() {
        return totalDwnProfitlossSport;
    }

    public void setTotalDwnProfitlossSport(BigDecimal totalDwnProfitlossSport) {
        this.totalDwnProfitlossSport = totalDwnProfitlossSport;
    }

    public BigDecimal getTotalCommisonSport() {
        return totalCommisonSport;
    }

    public void setTotalCommisonSport(BigDecimal totalCommisonSport) {
        this.totalCommisonSport = totalCommisonSport;
    }

    public BigDecimal getTotalUpProfitlosssport() {
        return totalUpProfitlosssport;
    }

    public void setTotalUpProfitlosssport(BigDecimal totalUpProfitlosssport) {
        this.totalUpProfitlosssport = totalUpProfitlosssport;
    }
}
