package com.bettorlogic.victoryexch.middlelayer.betfairapi.dto;

public class OddsTypes {

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
    private int selectionId;

    private int handicap;

    private String runnerStatus;

    private double lastPriceTraded;

    private int runnerTotalMatched;
    private double availableToBackprice;

    private double availableToBacksize;

    private double availableToLayprice;

    private double availableToLaysize;

    private double tradedVolumeSize;

    private double tradedVolumePrice;

    public OddsTypes(String marketId, boolean isMarketDataDelayed,
                     String status, int betDelay,
                     boolean bspReconciled,
                     boolean complete, boolean inplay, int numberOfWinners,
                     int numberOfRunners,
                     int numberOfActiveRunners, String lastMatchTime,
                     double totalMatched, double totalAvailable, boolean crossMatching,
                     boolean runnersVoidable, long version, int selectionId, int handicap,
                     String runnerStatus, double lastPriceTraded, int runnerTotalMatched,
                     double availableToBackprice, double availableToBacksize, double availableToLayprice,
                     double availableToLaysize) {
        this.marketId = marketId;
        this.isMarketDataDelayed = isMarketDataDelayed;
        this.status = status;
        this.betDelay = betDelay;
        this.bspReconciled = bspReconciled;
        this.complete = complete;
        this.inplay = inplay;
        this.numberOfWinners = numberOfWinners;
        this.numberOfRunners = numberOfRunners;
        this.numberOfActiveRunners = numberOfActiveRunners;
        this.lastMatchTime = lastMatchTime;
        this.totalMatched = totalMatched;
        this.totalAvailable = totalAvailable;
        this.crossMatching = crossMatching;
        this.runnersVoidable = runnersVoidable;
        this.version = version;
        this.selectionId = selectionId;
        this.handicap = handicap;
        this.runnerStatus = runnerStatus;
        this.lastPriceTraded = lastPriceTraded;
        this.runnerTotalMatched = runnerTotalMatched;
        this.availableToBackprice = availableToBackprice;
        this.availableToBacksize = availableToBacksize;
        this.availableToLayprice = availableToLayprice;
        this.availableToLaysize = availableToLaysize;


    }

    public OddsTypes() {

    }

    public boolean isMarketDataDelayed() {
        return isMarketDataDelayed;
    }

    public void setMarketDataDelayed(boolean marketDataDelayed) {
        isMarketDataDelayed = marketDataDelayed;
    }

    public double getTradedVolumeSize() {
        return tradedVolumeSize;
    }

    public void setTradedVolumeSize(double tradedVolumeSize) {
        this.tradedVolumeSize = tradedVolumeSize;
    }

    public double getTradedVolumePrice() {
        return tradedVolumePrice;
    }

    public void setTradedVolumePrice(double tradedVolumePrice) {
        this.tradedVolumePrice = tradedVolumePrice;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public boolean getisMarketDataDelayed() {
        return this.isMarketDataDelayed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBetDelay() {
        return betDelay;
    }

    public void setBetDelay(int betDelay) {
        this.betDelay = betDelay;
    }

    public boolean isBspReconciled() {
        return bspReconciled;
    }

    public void setBspReconciled(boolean bspReconciled) {
        this.bspReconciled = bspReconciled;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isInplay() {
        return inplay;
    }

    public void setInplay(boolean inplay) {
        this.inplay = inplay;
    }

    public int getNumberOfWinners() {
        return numberOfWinners;
    }

    public void setNumberOfWinners(int numberOfWinners) {
        this.numberOfWinners = numberOfWinners;
    }

    public int getNumberOfRunners() {
        return numberOfRunners;
    }

    public void setNumberOfRunners(int numberOfRunners) {
        this.numberOfRunners = numberOfRunners;
    }

    public int getNumberOfActiveRunners() {
        return numberOfActiveRunners;
    }

    public void setNumberOfActiveRunners(int numberOfActiveRunners) {
        this.numberOfActiveRunners = numberOfActiveRunners;
    }

    public String getLastMatchTime() {
        return lastMatchTime;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public double getTotalMatched() {
        return totalMatched;
    }

    public void setTotalMatched(double totalMatched) {
        this.totalMatched = totalMatched;
    }

    public double getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(double totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public boolean isCrossMatching() {
        return crossMatching;
    }

    public void setCrossMatching(boolean crossMatching) {
        this.crossMatching = crossMatching;
    }

    public boolean isRunnersVoidable() {
        return runnersVoidable;
    }

    public void setRunnersVoidable(boolean runnersVoidable) {
        this.runnersVoidable = runnersVoidable;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public int getHandicap() {
        return handicap;
    }

    public void setHandicap(int handicap) {
        this.handicap = handicap;
    }

    public String getRunnerStatus() {
        return runnerStatus;
    }

    public void setRunnerStatus(String runnerStatus) {
        this.runnerStatus = runnerStatus;
    }

    public double getLastPriceTraded() {
        return lastPriceTraded;
    }

    public void setLastPriceTraded(double lastPriceTraded) {
        this.lastPriceTraded = lastPriceTraded;
    }

    public int getRunnerTotalMatched() {
        return runnerTotalMatched;
    }

    public void setRunnerTotalMatched(int runnerTotalMatched) {
        this.runnerTotalMatched = runnerTotalMatched;
    }

    public double getAvailableToBackprice() {
        return availableToBackprice;
    }

    public void setAvailableToBackprice(double availableToBackprice) {
        this.availableToBackprice = availableToBackprice;
    }

    public double getAvailableToBacksize() {
        return availableToBacksize;
    }

    public void setAvailableToBacksize(double availableToBacksize) {
        this.availableToBacksize = availableToBacksize;
    }

    public double getAvailableToLayprice() {
        return availableToLayprice;
    }

    public void setAvailableToLayprice(double availableToLayprice) {
        this.availableToLayprice = availableToLayprice;
    }

    public double getAvailableToLaysize() {
        return availableToLaysize;
    }

    public void setAvailableToLaysize(double availableToLaysize) {
        this.availableToLaysize = availableToLaysize;
    }
}
