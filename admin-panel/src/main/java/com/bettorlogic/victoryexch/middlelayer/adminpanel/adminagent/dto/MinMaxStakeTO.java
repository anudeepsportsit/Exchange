package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class MinMaxStakeTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minStake;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxStake;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String marketName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer oldMinStake;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer oldMaxStake;

    private Integer eventId;

    private String isFancy;

    public String getIsFancy() {
        return isFancy;
    }

    public void setIsFancy(String isFancy) {
        this.isFancy = isFancy;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOldMinStake() {
        return oldMinStake;
    }

    public void setOldMinStake(Integer oldMinStake) {
        this.oldMinStake = oldMinStake;
    }

    public Integer getOldMaxStake() {
        return oldMaxStake;
    }

    public void setOldMaxStake(Integer oldMaxStake) {
        this.oldMaxStake = oldMaxStake;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public Integer getMinStake() {
        return minStake;
    }

    public void setMinStake(Integer minStake) {
        this.minStake = minStake;
    }

    public Integer getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(Integer maxStake) {
        this.maxStake = maxStake;
    }

    List<Integer> minStakeList = new ArrayList<>();
    List<Integer> maxStakeList = new ArrayList<>();

    public List<Integer> getMinStakeList() {
        return minStakeList;
    }

    public void setMinStakeList(List<Integer> minStakeList) {
        this.minStakeList = minStakeList;
    }

    public List<Integer> getMaxStakeList() {
        return maxStakeList;
    }

    public void setMaxStakeList(List<Integer> maxStakeList) {
        this.maxStakeList = maxStakeList;
    }
}
