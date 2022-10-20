package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss;

public class ProfitLossBetDetailsTO {
    private Integer betId;
    private String selection;
    private String odds;
    private String stake;
    private String type;
    private String betPlaced;
    private String profitLoss;
    private ProfitLossSubTotalsTO profitLossSubTotals;

    public String getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(String profitLoss) {
        this.profitLoss = profitLoss;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getStake() {
        return stake;
    }

    public void setStake(String stake) {
        this.stake = stake;
    }

    public ProfitLossSubTotalsTO getProfitLossSubTotals() {
        return profitLossSubTotals;
    }

    public void setProfitLossSubTotals(ProfitLossSubTotalsTO profitLossSubTotals) {
        this.profitLossSubTotals = profitLossSubTotals;
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    @Override
    public String toString() {
        return "ProfitLossBetDetails{" +
                "betId=" + betId +
                ", selection='" + selection + '\'' +
                ", odds='" + odds + '\'' +
                ", stake='" + stake + '\'' +
                ", type='" + type + '\'' +
                ", betPlaced='" + betPlaced + '\'' +
                '}';
    }
}
