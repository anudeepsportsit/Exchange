package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class BetSlipDetailsWrapperTO {

    @Valid
    @JsonProperty(SportsBookConstants.BET_DETAILS)
    private List<BetDetailsTO> betDetailsList;

    @JsonProperty(SportsBookConstants.USER_TOKEN_KEY)
    private String userToken;
    private Double totalStake;
    private Double totalReturns;
    private Integer oddsChangeAcceptanceFlag;
    private Integer betSlipId;

    /*
     * modified by anudeep for betfair API Starts
     */

    private String appKey;
    private String sessionToken;
    @JsonIgnore
    private List<BetSlipValidationDetailsTO> betSlipValidationDetailsList = new ArrayList<>();

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    /*
     * modified by anudeep for betfair API Ends
     */

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public List<BetDetailsTO> getBetDetailsList() {
        return betDetailsList;
    }

    public void setBetDetailsList(List<BetDetailsTO> betDetailsList) {
        this.betDetailsList = betDetailsList;
    }

    public Double getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(Double totalStake) {
        this.totalStake = totalStake;
    }

    public Double getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(Double totalReturns) {
        this.totalReturns = totalReturns;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getOddsChangeAcceptanceFlag() {
        return oddsChangeAcceptanceFlag;
    }

    public void setOddsChangeAcceptanceFlag(Integer oddsChangeAcceptanceFlag) {
        this.oddsChangeAcceptanceFlag = oddsChangeAcceptanceFlag;
    }

    public List<BetSlipValidationDetailsTO> getBetSlipValidationDetailsList() {
        return betSlipValidationDetailsList;
    }

    public void setBetSlipValidationDetailsList(List<BetSlipValidationDetailsTO> betSlipValidationDetailsList) {
        this.betSlipValidationDetailsList = betSlipValidationDetailsList;
    }

    public Integer getBetSlipId() {
        return betSlipId;
    }

    public void setBetSlipId(Integer betSlipId) {
        this.betSlipId = betSlipId;
    }
}