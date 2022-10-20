package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class MatchOddsWrapperTO {
    List<MatchOddsDetailsTO> matchOdds;

    public List<MatchOddsDetailsTO> getMatchOdds() {
        return matchOdds;
    }

    public void setMatchOdds(List<MatchOddsDetailsTO> matchOdds) {
        this.matchOdds = matchOdds;
    }
}
