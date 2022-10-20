package com.bettorlogic.victoryexch.middlelayer.betfairapi.dao;


import com.bettorlogic.victoryexch.middlelayer.betfairapi.dto.OddsMyPojo;

public interface BetfairAPIDao {

    OddsMyPojo getOdds(String marketId);
}
