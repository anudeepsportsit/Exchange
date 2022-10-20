package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public class AdminMatchOddsDetailsTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer sportId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer eventId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer teamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String team;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double backOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double layOdds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal backSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal laySize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal minStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal maxStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer marketGroupId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer providerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer oddDictionaryId;

    public AdminMatchOddsDetailsTO() {
    }

    public AdminMatchOddsDetailsTO(AdminMatchOddsDetailsTO adminMatchOddsDetailsTO) {
        this.sportId = adminMatchOddsDetailsTO.getSportId();
        this.eventId = adminMatchOddsDetailsTO.getEventId();
        this.team = adminMatchOddsDetailsTO.getTeam();
        this.backOdds = adminMatchOddsDetailsTO.getBackOdds();
        this.layOdds = adminMatchOddsDetailsTO.getLayOdds();
        this.backSize = adminMatchOddsDetailsTO.getBackSize();
        this.laySize = adminMatchOddsDetailsTO.getLaySize();
        this.minStake = adminMatchOddsDetailsTO.getMinStake();
        this.maxStake = adminMatchOddsDetailsTO.getMaxStake();
        this.marketGroupId = adminMatchOddsDetailsTO.getMarketGroupId();
        this.providerId = adminMatchOddsDetailsTO.getProviderId();
        this.oddDictionaryId = adminMatchOddsDetailsTO.getOddDictionaryId();
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public BigDecimal getBackSize() {
        return backSize;
    }

    public void setBackSize(BigDecimal backSize) {
        this.backSize = backSize;
    }

    public BigDecimal getLaySize() {
        return laySize;
    }

    public void setLaySize(BigDecimal laySize) {
        this.laySize = laySize;
    }

    public BigDecimal getMinStake() {
        return minStake;
    }

    public void setMinStake(BigDecimal minStake) {
        this.minStake = minStake;
    }

    public BigDecimal getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(BigDecimal maxStake) {
        this.maxStake = maxStake;
    }

    public Integer getMarketGroupId() {
        return marketGroupId;
    }

    public void setMarketGroupId(Integer marketGroupId) {
        this.marketGroupId = marketGroupId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getOddDictionaryId() {
        return oddDictionaryId;
    }

    public void setOddDictionaryId(Integer oddDictionaryId) {
        this.oddDictionaryId = oddDictionaryId;
    }
}
