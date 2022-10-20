package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class MarketProfitAndLossTO {
    private String marketId;
    private double commissionApplied;
    private List<RunnerProfitAndLossTO> profitAndLosses;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public double getCommissionApplied() {
        return commissionApplied;
    }

    public void setCommissionApplied(double commissionApplied) {
        this.commissionApplied = commissionApplied;
    }

    public List<RunnerProfitAndLossTO> getProfitAndLosses() {
        return profitAndLosses;
    }

    public void setProfitAndLosses(List<RunnerProfitAndLossTO> profitAndLosses) {
        this.profitAndLosses = profitAndLosses;
    }

}
