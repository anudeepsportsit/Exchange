package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyReportMarketDetailsTO {

    private Integer userId;
    @NotNull
    private BigDecimal totalMarketStake;
    @NotNull
    private BigDecimal totalMarketPlyrprofitlossSport;
    @NotNull
    private BigDecimal totalMarketDwnprofitlosssport;
    @NotNull
    private BigDecimal totalMarketCommisonSport;
    @NotNull
    private BigDecimal totalMarketUpProfitlosssport;
    private List<MyReportEventDetailsTO> myReportEventDetails = new ArrayList<>();

    public List<MyReportEventDetailsTO> getMyReportEventDetails() {
        return myReportEventDetails;
    }

    public void setMyReportEventDetails(List<MyReportEventDetailsTO> myReportEventDetails) {
        this.myReportEventDetails = myReportEventDetails;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalMarketStake() {
        return totalMarketStake;
    }

    public void setTotalMarketStake(BigDecimal totalMarketStake) {
        this.totalMarketStake = totalMarketStake;
    }

    public BigDecimal getTotalMarketPlyrprofitlossSport() {
        return totalMarketPlyrprofitlossSport;
    }

    public void setTotalMarketPlyrprofitlossSport(BigDecimal totalMarketPlyrprofitlossSport) {
        this.totalMarketPlyrprofitlossSport = totalMarketPlyrprofitlossSport;
    }

    public BigDecimal getTotalMarketDwnprofitlosssport() {
        return totalMarketDwnprofitlosssport;
    }

    public void setTotalMarketDwnprofitlosssport(BigDecimal totalMarketDwnprofitlosssport) {
        this.totalMarketDwnprofitlosssport = totalMarketDwnprofitlosssport;
    }

    public BigDecimal getTotalMarketCommisonSport() {
        return totalMarketCommisonSport;
    }

    public void setTotalMarketCommisonSport(BigDecimal totalMarketCommisonSport) {
        this.totalMarketCommisonSport = totalMarketCommisonSport;
    }

    public BigDecimal getTotalMarketUpProfitlosssport() {
        return totalMarketUpProfitlosssport;
    }

    public void setTotalMarketUpProfitlosssport(BigDecimal totalMarketUpProfitlosssport) {
        this.totalMarketUpProfitlosssport = totalMarketUpProfitlosssport;
    }


}
