package com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dto;

import com.bettorlogic.victoryexch.middlelayer.betfairapi.dto.OddsRunners;

import java.util.List;

public class OddsMyPojo {
    private String marketId;

    private boolean isMarketDataDelayed;

    private String status;

    private int betDelay;

    private boolean bspReconciled;

    private boolean complete;

    private boolean inplay;

    private int numberOfWinners;

    private int numberOfRunners;

    private int numberOfActiveRunners;

    private String lastMatchTime;

    private double totalMatched;

    private double totalAvailable;

    private boolean crossMatching;

    private boolean runnersVoidable;

    private long version;

    private List<OddsRunners> runners;

    public String getMarketId() {
        return this.marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public boolean getIsMarketDataDelayed() {
        return this.isMarketDataDelayed;
    }

    public void setIsMarketDataDelayed(boolean isMarketDataDelayed) {
        this.isMarketDataDelayed = isMarketDataDelayed;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBetDelay() {
        return this.betDelay;
    }

    public void setBetDelay(int betDelay) {
        this.betDelay = betDelay;
    }

    public boolean getBspReconciled() {
        return this.bspReconciled;
    }

    public void setBspReconciled(boolean bspReconciled) {
        this.bspReconciled = bspReconciled;
    }

    public boolean getComplete() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean getInplay() {
        return this.inplay;
    }

    public void setInplay(boolean inplay) {
        this.inplay = inplay;
    }

    public int getNumberOfWinners() {
        return this.numberOfWinners;
    }

    public void setNumberOfWinners(int numberOfWinners) {
        this.numberOfWinners = numberOfWinners;
    }

    public int getNumberOfRunners() {
        return this.numberOfRunners;
    }

    public void setNumberOfRunners(int numberOfRunners) {
        this.numberOfRunners = numberOfRunners;
    }

    public int getNumberOfActiveRunners() {
        return this.numberOfActiveRunners;
    }

    public void setNumberOfActiveRunners(int numberOfActiveRunners) {
        this.numberOfActiveRunners = numberOfActiveRunners;
    }

    public String getLastMatchTime() {
        return this.lastMatchTime;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public double getTotalMatched() {
        return this.totalMatched;
    }

    public void setTotalMatched(double totalMatched) {
        this.totalMatched = totalMatched;
    }

    public double getTotalAvailable() {
        return this.totalAvailable;
    }

    public void setTotalAvailable(double totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public boolean getCrossMatching() {
        return this.crossMatching;
    }

    public void setCrossMatching(boolean crossMatching) {
        this.crossMatching = crossMatching;
    }

    public boolean getRunnersVoidable() {
        return this.runnersVoidable;
    }

    public void setRunnersVoidable(boolean runnersVoidable) {
        this.runnersVoidable = runnersVoidable;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<OddsRunners> getRunners() {
        return this.runners;
    }

    public void setRunners(List<OddsRunners> runners) {
        this.runners = runners;
    }

    @Override
    public String toString() {
        return "OddsMyPojo{" +
                "marketId='" + marketId + '\'' +
                ", isMarketDataDelayed=" + isMarketDataDelayed +
                ", status='" + status + '\'' +
                ", betDelay=" + betDelay +
                ", bspReconciled=" + bspReconciled +
                ", complete=" + complete +
                ", inplay=" + inplay +
                ", numberOfWinners=" + numberOfWinners +
                ", numberOfRunners=" + numberOfRunners +
                ", numberOfActiveRunners=" + numberOfActiveRunners +
                ", lastMatchTime='" + lastMatchTime + '\'' +
                ", totalMatched=" + totalMatched +
                ", totalAvailable=" + totalAvailable +
                ", crossMatching=" + crossMatching +
                ", runnersVoidable=" + runnersVoidable +
                ", version=" + version +
                ", runners=" + runners +
                '}';
    }
}