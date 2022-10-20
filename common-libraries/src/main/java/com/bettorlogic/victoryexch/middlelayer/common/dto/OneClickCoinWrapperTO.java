package com.bettorlogic.victoryexch.middlelayer.common.dto;

import java.util.List;

public class OneClickCoinWrapperTO {

    private List<CoinDetailsTO> oneClickCoinDetailsList;

    public List<CoinDetailsTO> getOneClickCoinDetailsList() {
        return oneClickCoinDetailsList;
    }

    public void setOneClickCoinDetailsList(List<CoinDetailsTO> oneClickCoinDetailsList) {
        this.oneClickCoinDetailsList = oneClickCoinDetailsList;
    }

    @Override
    public String toString() {
        return "OneClickCoinWrapperTO{" +
                "oneClickCoinDetailsList=" + oneClickCoinDetailsList +
                '}';
    }
}