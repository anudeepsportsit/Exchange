package com.bettorlogic.victoryexch.middlelayer.playerpanel.openbets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class BetDetailsSubWrapperTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotEmpty(message = ExceptionConstants.INVALID_LOGIN_TOKEN)
    private String userToken;

    @JsonProperty(ColumnLabelConstants.BETS)
    private List<OpenBetDetailsTO> betDetailsList = new ArrayList<>();

    private List<OpenBetDetailsTO> layBets;

    private List<OpenBetDetailsTO> backBets;

    public List<OpenBetDetailsTO> getLayBets() {
        return layBets;
    }

    public void setLayBets(List<OpenBetDetailsTO> layBets) {
        this.layBets = layBets;
    }

    public List<OpenBetDetailsTO> getBackBets() {
        return backBets;
    }

    public void setBackBets(List<OpenBetDetailsTO> backBets) {
        this.backBets = backBets;
    }

    public List<OpenBetDetailsTO> getBetDetailsList() {
        return betDetailsList;
    }

    public void setBetDetailsList(List<OpenBetDetailsTO> betDetailsList) {
        this.betDetailsList = betDetailsList;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
