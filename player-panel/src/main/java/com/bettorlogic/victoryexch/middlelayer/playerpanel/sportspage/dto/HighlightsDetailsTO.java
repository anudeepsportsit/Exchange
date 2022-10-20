package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.EventDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class HighlightsDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String date;

    @JsonProperty(SportsBookConstants.EVENTS)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventDetailsTO> eventDetailsList = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EventDetailsTO> getEventDetailsList() {
        return eventDetailsList;
    }

    public void setEventDetailsList(List<EventDetailsTO> eventDetailsList) {
        this.eventDetailsList = eventDetailsList;
    }
}