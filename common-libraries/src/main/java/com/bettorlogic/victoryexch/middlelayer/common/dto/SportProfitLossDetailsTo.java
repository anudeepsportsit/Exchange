package com.bettorlogic.victoryexch.middlelayer.common.dto;
import java.math.BigDecimal;

public class SportProfitLossDetailsTo {
    private Integer sportId;
    private String sportName;
    private BigDecimal profitLossAmount;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public BigDecimal getProfitLossAmount() {
        return profitLossAmount;
    }

    public void setProfitLossAmount(BigDecimal profitLossAmount) {
        this.profitLossAmount = profitLossAmount;
    }

}
