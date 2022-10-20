package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

public class FancyMarketInputTO {

    @NotNull(message = AdminPanelConstants.ID_NOT_NULL)
    private Integer sportId;
    @NotNull(message = AdminPanelConstants.ID_NOT_NULL)
    private Integer leagueId;
    @NotNull(message = AdminPanelConstants.ID_NOT_NULL)
    private Integer eventId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String loginToken;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "FancyMarketInput{" +
                "sportId=" + sportId +
                ", leagueId=" + leagueId +
                ", eventId=" + eventId +
                '}';
    }
}
