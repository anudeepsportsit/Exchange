package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.utils.SportsPageConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeagueEventDetailsWrapperTO {

    @JsonProperty(SportsPageConstants.LEAGUES)
    private List<HighlightsDetailsTO> leagueDetailsList = new ArrayList<>();

    public List<HighlightsDetailsTO> getLeagueDetailsList() {
        return leagueDetailsList;
    }

    public void setLeagueDetailsList(List<HighlightsDetailsTO> leagueDetailsList) {
        this.leagueDetailsList = leagueDetailsList;
    }
}