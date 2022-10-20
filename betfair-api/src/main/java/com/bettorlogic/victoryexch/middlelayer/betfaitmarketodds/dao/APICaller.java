package com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dao;

public interface APICaller {

    String processAPICallForFeed(String url, String requestAsJson, String applicationKey, String authenticationKey);
}
