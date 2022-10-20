package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CancelOpenBetsOutput {
    private boolean isBetCancelled;
    private String reasonForFailure;

    @JsonIgnore
    private boolean isUnmatchedBet;

    @JsonIgnore
    private boolean isValidPlayer;

    @JsonIgnore
    private boolean playerHasBet;

    @JsonIgnore
    private boolean isInOpenStatus;

    public String getReasonForFailure() {
        return reasonForFailure;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public boolean isBetCancelled() {
        return isBetCancelled;
    }

    public void setBetCancelled(boolean betCancelled) {
        isBetCancelled = betCancelled;
    }

    public boolean isUnmatchedBet() {
        return isUnmatchedBet;
    }

    @JsonIgnore
    public void setUnmatchedBet(boolean unmatchedBet) {
        isUnmatchedBet = unmatchedBet;
    }

    public boolean isValidPlayer() {
        return isValidPlayer;
    }

    @JsonIgnore
    public void setValidPlayer(boolean validPlayer) {
        isValidPlayer = validPlayer;
    }

    public boolean isPlayerHasBet() {
        return playerHasBet;
    }

    public void setPlayerHasBet(boolean playerHasBet) {
        this.playerHasBet = playerHasBet;
    }

    public boolean isInOpenStatus() {
        return isInOpenStatus;
    }

    @JsonIgnore
    public void setInOpenStatus(boolean inOpenStatus) {
        isInOpenStatus = inOpenStatus;
    }
}
