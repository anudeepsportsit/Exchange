package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

public class TimeRangeResultTO {
    private TimeRangeTO timeRange;
    private int marketCount;

    public TimeRangeTO getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRangeTO timeRange) {
        this.timeRange = timeRange;
    }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }
}
