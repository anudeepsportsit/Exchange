package com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SportWrapperTO {

    @JsonProperty(SportsBookConstants.SPORTS)
    private List<SportHierarchyDetailsTO> sportsList = new ArrayList<>();

    public List<SportHierarchyDetailsTO> getSportsList() {
        return sportsList;
    }
}