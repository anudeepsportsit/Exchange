package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dao.FancyMarketDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FancyMarketDaoImpl implements FancyMarketDao {

    private static final String PROC_GET_FANCY_MARKETS = "select * from get_fancy_markets(?,?,?)";
    private static final String PROC_GET_SPORTS = "select * from get_fancy_market_sports()";
    private static final String PROC_GET_LEAGUES = "select * from  get_fancy_market_leagues(?)";
    private static final String PROC_GET_EVENTS = "select * from  get_fancy_market_sport_events(?)";
    private static final String PROC_INSERT_FANCY_MARKETS = "select * from add_fancy_markets(?,?,?,?,?,?,?,?)";
    private static final String PROC_INSERT_ADMIN_MATCH_ODDS = "select * from add_match_odds1(?,?,?)";
    private static final String PROC_GET_MATCH_ODDS = "select * from get_admin_math_odds(?,?,?,?)";
    private static final String PROC_UPDATE_FANCY_STATUS = "select * from update_fancy_market_status(?,?,?)";
    private static final String PROC_UPDATE_FANCY_SUSPEND_STATUS = "select * from update_fancy_market_suspended_status(?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public FancyMarketListTO getFancyMarket(FancyMarketInputTO input) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_FANCY_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, input.getSportId());
            cs.setInt(2, input.getLeagueId());
            cs.setInt(3, input.getEventId());
            return cs;
        }, (CallableStatement cs) -> this.extractFancyMarkets(cs.executeQuery()));
    }

    @Override
    public String updateFancyStatus(FancyMarketsSuspendDetailsTO suspendDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_FANCY_STATUS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, suspendDetails.getEventId());
            cs.setString(2, suspendDetails.getMarketName());
            cs.setInt(3, suspendDetails.getIsActive());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String status = null;
            while (rs.next()) {
                status = rs.getString(ColumnLabelConstants.UPDATE_FANCY_STATUS);
            }
            return status;
        });
    }

    @Override
    public String updateFancySuspention(FancyMarketsSuspendDetailsTO suspendDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_FANCY_SUSPEND_STATUS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, suspendDetails.getEventId());
            cs.setArray(2, connection.createArrayOf("INTEGER", suspendDetails.getMarketsList()));
            cs.setInt(3, suspendDetails.getIsSuspend());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String status = null;
            while (rs.next()) {
                status = rs.getString(ColumnLabelConstants.UPDATE_FANCY_SUSPEND_STATUS);
            }
            return status;
        });
    }

    @Override
    public FancySportsListTO getSports() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_SPORTS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getFancySports(cs.executeQuery()));
    }

    @Override
    public FancyLeaguesTO getLeagues(Integer sportId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_LEAGUES,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            return cs;
        }, (CallableStatement cs) -> this.getLeaguesHierarchy(cs.executeQuery()));
    }

    @Override
    public FancyEventsTO getEvents(Integer leagueId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_EVENTS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, leagueId);
            return cs;
        }, (CallableStatement cs) -> this.getEventsHierarchy(cs.executeQuery()));
    }

    @Override
    public void addMarkets(FancyMarketsTO markets) {
        jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_INSERT_FANCY_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, markets.getEventId());
            cs.setString(2, markets.getMarketName());
            cs.setString(3, markets.getOutcomeYes());
            cs.setString(4, markets.getOutcomeNo());
            cs.setBigDecimal(5, markets.getOddsYes());
            cs.setBigDecimal(6, markets.getOddsNo());
            cs.setBigDecimal(7, markets.getMinStake());
            cs.setBigDecimal(8, markets.getMaxStake());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String result = null;
            while (rs.next()) {
                result = rs.getString(ColumnLabelConstants.ADD_FANCY_MARKETS);
            }
            return result;
        });
    }

    private FancyEventsTO getEventsHierarchy(ResultSet rs) throws SQLException {
        FancyEventsTO fancyEventsTO = new FancyEventsTO();
        List<FancyEventsDetailsTO> fancyEventsDetailsTOS = new ArrayList<>();
        while (rs.next()) {
            FancyEventsDetailsTO fancyEventsDetailsTO = new FancyEventsDetailsTO();
            fancyEventsDetailsTO.setEventId(rs.getInt(AdminPanelColumnLabelConstants.ID));
            fancyEventsDetailsTO.setEventName(rs.getString(AdminPanelColumnLabelConstants.NAME));
            fancyEventsDetailsTO.setHomeTeamId(rs.getInt(ColumnLabelConstants.HOME_TEAM_ID));
            fancyEventsDetailsTO.setHomeTeamName(rs.getString(ColumnLabelConstants.HOME_TEAM));
            fancyEventsDetailsTO.setAwayTeamId(rs.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
            fancyEventsDetailsTO.setAwayTeamName(rs.getString(ColumnLabelConstants.AWAY_TEAM));
            fancyEventsDetailsTO.setDraw(rs.getBoolean(ColumnLabelConstants.IS_DRAW));
            fancyEventsDetailsTOS.add(fancyEventsDetailsTO);
        }
        fancyEventsTO.setEvents(fancyEventsDetailsTOS);
        return fancyEventsTO;
    }

    private FancyLeaguesTO getLeaguesHierarchy(ResultSet rs) throws SQLException {
        FancyLeaguesTO fancyLeaguesTO = new FancyLeaguesTO();
        List<FancyLeaguesDetailsTO> fancyLeaguesDetailsTOS = new ArrayList<>();
        while (rs.next()) {
            FancyLeaguesDetailsTO fancyLeaguesDetailsTO = new FancyLeaguesDetailsTO();
            fancyLeaguesDetailsTO.setLeagueId(rs.getInt(AdminPanelColumnLabelConstants.ID));
            fancyLeaguesDetailsTO.setLeagueName(rs.getString(AdminPanelColumnLabelConstants.NAME));
            fancyLeaguesDetailsTOS.add(fancyLeaguesDetailsTO);
        }
        fancyLeaguesTO.setLeagues(fancyLeaguesDetailsTOS);
        return fancyLeaguesTO;
    }

    private FancySportsListTO getFancySports(ResultSet rs) throws SQLException {
        FancySportsListTO fancySportsListTO = new FancySportsListTO();
        List<FancySportsDetailsTO> fancySportsDetailsTOS = new ArrayList<>();
        while (rs.next()) {
            FancySportsDetailsTO fancySportsDetailsTO = new FancySportsDetailsTO();
            fancySportsDetailsTO.setSportId(rs.getInt(AdminPanelColumnLabelConstants.ID));
            fancySportsDetailsTO.setSportName(rs.getString(AdminPanelColumnLabelConstants.NAME));
            fancySportsDetailsTOS.add(fancySportsDetailsTO);
        }
        fancySportsListTO.setSports(fancySportsDetailsTOS);
        return fancySportsListTO;
    }

    private FancyMarketListTO extractFancyMarkets(ResultSet rs) throws SQLException {
        FancyMarketListTO fancyMarketListTO = new FancyMarketListTO();
        List<FancyMarketTO> fancyMarketTO = new ArrayList<>();
        while (rs.next()) {
            FancyMarketTO fancyMarket = new FancyMarketTO();
            fancyMarket.setFancyMarketId(rs.getInt(AdminPanelColumnLabelConstants.FANCY_MARKET_ID));
            fancyMarket.setFancyMarketName(rs.getString(AdminPanelColumnLabelConstants.FANCY_MARKET_NAME));
            fancyMarket.setFancyOutcomeYes(rs.getString(AdminPanelColumnLabelConstants.FANCY_OUTCOME_YES));
            fancyMarket.setFancyOutcomeNo(rs.getString(AdminPanelColumnLabelConstants.FANCY_OUTCOME_NO));
            fancyMarket.setOddsYes(rs.getDouble(AdminPanelColumnLabelConstants.ODDS_YES));
            fancyMarket.setOddsNo(rs.getDouble(AdminPanelColumnLabelConstants.ODDS_NO));
            fancyMarket.setFancyStatus(rs.getString(AdminPanelColumnLabelConstants.FANCY_STATUS));
            fancyMarket.setFancyMinStake(rs.getDouble(AdminPanelColumnLabelConstants.FANCY_MIN_STAKE));
            fancyMarket.setFancyMaxStake(rs.getDouble(AdminPanelColumnLabelConstants.FANCY_MAX_STAKE));
            fancyMarket.setSuspended(rs.getBoolean(AdminPanelColumnLabelConstants.SUSPENDED));
            fancyMarketTO.add(fancyMarket);
        }
        fancyMarketListTO.setFancyMarketList(fancyMarketTO);
        return fancyMarketListTO;
    }

    @Override
    public void addFootballMatchOdds(AdminMatchOddsTO adminMatchOddsTO) {
        jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_INSERT_ADMIN_MATCH_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, adminMatchOddsTO.getEventId());
            cs.setInt(2, adminMatchOddsTO.getSportId());
            cs.setString(3, String.valueOf(adminMatchOddsTO.getOddsList()));
            return cs;
        }, (CallableStatement cs) -> {
            cs.executeQuery();
            return ColumnLabelConstants.SUCCESS;
        });
    }

    @Override
    public void addOtherMatchOdds(AdminMatchOddsTO adminMatchOddsTO) {
        jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_INSERT_ADMIN_MATCH_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, adminMatchOddsTO.getEventId());
            cs.setInt(2, adminMatchOddsTO.getSportId());
            cs.setString(3, String.valueOf(adminMatchOddsTO.getOddsList()));
            return cs;
        }, (CallableStatement cs) -> {
            cs.executeQuery();
            return ColumnLabelConstants.SUCCESS;
        });
    }

    @Override
    public List<AdminMatchOddsDetailsTO> getMatchOddsFootball(FancyMarketInputTO input) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_MATCH_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, input.getLoginToken());
            cs.setInt(2, input.getSportId());
            cs.setInt(3, input.getEventId());
            cs.setInt(4, input.getLeagueId());
            return cs;
        }, (CallableStatement cs) -> this.extractFootballMatchOdds(cs.executeQuery()));
    }

    @Override
    public List<AdminMatchOddsDetailsTO> getMatchOddsOther(FancyMarketInputTO input) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_MATCH_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, input.getLoginToken());
            cs.setInt(2, input.getSportId());
            cs.setInt(3, input.getEventId());
            cs.setInt(4, input.getLeagueId());
            return cs;
        }, (CallableStatement cs) -> this.extractOtherMatchOdds(cs.executeQuery()));
    }

    private List<AdminMatchOddsDetailsTO> extractOtherMatchOdds(ResultSet executeQuery) throws SQLException {
        List<AdminMatchOddsDetailsTO> matchOddsList = new ArrayList<>();
        while (executeQuery.next()) {
            AdminMatchOddsDetailsTO matchOddsTO = new AdminMatchOddsDetailsTO();
            matchOddsTO.setTeamId(executeQuery.getInt(ColumnLabelConstants.HOME_TEAM_ID));
            matchOddsTO.setTeam(executeQuery.getString(ColumnLabelConstants.HOME_TEAM));
            populateOtherSportsMatchOdds(executeQuery, matchOddsList, matchOddsTO);

            matchOddsTO = new AdminMatchOddsDetailsTO();
            executeQuery.next();
            matchOddsTO.setTeamId(executeQuery.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
            matchOddsTO.setTeam(executeQuery.getString(ColumnLabelConstants.AWAY_TEAM));
            populateOtherSportsMatchOdds(executeQuery, matchOddsList, matchOddsTO);
        }
        return matchOddsList;
    }

    private void populateOtherSportsMatchOdds(ResultSet executeQuery, List<AdminMatchOddsDetailsTO> matchOddsList, AdminMatchOddsDetailsTO matchOddsTO) throws SQLException {
        matchOddsTO.setEventId(executeQuery.getInt(ColumnLabelConstants.EVENT_ID));
        matchOddsTO.setSportId(executeQuery.getInt(ColumnLabelConstants.SPORT_ID));
        matchOddsTO.setBackOdds(executeQuery.getDouble(ColumnLabelConstants.BACK_ODDS));
        matchOddsTO.setBackSize(executeQuery.getBigDecimal(ColumnLabelConstants.BACK_ODDS_SIZE));
        executeQuery.next();
        matchOddsTO.setLayOdds(executeQuery.getDouble(ColumnLabelConstants.LAY_ODDS));
        matchOddsTO.setLaySize(executeQuery.getBigDecimal(ColumnLabelConstants.LAY_ODDS_SIZE));
        matchOddsTO.setMinStake(executeQuery.getBigDecimal(ColumnLabelConstants.MIN_STAKE));
        matchOddsTO.setMaxStake(executeQuery.getBigDecimal(ColumnLabelConstants.MAX_STAKE));
        matchOddsList.add(matchOddsTO);
    }

    private List<AdminMatchOddsDetailsTO> extractFootballMatchOdds(ResultSet executeQuery) throws SQLException {
        List<AdminMatchOddsDetailsTO> matchOddsList = new ArrayList<>();
        while (executeQuery.next()) {
            AdminMatchOddsDetailsTO matchOddsTO = new AdminMatchOddsDetailsTO();

            matchOddsTO.setTeamId(executeQuery.getInt(ColumnLabelConstants.HOME_TEAM_ID));
            matchOddsTO.setTeam(executeQuery.getString(ColumnLabelConstants.HOME_TEAM));
            populateOtherSportsMatchOdds(executeQuery, matchOddsList, matchOddsTO);

            matchOddsTO = new AdminMatchOddsDetailsTO();
            executeQuery.next();
            matchOddsTO.setTeamId(0);
            matchOddsTO.setTeam("Draw");
            populateOtherSportsMatchOdds(executeQuery, matchOddsList, matchOddsTO);

            matchOddsTO = new AdminMatchOddsDetailsTO();
            executeQuery.next();
            matchOddsTO.setTeamId(executeQuery.getInt(ColumnLabelConstants.AWAY_TEAM_ID));
            matchOddsTO.setTeam(executeQuery.getString(ColumnLabelConstants.AWAY_TEAM));
            populateOtherSportsMatchOdds(executeQuery, matchOddsList, matchOddsTO);
        }
        return matchOddsList;
    }
}
