package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.OrderType;

public class PlaceInstructionTO {

    private OrderType orderType;
    private long selectionId;
    private double handicap;
    private String side;
    private LimitOrderTO limitOrder;
    private LimitOnCloseOrderTO limitOnCloseOrder;
    private MarketOnCloseOrderTO marketOnCloseOrder;

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public LimitOrderTO getLimitOrder() {
        return limitOrder;
    }

    public void setLimitOrder(LimitOrderTO limitOrder) {
        this.limitOrder = limitOrder;
    }

    public LimitOnCloseOrderTO getLimitOnCloseOrder() {
        return limitOnCloseOrder;
    }

    public void setLimitOnCloseOrder(LimitOnCloseOrderTO limitOnCloseOrder) {
        this.limitOnCloseOrder = limitOnCloseOrder;
    }

    public MarketOnCloseOrderTO getMarketOnCloseOrder() {
        return marketOnCloseOrder;
    }

    public void setMarketOnCloseOrder(MarketOnCloseOrderTO marketOnCloseOrder) {
        this.marketOnCloseOrder = marketOnCloseOrder;
    }

}
