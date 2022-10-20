package com.bettorlogic.victoryexch.middlelayer.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CoinDetailsTO {

    private Integer coinId;
    private Double coinValue;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer isSelected;

    public Integer getCoinId() {
        return coinId;
    }


    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public Double getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(Double coinValue) {
        this.coinValue = coinValue;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }


    @Override
    public String toString() {
        return "CoinDetailsTO{" +
                "coinId=" + coinId +
                ", coinValue=" + coinValue +
                ", isSelected=" + isSelected +
                '}';
    }
}
