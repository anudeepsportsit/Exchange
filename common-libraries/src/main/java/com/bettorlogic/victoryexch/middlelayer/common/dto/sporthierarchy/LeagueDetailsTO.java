package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LeagueDetailsTO {

    @JsonProperty(SportsBookConstants.ID)
    private Integer leagueId;

    @JsonProperty(SportsBookConstants.NAME)
    private String leagueName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer countryId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String countryName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(SportsBookConstants.EVENTS)
    private List<EventDetailsTO> eventsList = new ArrayList<>();

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

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

    public List<EventDetailsTO> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<EventDetailsTO> eventsList) {
        this.eventsList = eventsList;
    }
}