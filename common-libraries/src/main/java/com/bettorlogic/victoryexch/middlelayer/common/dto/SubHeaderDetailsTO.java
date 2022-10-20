package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubHeaderDetailsTO {

    @JsonProperty(SportsBookConstants.ID)
    private Integer subHeaderId;

    @JsonProperty(SportsBookConstants.NAME)
    private String subHeaderName;

    @JsonProperty(SportsBookConstants.URL)
    private String subHeaderUrl;

    public Integer getSubHeaderId() {
        return subHeaderId;
    }

    public void setSubHeaderId(Integer subHeaderId) {
        this.subHeaderId = subHeaderId;
    }

    public String getSubHeaderName() {
        return subHeaderName;
    }

    public void setSubHeaderName(String subHeaderName) {
        this.subHeaderName = subHeaderName;
    }

    public String getSubHeaderUrl() {
        return subHeaderUrl;
    }

    public void setSubHeaderUrl(String subHeaderUrl) {
        this.subHeaderUrl = subHeaderUrl;
    }
}
