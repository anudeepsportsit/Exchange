package com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.MarketFilterTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.PriceProjectionTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.TimeRangeTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.MarketProjection;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.MarketType;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.PriceData;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Helpers {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s);

    }

    private static boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static double getMaxBetIncrement(double currentPrice) {
        if (currentPrice >= 1.01 && currentPrice < 2) {
            return currentPrice + 0.01;
        } else if (currentPrice >= 2 && currentPrice < 3) {
            return currentPrice + 0.02;
        } else if (currentPrice >= 3 && currentPrice < 4) {
            return currentPrice + 0.05;
        } else if (currentPrice >= 4 && currentPrice < 6) {
            return currentPrice + 0.1;
        } else if (currentPrice >= 6 && currentPrice < 10) {
            return currentPrice + 0.2;
        } else if (currentPrice >= 10 && currentPrice < 20) {
            return currentPrice + 0.5;
        } else if (currentPrice >= 20 && currentPrice < 30) {
            return currentPrice + 1;
        } else if (currentPrice >= 30 && currentPrice < 50) {
            return currentPrice + 2;
        } else if (currentPrice >= 50 && currentPrice < 100) {
            return currentPrice + 5;
        } else if (currentPrice >= 100 && currentPrice < 1000) {
            return currentPrice + 10;
        }
        return 0;
    }

    public static MarketFilterTO soccerMatchFilter(String country, TimeRangeTO timeRange, Set<String> marketTypeCodes, String textQuery) {
        MarketFilterTO marketFilter = new MarketFilterTO();
        //EventTypeId 1= SOCCER
        marketFilter.setEventTypeIds(new HashSet<>(Collections.singleton("1")));

        if (marketTypeCodes == null) {
            // default to MatchTO odds (1X2)
            marketFilter.setMarketTypeCodes(Collections.singleton(MarketType.MATCH_ODDS.toString()));
        } else {
            marketFilter.setMarketTypeCodes(marketTypeCodes);
        }


        if (timeRange == null) {
            TimeRangeTO localTimeRange = new TimeRangeTO();
            localTimeRange.setFrom(DateTime.now().minusHours(10).toDate());
            localTimeRange.setTo(DateTime.now().plusDays(3).toDate());
            marketFilter.setMarketStartTime(localTimeRange);
        } else {
            marketFilter.setMarketStartTime(timeRange);
        }

        if (country != null) {
            marketFilter.setMarketCountries(new HashSet<>(Collections.singletonList(country)));
        }

        if (!isNullOrEmpty(textQuery)) {
            marketFilter.setTextQuery(textQuery);
        }
        return marketFilter;
    }

    public static Set<MarketProjection> soccerMatchProjection() {
        Set<MarketProjection> marketProjections = new HashSet<>();
        marketProjections.add(MarketProjection.COMPETITION);
        marketProjections.add(MarketProjection.RUNNER_METADATA);
        marketProjections.add(MarketProjection.MARKET_DESCRIPTION);
        marketProjections.add(MarketProjection.EVENT);
        marketProjections.add(MarketProjection.EVENT_TYPE);

        return marketProjections;
    }

    public static PriceProjectionTO soccerPriceProjection() {

        PriceProjectionTO priceProjection = new PriceProjectionTO();
        Set<PriceData> priceData = new HashSet<>();
        priceData.add(PriceData.EX_BEST_OFFERS);
        priceProjection.setPriceData(priceData);

        return priceProjection;
    }


    public static MarketFilterTO horseRaceFilter() {
        return horseRaceFilter(null);
    }


    public static MarketFilterTO horseRaceFilter(String country) {
        MarketFilterTO marketFilter = new MarketFilterTO();
        marketFilter.setEventTypeIds(new HashSet<>(Collections.singleton("7")));
        TimeRangeTO timeRange = new TimeRangeTO();
        timeRange.setFrom(DateTime.now().toDate());
        timeRange.setTo(DateTime.now().plusDays(2).toDate());
        marketFilter.setMarketStartTime(timeRange);

        if (country != null)
            marketFilter.setMarketCountries(new HashSet<>(Arrays.asList(country)));
        marketFilter.setMarketTypeCodes(new HashSet<>(Arrays.asList("WIN")));

        return marketFilter;
    }

    public static PriceProjectionTO horseRacePriceProjection() {
        Set<PriceData> priceData = new HashSet<PriceData>();
        //get all prices from the exchange
        priceData.add(PriceData.EX_TRADED);
        priceData.add(PriceData.EX_ALL_OFFERS);

        PriceProjectionTO priceProjection = new PriceProjectionTO();
        priceProjection.setPriceData(priceData);
        return priceProjection;
    }

    public static Set<MarketProjection> horseRaceProjection() {
        Set<MarketProjection> marketProjections = new HashSet<MarketProjection>();
        marketProjections.add(MarketProjection.RUNNER_METADATA);
        marketProjections.add(MarketProjection.MARKET_DESCRIPTION);
        marketProjections.add(MarketProjection.EVENT);

        return marketProjections;
    }
}
