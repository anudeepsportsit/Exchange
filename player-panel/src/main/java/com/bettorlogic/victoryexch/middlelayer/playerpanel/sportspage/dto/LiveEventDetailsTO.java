package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.EventDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LiveEventDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.EVENTS)
    private List<EventDetailsTO> eventList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer leagueId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String leagueName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public List<EventDetailsTO> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDetailsTO> eventList) {
        this.eventList = eventList;
    }
}
