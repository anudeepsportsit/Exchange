package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class ExchangePricesTO {

    private List<PriceSizeTO> availableToBack;
    private List<PriceSizeTO> availableToLay;
    private List<PriceSizeTO> tradedVolume;

    public List<PriceSizeTO> getAvailableToBack() {
        return availableToBack;
    }

    public void setAvailableToBack(List<PriceSizeTO> availableToBack) {
        this.availableToBack = availableToBack;
    }

    public List<PriceSizeTO> getAvailableToLay() {
        return availableToLay;
    }

    public void setAvailableToLay(List<PriceSizeTO> availableToLay) {
        this.availableToLay = availableToLay;
    }

    public List<PriceSizeTO> getTradedVolume() {
        return tradedVolume;
    }

    public void setTradedVolume(List<PriceSizeTO> tradedVolume) {
        this.tradedVolume = tradedVolume;
    }

    public String toString() {
        return "{" + "" + "availableToBack=" + getAvailableToBack() + ","
                + "availableToLay=" + getAvailableToLay() + ","
                + "tradedVolume=" + getTradedVolume() + "," + "}";
    }

}
