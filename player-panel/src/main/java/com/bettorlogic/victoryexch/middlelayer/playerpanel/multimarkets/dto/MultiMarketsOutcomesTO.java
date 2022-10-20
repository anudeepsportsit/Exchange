package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsOutcomesTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OddDetailsTO> backOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OddDetailsTO> layOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clOutcomeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonProperty(HomePageConstants.NAME)
    private String outcomeName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer outcomeId;
    @JsonProperty(HomePageConstants.OUTCOME_NAME)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer odds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String outcome;

    public String getClOutcomeId() {
        return clOutcomeId;
    }

    public void setClOutcomeId(String clOutcomeId) {
        this.clOutcomeId = clOutcomeId;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(Integer outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOdds() {
        return odds;
    }

    public void setOdds(Integer odds) {
        this.odds = odds;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OddDetailsTO> getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(List<OddDetailsTO> backOdds) {
        this.backOdds = backOdds;
    }

    public List<OddDetailsTO> getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(List<OddDetailsTO> layOdds) {
        this.layOdds = layOdds;
    }
}
