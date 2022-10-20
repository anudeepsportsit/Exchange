package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HighlightsSportTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.EVENTS)
    private List<EventDetailsTO> eventDetailsList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sportId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    public List<EventDetailsTO> getEventDetailsList() {
        return eventDetailsList;
    }

    public void setEventDetailsList(List<EventDetailsTO> eventDetailsList) {
        this.eventDetailsList = eventDetailsList;
    }
}
