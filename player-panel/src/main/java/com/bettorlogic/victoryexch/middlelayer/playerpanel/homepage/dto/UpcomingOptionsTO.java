package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpcomingOptionsTO {

    @JsonProperty(SportsBookConstants.ID)
    private Integer optionId;

    @JsonProperty(HomePageConstants.FREQUENCY)
    private String upcomingFrequency;

    private String unitMeasure;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getUpcomingFrequency() {
        return upcomingFrequency;
    }

    public void setUpcomingFrequency(String upcomingFrequency) {
        this.upcomingFrequency = upcomingFrequency;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }
}