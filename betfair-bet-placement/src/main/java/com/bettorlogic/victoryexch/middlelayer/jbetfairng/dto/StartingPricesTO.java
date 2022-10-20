package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class StartingPricesTO {
    private Double nearPrice;
    private Double farPrice;
    private List<PriceSizeTO> backStakeTaken = null;
    private List<PriceSizeTO> layLiabilityTaken = null;
    private Double actualSP;

    public Double getNearPrice() {
        return nearPrice;
    }

    public void setNearPrice(Double nearPrice) {
        this.nearPrice = nearPrice;
    }

    public Double getFarPrice() {
        return farPrice;
    }

    public void setFarPrice(Double farPrice) {
        this.farPrice = farPrice;
    }

    public List<PriceSizeTO> getBackStakeTaken() {
        return backStakeTaken;
    }

    public void setBackStakeTaken(List<PriceSizeTO> backStakeTaken) {
        this.backStakeTaken = backStakeTaken;
    }

    public List<PriceSizeTO> getLayLiabilityTaken() {
        return layLiabilityTaken;
    }

    public void setLayLiabilityTaken(List<PriceSizeTO> layLiabilityTaken) {
        this.layLiabilityTaken = layLiabilityTaken;
    }

    public Double getActualSP() {
        return actualSP;
    }

    public void setActualSP(Double actualSP) {
        this.actualSP = actualSP;
    }

    public String toString() {
        return "{" + "" + "nearPrice=" + getNearPrice() + "," + "farPrice="
                + getFarPrice() + "," + "backStakeTaken=" + getBackStakeTaken()
                + "," + "layLiabilityTaken=" + getLayLiabilityTaken() + ","
                + "actualSP=" + getActualSP() + "," + "}";
    }
}
