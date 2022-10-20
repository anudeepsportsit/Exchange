package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FancyMarketDetailsWrapperTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(SportsBookConstants.MARKET_GROUP_ID)
    private Integer fancyMarketGroupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer providerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String providerName;

    @JsonProperty(SportsBookConstants.FANCY_MARKETS)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FancyBetDetailsTO> fancyBetDetailsList = new ArrayList<>();

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }

    public List<FancyBetDetailsTO> getFancyBetDetailsList() {
        return fancyBetDetailsList;
    }

    public void setFancyBetDetailsList(List<FancyBetDetailsTO> fancyBetDetailsList) {
        this.fancyBetDetailsList = fancyBetDetailsList;
    }

    public Integer getFancyMarketGroupId() {
        return fancyMarketGroupId;
    }

    public void setFancyMarketGroupId(Integer fancyMarketGroupId) {
        this.fancyMarketGroupId = fancyMarketGroupId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
