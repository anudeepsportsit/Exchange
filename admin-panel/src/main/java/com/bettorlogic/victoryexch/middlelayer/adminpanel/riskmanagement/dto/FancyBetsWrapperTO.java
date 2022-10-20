package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class FancyBetsWrapperTO {
    List<MatchOddsDetailsTO> fancyBets;

    public List<MatchOddsDetailsTO> getFancyBets() {
        return fancyBets;
    }

    public void setFancyBets(List<MatchOddsDetailsTO> fancyBets) {
        this.fancyBets = fancyBets;
    }
}
