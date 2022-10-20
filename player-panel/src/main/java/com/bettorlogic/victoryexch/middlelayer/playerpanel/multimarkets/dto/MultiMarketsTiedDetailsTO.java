package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MultiMarketsTiedDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OddDetailsTO> backOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OddDetailsTO> layOdds;
    @JsonProperty(SportsBookConstants.NAME)
    private String outcomeName;

    private String clOutcomeId;
    @JsonProperty(SportsBookConstants.ID)
    private Integer oddDictionaryId;

    public Integer getOddDictionaryId() {
        return oddDictionaryId;
    }

    public void setOddDictionaryId(Integer oddDictionaryId) {
        this.oddDictionaryId = oddDictionaryId;
    }

    public String getClOutcomeId() {
        return clOutcomeId;
    }

    public void setClOutcomeId(String clOutcomeId) {
        this.clOutcomeId = clOutcomeId;
    }

    public String getOutcomeName() {
        return outcomeName;
    }

    public void setOutcomeName(String outcomeName) {
        this.outcomeName = outcomeName;
    }

    public List<OddDetailsTO> getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(List<OddDetailsTO> layOdds) {
        this.layOdds = layOdds;
    }

    public List<OddDetailsTO> getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(List<OddDetailsTO> backOdds) {
        this.backOdds = backOdds;
    }
}
