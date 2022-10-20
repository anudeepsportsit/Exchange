package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UpdateOpenBetsOutput {

    @JsonIgnore
    private boolean isValidPlayer;

    private boolean isBetUpdated;

    private Integer betId;

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    @JsonIgnore
    private boolean playerHasBet;

    @JsonIgnore
    private boolean hasSufficientBalance;

    @JsonIgnore
    private boolean isMarketSuspended;

    @JsonIgnore
    private boolean hasExceededExposureLimit;

    private boolean ismatched;

    public boolean isIsmatched() {
        return ismatched;
    }

    public void setIsmatched(boolean ismatched) {
        this.ismatched = ismatched;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasonForFailure;

    @JsonIgnore
    private boolean isOpenBet;

    public boolean isOpenBet() {
        return isOpenBet;
    }

    @JsonIgnore
    public void setOpenBet(boolean openBet) {
        isOpenBet = openBet;
    }

    public String getReasonForFailure() {
        return reasonForFailure;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public boolean isValidPlayer() {
        return isValidPlayer;
    }

    @JsonIgnore
    public void setValidPlayer(boolean validPlayer) {
        isValidPlayer = validPlayer;
    }

    public boolean isBetUpdated() {
        return isBetUpdated;
    }

    public void setBetUpdated(boolean betUpdated) {
        isBetUpdated = betUpdated;
    }

    public boolean isPlayerHasBet() {
        return playerHasBet;
    }

    public void setPlayerHasBet(boolean playerHasBet) {
        this.playerHasBet = playerHasBet;
    }

    public boolean hasSufficientBalance() {
        return hasSufficientBalance;
    }

    public void setHasSufficientBalance(boolean hasSufficientBalance) {
        this.hasSufficientBalance = hasSufficientBalance;
    }

    public boolean isMarketSuspended() {
        return isMarketSuspended;
    }

    @JsonIgnore
    public void setMarketSuspended(boolean marketSuspended) {
        isMarketSuspended = marketSuspended;
    }

    public boolean hasExceededExposureLimit() {
        return hasExceededExposureLimit;
    }

    public void setHasExceededExposureLimit(boolean hasExceededExposureLimit) {
        this.hasExceededExposureLimit = hasExceededExposureLimit;
    }
}
