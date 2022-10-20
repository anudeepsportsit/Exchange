package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class PlayersWrapperTO {
    List<PlayersDetailsTO> matchedList;
    List<PlayersDetailsTO> exposureList;

    public List<PlayersDetailsTO> getMatchedList() {
        return matchedList;
    }

    public void setMatchedList(List<PlayersDetailsTO> matchedList) {
        this.matchedList = matchedList;
    }

    public List<PlayersDetailsTO> getExposureList() {
        return exposureList;
    }

    public void setExposureList(List<PlayersDetailsTO> exposureList) {
        this.exposureList = exposureList;
    }
}
