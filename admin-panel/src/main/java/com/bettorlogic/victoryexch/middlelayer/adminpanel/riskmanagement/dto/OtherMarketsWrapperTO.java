package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class OtherMarketsWrapperTO {

    List<MatchOddsDetailsTO> otherMarkets;

    public List<MatchOddsDetailsTO> getOtherMarkets() {
        return otherMarkets;
    }

    public void setOtherMarkets(List<MatchOddsDetailsTO> otherMarkets) {
        this.otherMarkets = otherMarkets;
    }
}
