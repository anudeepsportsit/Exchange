package com.bettorlogic.victoryexch.middlelayer.betfairapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class OddsEx {

    private List<AvailableToBack> availableToBack = new ArrayList<>();

    private List<AvailableToLay> availableToLay = new ArrayList<>();

    @JsonIgnore
    private List<TradedVolume> tradedVolume;

    public List<AvailableToBack> getAvailableToBack() {
        return this.availableToBack;
    }

    public void setAvailableToBack(List<AvailableToBack> availableToBack) {
        this.availableToBack = availableToBack;
    }

    public List<AvailableToLay> getAvailableToLay() {
        return this.availableToLay;
    }

    public void setAvailableToLay(List<AvailableToLay> availableToLay) {
        this.availableToLay = availableToLay;
    }

    public List<TradedVolume> getTradedVolume() {
        return tradedVolume;
    }

    public void setTradedVolume(List<TradedVolume> tradedVolume) {
        this.tradedVolume = tradedVolume;
    }

    @Override
    public String toString() {
        return "OddsEx{" +
                "availableToBack=" + availableToBack +
                ", availableToLay=" + availableToLay +
                ", tradedVolume=" + tradedVolume +
                '}';
    }
}
