package com.bettorlogic.victoryexch.middlelayer.adminpanel.riskmanagement.dto;

import java.util.List;

public class MatchOddsDetailsTO {

    List<MatchOddsBetsDetailsTO> bets;
    private Integer sportId;
    private String sportName;

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<MatchOddsBetsDetailsTO> getBets() {
        return bets;
    }

    public void setBets(List<MatchOddsBetsDetailsTO> bets) {
        this.bets = bets;
    }
}
