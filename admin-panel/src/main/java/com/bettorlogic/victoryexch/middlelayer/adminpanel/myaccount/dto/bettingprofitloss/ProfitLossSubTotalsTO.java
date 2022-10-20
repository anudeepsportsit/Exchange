package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto.bettingprofitloss;

public class ProfitLossSubTotalsTO {
    private String totalStake;
    private boolean isBackLay;
    private String backSubTotal;
    private String laySubTotal;
    private String marketSubTotal;
    private String commission;
    private String netMarketTotal;

    public String getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(String totalStake) {
        this.totalStake = totalStake;
    }

    public boolean isBackLay() {
        return isBackLay;
    }

    public void setBackLay(boolean backLay) {
        isBackLay = backLay;
    }

    public String getBackSubTotal() {
        return backSubTotal;
    }

    public void setBackSubTotal(String backSubTotal) {
        this.backSubTotal = backSubTotal;
    }

    public String getLaySubTotal() {
        return laySubTotal;
    }

    public void setLaySubTotal(String laySubTotal) {
        this.laySubTotal = laySubTotal;
    }

    public String getMarketSubTotal() {
        return marketSubTotal;
    }

    public void setMarketSubTotal(String marketSubTotal) {
        this.marketSubTotal = marketSubTotal;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getNetMarketTotal() {
        return netMarketTotal;
    }

    public void setNetMarketTotal(String netMarketTotal) {
        this.netMarketTotal = netMarketTotal;
    }

    @Override
    public String toString() {
        return "ProfitLossSubTotals{" +
                "totalStake='" + totalStake + '\'' +
                ", isBackLay=" + isBackLay +
                ", backSubTotal=" + backSubTotal +
                ", laySubTotal=" + laySubTotal +
                '}';
    }
}