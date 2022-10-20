package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CountryDetailsTO {
    private Integer countryId;
    private String countryName;

    @JsonProperty(SportsBookConstants.LEAGUES)
    private List<LeagueDetailsTO> leagueDetailsList = new ArrayList<>();

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

    public List<LeagueDetailsTO> getLeagueDetailsList() {
        return leagueDetailsList;
    }

    public void setLeagueDetailsList(List<LeagueDetailsTO> leagueDetailsList) {
        this.leagueDetailsList = leagueDetailsList;
    }
}
