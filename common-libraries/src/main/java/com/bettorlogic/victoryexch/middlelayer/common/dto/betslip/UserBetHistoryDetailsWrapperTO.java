package com.bettorlogic.victoryexch.middlelayer.common.dto.betslip;

import java.util.List;

public class UserBetHistoryDetailsWrapperTO {
    private List<UserBetHistoryDetailsTO> userBetDetails;

    public List<UserBetHistoryDetailsTO> getUserBetDetails() {
        return userBetDetails;
    }

    public void setUserBetDetails(List<UserBetHistoryDetailsTO> userBetDetails) {
        this.userBetDetails = userBetDetails;
    }
}
