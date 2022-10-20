package com.bettorlogic.victoryexch.middlelayer.betfairapi.dto;

public class AvailableToBack {
    private double price;

    private double size;

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}