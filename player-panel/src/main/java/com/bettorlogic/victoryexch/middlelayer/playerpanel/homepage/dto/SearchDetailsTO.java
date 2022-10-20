package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchDetailsTO {

    @JsonProperty("event")
    List<SearchEventDeatilsTO> eventList;

    public List<SearchEventDeatilsTO> getEventList() {
        return eventList;
    }

    public void setEventList(List<SearchEventDeatilsTO> eventList) {
        this.eventList = eventList;
    }
}
