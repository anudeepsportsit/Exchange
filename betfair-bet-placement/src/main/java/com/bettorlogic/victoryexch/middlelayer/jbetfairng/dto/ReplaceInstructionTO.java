package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class ReplaceInstructionTO {
    private String betId;
    private double newPrice;

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
