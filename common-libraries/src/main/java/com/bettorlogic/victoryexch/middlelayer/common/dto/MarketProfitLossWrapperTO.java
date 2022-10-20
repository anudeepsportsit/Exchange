package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MarketProfitLossWrapperTO {

    @NotEmpty(message = ExceptionConstants.INVALID_LOGIN_TOKEN)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userToken;

    @Valid
    @JsonProperty(SportsBookConstants.PROFIT_LOSS_DETAILS)
    @NotEmpty(message = ExceptionConstants.PROFIT_LOSS_DETAILS_EMPTY)
    private List<MarketProfitLossTO> marketProfitLossList;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<MarketProfitLossTO> getMarketProfitLossList() {
        return marketProfitLossList;
    }

    public void setMarketProfitLossList(List<MarketProfitLossTO> marketProfitLossList) {
        this.marketProfitLossList = marketProfitLossList;
    }
}