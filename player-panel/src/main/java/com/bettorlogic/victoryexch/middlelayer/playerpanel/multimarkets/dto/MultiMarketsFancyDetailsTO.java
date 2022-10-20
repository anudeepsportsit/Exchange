package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsFancyDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(HomePageConstants.FANCY_MARKETS)
    private List<MultiMarketsDetailsTO> fancyMarketList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isPinned;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;

    private Integer providerId;
    private String providerName;

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

    public Integer getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Integer isPinned) {
        this.isPinned = isPinned;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public List<MultiMarketsDetailsTO> getFancyMarketList() {
        return fancyMarketList;
    }

    public void setFancyMarketList(List<MultiMarketsDetailsTO> fancyMarketList) {
        this.fancyMarketList = fancyMarketList;
    }
}
