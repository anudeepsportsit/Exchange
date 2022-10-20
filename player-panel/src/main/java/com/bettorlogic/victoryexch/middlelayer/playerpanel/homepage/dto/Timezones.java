package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

public class Timezones {

    private int timeZoneId;
    private String timeZone;
    private String timeZoneDifference;

    public String getTimeZoneDifference() {
        return timeZoneDifference;
    }

    public void setTimeZoneDifference(String timeZoneDifference) {
        this.timeZoneDifference = timeZoneDifference;
    }

    public int getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(int timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}