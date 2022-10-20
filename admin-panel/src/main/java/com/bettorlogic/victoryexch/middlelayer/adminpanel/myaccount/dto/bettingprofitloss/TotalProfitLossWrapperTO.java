package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss;


import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.SportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TotalProfitLossWrapperTO {

    private String totalProfitLoss;
    private String currency;
    private String sportProfitLoss;

    @JsonProperty(SportsBookConstants.SPORTS)
    private List<SportDetailsTO> sportDetailsList;

    @JsonProperty(SportsBookConstants.BETTING_HISTORY)
    private List<BettingProfitLossOutput> bettingProfitLossOutputList = new ArrayList<>();

    public String getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(String totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSportProfitLoss() {
        return sportProfitLoss;
    }

    public void setSportProfitLoss(String sportProfitLoss) {
        this.sportProfitLoss = sportProfitLoss;
    }

    public List<BettingProfitLossOutput> getBettingProfitLossOutputList() {
        return bettingProfitLossOutputList;
    }

    public void setBettingProfitLossOutputList(List<BettingProfitLossOutput> bettingProfitLossOutputList) {
        this.bettingProfitLossOutputList = bettingProfitLossOutputList;
    }

    public List<SportDetailsTO> getSportDetailsList() {
        return sportDetailsList;
    }

    public void setSportDetailsList(List<SportDetailsTO> sportDetailsList) {
        this.sportDetailsList = sportDetailsList;
    }
}