package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import java.util.List;

public class CurrentOrderSummaryReportTO {
    private List<CurrentOrderSummaryTO> currentOrders;
    private boolean moreAvailable;

    public List<CurrentOrderSummaryTO> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(List<CurrentOrderSummaryTO> currentOrders) {
        this.currentOrders = currentOrders;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public void setMoreAvailable(boolean moreAvailable) {
        this.moreAvailable = moreAvailable;
    }
}
