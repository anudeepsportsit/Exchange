package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettinghistory;

public class BetDetailsTO {
    private String betTaken;
    private String oddsReq;
    private Double stake;
    private Double liability;
    private String oddsMatched;

    public String getBetTaken() {
        return betTaken;
    }

    public void setBetTaken(String betTaken) {
        this.betTaken = betTaken;
    }

    public String getOddsReq() {
        return oddsReq;
    }

    public void setOddsReq(String oddsReq) {
        this.oddsReq = oddsReq;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public Double getLiability() {
        return liability;
    }

    public void setLiability(Double liability) {
        this.liability = liability;
    }

    public String getOddsMatched() {
        return oddsMatched;
    }

    public void setOddsMatched(String oddsMatched) {
        this.oddsMatched = oddsMatched;
    }

    @Override
    public String toString() {
        return "BetDetails{" +
                "betTaken='" + betTaken + '\'' +
                ", oddsReq='" + oddsReq + '\'' +
                ", stake=" + stake +
                ", liability=" + liability +
                ", oddsMatched=" + oddsMatched +
                '}';
    }
}
