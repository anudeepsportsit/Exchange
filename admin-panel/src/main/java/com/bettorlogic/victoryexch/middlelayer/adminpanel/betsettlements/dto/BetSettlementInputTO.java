package com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betsettlements.utils.BetSettlementConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BetSettlementInputTO {

    @NotNull(message = BetSettlementConstants.BET_SETTLEMENT_LIST_NULL_CHECK)
    @NotEmpty
    private List<BetSettlementTO> betSettlementsList;

    @NotNull(message = SportsBookConstants.USER_TOKEN_NOT_NULL)
    private String userLoginToken;

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public List<BetSettlementTO> getBetSettlementsList() {
        return betSettlementsList;
    }

    public void setBetSettlementsList(List<BetSettlementTO> betSettlementsList) {
        this.betSettlementsList = betSettlementsList;
    }
}
