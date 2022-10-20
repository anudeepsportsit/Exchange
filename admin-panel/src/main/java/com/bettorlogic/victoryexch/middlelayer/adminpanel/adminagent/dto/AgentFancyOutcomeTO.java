package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public class AgentFancyOutcomeTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String outcome;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double bookMakerBackOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double minStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double maxStake;

    private Integer oddDictionaryId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isSuspend;

    public Boolean getSuspend() {
        return isSuspend;
    }

    public void setSuspend(Boolean suspend) {
        isSuspend = suspend;
    }

    public Integer getOddDictionaryId() {
        return oddDictionaryId;
    }

    public void setOddDictionaryId(Integer oddDictionaryId) {
        this.oddDictionaryId = oddDictionaryId;
    }

    private Double backOdds;
    private Double layOdds;
    private Double oldBackOdds;
    private Double oldLayOdds;


    private BigDecimal hommeTeamProfitLoss;
    private BigDecimal awayTeamProfitLoss;
    private BigDecimal drawProfitLoss;

    public BigDecimal getHommeTeamProfitLoss() {
        return hommeTeamProfitLoss;
    }

    public void setHommeTeamProfitLoss(BigDecimal hommeTeamProfitLoss) {
        this.hommeTeamProfitLoss = hommeTeamProfitLoss;
    }

    public BigDecimal getAwayTeamProfitLoss() {
        return awayTeamProfitLoss;
    }

    public void setAwayTeamProfitLoss(BigDecimal awayTeamProfitLoss) {
        this.awayTeamProfitLoss = awayTeamProfitLoss;
    }

    public BigDecimal getDrawProfitLoss() {
        return drawProfitLoss;
    }

    public void setDrawProfitLoss(BigDecimal drawProfitLoss) {
        this.drawProfitLoss = drawProfitLoss;
    }

    private Double betfairbackOdds;

    public Double getOldBackOdds() {
        return oldBackOdds;
    }

    public void setOldBackOdds(Double oldBackOdds) {
        this.oldBackOdds = oldBackOdds;
    }

    public Double getOldLayOdds() {
        return oldLayOdds;
    }

    public void setOldLayOdds(Double oldLayOdds) {
        this.oldLayOdds = oldLayOdds;
    }

    private Double betfairlayOdds;

    public Double getBetfairbackOdds() {
        return betfairbackOdds;
    }

    public void setBetfairbackOdds(Double betfairbackOdds) {
        this.betfairbackOdds = betfairbackOdds;
    }

    public Double getBetfairlayOdds() {
        return betfairlayOdds;
    }

    public void setBetfairlayOdds(Double betfairlayOdds) {
        this.betfairlayOdds = betfairlayOdds;
    }

    public Double getBackOdds() {
        return backOdds;
    }

    public void setBackOdds(Double backOdds) {
        this.backOdds = backOdds;
    }

    public Double getLayOdds() {
        return layOdds;
    }

    public void setLayOdds(Double layOdds) {
        this.layOdds = layOdds;
    }

    public Double getMinStake() {
        return minStake;
    }

    public void setMinStake(Double minStake) {
        this.minStake = minStake;
    }

    public Double getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(Double maxStake) {
        this.maxStake = maxStake;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Double getBookMakerBackOdds() {
        return bookMakerBackOdds;
    }

    public void setBookMakerBackOdds(Double bookMakerBackOdds) {
        this.bookMakerBackOdds = bookMakerBackOdds;
    }
}
