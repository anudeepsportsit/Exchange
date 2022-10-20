package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class ClearedOrderSummaryReportTO {
    private boolean moreAvailable;
    private List<ClearedOrderSummaryTO> clearedOrders;

    public List<ClearedOrderSummaryTO> getClearedOrders() {
        return clearedOrders;
    }

    public void setClearedOrders(List<ClearedOrderSummaryTO> clearedOrders) {
        this.clearedOrders = clearedOrders;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }
}

