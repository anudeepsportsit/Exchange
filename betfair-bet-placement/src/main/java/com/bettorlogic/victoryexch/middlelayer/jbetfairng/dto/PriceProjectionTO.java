package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.PriceData;

import java.util.Set;


public class PriceProjectionTO {
    private Set<PriceData> priceData;
    private ExBestOfferOverRidesTO exBestOfferOverRides;
    private boolean virtualise;
    private boolean rolloverStakes;

    public Set<PriceData> getPriceData() {
        return priceData;
    }

    public void setPriceData(Set<PriceData> priceData) {
        this.priceData = priceData;
    }

    public ExBestOfferOverRidesTO getExBestOfferOverRides() {
        return exBestOfferOverRides;
    }

    public void setExBestOfferOverRides(
            ExBestOfferOverRidesTO exBestOfferOverRides) {
        this.exBestOfferOverRides = exBestOfferOverRides;
    }

    public boolean isVirtualise() {
        return virtualise;
    }

    public void setVirtualise(boolean virtualise) {
        this.virtualise = virtualise;
    }

    public boolean isRolloverStakes() {
        return rolloverStakes;
    }

    public void setRolloverStakes(boolean rolloverStakes) {
        this.rolloverStakes = rolloverStakes;
    }

}
