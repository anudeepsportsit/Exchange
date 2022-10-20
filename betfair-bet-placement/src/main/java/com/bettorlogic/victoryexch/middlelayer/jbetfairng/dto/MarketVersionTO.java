package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

/**
 * @author catalin.mincu @since 27/12/17.
 */
public class MarketVersionTO {
    private long version;

    public MarketVersionTO(long version) {
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
