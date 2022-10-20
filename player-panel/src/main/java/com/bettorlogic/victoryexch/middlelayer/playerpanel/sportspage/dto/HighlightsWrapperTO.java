package com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.dto;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.sportspage.utils.SportsPageConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HighlightsWrapperTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsPageConstants.HIGHLIGHTS)
    private List<HighlightsDetailsTO> highlightsDetailsList;

    public List<HighlightsDetailsTO> getHighlightsDetailsList() {
        return highlightsDetailsList;
    }

    public void setHighlightsDetailsList(List<HighlightsDetailsTO> highlightsDetailsList) {
        this.highlightsDetailsList = highlightsDetailsList;
    }
}
