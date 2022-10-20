package com.bettorlogic.victoryexch.middlelayer.common.dto.eventview;

import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EventViewOutcomeDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clOutcomeId;

    @JsonProperty(SportsBookConstants.ID)
    private Integer outcomeId;

    @JsonProperty(SportsBookConstants.NAME)
    private String outcomeName;

    @JsonProperty(SportsBookConstants.BACK_ODDS)
    private List<OddDetailsTO> backOddList = new ArrayList<>();

    @JsonProperty(SportsBookConstants.LAY_ODDS)
    private List<OddDetailsTO> layOddList = new ArrayList<>();


    private Integer marketSuspended;

    @JsonIgnore
    private int outcomeOrder;

    public int getOutcomeOrder() {
        return outcomeOrder;
    }

    public void setOutcomeOrder(int outcomeOrder) {
        this.outcomeOrder = outcomeOrder;
    }

    public List<OddDetailsTO> getBackOddList() {
        return backOddList;
    }

    public void setBackOddList(List<OddDetailsTO> backOddList) {
        this.backOddList = backOddList;
    }

    public List<OddDetailsTO> getLayOddList() {
        return layOddList;
    }

    public void setLayOddList(List<OddDetailsTO> layOddList) {
        this.layOddList = layOddList;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public String getClOutcomeId() {
        return clOutcomeId;
    }

    public void setClOutcomeId(String clOutcomeId) {
        this.clOutcomeId = clOutcomeId;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public Integer getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Integer marketSuspended) {
        this.marketSuspended = marketSuspended;
    }
}
