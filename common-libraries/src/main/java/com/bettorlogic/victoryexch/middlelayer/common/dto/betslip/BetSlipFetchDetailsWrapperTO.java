package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BetSlipFetchDetailsWrapperTO {

    @Valid
    @NotNull
    @JsonProperty(ColumnLabelConstants.BACK_BETS)
    private List<BetDetailsFetchTO> backBetsList = new ArrayList<>();

    @Valid
    @NotNull
    @JsonProperty(ColumnLabelConstants.LAY_BETS)
    private List<BetDetailsFetchTO> layBetsList = new ArrayList<>();

    private String userComments;

    public List<BetDetailsFetchTO> getBackBetsList() {
        return backBetsList;
    }

    public void setBackBetsList(List<BetDetailsFetchTO> backBetsList) {
        this.backBetsList = backBetsList;
    }

    public List<BetDetailsFetchTO> getLayBetsList() {
        return layBetsList;
    }

    public void setLayBetsList(List<BetDetailsFetchTO> layBetsList) {
        this.layBetsList = layBetsList;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }
}