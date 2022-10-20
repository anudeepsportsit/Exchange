package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.utils.MultiMarketsConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsWrapperTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(MultiMarketsConstants.MULTI_MARKETS)
    private List<MultiMarketsEventsTO> multiMarketsList;

    public List<MultiMarketsEventsTO> getMultiMarketsList() {
        return multiMarketsList;
    }

    public void setMultiMarketsList(List<MultiMarketsEventsTO> multiMarketsList) {
        this.multiMarketsList = multiMarketsList;
    }
}
