package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.utils.BetSettlementConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;

import javax.validation.constraints.NotNull;

public class BetSettlementTO {

    @NotNull(message = BetSettlementConstants.BET_SETTLEMENT_NULL_CHECK)
    private Integer betSettlementId;

    @NotNull(message = SportsBookConstants.BET_STATUS_ID_NULL)
    private Integer betStatusId;

    public Integer getBetSettlementId() {
        return betSettlementId;
    }

    public void setBetSettlementId(Integer betSettlementId) {
        this.betSettlementId = betSettlementId;
    }

    public Integer getBetStatusId() {
        return betStatusId;
    }

    public void setBetStatusId(Integer betStatusId) {
        this.betStatusId = betStatusId;
    }

    @Override
    public String toString() {
        return "BetSettlementInput{" +
                " betSettlementId=" + betSettlementId +
                ", betStatusId=" + betStatusId +
                '}';
    }
}
