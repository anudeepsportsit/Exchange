package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;

import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderType;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.PersistenceType;

import java.util.Date;

public class ClearedOrderSummaryTO {
    private String eventTypeId;
    private String eventId;
    private String marketId;
    private long selectionId;
    private double handicap;
    private String betId;
    private Date placedDate;
    private PersistenceType persistenceType;
    private OrderType orderType;
    private ItemDescriptionTO itemDescription;
    private String betOutcome;
    private Double priceRequested;
    private int betCount;
    private double priceMatched;
    private boolean priceReduced;
    private double profit;
    private double commission;

    public String getBetOutcome() {
        return betOutcome;
    }

    public void setBetOutcome(String betOutcome) {
        this.betOutcome = betOutcome;
    }

    public Double getPriceRequested() {
        return priceRequested;
    }

    public void setPriceRequested(Double priceRequested) {
        this.priceRequested = priceRequested;
    }

    public int getBetCount() {
        return betCount;
    }

    public void setBetCount(int betCount) {
        this.betCount = betCount;
    }

    public double getPriceMatched() {
        return priceMatched;
    }

    public void setPriceMatched(double priceMatched) {
        this.priceMatched = priceMatched;
    }

    public boolean isPriceReduced() {
        return priceReduced;
    }

    public void setPriceReduced(boolean priceReduced) {
        this.priceReduced = priceReduced;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public ItemDescriptionTO getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(ItemDescriptionTO itemDescription) {
        this.itemDescription = itemDescription;
    }
}

    
