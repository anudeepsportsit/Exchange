package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import java.util.List;

public class LiveEventWrapperTO {

    List<LiveEventDetailsTO> liveEventDetails;

    public List<LiveEventDetailsTO> getLiveEventDetails() {
        return liveEventDetails;
    }

    public void setLiveEventDetails(List<LiveEventDetailsTO> liveEventDetails) {
        this.liveEventDetails = liveEventDetails;
    }
}
