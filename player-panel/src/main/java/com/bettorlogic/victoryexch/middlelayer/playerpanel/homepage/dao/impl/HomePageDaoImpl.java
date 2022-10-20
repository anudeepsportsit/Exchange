package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.EventViewOutcomeDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.LiveScoreDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dao.HomePageDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto.*;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils.HomePageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class HomePageDaoImpl implements HomePageDao {

    private static final String PROC_PROCESS_BANNER = "select * from homepage_banners_v1(?,?)";
    private static final String PROC_PROCESS_HEADERS = "select * from top_header()";
    private static final String PROC_PROCESS_LEFTMENU = "select * from get_sports()";
    private static final String PROC_GET_UPCOMING_EVENTS = "select * from get_upcoming_events(?,?,?,?)";
    private static final String PROC_GET_UPCOMING_OPTIONS = "select * from get_upcoming_options()";
    private static final String PROC_GET_UPCOMING_SPORT_BUTTONS = "select * from get_upcoming_sports(?)";
    private static final String PROC_PROCESS_HIGHLIGHTS = "select * from get_homepage_highlights(?,?) order by is_live desc,event_kickoff,event_id,odd_dictionary_id,market_group_id";
    private static final String PROC_PROCESS_INPLAY = "select * from get_inplay(?,?) order by event_kickoff,event_id,odd_dictionary_id";
    private static final String PROC_GET_USERMENU_DETAILS = "select * from user_menu_details()";
    private static final String PROC_GET_HIGHLIGHTS_SPORTS = "select * from get_highlights_sports()";
    private static final String PROC_GET_SEARCH_RESULTS = "select * from search_events(?)";
    private static final String PROC_GET_NAVIGATION_BAR = "select * from get_navigationbar(?)";
    private static final String PROC_PROFIT_LOSS_DETAILS = "select * from get_profit_loss_view(?,?,?) order by sport_name,event_name,selection_market,bet_id";
    private static final String PROC_GET_INPLAY_SPORTS = "select * from get_inplay_sports()";

    private static final String PROC_GET_TIMEZONES = "select * from get_timezones()";
    private static final String ALL_SPORTS = "Sports";
    private static final Integer CATEGERY_ID = 1001;
    private static final Double DEFAULT_VALUE = 1.000;
    private static final String PROC_PROCESS_ADMIN_INPLAY = "select * from get_admin_inplay(?,?) order by event_kickoff,event_id,odd_dictionary_id";

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public HomePageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BannersTO getBannersDao(Integer pageId, String userToken) {

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_BANNER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, pageId);
            cs.setString(2, userToken == null ? null : (userToken.equals("") ? null : userToken));
            return cs;
        }, (CallableStatement cs) -> getBanners(cs.executeQuery()));
    }

    @Override
    public HighlightsSportTO retriveInplay(int sportId, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_INPLAY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.getHighlights(cs.executeQuery()));
    }

    @Override
    public SportWrapperTO getUpcomingEvents(Integer sportId, Integer optionId, Integer timeInterval, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_UPCOMING_EVENTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setInt(2, optionId);
            cs.setInt(3, timeInterval);
            cs.setString(4, userToken);
            return cs;
        }, (CallableStatement cs) -> this.extractUpcomingEventDetailsFromRs(cs.executeQuery()));
    }

    @Override
    public HighlightsSportTO retriveHighlights(int sportId, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_HIGHLIGHTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.getHighlights(cs.executeQuery()));
    }

    @Override
    public UserMenuDetailsWrapperTO getMenuDetails() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_USERMENU_DETAILS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getUserMenuDetails(cs.executeQuery()));
    }

    @Override
    public HighlightsSportsDetailsTO retriveHighlightsSports() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_HIGHLIGHTS_SPORTS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getHighlightsSports(cs.executeQuery()));
    }

    @Override
    public HighlightsSportsDetailsTO retriveInplaySports() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_INPLAY_SPORTS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getHighlightsSports(cs.executeQuery()));
    }

    @Override
    public SearchDetailsTO getResults(String team) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_SEARCH_RESULTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, team);
            return cs;
        }, (CallableStatement cs) -> this.getSearchDetails(cs.executeQuery()));
    }

    @Override
    public SubMenuDetailsWrapperTO retrieveNavigationBar(Integer sportId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_NAVIGATION_BAR, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            return cs;
        }, (CallableStatement cs) -> this.getNavigationBarDetails(cs.executeQuery()));


    }

    private SubMenuDetailsWrapperTO getNavigationBarDetails(ResultSet rs) throws SQLException {
        SubMenuDetailsWrapperTO subMenuTO = new SubMenuDetailsWrapperTO();
        List<HeaderDetailsTO> subMenuDetailsList = new ArrayList<>();
        while (rs.next()) {
            HeaderDetailsTO subMenuDetailsTo = new HeaderDetailsTO();
            subMenuDetailsTo.setId(rs.getInt
                    (HomePageConstants.SUB_MENU_ID));
            subMenuDetailsTo.setName(rs.getString(ColumnLabelConstants.SUB_MENU_NAME));
            subMenuDetailsTo.setUrl(rs.getString(ColumnLabelConstants.LINK));
            subMenuDetailsList.add(subMenuDetailsTo);
        }
        subMenuTO.setSubMenuDetailsTO(subMenuDetailsList);
        return subMenuTO;
    }


    private SearchDetailsTO getSearchDetails(ResultSet executeQuery) throws SQLException {
        SearchDetailsTO searchDetailsTO = new SearchDetailsTO();
        List<SearchEventDeatilsTO> searchEventDeatilsTOS = new ArrayList<>();
        while (executeQuery.next()) {
            SearchEventDeatilsTO searchEventDeatilsTO = new SearchEventDeatilsTO();
            searchEventDeatilsTO.setId(executeQuery.getInt(ColumnLabelConstants.ID));
            searchEventDeatilsTO.setName(executeQuery.getString(ColumnLabelConstants.NAME));
            searchEventDeatilsTO.setKickofftime(executeQuery.getString(SportsBookConstants.KICK_OFF_TIME));
            searchEventDeatilsTO.setSportId(executeQuery.getInt(HomePageConstants.SPORT_ID));
            searchEventDeatilsTO.setSportName(executeQuery.getString(HomePageConstants.SPORT_NAME));
            searchEventDeatilsTOS.add(searchEventDeatilsTO);
        }
        searchDetailsTO.setEventList(searchEventDeatilsTOS);
        return searchDetailsTO;
    }

    private HighlightsSportTO getHighlights(ResultSet rs) throws SQLException {
        HighlightsSportTO highlightsSportTO = new HighlightsSportTO();
        SportDetailsTO sportDetailsTO = new SportDetailsTO();
        EventDetailsTO eventDetailsTO = null;
        MarketDetailsTO marketDetailsTO;
        OutcomeDetailsTO outcomeDetailsTO;
        LiveScoreDetailsTO scoresDetailsTO;
        List<EventDetailsTO> highlightsEventTOS = new ArrayList<>();
        List<MarketDetailsTO> highlightsMarketTOS = null;
        List<OutcomeDetailsTO> highlightsOddsTOS = null;
        List<LiveScoreDetailsTO> scoresDetailsTOS = null;
        List<String> scoresList = new ArrayList<>();
        int sportId = 0;
        int eventId = 0;
        String marketName = "";
        int oddsId = 0;
        String title = "";
        while (rs.next()) {
            int sport = rs.getInt(ColumnLabelConstants.SPORT_ID);
            if (sport != sportId) {
                sportDetailsTO.setId(rs.getInt(ColumnLabelConstants.SPORT_ID));
                sportDetailsTO.setName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            }
            sportId = sport;
            int event = rs.getInt(ColumnLabelConstants.EVENT_ID);
            if (event != eventId && eventId != 0) {
                populateDrawOutcome(highlightsOddsTOS, sport);
            }

            eventDetailsTO = new EventDetailsTO();
            this.populateEvents(eventDetailsTO, rs);
            if (event != eventId) {
                if(sport == 4 && eventId != 0){Collections.swap(highlightsOddsTOS, 1, 2);}
                scoresList.clear();
                scoresDetailsTOS = new ArrayList<>();
                highlightsEventTOS.add(eventDetailsTO);
                highlightsMarketTOS = new ArrayList<>();
                marketName = "";
            }
            eventId = event;
            marketDetailsTO = new MarketDetailsTO();
            String market = rs.getString(ColumnLabelConstants.MARKET_NAME);
            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && (sport == 1 || sport == 4) && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    highlightsMarketTOS.add(marketDetailsTO);
                    highlightsOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(highlightsMarketTOS);
                    marketName = market;
                    getOutcomesFootball(rs, marketDetailsTO, highlightsOddsTOS);
                    continue;
                }
            }

            if (!market.contains(ColumnLabelConstants.MATCH_ODDS) && (sport == 2) && (!market.contains(marketName) || marketName == "")) {
                this.populateMarkets(marketDetailsTO, rs);
                String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
                scoresDetailsTO = new LiveScoreDetailsTO();
                if (!(scorettile == null)) {
                    if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                        this.populateScores(scoresDetailsTO, rs);
                        scoresDetailsTOS.add(scoresDetailsTO);
                        eventDetailsTO.setScores(scoresDetailsTOS);
                    }
                }
                scoresList.add(scorettile);
                title = scorettile;
                if (event == eventId) {
                    highlightsMarketTOS.add(marketDetailsTO);
                    highlightsOddsTOS = new ArrayList<>();
                    eventDetailsTO.setMarketsList(highlightsMarketTOS);
                    getOutcomesOther(rs, marketDetailsTO, highlightsOddsTOS, market);
                    continue;
                }
                marketName = market;
            }

            this.populateMarkets(marketDetailsTO, rs);
            String scorettile = rs.getString(ColumnLabelConstants.SCORE_TITLE);
            scoresDetailsTO = new LiveScoreDetailsTO();
            if (!(scorettile == null)) {
                if (!scorettile.equalsIgnoreCase(title) && !scoresList.contains(scorettile)) {
                    this.populateScores(scoresDetailsTO, rs);
                    scoresDetailsTOS.add(scoresDetailsTO);
                    eventDetailsTO.setScores(scoresDetailsTOS);
                }
            }
            scoresList.add(scorettile);
            title = scorettile;
            if (market != null) {
                if (!market.equalsIgnoreCase(marketName)) {
                    if (event == eventId) {
                        highlightsMarketTOS.add(marketDetailsTO);
                        highlightsOddsTOS = new ArrayList<>();
                        eventDetailsTO.setMarketsList(highlightsMarketTOS);
                        oddsId = 0;
                    }
                }
            }
            marketName = market;
            outcomeDetailsTO = new OutcomeDetailsTO();
            int oddDictionary = rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID);
            if (!rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.OVER) && !rs.getString(ColumnLabelConstants.OUTCOME_NAME).contains(ColumnLabelConstants.UNDER)) {
                this.populateOutcomes(outcomeDetailsTO, rs);
                if (oddsId != oddDictionary) {
                    highlightsOddsTOS.add(outcomeDetailsTO);
                    marketDetailsTO.setOutcomesList(highlightsOddsTOS);
                }
                oddsId = oddDictionary;
            }
        }
        populateDrawOutcome(highlightsOddsTOS, sportId);
        if(sportId == 4){ Collections.swap(highlightsOddsTOS, 1, 2);}
        highlightsSportTO.setSportId(sportDetailsTO.getId());
        highlightsSportTO.setSportName(sportDetailsTO.getName());
        highlightsSportTO.setEventDetailsList(highlightsEventTOS);
        return highlightsSportTO;
    }

    private void populateDrawOutcome(List<OutcomeDetailsTO> highlightsOddsTOS, int sport) {
        OutcomeDetailsTO outcomeDetailsTO;
        if (sport == 4 && highlightsOddsTOS.size() != 3) {
            outcomeDetailsTO = new OutcomeDetailsTO();
            outcomeDetailsTO.setOutcomeId(0);
            outcomeDetailsTO.setOutcomeName("X");
            outcomeDetailsTO.setBackOdds(0.0);
            outcomeDetailsTO.setLayOdds(0.0);
            highlightsOddsTOS.add(outcomeDetailsTO);
        }

    }

    private String getOutcomesOther(ResultSet rs, MarketDetailsTO marketDetailsTO, List<OutcomeDetailsTO> highlightsOddsTOS, String market) throws SQLException {
        String marketName;
        OutcomeDetailsTO outcomeDetailsTO;
        marketName = market;
        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.ONE);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.TWO);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);
        marketDetailsTO.setOutcomesList(highlightsOddsTOS);
        return marketName;
    }

    private void getOutcomesFootball(ResultSet rs, MarketDetailsTO marketDetailsTO, List<OutcomeDetailsTO> highlightsOddsTOS) throws SQLException {
        OutcomeDetailsTO outcomeDetailsTO;
        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.ONE);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.X);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);

        outcomeDetailsTO = new OutcomeDetailsTO();
        outcomeDetailsTO.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetailsTO.setOutcomeName(SportsBookConstants.OutcomeNames.TWO);
        outcomeDetailsTO.setLayOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setBackOdds(SportsBookConstants.DOUBLE);
        outcomeDetailsTO.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        highlightsOddsTOS.add(outcomeDetailsTO);
        marketDetailsTO.setOutcomesList(highlightsOddsTOS);
    }

    private void populateOutcomes(OutcomeDetailsTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.ODDS_DICTIONARY_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setBackOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.BACK_ODDS))));
        outcomeDetails.setLayOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.LAY_ODDS))));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
    }

    private void populateMarkets(MarketDetailsTO marketDetails, ResultSet rs) throws SQLException {
        marketDetails.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        marketDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        marketDetails.setMarketSuspendedFlag(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
        marketDetails.setSubMarketValue(rs.getString(ColumnLabelConstants.SUBMARKET_VALUE));
//        marketDetails.setMarketBlockedFlag(rs.getInt(ColumnLabelConstants.MARKET_BLOCKED));
        marketDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        marketDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        marketDetails.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
    }

    private void populateEvents(EventDetailsTO eventDetails, ResultSet rs) throws SQLException {
        eventDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        eventDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        eventDetails.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
        eventDetails.setLeagueId(rs.getInt(ColumnLabelConstants.LEAGUE_ID));
        eventDetails.setLeagueName(rs.getString(ColumnLabelConstants.LEAGUE_NAME));
        eventDetails.setIsFancy(rs.getInt(ColumnLabelConstants.EVENT_ISFANCY));
//        eventDetails.setEventBlockedFlag(rs.getInt(ColumnLabelConstants.EVENT_BLOCKED));
//        eventDetails.setEventSuspendedFlag(rs.getInt(ColumnLabelConstants.EVENT_SUSPENDED));
        eventDetails.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        eventDetails.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        eventDetails.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        eventDetails.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        eventDetails.setClEventId(rs.getString(ColumnLabelConstants.CL_EVENT_ID));
        eventDetails.setIsLive(rs.getInt(ColumnLabelConstants.IS_LIVE));
        eventDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PIN));
        eventDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        //eventDetails.setMatchMinutes(rs.getString(ColumnLabelConstants.MATCH_MINUTES));
    }

    private void populateScores(LiveScoreDetailsTO scoresDetailsTO, ResultSet rs) throws SQLException {
        scoresDetailsTO.setScoreTitle(rs.getString(ColumnLabelConstants.SCORE_TITLE));
        scoresDetailsTO.setHomeTeamScore(rs.getString(ColumnLabelConstants.HOME_TEAM_SCORE));
        scoresDetailsTO.setAwayTeamScore(rs.getString(ColumnLabelConstants.AWAY_TEAM_SCORE));
        scoresDetailsTO.setHomeTeamRunRate(rs.getString(ColumnLabelConstants.HOME_TEAM_RUNRATE));
        scoresDetailsTO.setAwayTeamRunRate(rs.getString(ColumnLabelConstants.AWAY_TEAM_RUNRATE));
        scoresDetailsTO.setMatchMinutes(rs.getString(ColumnLabelConstants.MATCH_MINUTES));
    }

    @Override
    public ProfitLossWrapperTO retriveResults(ProfitLossInputDetailsTO profitLossInputDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROFIT_LOSS_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, profitLossInputDetails.getUserToken());
            cs.setString(2, profitLossInputDetails.getStartDate());
            cs.setString(3, profitLossInputDetails.getEndDate());
            return cs;
        }, (CallableStatement cs) -> this.getProfitLoss(cs.executeQuery()));
    }

    @Override
    public List<Timezones> getTimezones() {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_GET_TIMEZONES,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            return cs;
        }, (CallableStatement cs) -> this.populateTimezones(cs.executeQuery()));
    }

    @Override
    public HighlightsSportTO retriveAdminInplay(int sportId, String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_ADMIN_INPLAY,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            cs.setString(2, userToken);
            return cs;
        }, (CallableStatement cs) -> this.getHighlights(cs.executeQuery()));
    }

    private List<Timezones> populateTimezones(ResultSet rs) throws SQLException {
        List<Timezones> timezonesList = new ArrayList<>();
        while (rs.next()) {
            Timezones timezones = new Timezones();
            timezones.setTimeZoneId(rs.getInt(ColumnLabelConstants.TIMEZONE_ID));
            timezones.setTimeZone(rs.getString(ColumnLabelConstants.TIMEZONE));
            timezones.setTimeZoneDifference(rs.getString(ColumnLabelConstants.TIMEZONE_DIFFERENCE));
            timezonesList.add(timezones);
        }
        return timezonesList;
    }
    private ProfitLossWrapperTO getProfitLoss(ResultSet rs) throws SQLException {
        ProfitLossWrapperTO profitLossWrapperTO = new ProfitLossWrapperTO();
        SelectionDetailsTO selectionDetailsTO = new SelectionDetailsTO();
        BetDetailsTO betDetailsTO = new BetDetailsTO();
        Integer bet = 0;
        List<BetDetailsTO> betDetailsTOS;
        List<SelectionDetailsTO> selectionDetailsTOS = new ArrayList<>();
        while (rs.next()) {
            Integer betId = rs.getInt(ColumnLabelConstants.BET_ID);
            if (betId != bet || bet != 0) {
                betDetailsTO = new BetDetailsTO();
                betDetailsTOS = new ArrayList<>();
                selectionDetailsTO = new SelectionDetailsTO();
                this.populateSelections(selectionDetailsTO, rs);
                this.populateBets(betDetailsTO, rs);
                if (betDetailsTO.getBetStatus().equalsIgnoreCase(ColumnLabelConstants.WIN)) {
                    betDetailsTO.setAmount((betDetailsTO.getOdds().subtract(BigDecimal.valueOf(DEFAULT_VALUE)).multiply(betDetailsTO.getStake())));
                    betDetailsTO.setSelection(rs.getString(ColumnLabelConstants.OUTCOMENAME));
                    selectionDetailsTO.setAmount((betDetailsTO.getOdds().subtract(BigDecimal.valueOf(DEFAULT_VALUE)).multiply(betDetailsTO.getStake())));
                    selectionDetailsTO.setIsProfit(1);

                } else if (betDetailsTO.getBetStatus().equalsIgnoreCase(ColumnLabelConstants.LOSS)) {
                    betDetailsTO.setAmount(betDetailsTO.getStake());
                    betDetailsTO.setIsProfit(0);
                    betDetailsTO.setSelection(rs.getString(ColumnLabelConstants.OUTCOMENAME));
                    selectionDetailsTO.setAmount(betDetailsTO.getStake());
                    selectionDetailsTO.setIsProfit(0);
                } else {
                    betDetailsTO.setAmount(betDetailsTO.getStake());
                    betDetailsTO.setIsProfit(0);
                    betDetailsTO.setSelection(rs.getString(ColumnLabelConstants.OUTCOMENAME));
                    selectionDetailsTO.setAmount(betDetailsTO.getStake());
                    selectionDetailsTO.setIsProfit(0);
                }
                bet = betId;
                betDetailsTOS.add(betDetailsTO);
                selectionDetailsTO.setBetDetailsList(betDetailsTOS);
                selectionDetailsTOS.add(selectionDetailsTO);
            }
        }
        profitLossWrapperTO.setSelectionList(selectionDetailsTOS);
        return profitLossWrapperTO;
    }

    private void populateBets(BetDetailsTO betDetailsTO, ResultSet rs) throws SQLException {
        betDetailsTO.setBetId(rs.getLong(ColumnLabelConstants.BET_FAIR_ID));
        betDetailsTO.setSelection(rs.getString(ColumnLabelConstants.SELECTION_SUB_MARKET));
        betDetailsTO.setOdds(rs.getBigDecimal(ColumnLabelConstants.ODDS));
        betDetailsTO.setStake(rs.getBigDecimal(ColumnLabelConstants.STAKES));
        betDetailsTO.setType(rs.getString(ColumnLabelConstants.OUTCOME_TYPE));
        betDetailsTO.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED_DATE));
        betDetailsTO.setBetStatus(rs.getString(ColumnLabelConstants.BET_STATUS));
        betDetailsTO.setTotalStake(rs.getBigDecimal(ColumnLabelConstants.TOTAL_STAKES));
    }

    private void populateSelections(SelectionDetailsTO selectionDetailsTO, ResultSet rs) throws SQLException {
        selectionDetailsTO.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
        selectionDetailsTO.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        selectionDetailsTO.setMarketName(rs.getString(ColumnLabelConstants.SELECTION_MARKET));
        selectionDetailsTO.setStartTime(rs.getString(ColumnLabelConstants.BET_START_TIME));
        selectionDetailsTO.setSettledDate(rs.getString(ColumnLabelConstants.BET_SETTLED_DATE));
    }

    @Override
    public SportTO getSportsDao() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_PROCESS_LEFTMENU,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getLeftMenu(cs.executeQuery()));
    }

    @Override
    public HeadersTO getHeadersDao() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_PROCESS_HEADERS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getHeaders(cs.executeQuery()));
    }

    @Override
    public List<UpcomingOptionsTO> getUpcomingOptions() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_UPCOMING_OPTIONS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> extractOptionsFromResultSet(cs.executeQuery()));
    }

    @Override
    public List<SportDetailsTO> getUpcomingSportButtonDetails(Integer timeInterval) {
        return jdbcTemplate.execute(connection -> {
                    CallableStatement cs = connection.prepareCall(PROC_GET_UPCOMING_SPORT_BUTTONS,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    cs.setInt(1, timeInterval);
                    return cs;
                },
                (CallableStatement cs) -> extractsportButtonsFromResultSet(cs.executeQuery()));
    }

    private List<SportDetailsTO> extractsportButtonsFromResultSet(ResultSet rs) throws SQLException {
        List<SportDetailsTO> sportDetailsList = new ArrayList<>();
        while (rs.next()) {
            SportDetailsTO button = new SportDetailsTO();
            button.setId(rs.getInt(ColumnLabelConstants.ID));
            button.setName(rs.getString(ColumnLabelConstants.NAME));
            button.setUrl(rs.getString(ColumnLabelConstants.LINK_URL));
            sportDetailsList.add(button);
        }
        return sportDetailsList;
    }

    private List<UpcomingOptionsTO> extractOptionsFromResultSet(ResultSet rs) throws SQLException {
        List<UpcomingOptionsTO> upcomingOptionsList = new ArrayList<>();
        while (rs.next()) {
            UpcomingOptionsTO option = new UpcomingOptionsTO();
            option.setOptionId(rs.getInt(ColumnLabelConstants.OPTION_ID));
            option.setUpcomingFrequency(rs.getString(ColumnLabelConstants.UPCOMING_FREQUENCY));
            option.setUnitMeasure(rs.getString(HomePageConstants.UNIT_MEASURE));
            upcomingOptionsList.add(option);
        }
        return upcomingOptionsList;
    }

    private SportTO getLeftMenu(ResultSet rs) throws SQLException {
        SportCategoriesTO categories = new SportCategoriesTO();
        SportTO sport = new SportTO();
        List<SportDetailsTO> sportsList = new ArrayList<>();
        List<SportCategoriesTO> categoriesList = new ArrayList<>();
        while (rs.next()) {
            this.populateLeftMenuSportDetails(sportsList, rs);
        }
        categories.setId(CATEGERY_ID);
        categories.setName(ALL_SPORTS);
        categories.setSportsList(sportsList);
        categoriesList.add(categories);
        sport.setCategories(categoriesList);
        return sport;
    }

    private void populateLeftMenuSportDetails(List<SportDetailsTO> sportsList, ResultSet rs) throws SQLException {
        SportDetailsTO sportDetails = new SportDetailsTO();
        sportDetails.setId(rs.getInt(ColumnLabelConstants.ID));
        sportDetails.setName(rs.getString(ColumnLabelConstants.NAME));
        sportDetails.setUrl(rs.getString(ColumnLabelConstants.LINK_URL));
        sportsList.add(sportDetails);
    }

    private void populateOutcomeDetails(
            EventViewOutcomeDetailsTO outcomeDetails, ResultSet rs) throws SQLException {
        outcomeDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        outcomeDetails.setClOutcomeId(rs.getString(ColumnLabelConstants.CL_OUTCOME_ID));
        outcomeDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        outcomeDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED_FLAG));
        outcomeDetails.setOutcomeOrder(rs.getInt(ColumnLabelConstants.OUTCOME_ORDER));
        OddDetailsTO backOddDetails = new OddDetailsTO();
        backOddDetails.setOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.BACK_ODDS))));
        backOddDetails.setSize(rs.getDouble(ColumnLabelConstants.BACK_ODDS_SIZE));
        backOddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED_FLAG));
        outcomeDetails.getBackOddList().add(backOddDetails);
        OddDetailsTO layOddDetails = new OddDetailsTO();
        layOddDetails.setOdds(Double.valueOf(new DecimalFormat(
                HomePageConstants.DECIMAL_FORMAT_PATTERN).format(rs.getDouble(ColumnLabelConstants.LAY_ODDS))));
        layOddDetails.setSize(rs.getDouble(ColumnLabelConstants.LAY_ODDS_SIZE));
        layOddDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED_FLAG));
        outcomeDetails.getLayOddList().add(layOddDetails);
    }

    private void populateMarketDetails(MarketDetailsTO marketDetails, ResultSet rs) throws SQLException {
        marketDetails.setMarketGroupId(rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID));
        marketDetails.setClMarketId(rs.getString(ColumnLabelConstants.CL_MARKET_ID));
        marketDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        marketDetails.setSubMarketValue(rs.getString(ColumnLabelConstants.SUB_MARKET));
        marketDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE_VALUE));
        marketDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE_VALUE));
