package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SportHierarchyDetailsTO {

    @JsonProperty(SportsBookConstants.ID)
    private Integer sportId;

    @JsonProperty(SportsBookConstants.NAME)
    private String sportName;

    @JsonProperty(SportsBookConstants.EVENTS)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<EventDetailsTO> eventsList = new ArrayList<>();

    @JsonProperty(SportsBookConstants.LEAGUES)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<LeagueDetailsTO> leagueDetailsList = new ArrayList<>();

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

    public List<EventDetailsTO> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<EventDetailsTO> eventsList) {
        this.eventsList = eventsList;
    }

    public List<LeagueDetailsTO> getLeagueDetailsList() {
        return leagueDetailsList;
    }

    public void setLeagueDetailsList(List<LeagueDetailsTO> leagueDetailsList) {
        this.leagueDetailsList = leagueDetailsList;
    }
}