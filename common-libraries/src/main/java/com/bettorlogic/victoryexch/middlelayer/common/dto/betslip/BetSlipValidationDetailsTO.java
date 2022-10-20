package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class BetSlipValidationDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasonForFailure;

    @JsonIgnore
    private boolean marketSuspended;

    @JsonIgnore
    private boolean oddsChangeFlag;

    private boolean isValidBet;

    @JsonIgnore
    private boolean isValidUser;

    @JsonIgnore
    private boolean hasExceededExposureLimit;

    @JsonIgnore
    private boolean isActive;

    @JsonIgnore
    private boolean isPlayer;

    @JsonIgnore
    private boolean hasSufficientBalance;

    @JsonIgnore
    private boolean isValidMarket;

    @JsonIgnore
    private boolean isValidEvent;

    @JsonIgnore
    private boolean isValidOutcome;

    @JsonIgnore
    private double remainingBalance;

    public String getReasonForFailure() {
        return reasonForFailure;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public Boolean getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Boolean marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public Boolean getOddsChangeFlag() {
        return oddsChangeFlag;
    }

    public void setOddsChangeFlag(Boolean oddsChangeFlag) {
        this.oddsChangeFlag = oddsChangeFlag;
    }

    public Boolean getValidBet() {
        return isValidBet;
    }

    public void setValidBet(Boolean validBet) {
        isValidBet = validBet;
    }

    public Double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(Double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public boolean getIsValidUser() {
        return isValidUser;
    }

    public void setIsValidUser(boolean validUser) {
        isValidUser = validUser;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setHasInsufficientBalance(boolean hasSufficientBalance) {
        this.hasSufficientBalance = hasSufficientBalance;
    }

    public boolean hasSufficientBalance() {
        return hasSufficientBalance;
    }

    public boolean getIsValidMarket() {
        return isValidMarket;
    }

    public void setIsValidMarket(boolean isValidMarket) {
        this.isValidMarket = isValidMarket;
    }

    public boolean getIsValidEvent() {
        return isValidEvent;
    }

    public void setIsValidEvent(boolean isValidEvent) {
        this.isValidEvent = isValidEvent;
    }

    public boolean getIsValidOutcome() {
        return isValidOutcome;
    }

    public void setIsValidOutcome(boolean validOutcome) {
        isValidOutcome = validOutcome;
    }

    public boolean getIsPlayer() {
        return isPlayer;
    }

    public void setIsPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean hasExceededExposureLimit() {
        return hasExceededExposureLimit;
    }

    public void setHasExceededExposureLimit(boolean hasExceededExposureLimit) {
        this.hasExceededExposureLimit = hasExceededExposureLimit;
    }
}