//        marketDetails.setMarketBlockedFlag(rs.getInt(ColumnLabelConstants.MARKET_BLOCKED_FLAG));
    }


    private void populateEventDetails(EventDetailsTO eventDetails, ResultSet rs) throws SQLException {
        eventDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
        eventDetails.setClEventId(rs.getString(ColumnLabelConstants.CL_EVENT_ID));
        eventDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
        eventDetails.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
        eventDetails.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
        eventDetails.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
        eventDetails.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
        eventDetails.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
        eventDetails.setIsFancy(rs.getInt(ColumnLabelConstants.IS_FANCY_FLAG));
//        eventDetails.setEventSuspendedFlag(rs.getInt(ColumnLabelConstants.EVENT_SUSPENDED_FLAG));
//        eventDetails.setEventBlockedFlag(rs.getInt(ColumnLabelConstants.EVENT_BLOCKED_FLAG));
        eventDetails.setIsPinned(rs.getInt(ColumnLabelConstants.IS_PINNED));
        eventDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
    }

    private void populateSportDetails(SportHierarchyDetailsTO sportDetails, ResultSet rs) throws SQLException {
        sportDetails.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
        sportDetails.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
    }

    private SportWrapperTO extractUpcomingEventDetailsFromRs(ResultSet rs) throws SQLException {
        SportHierarchyDetailsTO sportDetails = null;
        EventDetailsTO eventDetails = null;
        MarketDetailsTO marketDetails = null;
        EventViewOutcomeDetailsTO outcomeDetails = null;
        SportWrapperTO sportWrapper = new SportWrapperTO();
        int lastSportId = 0;
        int lastEventId = 0;
        int lastMarketId = 0;
        int lastOutcomeId = 0;
        while (rs.next()) {
            int sportId = rs.getInt(ColumnLabelConstants.SPORT_ID);
            if (sportId == lastSportId) {
                assert sportDetails != null;
                this.populateSportDetails(sportDetails, rs);
                int eventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                int marketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                if (eventId == lastEventId && marketId == lastMarketId) {
                    assert eventDetails != null;
                    this.populateEventDetails(eventDetails, rs);
                    this.populateMarketDetails(marketDetails, rs);

                    int outcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                    if (outcomeId == lastOutcomeId) {
                        assert outcomeDetails != null;
                        this.populateOutcomeDetails(outcomeDetails, rs);

                    } else {
                        lastOutcomeId = rs.getInt(ColumnLabelConstants.OUTCOME_ID);
                        rs.previous();
                        outcomeDetails = new EventViewOutcomeDetailsTO();
                        marketDetails.getEventViewOutcomeDetailsList().add(outcomeDetails);
                    }
                } else {
                    lastEventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                    lastMarketId = rs.getInt(ColumnLabelConstants.MARKET_GROUP_ID);
                    rs.previous();
                    eventDetails = new EventDetailsTO();
                    marketDetails = new MarketDetailsTO();
                    eventDetails.getMarketsList().add(marketDetails);
                    sportDetails.getEventsList().add(eventDetails);
                }
            } else {
                lastSportId = rs.getInt(ColumnLabelConstants.SPORT_ID);
                rs.previous();
                sportDetails = new SportHierarchyDetailsTO();
                sportWrapper.getSportsList().add(sportDetails);
            }
        }
        return sportWrapper;
    }

    private HighlightsSportsDetailsTO getHighlightsSports(ResultSet executeQuery) throws SQLException {
        HighlightsSportsDetailsTO highlightsSportsDetailsTO = new HighlightsSportsDetailsTO();
        List<SportDetailsTO> sportDetailsTOS = new ArrayList<>();
        while (executeQuery.next()) {
            SportDetailsTO sportDetailsTO = new SportDetailsTO();
            sportDetailsTO.setId(executeQuery.getInt(ColumnLabelConstants.ID));
            sportDetailsTO.setName(executeQuery.getString(ColumnLabelConstants.NAME));
            sportDetailsTO.setUrl(executeQuery.getString(ColumnLabelConstants.LINK));
            sportDetailsTOS.add(sportDetailsTO);
        }
        highlightsSportsDetailsTO.setSportDetailsList(sportDetailsTOS);
        return highlightsSportsDetailsTO;
    }


    private UserMenuDetailsWrapperTO getUserMenuDetails(ResultSet executeQuery) throws SQLException {
        UserMenuDetailsWrapperTO userMenuDetailsWrapperTO = new UserMenuDetailsWrapperTO();
        List<UserMenuDetailsTO> userMenuDetailsTOS = new ArrayList<>();
        while (executeQuery.next()) {
            UserMenuDetailsTO menuDetailsTO = new UserMenuDetailsTO();
            menuDetailsTO.setUserMenuId(executeQuery.getInt(ColumnLabelConstants.ID));
            menuDetailsTO.setUserMenuName(executeQuery.getString(ColumnLabelConstants.NAME));
            menuDetailsTO.setUserMenuLinkUrl(executeQuery.getString(ColumnLabelConstants.LINK_URL));
            userMenuDetailsTOS.add(menuDetailsTO);
        }
        userMenuDetailsWrapperTO.setUserMenu(userMenuDetailsTOS);
        return userMenuDetailsWrapperTO;
    }


    private BannersTO getBanners(ResultSet executeQuery) throws SQLException {
        BannersTO bannersTo = new BannersTO();
        List<BannerDetailsTO> banners = new ArrayList<>();
        while (executeQuery.next()) {
            BannerDetailsTO banner = new BannerDetailsTO();
            banner.setId(executeQuery.getInt(ColumnLabelConstants.ID));
            banner.setName(executeQuery.getString(ColumnLabelConstants.NAME));
            banner.setText(executeQuery.getString(ColumnLabelConstants.TEXT));
            banner.setButtonText(executeQuery.getString(ColumnLabelConstants.BUTTON_TEXT));
            banner.setImageUrl(executeQuery.getString(ColumnLabelConstants.IMAGE_URL));
            banner.setLinkUrl(executeQuery.getString(ColumnLabelConstants.LINK_URL));
            banner.setButton(executeQuery.getBoolean(ColumnLabelConstants.IS_BUTTON));
            banner.setButtonType(executeQuery.getInt(ColumnLabelConstants.BUTTON_TYPE));
            banners.add(banner);
        }
        bannersTo.setBannersList(banners);
        return bannersTo;
    }

    private HeadersTO getHeaders(ResultSet executeQuery) throws SQLException {
        HeadersTO header = new HeadersTO();
        List<HeaderDetailsTO> headersTOS = new ArrayList<>();
        while (executeQuery.next()) {
            HeaderDetailsTO headers = new HeaderDetailsTO();
            headers.setId(executeQuery.getInt(ColumnLabelConstants.ID));
            headers.setName(executeQuery.getString(ColumnLabelConstants.NAME));
            headers.setUrl(executeQuery.getString(ColumnLabelConstants.PAGE_URL));
            headersTOS.add(headers);
        }
        header.setHeadersList(headersTOS);
        return header;
    }

}