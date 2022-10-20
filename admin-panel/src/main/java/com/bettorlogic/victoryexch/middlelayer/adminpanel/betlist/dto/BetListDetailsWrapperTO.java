package com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto;

import java.util.List;

public class BetListDetailsWrapperTO {
    private List<BetListDetailsTO> betListDetailsList;

    public List<BetListDetailsTO> getBetListDetailsList() {
        return betListDetailsList;
    }

    public void setBetListDetailsList(List<BetListDetailsTO> betListDetailsList) {
        this.betListDetailsList = betListDetailsList;
    }
}