package com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dao.impl;


import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dao.MultiMarketsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.multimarkets.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants.*;

@Repository
public class MultiMarketsDaoImpl implements MultiMarketsDao {

    //private static final String PROC_PROCESS_MULTI_MARKETS = "select * from get_multi_markets_view_1(?) order by event_id,market_group_id,odd_dictionary,market_name,back_odds,lay_odds";
    private static final String PROC_PROCESS_MULTI_MARKETS = "select distinct * from get_multi_markets_view_5(?) order by event_id,market_group_id,provider_id,market_name,odd_dictionary,back_odds,lay_odds";
    private static final Integer TIED_MATCH_ID = 24;
    private static final Integer GOALS = 2;
    private static final Integer FANCY = 25;
    private static final Integer ADMIN = 1;
    private static final Integer BET_FAIR = 2;
    private static final Integer FANCY_YES = 3;
    private static final Integer FANCY_BACK = 4;
    private static final Integer BOTH_TEAMS_TO_SCORE = 3;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MultiMarketsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public MultiMarketsWrapperTO getMultiMarkets(String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_MULTI_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.getMarkets(cs.executeQuery()));
    }

    private MultiMarketsWrapperTO getMarkets(ResultSet rs) throws SQLException {
        MultiMarketsWrapperTO multiMarketsWrapperTO = new MultiMarketsWrapperTO();
        MultiMarketsEventsTO multiMarketsEventsTO = new MultiMarketsEventsTO();
        MultiMarketsDetailsTO matchoddsDetailsTO = new MultiMarketsDetailsTO();
        MultiMarketsDetailsTO matchodds1DetailsTO = new MultiMarketsDetailsTO();
        MultiMarketsDetailsTO fancyDetailsTO = new MultiMarketsDetailsTO();
        MultiMarketsDetailsTO fancy1DetailsTO = new MultiMarketsDetailsTO();
        MultiMarketsGoalsTO multiMarketsGoalsTO = new MultiMarketsGoalsTO();

        MultiMarketsGoalsTO multiMarketsPopularTO = new MultiMarketsGoalsTO();

        MultiMarketsOutcomesTO multiMarketsOutcomesTO;
        OddDetailsTO OddDetailsTO = new OddDetailsTO();
        List<MultiMarketsOutcomesTO> matchoddsOutcomesTOS = new ArrayList<>();
        List<MultiMarketsOutcomesTO> matchodds1OutcomesTOS = new ArrayList<>();
        List<MultiMarketsOutcomesTO> fancyOutcomesTOS = new ArrayList<>();
        List<MultiMarketsOutcomesTO> fancy1OutcomesTOS = new ArrayList<>();
        List<MultiMarketsEventsTO> multiMarketsEventsTOS = new ArrayList<>();
        List<MultiMarketsTiedDetailsTO> marketsTiedDetailsTOS = new ArrayList<>();
        List<MultiMarketsDetailsTO> multiMarketsDetailsTOS = new ArrayList<>();
        List<MultiMarketsDetailsTO> multiMarketsDetailsTOS1 = new ArrayList<>();
        MultiMarketsFancyDetailsTO multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
        MultiMarketsTiedTO multiMarketsTiedTO = new MultiMarketsTiedTO();
        List<MultiMarketsGoalsTO> goalList = new ArrayList<>();
        List<MultiMarketsGoalsTO> popularList = new ArrayList<>();

        List<MultiMarketsOutcomesTO> goalOutcomesTOS = new ArrayList<>();
        List<MultiMarketsOutcomesTO> popularOutcomesTOS = new ArrayList<>();

        int eventId = 0;
        String outcome = "";
        List<String> marketList = new ArrayList<>();
        Integer isPin = 0;
        Integer marketGroupId = 0;
        while (rs.next()) {
            int sportId = rs.getInt(ColumnLabelConstants.SPORT_ID);
            int event = rs.getInt(ColumnLabelConstants.EVENT_ID);
            String outcomeName = rs.getString(ColumnLabelConstants.OUTCOME_NAME);
            String market = rs.getString(ColumnLabelConstants.MARKET_NAME);
            int groupId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            isPin = rs.getInt(ColumnLabelConstants.IS_PINNED);
            marketGroupId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            int providerId = rs.getInt(ColumnLabelConstants.PROVIDER_ID);
            if (event != eventId && eventId != 0) {
                addMultiMarkets(rs, multiMarketsEventsTO, matchoddsDetailsTO, fancyDetailsTO, multiMarketsGoalsTO,multiMarketsPopularTO, multiMarketsEventsTOS, multiMarketsDetailsTOS,
                        multiMarketsTiedTO, goalList,popularList, goalOutcomesTOS,popularOutcomesTOS, matchodds1DetailsTO, multiMarketsDetailsTOS1, fancy1DetailsTO);
                fancyDetailsTO = new MultiMarketsDetailsTO();
                fancy1DetailsTO = new MultiMarketsDetailsTO();
                multiMarketsDetailsTOS = new ArrayList<>();
                matchoddsOutcomesTOS = new ArrayList<>();
                matchodds1OutcomesTOS = new ArrayList<>();
                fancyOutcomesTOS = new ArrayList<>();
                fancy1OutcomesTOS = new ArrayList<>();
                OddDetailsTO = new OddDetailsTO();
                multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
                matchoddsDetailsTO = new MultiMarketsDetailsTO();
                matchodds1DetailsTO = new MultiMarketsDetailsTO();
                multiMarketsTiedTO = new MultiMarketsTiedTO();
                multiMarketsGoalsTO = new MultiMarketsGoalsTO();
                multiMarketsPopularTO = new MultiMarketsGoalsTO();
                multiMarketsDetailsTOS1 = new ArrayList<>();
                marketList.clear();
                goalList = new ArrayList<>();
                popularList = new ArrayList<>();
                goalOutcomesTOS = new ArrayList<>();
                popularOutcomesTOS = new ArrayList<>();
            }
            if (event != eventId) {
                multiMarketsEventsTO = new MultiMarketsEventsTO();
                this.populateEvents(multiMarketsEventsTO, rs);
            }
            if (groupId == MATCH_ODDS_ID && providerId == BET_FAIR) {
                if (outcomeName == null) {
                    outcomeName = "null";
                }
                if (!marketList.contains(market) || !outcomeName.contains(outcome) || event != eventId) {
                    matchoddsDetailsTO = new MultiMarketsDetailsTO();
                    this.populateMatchOdds(matchoddsDetailsTO, rs);
                    matchoddsDetailsTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
                    marketList.add(market);
                }
                if (outcomeName == "null") {
                    OddDetailsTO = populateNullMatchOdds(rs, matchoddsDetailsTO, OddDetailsTO, matchoddsOutcomesTOS);
                    matchoddsOutcomesTOS.get(0).setName(rs.getString(ColumnLabelConstants.HOME_TEAM));
                    matchoddsOutcomesTOS.get(0).setOutcomeName(rs.getString(ColumnLabelConstants.HOME_TEAM));
                    OddDetailsTO = populateNullMatchOdds(rs, matchoddsDetailsTO, OddDetailsTO, matchoddsOutcomesTOS);
                    matchoddsOutcomesTOS.get(1).setName(ColumnLabelConstants.DRAW);
                    matchoddsOutcomesTOS.get(1).setOutcomeName(ColumnLabelConstants.DRAW);
                    OddDetailsTO = populateNullMatchOdds(rs, matchoddsDetailsTO, OddDetailsTO, matchoddsOutcomesTOS);
                    matchoddsOutcomesTOS.get(2).setName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
                    matchoddsOutcomesTOS.get(2).setOutcomeName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
                    eventId = event;
                    continue;
                }
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    OddDetailsTO = populateMatchOdds(rs, matchoddsDetailsTO, OddDetailsTO, matchoddsOutcomesTOS);
                }
                outcome = outcomeName;
            }
            if (groupId == MATCH_ODDS_ID && providerId == ADMIN) {
                if (outcomeName == null) {
                    outcomeName = "null";
                }
                if (!marketList.contains(market) || outcomeName.contains(outcome)) {
                    matchodds1DetailsTO = new MultiMarketsDetailsTO();
                    this.populateMatchOdds(matchodds1DetailsTO, rs);
                    matchodds1DetailsTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
                    marketList.add(market);
                }
                if (outcomeName == "null") {
                    OddDetailsTO = populateNullMatchOdds1(rs, matchodds1DetailsTO, OddDetailsTO, matchodds1OutcomesTOS);
                    matchodds1OutcomesTOS.get(0).setName(rs.getString(ColumnLabelConstants.HOME_TEAM));
                    matchodds1OutcomesTOS.get(0).setOutcomeName(rs.getString(ColumnLabelConstants.HOME_TEAM));
                    OddDetailsTO = populateNullMatchOdds1(rs, matchodds1DetailsTO, OddDetailsTO, matchodds1OutcomesTOS);
                    matchodds1OutcomesTOS.get(1).setName(ColumnLabelConstants.DRAW);
                    matchodds1OutcomesTOS.get(1).setOutcomeName(ColumnLabelConstants.DRAW);
                    OddDetailsTO = populateNullMatchOdds1(rs, matchodds1DetailsTO, OddDetailsTO, matchodds1OutcomesTOS);
                    matchodds1OutcomesTOS.get(2).setName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
                    matchodds1OutcomesTOS.get(2).setOutcomeName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
                    eventId = event;
                    continue;
                }
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    OddDetailsTO = populateMatchOdds1(rs, matchodds1DetailsTO, OddDetailsTO, matchodds1OutcomesTOS);
                }
                outcome = outcomeName;
            }


            if (groupId == GOALS) {
                if (!marketList.contains(market) || !outcomeName.contains(outcome)) {
                    if (multiMarketsGoalsTO.getMarketGroupId() != null) {
                        if (!marketList.contains(market) && !outcomeName.equalsIgnoreCase(outcome) && !outcome.isEmpty() && event == eventId) {
                            Collections.swap(goalOutcomesTOS, 0, 1);
                            multiMarketsGoalsTO.setOutcomesList(goalOutcomesTOS);
                            goalList.add(multiMarketsGoalsTO);
                            multiMarketsGoalsTO = new MultiMarketsGoalsTO();
                            goalOutcomesTOS = new ArrayList<>();
                        }
                    }
                    this.filterGoals(multiMarketsGoalsTO, rs);
                    marketList.add(market);
                }
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
                    this.populateOutcomes(multiMarketsOutcomesTO, rs);
                    OddDetailsTO oddDetailsTO = new OddDetailsTO();
                    List<OddDetailsTO> OddDetailsTOS = new ArrayList<>();
                    this.populatebackOdds(oddDetailsTO, rs);
                    OddDetailsTOS.add(oddDetailsTO);
                    oddDetailsTO = new OddDetailsTO();
                    multiMarketsOutcomesTO.setBackOdds(OddDetailsTOS);
                    OddDetailsTOS = new ArrayList<>();
                    this.populatelayOdds(oddDetailsTO, rs);
                    OddDetailsTOS.add(oddDetailsTO);
                    multiMarketsOutcomesTO.setLayOdds(OddDetailsTOS);
                    goalOutcomesTOS.add(multiMarketsOutcomesTO);
                }
                outcome = outcomeName;
            }

            if (groupId == BOTH_TEAMS_TO_SCORE) {
                if (!marketList.contains(market) || !outcomeName.contains(outcome)) {
                    if (multiMarketsPopularTO.getMarketGroupId() != null) {
                        if (!marketList.contains(market) && !outcomeName.equalsIgnoreCase(outcome) && !outcome.isEmpty() && event == eventId) {
                            multiMarketsPopularTO.setOutcomesList(popularOutcomesTOS);
                            popularList.add(multiMarketsPopularTO);
                            multiMarketsPopularTO = new MultiMarketsGoalsTO();
                            popularOutcomesTOS = new ArrayList<>();
                        }
                    }
                    this.filterGoals(multiMarketsPopularTO, rs);
                    marketList.add(market);
                }
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
                    this.populateOutcomes(multiMarketsOutcomesTO, rs);
                    OddDetailsTO oddDetailsTO = new OddDetailsTO();
                    List<OddDetailsTO> OddDetailsTOS = new ArrayList<>();
                    this.populatebackOdds(oddDetailsTO, rs);
                    OddDetailsTOS.add(oddDetailsTO);
                    oddDetailsTO = new OddDetailsTO();
                    multiMarketsOutcomesTO.setBackOdds(OddDetailsTOS);
                    OddDetailsTOS = new ArrayList<>();
                    this.populatelayOdds(oddDetailsTO, rs);
                    OddDetailsTOS.add(oddDetailsTO);
                    multiMarketsOutcomesTO.setLayOdds(OddDetailsTOS);
                    popularOutcomesTOS.add(multiMarketsOutcomesTO);
                }
                outcome = outcomeName;
            }


            if (groupId == TIED_MATCH_ID) {
                if (event == eventId) {
                    matchoddsOutcomesTOS = new ArrayList<>();
                }
                marketsTiedDetailsTOS = populateTiedMatch(rs, OddDetailsTO);
                multiMarketsTiedTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
                multiMarketsTiedTO.setMarketName(TIED_MATCH);
                multiMarketsTiedTO.setMarketGroupId(groupId);
                multiMarketsTiedTO.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
                multiMarketsTiedTO.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
                multiMarketsTiedTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
                multiMarketsTiedTO.setSubMarketValue(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));
                multiMarketsTiedTO.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
                multiMarketsTiedTO.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
                multiMarketsTiedTO.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
                multiMarketsTiedTO.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
                multiMarketsTiedTO.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));

                multiMarketsTiedTO.setTiedMatchDetailsList(marketsTiedDetailsTOS);
            }
            if ((groupId == FANCY_ID || groupId == 3) && (providerId == FANCY_YES || providerId == 2)) {
                if (event == eventId) {
                    matchoddsOutcomesTOS = new ArrayList<>();
                }
                if (!marketList.contains(market)) {
                    if (fancyDetailsTO.getMarketName() != null) {
                        multiMarketsDetailsTOS.add(fancyDetailsTO);
                    }
                    multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
                    fancyDetailsTO = new MultiMarketsDetailsTO();
                    this.populateTiedMatchOdds(fancyDetailsTO, rs);
                    fancyOutcomesTOS = new ArrayList<>();
                }
                marketList.add(market);
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    if (fancyDetailsTO.getMarketName() != null) {
                        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
                        this.populateFancyOutcomes(multiMarketsOutcomesTO, rs);
                        fancyOutcomesTOS.add(multiMarketsOutcomesTO);
                        fancyDetailsTO.setOutcomesList(fancyOutcomesTOS);
                    }
                }
                outcome = outcomeName;
            }
            if (groupId == FANCY_ID && providerId == FANCY_BACK) {
                if (event == eventId) {
                    matchoddsOutcomesTOS = new ArrayList<>();
                }
                if (!marketList.contains(market)) {
                    if (fancy1DetailsTO.getMarketName() != null) {
                        multiMarketsDetailsTOS1.add(fancy1DetailsTO);
                    }
                    multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
                    fancy1DetailsTO = new MultiMarketsDetailsTO();
                    this.populateTiedMatchOdds(fancy1DetailsTO, rs);
                    fancy1OutcomesTOS = new ArrayList<>();
                }
                marketList.add(market);
                if (!outcomeName.contains(outcome) || outcome.contains("")) {
                    if (fancy1DetailsTO.getMarketName() != null) {
                        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
                        this.populateFancyOutcomes(multiMarketsOutcomesTO, rs);
                        fancy1OutcomesTOS.add(multiMarketsOutcomesTO);
                        fancy1DetailsTO.setOutcomesList(fancy1OutcomesTOS);
                    }
                }
                outcome = outcomeName;
            }


            eventId = event;
        }
        filterMultiMarkets(isPin, marketGroupId, multiMarketsEventsTO, matchoddsDetailsTO, fancyDetailsTO, multiMarketsGoalsTO,multiMarketsPopularTO, multiMarketsEventsTOS, multiMarketsDetailsTOS, multiMarketsTiedTO, goalList,popularList, multiMarketsWrapperTO, goalOutcomesTOS,popularOutcomesTOS, matchodds1DetailsTO, multiMarketsDetailsTOS1, fancy1DetailsTO);
        return multiMarketsWrapperTO;
    }

    private OddDetailsTO populateNullMatchOdds(ResultSet rs, MultiMarketsDetailsTO matchoddsDetailsTO, OddDetailsTO oddDetailsTO, List<MultiMarketsOutcomesTO> matchoddsOutcomesTOS) throws SQLException {
        MultiMarketsOutcomesTO multiMarketsOutcomesTO;
        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
        this.populateOutcomes(multiMarketsOutcomesTO, rs);
        List<OddDetailsTO> OddDetailsTOS = new ArrayList<>();
        this.populatebackOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatebackOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatebackOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        multiMarketsOutcomesTO.setBackOdds(OddDetailsTOS);
        OddDetailsTOS = new ArrayList<>();
        this.populatelayOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatelayOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatelayOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        multiMarketsOutcomesTO.setLayOdds(OddDetailsTOS);
        matchoddsOutcomesTOS.add(multiMarketsOutcomesTO);
        matchoddsDetailsTO.setOutcomesList(matchoddsOutcomesTOS);
        return oddDetailsTO;
    }

    private OddDetailsTO populateNullMatchOdds1(ResultSet rs, MultiMarketsDetailsTO matchoddsDetailsTO, OddDetailsTO oddDetailsTO, List<MultiMarketsOutcomesTO> matchoddsOutcomesTOS) throws SQLException {
        MultiMarketsOutcomesTO multiMarketsOutcomesTO;
        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
        this.populateOutcomes(multiMarketsOutcomesTO, rs);
        List<OddDetailsTO> OddDetailsTOS = new ArrayList<>();
        this.populatebackOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        multiMarketsOutcomesTO.setBackOdds(OddDetailsTOS);
        OddDetailsTOS = new ArrayList<>();
        this.populatelayOdds(oddDetailsTO, rs);
        OddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        multiMarketsOutcomesTO.setLayOdds(OddDetailsTOS);
        matchoddsOutcomesTOS.add(multiMarketsOutcomesTO);
        matchoddsDetailsTO.setOutcomesList(matchoddsOutcomesTOS);
        return oddDetailsTO;
    }

    private void addMultiMarkets(ResultSet rs, MultiMarketsEventsTO multiMarketsEventsTO, MultiMarketsDetailsTO matchoddsDetailsTO, MultiMarketsDetailsTO fancyDetailsTO, MultiMarketsGoalsTO multiMarketsGoalsTO, MultiMarketsGoalsTO multiMarketsPopularTO, List<MultiMarketsEventsTO> multiMarketsEventsTOS, List<MultiMarketsDetailsTO> multiMarketsDetailsTOS, MultiMarketsTiedTO multiMarketsTiedTO, List<MultiMarketsGoalsTO> goalList, List<MultiMarketsGoalsTO> popularList, List<MultiMarketsOutcomesTO> goalOutcomesTOS, List<MultiMarketsOutcomesTO> popularOutcomesTOS, MultiMarketsDetailsTO matchodds1DetailsTO, List<MultiMarketsDetailsTO> multiMarketsDetailsTOS1, MultiMarketsDetailsTO fancy1DetailsTO) throws SQLException {
        boolean res = false;
        if (fancyDetailsTO.getMarketName() != null) {
            multiMarketsDetailsTOS.add(fancyDetailsTO);
            MultiMarketsFancyDetailsTO multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
            multiMarketsFancyDetailsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
            multiMarketsFancyDetailsTO.setMarketGroupId(FANCY);
            multiMarketsFancyDetailsTO.setProviderId(fancyDetailsTO.getProviderId());
            multiMarketsFancyDetailsTO.setProviderName(fancyDetailsTO.getProviderName());
            multiMarketsFancyDetailsTO.setFancyMarketList(multiMarketsDetailsTOS);
            if (multiMarketsFancyDetailsTO.getMarketGroupId() != null) {
                multiMarketsEventsTO.setFancyBetDetailsList(multiMarketsFancyDetailsTO);
                res = true;
            }
        }
        if (fancy1DetailsTO.getMarketName() != null) {
            multiMarketsDetailsTOS1.add(fancy1DetailsTO);
            MultiMarketsFancyDetailsTO multiMarketsFancy1DetailsTO = new MultiMarketsFancyDetailsTO();
            multiMarketsFancy1DetailsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
            multiMarketsFancy1DetailsTO.setMarketGroupId(FANCY);
            multiMarketsFancy1DetailsTO.setProviderId(fancy1DetailsTO.getProviderId());
            multiMarketsFancy1DetailsTO.setProviderName(fancy1DetailsTO.getProviderName());
            multiMarketsFancy1DetailsTO.setFancyMarketList(multiMarketsDetailsTOS1);
            if (multiMarketsFancy1DetailsTO.getMarketGroupId() != null) {
                multiMarketsEventsTO.setFancyBet1DetailsList(multiMarketsFancy1DetailsTO);
                res = true;
            }
        }
        if (matchoddsDetailsTO.getMarketGroupId() != null) {
            if (matchoddsDetailsTO.getMarketGroupId() != 0 && matchoddsDetailsTO.getProviderId() == 2) {
                if(multiMarketsEventsTO.getSportId()==1){
                    Collections.swap( matchoddsDetailsTO.getOutcomesList(), 1, 2);
                }
                /*if(multiMarketsEventsTO.getSportId() == 4 && matchoddsDetailsTO.getOutcomesList().size() == 3){
                    Collections.swap( matchoddsDetailsTO.getOutcomesList(), 1, 2);
                }*/
                multiMarketsEventsTO.setMatchOddsDetailsList(matchoddsDetailsTO);
                res = true;
            }
        }
        if (matchodds1DetailsTO.getMarketGroupId() != null) {
            if (matchodds1DetailsTO.getMarketGroupId() != 0) {
                if(multiMarketsEventsTO.getSportId()==1){
                    Collections.swap( matchodds1DetailsTO.getOutcomesList(), 1, 2);
                }
                multiMarketsEventsTO.setMatchOdds1DetailsList(matchodds1DetailsTO);
                res = true;
            }
        }

        if (multiMarketsTiedTO.getMarketGroupId() != null) {
            multiMarketsEventsTO.setTiedMatch(multiMarketsTiedTO);
            res = true;
        }
        if (multiMarketsGoalsTO.getMarketGroupId() != null) {
            Collections.swap(goalOutcomesTOS, 0, 1);
            multiMarketsGoalsTO.setOutcomesList(goalOutcomesTOS);
            goalList.add(multiMarketsGoalsTO);
            //multiMarketsEventsTO.setGoals(goalList);
            res = true;
        }
        if (multiMarketsPopularTO.getMarketGroupId() != null) {
            //Collections.swap(popularOutcomesTOS, 0, 1);
            multiMarketsPopularTO.setOutcomesList(popularOutcomesTOS);
            popularList.add(multiMarketsPopularTO);
            //multiMarketsEventsTO.setPopular(popularList);
            res = true;
        }

        if (res) {
            multiMarketsEventsTO.setGoals(goalList);
            multiMarketsEventsTO.setPopular(popularList);
            multiMarketsEventsTOS.add(multiMarketsEventsTO);
        }
    }

    private void filterGoals(MultiMarketsGoalsTO multiMarketsGoalsTO, ResultSet rs) throws SQLException {
        multiMarketsGoalsTO.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        multiMarketsGoalsTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        multiMarketsGoalsTO.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        multiMarketsGoalsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        multiMarketsGoalsTO.setSubMarket(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));


        multiMarketsGoalsTO.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        multiMarketsGoalsTO.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        multiMarketsGoalsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        multiMarketsGoalsTO.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        multiMarketsGoalsTO.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
        multiMarketsGoalsTO.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
    }

    private void filterMultiMarkets(Integer isPin, Integer marketGroupId, MultiMarketsEventsTO multiMarketsEventsTO, MultiMarketsDetailsTO matchoddsDetailsTO, MultiMarketsDetailsTO fancyDetailsTO, MultiMarketsGoalsTO multiMarketsGoalsTO, MultiMarketsGoalsTO multiMarketsPopularTO, List<MultiMarketsEventsTO> multiMarketsEventsTOS, List<MultiMarketsDetailsTO> multiMarketsDetailsTOS, MultiMarketsTiedTO multiMarketsTiedTO, List<MultiMarketsGoalsTO> goalList, List<MultiMarketsGoalsTO> popularList, MultiMarketsWrapperTO multiMarketsWrapperTO, List<MultiMarketsOutcomesTO> goalOutcomesTOS, List<MultiMarketsOutcomesTO> popularOutcomesTOS, MultiMarketsDetailsTO matchOdds1DetailsTO, List<MultiMarketsDetailsTO> multiMarketsDetailsTOS1, MultiMarketsDetailsTO fancy1DetailsTO) throws SQLException {
        boolean res = false;
        if (fancyDetailsTO.getMarketName() != null) {
            multiMarketsDetailsTOS.add(fancyDetailsTO);
            MultiMarketsFancyDetailsTO multiMarketsFancyDetailsTO = new MultiMarketsFancyDetailsTO();
            multiMarketsFancyDetailsTO.setIsPinned(isPin);
            multiMarketsFancyDetailsTO.setMarketGroupId(marketGroupId);
            multiMarketsFancyDetailsTO.setProviderId(fancyDetailsTO.getProviderId());
            multiMarketsFancyDetailsTO.setProviderName(fancyDetailsTO.getProviderName());
            multiMarketsFancyDetailsTO.setFancyMarketList(multiMarketsDetailsTOS);
            if (multiMarketsFancyDetailsTO.getMarketGroupId() != null) {
                multiMarketsEventsTO.setFancyBetDetailsList(multiMarketsFancyDetailsTO);
                res = true;
            }
        }
        if (fancy1DetailsTO.getMarketName() != null) {
            multiMarketsDetailsTOS1.add(fancy1DetailsTO);
            MultiMarketsFancyDetailsTO multiMarketsFancy1DetailsTO = new MultiMarketsFancyDetailsTO();
            multiMarketsFancy1DetailsTO.setIsPinned(isPin);
            multiMarketsFancy1DetailsTO.setMarketGroupId(marketGroupId);
            multiMarketsFancy1DetailsTO.setProviderId(fancy1DetailsTO.getProviderId());
            multiMarketsFancy1DetailsTO.setProviderName(fancy1DetailsTO.getProviderName());
            multiMarketsFancy1DetailsTO.setFancyMarketList(multiMarketsDetailsTOS1);
            if (multiMarketsFancy1DetailsTO.getMarketGroupId() != null) {
                multiMarketsEventsTO.setFancyBet1DetailsList(multiMarketsFancy1DetailsTO);
                res = true;
            }
        }
        if (matchoddsDetailsTO.getMarketName() != null) {
            if (matchoddsDetailsTO.getMarketGroupId() != 0) {
                if(multiMarketsEventsTO.getSportId()==1){
                    Collections.swap( matchoddsDetailsTO.getOutcomesList(), 1, 2);
                }
                /*if(multiMarketsEventsTO.getSportId() == 4 && matchoddsDetailsTO.getOutcomesList().size() == 3){
                    Collections.swap( matchoddsDetailsTO.getOutcomesList(), 1, 2);
                }*/
                multiMarketsEventsTO.setMatchOddsDetailsList(matchoddsDetailsTO);
                res = true;
            }
        }
        if (matchOdds1DetailsTO.getMarketGroupId() != null) {
            if (matchOdds1DetailsTO.getMarketGroupId() != 0) {
                if(multiMarketsEventsTO.getSportId()==1){
                    Collections.swap( matchOdds1DetailsTO.getOutcomesList(), 1, 2);
                }
                multiMarketsEventsTO.setMatchOdds1DetailsList(matchOdds1DetailsTO);
                res = true;
            }
        }
        if (multiMarketsTiedTO.getMarketGroupId() != null) {
            multiMarketsEventsTO.setTiedMatch(multiMarketsTiedTO);
            res = true;
        }


        if (multiMarketsGoalsTO.getMarketGroupId() != null) {
            Collections.swap(goalOutcomesTOS, 0, 1);
            multiMarketsGoalsTO.setOutcomesList(goalOutcomesTOS);
            goalList.add(multiMarketsGoalsTO);
            //multiMarketsEventsTO.setGoals(goalList);
            res = true;
        }

        if (multiMarketsPopularTO.getMarketGroupId() != null) {
            //Collections.swap(popularOutcomesTOS, 0, 1);
            multiMarketsPopularTO.setOutcomesList(popularOutcomesTOS);
            popularList.add(multiMarketsPopularTO);
            //multiMarketsEventsTO.setPopular(popularList);
            res = true;
        }

        if (res) {
            multiMarketsEventsTO.setGoals(goalList);
            multiMarketsEventsTO.setPopular(popularList);
            multiMarketsEventsTOS.add(multiMarketsEventsTO);
        }
        multiMarketsWrapperTO.setMultiMarketsList(multiMarketsEventsTOS);
    }

    private OddDetailsTO populateMatchOdds(ResultSet rs, MultiMarketsDetailsTO matchoddsDetailsTO, OddDetailsTO oddDetailsTO, List<MultiMarketsOutcomesTO> matchoddsOutcomesTOS) throws SQLException {
        MultiMarketsOutcomesTO multiMarketsOutcomesTO;
        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
        this.populateOutcomes(multiMarketsOutcomesTO, rs);
        List<OddDetailsTO> backOddDetailsTOS = new ArrayList<>();
        List<OddDetailsTO> layOddDetailsTOS = new ArrayList<>();
        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();

        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        rs.next();

        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();

        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();

        rs.next();
        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        multiMarketsOutcomesTO.setBackOdds(backOddDetailsTOS);
        multiMarketsOutcomesTO.setLayOdds(layOddDetailsTOS);
        matchoddsOutcomesTOS.add(multiMarketsOutcomesTO);
        matchoddsDetailsTO.setOutcomesList(matchoddsOutcomesTOS);
        return oddDetailsTO;
    }


    private OddDetailsTO populateMatchOdds1(ResultSet rs, MultiMarketsDetailsTO matchoddsDetailsTO, OddDetailsTO oddDetailsTO, List<MultiMarketsOutcomesTO> matchoddsOutcomesTOS) throws SQLException {
        MultiMarketsOutcomesTO multiMarketsOutcomesTO;
        multiMarketsOutcomesTO = new MultiMarketsOutcomesTO();
        this.populateMatchOdds1Outcomes(multiMarketsOutcomesTO, rs);
        List<OddDetailsTO> backOddDetailsTOS = new ArrayList<>();
        List<OddDetailsTO> layOddDetailsTOS = new ArrayList<>();
        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();

        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        multiMarketsOutcomesTO.setBackOdds(backOddDetailsTOS);
        multiMarketsOutcomesTO.setLayOdds(layOddDetailsTOS);
        matchoddsOutcomesTOS.add(multiMarketsOutcomesTO);
        matchoddsDetailsTO.setOutcomesList(matchoddsOutcomesTOS);
        return oddDetailsTO;
    }


    private List<MultiMarketsTiedDetailsTO> populateTiedMatch(ResultSet rs, OddDetailsTO oddDetailsTO) throws SQLException {
        List<MultiMarketsTiedDetailsTO> marketsTiedDetailsTOS;
        MultiMarketsTiedDetailsTO multiMarketsTiedTO;
        marketsTiedDetailsTOS = new ArrayList<>();
        multiMarketsTiedTO = new MultiMarketsTiedDetailsTO();
        oddDetailsTO = new OddDetailsTO();
        multiMarketsTiedTO.setClOutcomeId(rs.getString(ColumnLabelConstants.OUTCOME_ID));
        multiMarketsTiedTO.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        multiMarketsTiedTO.setOddDictionaryId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY));

        List<OddDetailsTO> backOddDetailsTOS = new ArrayList<>();
        List<OddDetailsTO> layOddDetailsTOS = new ArrayList<>();
        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();

        if (rs.next()) {
            int marketGroup = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            int oddDicId = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY);
            if (marketGroup != 24 || oddDicId != 25) {
                this.populateNullBackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populateNullLayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                rs.previous();
            } else {
                this.populatebackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populatelayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
            }
        }

        if (rs.next()) {
            int marketGroup = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            int oddDicId = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY);
            if (marketGroup != 24 || oddDicId != 25) {
                this.populateNullBackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populateNullLayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                rs.previous();
            } else {
                this.populatebackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populatelayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
            }
        }

        multiMarketsTiedTO.setBackOdds(backOddDetailsTOS);
        multiMarketsTiedTO.setLayOdds(layOddDetailsTOS);
        marketsTiedDetailsTOS.add(multiMarketsTiedTO);
        multiMarketsTiedTO = new MultiMarketsTiedDetailsTO();
        backOddDetailsTOS = new ArrayList<>();
        layOddDetailsTOS = new ArrayList<>();

        rs.next();
        multiMarketsTiedTO.setClOutcomeId(rs.getString(ColumnLabelConstants.OUTCOME_ID));
        multiMarketsTiedTO.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        multiMarketsTiedTO.setOddDictionaryId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY));
        this.populatebackOdds(oddDetailsTO, rs);
        backOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();
        this.populatelayOdds(oddDetailsTO, rs);
        layOddDetailsTOS.add(oddDetailsTO);
        oddDetailsTO = new OddDetailsTO();


        if (rs.next()) {
            int marketGroup = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            int oddDicId = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY);
            if (marketGroup != 24 || oddDicId != 26) {
                this.populateNullBackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populateNullLayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                rs.previous();
            } else {
                this.populatebackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populatelayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
            }
        } else {
            this.populateNullBackOdds(oddDetailsTO, rs);
            backOddDetailsTOS.add(oddDetailsTO);
            oddDetailsTO = new OddDetailsTO();
            this.populateNullLayOdds(oddDetailsTO, rs);
            layOddDetailsTOS.add(oddDetailsTO);
            oddDetailsTO = new OddDetailsTO();
        }

        if (rs.next()) {
            int marketGroup = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
            int oddDicId = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY);
            if (marketGroup != 24 || oddDicId != 26) {
                this.populateNullBackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populateNullLayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                rs.previous();
            } else {
                this.populatebackOdds(oddDetailsTO, rs);
                backOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
                this.populatelayOdds(oddDetailsTO, rs);
                layOddDetailsTOS.add(oddDetailsTO);
                oddDetailsTO = new OddDetailsTO();
            }
        } else {
            this.populateNullBackOdds(oddDetailsTO, rs);
            backOddDetailsTOS.add(oddDetailsTO);
            oddDetailsTO = new OddDetailsTO();
            this.populateNullLayOdds(oddDetailsTO, rs);
            layOddDetailsTOS.add(oddDetailsTO);
            oddDetailsTO = new OddDetailsTO();
            rs.previous();
        }

        multiMarketsTiedTO.setBackOdds(backOddDetailsTOS);
        multiMarketsTiedTO.setLayOdds(layOddDetailsTOS);
        marketsTiedDetailsTOS.add(multiMarketsTiedTO);
        return marketsTiedDetailsTOS;
    }

    private void populatebackOdds(OddDetailsTO oddDetailsTO, ResultSet rs) throws SQLException {
        oddDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.BACK_ODDS_ID));
        oddDetailsTO.setOdds(rs.getDouble(ColumnLabelConstants.BACK_ODDS));
        oddDetailsTO.setSize(rs.getDouble(ColumnLabelConstants.BACK_ODDS_SIZE));
    }

    private void populatelayOdds(OddDetailsTO oddDetailsTO, ResultSet rs) throws SQLException {
        oddDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.LAY_ODDS_ID));
        oddDetailsTO.setOdds(rs.getDouble(ColumnLabelConstants.LAY_ODDS));
        oddDetailsTO.setSize(rs.getDouble(ColumnLabelConstants.LAY_ODDS_SIZE));
    }

    private void populateNullBackOdds(OddDetailsTO oddDetailsTO, ResultSet rs) throws SQLException {
        oddDetailsTO.setOutcomeId(0);
        oddDetailsTO.setOdds(0.0);
        oddDetailsTO.setSize(0.0);
    }

    private void populateNullLayOdds(OddDetailsTO oddDetailsTO, ResultSet rs) throws SQLException {
        oddDetailsTO.setOutcomeId(0);
        oddDetailsTO.setOdds(0.0);
        oddDetailsTO.setSize(0.0);
    }

    private void populateOutcomes(MultiMarketsOutcomesTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setClOutcomeId(String.valueOf(rs.getInt(ColumnLabelConstants.OUTCOME_ID)));
    }

    private void populateMatchOdds1Outcomes(MultiMarketsOutcomesTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setClOutcomeId(String.valueOf(rs.getInt(ColumnLabelConstants.OUTCOME_ID)));
    }

    private void populateFancyOutcomes(MultiMarketsOutcomesTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        outcomeDetails.setName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setOutcome(rs.getString(ColumnLabelConstants.CLOUTCOMEID));
        outcomeDetails.setOdds(rs.getInt(ColumnLabelConstants.BACK_ODDS));
        outcomeDetails.setSize(rs.getInt(ColumnLabelConstants.BACK_ODDS_SIZE));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
    }

    private void populateMatchOdds(MultiMarketsDetailsTO matchOddDetailsTO, ResultSet rs) throws SQLException {
        matchOddDetailsTO.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        matchOddDetailsTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        matchOddDetailsTO.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        matchOddDetailsTO.setSubMarketValue(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));
        matchOddDetailsTO.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        matchOddDetailsTO.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        matchOddDetailsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        matchOddDetailsTO.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        matchOddDetailsTO.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
        matchOddDetailsTO.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
    }

    private void populateTiedMatchOdds(MultiMarketsDetailsTO matchOddDetailsTO, ResultSet rs) throws SQLException {
        matchOddDetailsTO.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        matchOddDetailsTO.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        matchOddDetailsTO.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        matchOddDetailsTO.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        matchOddDetailsTO.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        matchOddDetailsTO.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        matchOddDetailsTO.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        matchOddDetailsTO.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
        matchOddDetailsTO.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
    }

    private void populateEvents(MultiMarketsEventsTO multiMarketsEventsTO, ResultSet rs) throws SQLException {
        multiMarketsEventsTO.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        multiMarketsEventsTO.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        multiMarketsEventsTO.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
        multiMarketsEventsTO.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        multiMarketsEventsTO.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        multiMarketsEventsTO.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        multiMarketsEventsTO.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        multiMarketsEventsTO.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
        multiMarketsEventsTO.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
        multiMarketsEventsTO.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
    }

}
