package com.bettorlogic.victoryexch.middlelayer.common.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.common.dao.SportsBookDao;
import com.bettorlogic.victoryexch.middlelayer.common.dto.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.betslip.*;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.FancyBetDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.FancyMarketDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.BalanceDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.EventDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.LeagueDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportHierarchyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.GeoLocation;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ClearedOrderSummaryReportTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.ClearedOrderSummaryTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;
import com.maxmind.geoip.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;
import java.util.function.Function;

import static com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants.EMAIL_CONFIRMATION_STATUS;
import static com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants.EMAIL_VERIFIED;

@Repository
public class SportsBookDaoImpl implements SportsBookDao {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(SportsBookConstants.DATE_FORMAT);
    // private static final String PROC_PROCESS_USER_REGISTRATION = "select * from USER_REGISTRATION(?,?,?,?)";
    private static final String PROC_PROCESS_USER_REGISTRATION = "select * from user_registration_email_confirmation(?,?,?,?,?)";
    private static final String PROC_CHECK_FOR_DUPLICATE_USER = "select * from check_for_duplicate_user(?,?)";
    //    private static final DateFormat TRANSACTION_DATE_FORMAT = new SimpleDateFormat(SportsBookConstants.TRANSACTION_DATE_FORMAT);
    private static final String PROC_GET_LOGIN_DETAILS = "select * from user_login_1(?,?,?,?,?,?)";
    private static final String PROC_GET_USER_PASSWORD = "select * from user_login(?,?,?)";
    private static final String PROC_GENERATING_LOGIN_TOKEN = "select * from generating_token(?,?)";
    private static final String PROC_GET_FOOTER_CONTROLS = "select * from get_footer_controls()";
    private static final String PROC_GET_LOGOUT = "select * from logout(?)";
    private static final String PROC_PROCESS_SUBHEADERS = "select * from get_sub_header()";
    private static final String PROC_GET_LOGO_AND_LICENCING_INFO = "select * from get_fc_logo_and_licencing_info()";
    private static final String PROC_ADD_ADMIN_LEAGUES = "select * from add_admin_leagues(?,?,?,?)";
    private static final String PROC_GET_PERCENTAGE = "select betfairpercentage from betplacement_client_percentages";
    private static final String QUERY_GET_EV_KO = "select eventko_utc from bmevents where eventid=?";
    private static final String UPDATE_BET_BETFAIR_SETTLEMENT = "update bet set betstatus=?, bet_settlement_id=?, betfair_profit=?, betfair_commission=?, betstatusid=?, betsettled=? where betfair_betid=?";
    private static final String GET_ROLEID="select roleid from bmeusers b join userprofile up on b.profileid=up.profileid where userid=? or profilename=?";
    private static final String PROC_GET_ONE_CLICK = "select * from get_one_click_coin_details(?)";
    private static final String PROC_GET_USER_PROFILE = "select * from get_user_profile(?)";
    private static final String PROC_GET_TRANSACTION_PROFIT_LOSS_EVENT_VIEW = "select * from get_accountstatement_profitloss_eventview(?,?,?,?,?,?)";
    private static final String PROC_GET_TRANSACTION_DETAILS = "select * from get_accountstatement_profitloss_transactions(?,?,?,?,?,?,?) limit 500";
    private static final String PROC_GET_SPORTS_PROFIT_LOSS = "select * from get_sportwise_profit_loss(?,?,?)";
    private static final String PROC_GET_TRANSACTION_COUNT = "select * from get_acount_statements_profit_loss_count(?,?,?,?)";
    private static final String PROC_GET_BET_HISTORY_DETAILS = "select * from get_bet_history(?,?,?,?,?)";
    private static final String PROC_GET_SESSION_KEY = "select * from betfairodds.get_betfair_session_key()";
    private static final String PROC_GET_USER_COIN = "select * from get_user_coins(?)";
    private static final String PROC_SAVE_COIN_DETAILS = "select * from save_user_coinsettings(?)";
    //    private static final String PROC_UPDATE_COIN_DETAILS = "select * from update_user_coinsettings_save(?,?)";
    private static final String PROC_SAVE_USER_COIN_DETAILS = "select * from save_coin_details(?,?,?,?)";
    //    private static final String PROC_UPDATE_ONECLICK_USER_COIN_DETAILS = "select * from save_oneclick_details(?,?)";
    private static final String GET_ID = "select id from bmeusers where user_login_token = ?";
    private static final String GET_EMAIL_ID = "select userid from bmeusers where user_login_token = ?";
    private static final String GET_EMAIL_ID_COUNT = "select count(*) from bmeusers b join userprofile up on up.profileid=b.profileid and lower(up.profilename)=? or  lower(b.userid)= ? ";
    private static final String GET_COMMON_EVENT_DETAILS = "select * from get_event_view_fancy_bet_details(?)";
    private static final String GET_EMAIL_VERIFICATION_STATUS = "select * from get_email_verification_status(?)";
    private static final String UPDATE_COIN_SETTINGS = "update user_coinsettings SET coinvalue=? WHERE coinid = ? and bmeuserid = ?;";
    private static final String UPDATE_USER_ONE_CLICK_COIN_SETTINGS = "update user_onclickcoinsettings SET coinvalue=? WHERE coinid = ? and bmeuserid = ?;";
    private static final String SAVE_COIN_SETTINGS = "update user_coinsettings SET isdefaultcoin=? WHERE coinid = ? and bmeuserid = ?;";
    private static final String UPDATE_USER_SETTINGS = "update usersettings SET defaultstake=?, highlightodds=? WHERE bmeuserid=?;";
    private static final String GET_EMAIL_VERIFIED_DETAILS = "select * from email_verification(?)";
    private static final String PROC_GET_USER_BALANCE = "select * from get_user_balance(?)";
    private static final String LOGIN_SUCCESS = "Login Success";
    private static final String NO_ERROR = "no error";
    private static final String STATUS_ID = "status_id";
    private static final String PROC_USER_ID = "select count(*) from bmeusers where user_login_token='";
    private static final String GEO_IP_AMAZON_AWS_URL = "http://checkip.amazonaws.com";
    private static final String GEO_LOCATION_FILE_NAME =  "/var/www/devops/geolocation/GeoLiteCity.dat";
    //private static final String GEO_LOCATION_FILE_NAME =  "D:\\anudeep\\Betfair API\\GeoLiteCity.dat";
    private static final String UPDATE_GENERATED_TOKEN = "update bmeusers set forget_pwd_confirm_token=? where userid = ?";
    private static final String GET_EMAIL = "select userid  from bmeusers where forget_pwd_confirm_token = ?";
    private static final String UPDATE_USER_PASSWORD = "update bmeusers set userpwd=? where userid=?";
    private static final String SAVE_MARKET_PROFIT_LOSS_DETAILS =
            "insert into beforebet(eventid, marketid, usertoken, hometeamprofitloss, " +
                    "awayteamprofitloss, isfancy, drawprofitloss, providerid)" +
                    "values(?,?,?,?,?,?,?,?)";
    private static final String GET_MARKET_PROFIT_LOSS_DETAILS = "select * from get_bet_profitloss(?)";
    private static final String GET_PLAYER_DETAILS = "select b.roleid,b.statusid,ub.userbalance,us.exposurelimit,ub.userexposure, b.id, ub.settledbalance from bmeusers b" +
            " join userbalance ub on b.id = ub.userid" +
            " join usersettings us on b.id = us.bmeuserid" +
            " where b.user_login_token=?";
    private static final String GET_BALANCE = "select * from validate_balance_availability_test(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER_PROFILE = "select * from update_user_timezone(?,?)";
    private static final String PROC_AGENT_LOGIN_DETAILS = "select * from get_agentdetails(?)";

    private static final Function<FavouriteEventDetailsTO, String> PROC_SAVE_PIN_DETAILS_FUNCTION =
            (FavouriteEventDetailsTO favouriteEventDetails) -> {
                String sql = "select * from save_favourite_event_details(?,?,?)";
                if (!StringUtils.isEmpty(favouriteEventDetails.getMarketGroupId())) {
                    sql = "select * from save_favourite_event_details(?,?,?,?)";
                    if (!StringUtils.isEmpty(favouriteEventDetails.getSubMarket())) {
                        sql = "select * from save_favourite_event_details(?,?,?,?,?)";
                    }
                }
                return sql;
            };
    private static final Function<FavouriteEventDetailsTO, String> PROC_DELETE_PIN_DETAILS_FUNCTION =
            (FavouriteEventDetailsTO favouriteEventDetails) -> {
                String sql = "select * from delete_favourite_event_details(?,?,?)";
                if (!StringUtils.isEmpty(favouriteEventDetails.getMarketGroupId())) {
                    sql = "select * from delete_favourite_event_details(?,?,?,?)";
                    if (!StringUtils.isEmpty(favouriteEventDetails.getSubMarket())) {
                        sql = "select * from delete_favourite_event_details(?,?,?,?,?)";
                    }
                }
                return sql;
            };
    private static final String GET_AGENT_MARKET_PROFIT_LOSS_DETAILS = "select * from get_admin_bet_profitloss()";
    private static final String GET_EZUGI_TOKEN ="select * from ezugi.get_ezugi_token(?)" ;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private LookupService lookUp;

    @Override
    public int getuserId(String userToken) {
        return jdbcTemplate.queryForObject(PROC_USER_ID +  userToken + "'", Integer.class);
    }

    @Override
    public UserBetDetailsTO validateBet(String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_PLAYER_DETAILS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.populateResultset(cs.executeQuery()));
    }

    private UserBetDetailsTO populateResultset(ResultSet rs) throws SQLException {
        UserBetDetailsTO userBetDetailsTO = new UserBetDetailsTO();
        while (rs.next()){
            userBetDetailsTO.setBalance(rs.getBigDecimal("userbalance"));
            userBetDetailsTO.setExposureLimit(rs.getBigDecimal("exposurelimit"));
            userBetDetailsTO.setRoleId(rs.getInt("roleid"));
            userBetDetailsTO.setStatusId(rs.getInt("statusid"));
            userBetDetailsTO.setUserExposure(rs.getBigDecimal("userexposure"));
            userBetDetailsTO.setUserId(rs.getInt("id"));
            userBetDetailsTO.setSettledBalance(rs.getBigDecimal("settledbalance"));
        }
        return userBetDetailsTO;
    }

    @Override
    public Double getBalance(BetDetailsTO betDetailsTO, Integer userId, String bet, Integer betId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_BALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            cs.setString(2, betDetailsTO.getOddType());
            cs.setBigDecimal(3, new BigDecimal(betDetailsTO.getStakeAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            cs.setBigDecimal(4, new BigDecimal(betDetailsTO.getReturns()).setScale(2, BigDecimal.ROUND_HALF_UP));
            cs.setInt(5, betDetailsTO.getEventId());
            cs.setInt(6, betDetailsTO.getOutcomeId());
            cs.setInt(7,betId);
            cs.setInt(8, betDetailsTO.getMarketId());
            cs.setString(9, bet);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            Double id = null;
            while (rs.next()) {
                id = rs.getDouble("validate_balance_availability_test");
            }
            return id;
        });
    }

    @Override
    public Double getUpdateBalance(Integer eventId, String oddType, String stake, String returns, Integer marketId, String odds, Integer betId, int userId, String update, Integer outcomeId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_BALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            cs.setString(2, oddType);
            cs.setBigDecimal(3, new BigDecimal(stake));
            cs.setBigDecimal(4, new BigDecimal(returns));
            cs.setInt(5, eventId);
            cs.setInt(6, outcomeId);
            cs.setInt(7,betId);
            cs.setInt(8, marketId);
            cs.setString(9, update);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            Double id = null;
            while (rs.next()) {
                id = rs.getDouble("validate_balance_availability_test");
            }
            return id;
        });
    }

    @Override
    public int saveUserTimeZone(Integer id, String userToken) {
        return jdbcTemplate.execute(connection -> connection.prepareCall(UPDATE_USER_PROFILE,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> {
            cs.setInt(1,id);
            cs.setString(2,userToken);
            ResultSet rs = cs.executeQuery();
            int result = 0;
            while (rs.next()) {
                result = rs.getInt("msg");
            }
            return result;
        });
    }

    @Override
    public AgentTO insertAgentLoginToken(String emailId) {

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GENERATING_LOGIN_TOKEN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, emailId);
            cs.setString(2, UUID.randomUUID().toString());
             return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            AgentTO agentTO = new AgentTO();
            while (rs.next()) {
                agentTO.setToken(rs.getString(ColumnLabelConstants.GENERATED_TOKEN));
            }
            return agentTO;
        });
    }

    @Override
    public AgentTO getAgentDetails(String emailId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_AGENT_LOGIN_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            cs.setString(1, emailId);

            return cs;
        }, (CallableStatement cs) -> getAgent(cs.executeQuery()));
    }

    @Override
    public Integer getRole(String username) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_ROLEID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1,username);
            cs.setString(2,username);
            return cs;
        }, (CallableStatement cs) -> getRole(cs.executeQuery()));
    }

    @Override
    public List<MarketProfitLossTO> getAgentMarketProfitLossDetails() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(GET_AGENT_MARKET_PROFIT_LOSS_DETAILS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> this.populateAdminMarketProfitLossDetails(cs.executeQuery()));
    }

    @Override
    public String getEzugiToken(String userToken) {
        return jdbcTemplate.execute(connection -> connection.prepareCall(GET_EZUGI_TOKEN,
                         ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> {
                    cs.setString(1,userToken);

                            ResultSet rs = cs.executeQuery();
                      String result = "";
                    while (rs.next()) {
                            result = rs.getString("ezugitoken");
                          }
                     return result;
                });
    }

    @Override
    public Integer getRoleId(String userToken) {
        try {
            String SQL = "select roleid from bmeusers where user_login_token=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{userToken}, Integer.class);
        }catch (Exception e){
            return 0;
        }
    }


    @Override
    public Integer getStatus(String emailId) {
        try {
            String SQL = "select statusid from bmeusers where userid=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{emailId}, Integer.class);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public String getPassword(String name) {
        try {
            String SQL = "select userpwd from bmeusers where userid=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{name}, String.class);
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public void suspendAgentMarket(Integer agentId, String loginToken) {
        try {
            String suspendMarkets = "update odds set status='SUSPENDED' where eventid in (select event_id from agent_markets where agent_id=?) and providerid in (1,3)";
            jdbcTemplate.update(suspendMarkets, agentId);

            String loggedin = "update bmeusers set is_logged_in=0 where user_login_token=?";
            jdbcTemplate.update(loggedin, loginToken);

        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void unSuspend(Integer agentId) {
        try {
            String unsuspendMarkets = "update odds set status='UNSUSPENDED' where eventid in (select event_id from agent_markets where agent_id=?) and providerid in (1,3)";
            jdbcTemplate.update(unsuspendMarkets, agentId);
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void insertLogin(String token) {
        try {
            String loggedin = "update bmeusers set is_logged_in=1 where user_login_token=?";
            jdbcTemplate.update(loggedin, token);
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void updateAgentPwd(UserPasswordUpdateDetailsTO password) {
        try {
            String loggedin = "update admin_agent set password=? where agent_name=?";
            jdbcTemplate.update(loggedin, password.getNewPassword(), password.getEmailId());
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public Integer getId(String emailId) {
        try {
            String SQL = "select id from bmeusers where userid=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{emailId}, Integer.class);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void insertCommission(Integer id) {
        try {
            String sql = "insert into usersettings(bmeuserid,commission_charges,currencyid,oddsformatid,defaultstake,highlightodds,fancy_commission_charges) values(?,2.0,1,1,100.0,1,2.0)";
            jdbcTemplate.update(sql, new Object[]{id});
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public BigDecimal getUserProfit(BetDetailsTO userBetDetailsTO, Integer userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall("select * from user_bets_profit(?,?,?,?,?,?,?,?,?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, userId);
            cs.setString(2, userBetDetailsTO.getOddType());
            cs.setBigDecimal(3, new BigDecimal(userBetDetailsTO.getStakeAmount()).setScale(2, BigDecimal.ROUND_FLOOR));
            cs.setBigDecimal(4, new BigDecimal(userBetDetailsTO.getReturns()).setScale(2, BigDecimal.ROUND_FLOOR));
            cs.setInt(5, userBetDetailsTO.getEventId());
            cs.setInt(6, userBetDetailsTO.getOutcomeId());
            cs.setInt(7,0);
            cs.setInt(8, userBetDetailsTO.getMarketId());
            cs.setString(9, "PLACEMENT");
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            BigDecimal id = new BigDecimal(0.0);
            while (rs.next()) {
                id = rs.getBigDecimal("user_bets_profit");
            }
            return id;
        });
    }


    @Override
    public Integer getUser(String userToken) {
        try {
            String SQL = "select id from bmeusers where user_login_token=?";
            return jdbcTemplate.queryForObject(SQL, new Object[]{userToken}, Integer.class);
        }catch (Exception e){
            return 0;
        }
    }

    private List<MarketProfitLossTO> populateAdminMarketProfitLossDetails(ResultSet rs) throws SQLException {
        List<MarketProfitLossTO> marketProfitLossList = new ArrayList<>();
        while (rs.next()) {
            MarketProfitLossTO marketProfitLoss = new MarketProfitLossTO();
            marketProfitLoss.setSportName(rs.getString(ColumnLabelConstants.SPORTNAME));
            marketProfitLoss.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
            marketProfitLoss.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            marketProfitLoss.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_ID));
            marketProfitLoss.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
            marketProfitLoss.setHomeTeamProfitLoss(rs.getBigDecimal(ColumnLabelConstants.HOME_TEAM_PROFIT_LOSS));
            marketProfitLoss.setAwayTeamProfitLoss(rs.getBigDecimal(ColumnLabelConstants.AWAY_TEAM_PROFIT_LOSS));
            marketProfitLoss.setIsFancy(rs.getBoolean(ColumnLabelConstants.IS_FANCY_FLAG));
            marketProfitLoss.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
            marketProfitLoss.setDrawProfitLoss(rs.getBigDecimal(ColumnLabelConstants.DRAW_PROFIT_LOSS));
            marketProfitLoss.setSubMarket(rs.getString(ColumnLabelConstants.SUBMARKET_NAME));
            marketProfitLoss.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));


            marketProfitLossList.add(marketProfitLoss);
        }
        return marketProfitLossList;

    }

    private Integer getRole(ResultSet rs) throws SQLException {
        int roleId=0;
        while(rs.next())
        {
            System.out.println("role");
            roleId=rs.getInt(ColumnLabelConstants.ROLEID);
        }
        return  roleId;

    }



    private AgentTO getAgent(ResultSet rs) throws SQLException {
      AgentTO agentTO=new AgentTO();
              while (rs.next()) {
        agentTO.setAgentId(rs.getInt(ColumnLabelConstants.AGENT_ID));
        agentTO.setPassword(rs.getString(ColumnLabelConstants.AGENTPASSWORD));
        agentTO.setAgentName(rs.getString(ColumnLabelConstants.AGENT_NAME));
        agentTO.setInactiveSessionSuspension(rs.getInt(ColumnLabelConstants.INACTIVE_SESSION_SUSPENSION));
        agentTO.setBookPosition(rs.getString(ColumnLabelConstants.BOOK_POSITION));
        agentTO.setBetlistLive(rs.getString(ColumnLabelConstants.BET_LIST_LIVE));
        agentTO.setResultDeclation(rs.getString(ColumnLabelConstants.RESULT_DELCATION));
        agentTO.setStatus(rs.getString(ColumnLabelConstants.AGENT_STATUS));
        agentTO.setAvailable(rs.getString(ColumnLabelConstants.AVAILABLE));
        agentTO.setToken(rs.getString(ColumnLabelConstants.AGENTLOGINTOKEN));
        agentTO.setRoleId(rs.getInt(ColumnLabelConstants.ROLEID));
        }
        return agentTO;
    }
    @Override
    public Integer addAdminLeagues(LeaguesTO leaguesDetails) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_ADD_ADMIN_LEAGUES,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, leaguesDetails.getUserToken());
            cs.setInt(2, leaguesDetails.getSportId());
            cs.setInt(3, leaguesDetails.getLeagueId());
            cs.setInt(4, leaguesDetails.getStatus());

            return cs;
        }, (CallableStatement cs) -> getUserId(cs.executeQuery(), ColumnLabelConstants.ADMIN_LEAGUE_ID));
    }

    @Override
    public Integer saveMarketProfitLossDetails(String userToken, MarketProfitLossTO marketProfitLossDetails) {
        Integer result;
        result = jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(SAVE_MARKET_PROFIT_LOSS_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, marketProfitLossDetails.getEventId());
            cs.setInt(2, marketProfitLossDetails.getMarketId());
            cs.setString(3, userToken);
            cs.setBigDecimal(4, marketProfitLossDetails.getHomeTeamProfitLoss());
            cs.setBigDecimal(5, marketProfitLossDetails.getAwayTeamProfitLoss());
            cs.setBoolean(6, marketProfitLossDetails.isFancy());
            cs.setBigDecimal(7, marketProfitLossDetails.getDrawProfitLoss());
            cs.setInt(8, marketProfitLossDetails.getProviderId());
            return cs;
        }, (CallableStatementCallback<Integer>) PreparedStatement::executeUpdate);
        return result;
    }

    @Override
    public List<MarketProfitLossTO> getMarketProfitLossDetails(String userToken) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_MARKET_PROFIT_LOSS_DETAILS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> this.populateMarketProfitLossDetails(cs.executeQuery()));
    }

    private List<MarketProfitLossTO> populateMarketProfitLossDetails(ResultSet rs) throws SQLException {
        List<MarketProfitLossTO> marketProfitLossList = new ArrayList<>();
        while (rs.next()) {
            MarketProfitLossTO marketProfitLoss = new MarketProfitLossTO();
            marketProfitLoss.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
            marketProfitLoss.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_ID));
            marketProfitLoss.setHomeTeamProfitLoss(rs.getBigDecimal(ColumnLabelConstants.HOME_TEAM_PROFIT_LOSS));
            marketProfitLoss.setAwayTeamProfitLoss(rs.getBigDecimal(ColumnLabelConstants.AWAY_TEAM_PROFIT_LOSS));
            marketProfitLoss.setIsFancy(rs.getBoolean(ColumnLabelConstants.IS_FANCY_FLAG));
            marketProfitLoss.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
            marketProfitLoss.setDrawProfitLoss(rs.getBigDecimal(ColumnLabelConstants.DRAW_PROFIT_LOSS));
            marketProfitLoss.setSubMarket(rs.getString(ColumnLabelConstants.SUBMARKET_NAME));

            marketProfitLossList.add(marketProfitLoss);
        }
        return marketProfitLossList;
    }

    private Integer getUserId(ResultSet rs, String idType) throws SQLException {
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(idType);
        }
        return id;
    }

    private OddDetailsTO populateOddDetails(ResultSet rs) throws SQLException {
        OddDetailsTO oddDetails = new OddDetailsTO();
        oddDetails.setOutcomeId(rs.getInt(ColumnLabelConstants.OUTCOME_ID));
        oddDetails.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
        int providerId = rs.getInt(ColumnLabelConstants.PROVIDER_ID);
        if (providerId == 3) {
            oddDetails.setOdds(rs.getDouble(ColumnLabelConstants.ODDS));
            oddDetails.setOutcome(rs.getDouble(ColumnLabelConstants.OUTCOME));
        } else if (providerId == 4) {
            oddDetails.setOdds(rs.getDouble(ColumnLabelConstants.BACK_LAY_ODDS));
            oddDetails.setSize(rs.getDouble(ColumnLabelConstants.BACK_LAY_ODDS_SIZE));
        }
        return oddDetails;
    }

    private List<FancyBetDetailsTO> extractCommonEventDetailsFromResultSet(ResultSet rs) throws SQLException {
        List<FancyBetDetailsTO> fancyBetDetailsList = new ArrayList<>();
        int lastMarketId = 0;
        FancyBetDetailsTO fancyBetDetails = null;
        rs.previous();
        while (rs.next()) {
            int marketId = rs.getInt(ColumnLabelConstants.MARKET_ID);
            if (marketId == lastMarketId) {
                if (fancyBetDetails != null) {
                    this.populateFancyBetDetails(fancyBetDetails, rs);
                    fancyBetDetails.getOutcomeDetailsList().add(this.populateOddDetails(rs));
                }
            } else {
                lastMarketId = rs.getInt(ColumnLabelConstants.MARKET_ID);
                rs.previous();
                fancyBetDetails = new FancyBetDetailsTO();
                fancyBetDetailsList.add(fancyBetDetails);
            }
        }
        return fancyBetDetailsList;
    }

    @Override
    public Double getBetPercentage() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_PERCENTAGE,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getPercentage(cs.executeQuery()));
    }

    private Double getPercentage(ResultSet executeQuery) throws SQLException {
        Double percentage = 0.0;
        while (executeQuery.next()) {
            percentage = executeQuery.getDouble(ColumnLabelConstants.BETFAIR_PERCENTAGE);
        }
        return percentage;
    }

    @Override
    public String getEventKO(Integer eventId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(QUERY_GET_EV_KO,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, eventId);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String id = null;
            while (rs.next()) {
                id = rs.getString(ColumnLabelConstants.EVENT_KO);
            }
            return id;
        });
    }

    @Override
    public void betfairSettlement(BetfairServerResponse<ClearedOrderSummaryReportTO> response) throws ParseException {
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
        String utcDate = odt.format((DateTimeFormatter.ofPattern(ColumnLabelConstants.DATE_PATTERN)));
        jdbcTemplate.batchUpdate(UPDATE_BET_BETFAIR_SETTLEMENT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                ClearedOrderSummaryTO summary = response.getResponse().getClearedOrders().get(i);
                preparedStatement.setString(1, summary.getBetOutcome().equalsIgnoreCase(ColumnLabelConstants.LOST) ? ColumnLabelConstants.LOSS : summary.getBetOutcome().equalsIgnoreCase(ColumnLabelConstants.WON) ? ColumnLabelConstants.WIN : summary.getBetOutcome());
                preparedStatement.setLong(2, Long.parseLong(summary.getBetId()));
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(summary.getProfit()));
                preparedStatement.setBigDecimal(4, BigDecimal.valueOf(summary.getCommission()));
                preparedStatement.setInt(5, summary.getBetOutcome().equalsIgnoreCase(ColumnLabelConstants.LOST) ? 3 : summary.getBetOutcome().equalsIgnoreCase(ColumnLabelConstants.WON) ? 2 : summary.getBetOutcome().equalsIgnoreCase(ColumnLabelConstants.VOID) ? 3 : 1);
                preparedStatement.setTimestamp(6, Timestamp.valueOf(utcDate));
                preparedStatement.setLong(7, Long.parseLong(summary.getBetId()));
            }

            @Override
            public int getBatchSize() {
                return response.getResponse().getClearedOrders().size();
            }
        });
    }

    private void populateFancyBetDetails(FancyBetDetailsTO fancyBetDetails, ResultSet rs) throws SQLException {
        fancyBetDetails.setProviderId(rs.getInt(ColumnLabelConstants.PROVIDER_ID));
        fancyBetDetails.setProviderName(rs.getString(ColumnLabelConstants.PROVIDER_NAME));
        fancyBetDetails.setMarketId(rs.getInt(ColumnLabelConstants.MARKET_ID));
        fancyBetDetails.setMarketName(rs.getString(ColumnLabelConstants.MARKET_NAME));
        fancyBetDetails.setMinStake(rs.getDouble(ColumnLabelConstants.MIN_STAKE));
        fancyBetDetails.setMaxStake(rs.getDouble(ColumnLabelConstants.MAX_STAKE));
        fancyBetDetails.setMarketSuspended(rs.getInt(ColumnLabelConstants.MARKET_SUSPENDED));
    }

    @Override
    public SubHeaderDetailsWrapperTO getSubHeaderDetails() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_PROCESS_SUBHEADERS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> extractSubHeadersFromResultSet(cs.executeQuery()));

    }

    @Override
    public String saveCoinDetails(String userId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_COIN_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userId);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String status = null;
            while (rs.next()) {
                status = rs.getString(ColumnLabelConstants.SAVE_COIN_USER_SETTINGS);
            }
            return status;
        });
    }

    @Override
    public String storeCoinDetails(UserCoinDetailsTO userCoinDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_USER_COIN_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            cs.setString(1, userCoinDetails.getUserToken());
            cs.setArray(2, connection.createArrayOf("numeric", userCoinDetails.getUserCoins()));
            cs.setInt(3, userCoinDetails.getStake());
            cs.setInt(4, userCoinDetails.getIsHighlight());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String result = null;
            while (rs.next()) {
                result = rs.getString(ColumnLabelConstants.SAVE_COIN_DETAILS);
            }
            return result;
        });
    }

    public Boolean updateOneClick(UserCoinDetailsTO UserCoinDetailsTO) {
        String userId = getUserId(UserCoinDetailsTO.getUserToken());
        if (userId != null) {
            updateOneClickSettings(UserCoinDetailsTO.getUserCoinsDetailsList(), getUserId(UserCoinDetailsTO.getUserToken()));
            return true;
        } else {
            return false;
        }

    }

    private void updateOneClickSettings(List<CoinDetailsTO> coinDetailsList, String loginToken) {
        jdbcTemplate.batchUpdate(UPDATE_USER_ONE_CLICK_COIN_SETTINGS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                CoinDetailsTO coin = coinDetailsList.get(i);
                preparedStatement.setDouble(1, coin.getCoinValue());
                preparedStatement.setInt(2, coin.getCoinId());
                preparedStatement.setInt(3, Integer.valueOf(loginToken));
            }

            @Override
            public int getBatchSize() {
                return coinDetailsList.size();
            }
        });
    }

    private SubHeaderDetailsWrapperTO extractSubHeadersFromResultSet(ResultSet executeQuery) throws SQLException {
        SubHeaderDetailsWrapperTO subHeader = new SubHeaderDetailsWrapperTO();
        List<SubHeaderDetailsTO> subHeadersList = new ArrayList<>();
        while (executeQuery.next()) {
            SubHeaderDetailsTO subHeaderDetailsTO = new SubHeaderDetailsTO();
            subHeaderDetailsTO.setSubHeaderId(executeQuery.getInt(ColumnLabelConstants.SUBMENU_ID));
            subHeaderDetailsTO.setSubHeaderName(executeQuery.getString(ColumnLabelConstants.SUBMENU_NAME));
            subHeaderDetailsTO.setSubHeaderUrl(executeQuery.getString(ColumnLabelConstants.URL));
            subHeadersList.add(subHeaderDetailsTO);
        }
        subHeader.setSubHeadersList(subHeadersList);
        return subHeader;
    }

    @Override
    public OneClickCoinWrapperTO getOneClickDetails(String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_ONE_CLICK,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet resultSet = cs.executeQuery();
            return this.populateOneClickDetails(resultSet);
        });
    }

    @Override
    public UserCoinDetailsTO getUserCoins(String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_USER_COIN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            List<CoinDetailsTO> coinDetailsList = new ArrayList<>();
            UserCoinDetailsTO userCoinDetailsTO = new UserCoinDetailsTO();
            int stake = 0;
            int highlight = 0;
            while (rs.next()) {
                highlight = rs.getInt(ColumnLabelConstants.HIGHLIGHT);
                stake = rs.getInt(ColumnLabelConstants.STAKE);
                CoinDetailsTO coin = new CoinDetailsTO();
                coin.setCoinValue(rs.getDouble(ColumnLabelConstants.COIN_VALUE));
                coin.setCoinId(rs.getInt(ColumnLabelConstants.COIN_ID));
                coin.setIsSelected(rs.getInt(ColumnLabelConstants.IS_SELECTED));
                coinDetailsList.add(coin);
            }
            userCoinDetailsTO.setStake(stake);
            userCoinDetailsTO.setIsHighlight(highlight);
            userCoinDetailsTO.setUserCoinsDetailsList(coinDetailsList);
            return userCoinDetailsTO;
        });
    }

    @Override
    public Boolean updateUserCoins(UserCoinDetailsTO UserCoinDetailsTO) {
        String userId = getUserId(UserCoinDetailsTO.getUserToken());
        if (userId != null) {
            updateCoins(UserCoinDetailsTO.getUserCoinsDetailsList(), getUserId(UserCoinDetailsTO.getUserToken()));
            return true;
        } else {
            return false;
        }

    }

    private void updateCoins(List<CoinDetailsTO> coinDetailsList, String userId) {
        jdbcTemplate.batchUpdate(UPDATE_COIN_SETTINGS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                CoinDetailsTO coin = coinDetailsList.get(i);
                preparedStatement.setDouble(1, coin.getCoinValue());
                preparedStatement.setInt(2, coin.getCoinId());
                preparedStatement.setInt(3, Integer.valueOf(userId));
            }

            @Override
            public int getBatchSize() {
                return coinDetailsList.size();
            }
        });
    }

    @Override
    public Boolean storeCoinSettingDetails(UserCoinDetailsTO UserCoinDetailsTO) {
        String userId = getUserId(UserCoinDetailsTO.getUserToken());
        if (userId != null) {
            storeCoinSettingInfo(UserCoinDetailsTO.getUserCoinsDetailsList(), userId);
            storeStakeHighlights(UserCoinDetailsTO.getStake(), UserCoinDetailsTO.getIsHighlight(), userId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean validateUserToken(String userToken) {
        String userId = getUserId(userToken);
        return userId != null;
    }

    @Override
    public FavouriteEventDetailsTO saveFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails) {
        return jdbcTemplate.execute(connection -> this.getFavouriteEventDetails(
                connection, favouriteTeamDetails, PROC_SAVE_PIN_DETAILS_FUNCTION.apply(favouriteTeamDetails)),
                (CallableStatement cs) -> this.extractFavouriteEventDetails(
                        cs.executeQuery(), ColumnLabelConstants.SAVE_FAVOURITE_EVENT_DETAILS));
    }

    @Override
    public FavouriteEventDetailsTO deleteFavouriteEventDetails(FavouriteEventDetailsTO favouriteTeamDetails) {
        return jdbcTemplate.execute(connection -> this.getFavouriteEventDetails(
                connection, favouriteTeamDetails, PROC_DELETE_PIN_DETAILS_FUNCTION.apply(favouriteTeamDetails))
                , (CallableStatement cs) -> this.extractFavouriteEventDetails(
                        cs.executeQuery(), ColumnLabelConstants.DELETE_FAVOURITE_EVENT_DETAILS));
    }

    private CallableStatement getFavouriteEventDetails(
            Connection connection, FavouriteEventDetailsTO favouriteTeamDetails, String sql) throws SQLException {
        CallableStatement cs = connection.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        cs.setString(1, favouriteTeamDetails.getUserToken());
        cs.setInt(2, favouriteTeamDetails.getEventId());
        if (favouriteTeamDetails.getProviderId() != null) {
            cs.setInt(3, favouriteTeamDetails.getProviderId());
        } else {
            cs.setInt(3, SportsBookConstants.BET_FAIR_PROVIDER_ID);
        }

        if (!StringUtils.isEmpty(favouriteTeamDetails.getMarketGroupId())) {
            cs.setInt(4, favouriteTeamDetails.getMarketGroupId());
            if (!StringUtils.isEmpty(favouriteTeamDetails.getSubMarket())) {
                cs.setString(5, favouriteTeamDetails.getSubMarket());
            }
        }
        return cs;
    }

    private FavouriteEventDetailsTO extractFavouriteEventDetails(ResultSet rs, String columnLabel) throws SQLException {
        FavouriteEventDetailsTO favouriteTeamDetailsOutput = new FavouriteEventDetailsTO();
        while (rs.next()) {
            if (rs.getString(columnLabel) != null) {
                favouriteTeamDetailsOutput.setErrorDetails(rs.getString(columnLabel));
                if (columnLabel.equals(ColumnLabelConstants.SAVE_FAVOURITE_EVENT_DETAILS)) {
                    favouriteTeamDetailsOutput.setPinDetailsSaved(false);
                } else {
                    favouriteTeamDetailsOutput.setPinDetailsDeleted(false);
                }
            } else {
                if (columnLabel.equals(ColumnLabelConstants.SAVE_FAVOURITE_EVENT_DETAILS)) {
                    favouriteTeamDetailsOutput.setPinDetailsSaved(true);
                } else {
                    favouriteTeamDetailsOutput.setPinDetailsDeleted(true);
                }
            }
        }
        return favouriteTeamDetailsOutput;
    }

    @Override
    public UserProfileDetailsWrapperTO getUserProfileDetails(String userToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_USER_PROFILE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            return cs;
        }, (CallableStatement cs) -> {
            UserProfileDetailsWrapperTO userProfileDetailsWrapper = new UserProfileDetailsWrapperTO();
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                userProfileDetailsWrapper.setFirstName(rs.getString(ColumnLabelConstants.FIRST_NAME));
                userProfileDetailsWrapper.setLastName(rs.getString(ColumnLabelConstants.LAST_NAME));
                Timestamp birthday = rs.getTimestamp(ColumnLabelConstants.BIRTH_DAY);
                if (birthday != null) {
                    userProfileDetailsWrapper.setDateOfBirth(getDateFromTimestamp(birthday));
                }
                userProfileDetailsWrapper.setProfileEmail(rs.getString(ColumnLabelConstants.PROFILE_EMAIL));
                userProfileDetailsWrapper.setUserAddress(rs.getString(ColumnLabelConstants.USER_ADDRESS));
                userProfileDetailsWrapper.setTownCity(rs.getString(ColumnLabelConstants.TOWN_CITY));
                userProfileDetailsWrapper.setPostalCode(rs.getString(ColumnLabelConstants.POSTAL_CODE));
                userProfileDetailsWrapper.setTimeZone(rs.getString(ColumnLabelConstants.TIME_ZONE));
                userProfileDetailsWrapper.setProfilePhone(rs.getString(ColumnLabelConstants.PROFILE_PHONE));
                userProfileDetailsWrapper.setCountryName(rs.getString(ColumnLabelConstants.USER_COUNTRY_NAME));
                userProfileDetailsWrapper.setCurrencyTitle(rs.getString(ColumnLabelConstants.CURRENCY_TITLE));
                userProfileDetailsWrapper.setOddsFormat(rs.getString(ColumnLabelConstants.ODDS_NAME));
                userProfileDetailsWrapper.setCommisionCharges(rs.getInt(ColumnLabelConstants.COMM_CHARGE));
                userProfileDetailsWrapper.setUserLang(rs.getString(ColumnLabelConstants.USER_LANG));
                userProfileDetailsWrapper.setTimeZoneId(rs.getInt(ColumnLabelConstants.TIMEZONE_ID));
            }
            return userProfileDetailsWrapper;
        });
    }

    private String getDateFromTimestamp(Timestamp timestamp) {
        return DATE_FORMAT.format(new Date(timestamp.getTime()));
    }

    @Override
    public void updateUserPassword(String password, String emailId) {
        jdbcTemplate.update(UPDATE_USER_PASSWORD, password, emailId);
    }

    @Override
    public UserLoginDetailsTO getUserDetails(UserPasswordUpdateDetailsTO password) throws Exception {
        return getLoginDetails(getUserEmailId(password.getUserToken()));
    }
    @Override
    public List<UserTransactionEventViewDetailsTO> getTransactionProfitLossView(String userToken, String transactionType,String transactionDate,String betPlaced,Integer eventId,String outcomeName) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_TRANSACTION_PROFIT_LOSS_EVENT_VIEW,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setTimestamp(2, Timestamp.valueOf(transactionDate));
            cs.setString(3, transactionType);
            cs.setTimestamp(4, betPlaced != null ? Timestamp.valueOf(betPlaced): null);
            cs.setInt(5, eventId);
            cs.setString(6, outcomeName!="null" ? outcomeName : null);
            return cs;
        }, (CallableStatement cs) -> extractTransactionProfitLossView(cs.executeQuery()));
    }

    private List<UserTransactionEventViewDetailsTO> extractTransactionProfitLossView(ResultSet rs) throws SQLException {
        UserTransactionDetailsWrapperTO transactionDetails = new UserTransactionDetailsWrapperTO();
        List<UserTransactionEventViewDetailsTO> userTransactionEventViewDetailsList = new ArrayList<>();
        while (rs.next()) {
            UserTransactionEventViewDetailsTO userTransactionDetails = new UserTransactionEventViewDetailsTO();
            userTransactionDetails.setTransactionDate(rs.getString(ColumnLabelConstants.TRANS_DATE));
            userTransactionDetails.setBetId(rs.getLong(ColumnLabelConstants.BET_FAIR_ID));
            userTransactionDetails.setSelectName(rs.getString(ColumnLabelConstants.SELECTION_NAME));
            userTransactionDetails.setOddType(rs.getString(ColumnLabelConstants.ODD_TYPE));
            userTransactionDetails.setOdds(rs.getBigDecimal(ColumnLabelConstants.ODDS));
            userTransactionDetails.setBetStake(rs.getBigDecimal(ColumnLabelConstants.BET_STAKE));
            userTransactionDetails.setBetReturns(rs.getBigDecimal(ColumnLabelConstants.BET_RETURNS));
            userTransactionDetails.setTransactionAmount(rs.getBigDecimal(ColumnLabelConstants.TRANSACTION_AMOUNT));
            userTransactionDetails.setProfitLossAmount(rs.getBigDecimal(ColumnLabelConstants.PROFIT_LOSS));
            userTransactionDetails.setBalance(rs.getBigDecimal(ColumnLabelConstants.BALANCE));
            userTransactionDetails.setCommission(rs.getBigDecimal(ColumnLabelConstants.COMMISSION));
            userTransactionDetails.setBetStatus(rs.getString(ColumnLabelConstants.BET_STATUS));
            userTransactionEventViewDetailsList.add(userTransactionDetails);
        }
        return userTransactionEventViewDetailsList;
    }

    public int getTransactionsCount (String userToken, String transactionType, String fromDate, String toDate){
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_TRANSACTION_COUNT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setString(2, transactionType);
            cs.setString(3, fromDate);
            cs.setString(4, toDate);
            return cs;
        }, (CallableStatement cs) -> extractTransactionsCount(cs.executeQuery()));
    }
    @Override
    public List<UserTransactionDetailsTO> getTransactionDetails(String userToken, String transactionType, String fromDate, String toDate, Integer pageNumber, Integer pageSize,Integer sportId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_TRANSACTION_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setString(2, transactionType);
            cs.setString(3, fromDate);
            cs.setString(4,toDate);
            cs.setInt(5, pageNumber==null ?0 : pageNumber);
            cs.setInt(6, pageSize==null ?0 : pageSize);
            cs.setInt(7, sportId==null ?0 : sportId);
            return cs;
        }, (CallableStatement cs) -> extractTransactionDetails(cs.executeQuery()));
    }

    private int extractTransactionsCount(ResultSet rs) throws SQLException {
        int transactionsCount=0;
        while (rs.next()){
            transactionsCount=rs.getInt(ColumnLabelConstants.TRANSACTION_COUNT);
        }
        return transactionsCount;
    }

    private List<UserTransactionDetailsTO> extractTransactionDetails(ResultSet rs) throws SQLException {
        List<UserTransactionDetailsTO> userTransactionDetailsList = new ArrayList<>();
        while (rs.next()) {
            UserTransactionDetailsTO userTransactionDetailsTO = new UserTransactionDetailsTO();
            userTransactionDetailsTO.setTransactionId(rs.getInt(ColumnLabelConstants.TRANSACTION_ID));
            userTransactionDetailsTO.setTransactionDate(rs.getString(ColumnLabelConstants.TRANS_DATE));
            userTransactionDetailsTO.setDate(rs.getString(ColumnLabelConstants.TRANSACTION_DATE));
            userTransactionDetailsTO.setTime(rs.getString(ColumnLabelConstants.TRANSACTION_TIME));
            userTransactionDetailsTO.setTransactionType(rs.getString(ColumnLabelConstants.TRANSACTION_TYPE));
            userTransactionDetailsTO.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
            userTransactionDetailsTO.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
            userTransactionDetailsTO.setOutcomeName(rs.getString(ColumnLabelConstants.OUTCOME_NAME));
            userTransactionDetailsTO.setProfitLossAmount(rs.getBigDecimal(ColumnLabelConstants.TRANSACTION_AMOUNT)==null ? BigDecimal.ZERO :rs.getBigDecimal(ColumnLabelConstants.TRANSACTION_AMOUNT));
            userTransactionDetailsTO.setBalance(rs.getBigDecimal(ColumnLabelConstants.BALANCE)==null ? BigDecimal.ZERO :rs.getBigDecimal(ColumnLabelConstants.BALANCE));
            userTransactionDetailsTO.setBetPlacedDate(rs.getString(ColumnLabelConstants.BET_PLACED));
            userTransactionDetailsList.add(userTransactionDetailsTO);
        }
        return userTransactionDetailsList;
    }

    @Override
    public List<SportProfitLossDetailsTo> getSportsProftLossDetails(String userToken, String fromDate, String toDate) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_SPORTS_PROFIT_LOSS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userToken);
            cs.setString(2, fromDate);
            cs.setString(3,toDate);
            return cs;
        }, (CallableStatement cs) -> extractSportsProftLossDetails(cs.executeQuery()));
    }

    private List<SportProfitLossDetailsTo> extractSportsProftLossDetails(ResultSet rs) throws SQLException {
        List<SportProfitLossDetailsTo> sportProfitLossDetails = new ArrayList<>();
        while (rs.next()) {
            SportProfitLossDetailsTo sportProfitLossDetailsTO = new SportProfitLossDetailsTo();
            sportProfitLossDetailsTO.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
            sportProfitLossDetailsTO.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            sportProfitLossDetailsTO.setProfitLossAmount(rs.getBigDecimal(ColumnLabelConstants.SPORT_PROFIT_LOSS));
            sportProfitLossDetails.add(sportProfitLossDetailsTO);
        }
        return sportProfitLossDetails;
    }

    @Override
    public UserBetHistoryDetailsWrapperTO extractBetHistoryDetails(BetHistoryFetchDetailsTO betHistoryFetchDetailsTO) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_BET_HISTORY_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, betHistoryFetchDetailsTO.getTransactionType());
            cs.setString(2, betHistoryFetchDetailsTO.getFromDate());
            cs.setString(3, betHistoryFetchDetailsTO.getToDate());
            cs.setString(4, betHistoryFetchDetailsTO.getBetSlipStatus());
            cs.setString(5, betHistoryFetchDetailsTO.getUserToken());
            return cs;
        }, (CallableStatement cs) -> extractUserBetHistoryDetails(cs.executeQuery()));
    }

    private UserBetHistoryDetailsWrapperTO extractUserBetHistoryDetails(ResultSet rs) throws SQLException {
        UserBetHistoryDetailsWrapperTO userBetDetails = new UserBetHistoryDetailsWrapperTO();
        List<UserBetHistoryDetailsTO> userBetDetailsList = new ArrayList<>();
        while (rs.next()) {
            UserBetHistoryDetailsTO userBetHistoryDetailsTO = new UserBetHistoryDetailsTO();
            userBetHistoryDetailsTO.setBetSlipId(rs.getLong(ColumnLabelConstants.BET_FAIR_ID));
            userBetHistoryDetailsTO.setProfileId(rs.getInt(ColumnLabelConstants.PL_ID));
            userBetHistoryDetailsTO.setBetSlipTotalStake(rs.getDouble(ColumnLabelConstants.BETSLIP_TOTAL_STAKE));
            userBetHistoryDetailsTO.setBetPlaced(rs.getString(ColumnLabelConstants.BET_PLACED));
            userBetHistoryDetailsTO.setBetSlipStatus(rs.getString(ColumnLabelConstants.BETSLIP_STATUS));
            userBetHistoryDetailsTO.setBetSlipTotalReturns(rs.getDouble(ColumnLabelConstants.BETSLIP_TOTAL_RETURNS));
            userBetHistoryDetailsTO.setBetStake(rs.getDouble(ColumnLabelConstants.BET_STAKE));
            userBetHistoryDetailsTO.setBetOdds(rs.getDouble(ColumnLabelConstants.BET_ODDS));
            userBetHistoryDetailsTO.setBetOddType(rs.getString(ColumnLabelConstants.BET_ODD_TYPE));
            userBetHistoryDetailsTO.setBetType(rs.getString(ColumnLabelConstants.BET_TYPE));
            userBetHistoryDetailsTO.setbMarket(rs.getString(ColumnLabelConstants.B_MARKET));
            userBetHistoryDetailsTO.setbSelection(rs.getString(ColumnLabelConstants.B_SELECTION));
            userBetHistoryDetailsTO.setTotalMatched(rs.getDouble(ColumnLabelConstants.ODDSLIP_TOTAL_MATCHED));
            userBetHistoryDetailsTO.setProviderMarketGroup(rs.getString(ColumnLabelConstants.PROVIDER_MARKET_GROUP));
            userBetHistoryDetailsTO.setProfitLoss(rs.getString(ColumnLabelConstants.PROFIT_LOSS));
            userBetDetailsList.add(userBetHistoryDetailsTO);
        }
        userBetDetails.setUserBetDetails(userBetDetailsList);
        return userBetDetails;
    }

    private BetfairSessionTO sessionKey(ResultSet rs) throws SQLException {

        BetfairSessionTO betfairSession = new BetfairSessionTO();
        while (rs.next()) {
            betfairSession.setBetFairSession(rs.getString(ColumnLabelConstants.SESSION_KEY));
            betfairSession.setBetFairAppKey(rs.getString(ColumnLabelConstants.APP_KEY));

        }
        return betfairSession;
    }

    private void storeStakeHighlights(Integer stake, Integer isHighlight, String userId) {
        jdbcTemplate.update(UPDATE_USER_SETTINGS, stake, isHighlight, Integer.valueOf(userId));
    }

    private void storeCoinSettingInfo(List<CoinDetailsTO> coinDetailsTOList, String userId) {
        jdbcTemplate.batchUpdate(SAVE_COIN_SETTINGS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                CoinDetailsTO coin = coinDetailsTOList.get(i);
                preparedStatement.setInt(1, coin.getIsSelected());
                preparedStatement.setInt(2, coin.getCoinId());
                preparedStatement.setInt(3, Integer.valueOf(userId));
            }

            @Override
            public int getBatchSize() {
                return coinDetailsTOList.size();
            }
        });
    }

    private String getUserId(String loginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, loginToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String id = null;
            while (rs.next()) {
                id = rs.getString(ColumnLabelConstants.ID);
            }
            return id;
        });
    }

    private String getUserEmailId(String loginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_EMAIL_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, loginToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String userId = null;
            while (rs.next()) {
                userId = rs.getString(ColumnLabelConstants.BM_USER_ID);
            }
            return userId;
        });
    }

    private OneClickCoinWrapperTO populateOneClickDetails(ResultSet executeQuery) throws SQLException {
        OneClickCoinWrapperTO oneClickCoinWrapperTO = new OneClickCoinWrapperTO();
        List<CoinDetailsTO> oneClickCoinDetails = new ArrayList<>();
        while (executeQuery.next()) {
            CoinDetailsTO oneClickCoinDetailsTO = new CoinDetailsTO();
            oneClickCoinDetailsTO.setCoinId(executeQuery.getInt(ColumnLabelConstants.COIN));
            oneClickCoinDetailsTO.setCoinValue(executeQuery.getDouble(ColumnLabelConstants.COIN_VALUE));
            oneClickCoinDetails.add(oneClickCoinDetailsTO);
            Comparator<CoinDetailsTO> coinValueComparator = Comparator.comparing(CoinDetailsTO::getCoinValue);
            oneClickCoinDetails.sort(coinValueComparator);
        }
        oneClickCoinWrapperTO.setOneClickCoinDetailsList(oneClickCoinDetails);
        return oneClickCoinWrapperTO;
    }

    @Override
    public Integer processUserRegistration(UserRegistrationDetailsTO registrationDetails, String encodedPassword, String emailConfirmationToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_PROCESS_USER_REGISTRATION,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, registrationDetails.getUserName());
            cs.setString(2, registrationDetails.getEmailId());
            cs.setString(3, encodedPassword);
            cs.setString(4, registrationDetails.getPhoneNumber());
            cs.setString(5, emailConfirmationToken);
            return cs;
        }, (CallableStatement cs) -> registerAndGetId(cs.executeQuery(), ColumnLabelConstants.PROFILE_ID));
    }

    @Override
    public String processUserLogout(String loginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_LOGOUT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, loginToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String status = null;
            while (rs.next()) {
                status = rs.getString(ColumnLabelConstants.LOGOUT_STATUS);
            }
            return status;
        });
    }

    @Override
    public UniqueUserResultsTO isEmailIdAndUsernameUnique(String emailId, String userName) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_CHECK_FOR_DUPLICATE_USER,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userName);
            cs.setString(2, emailId);
            return cs;
        }, (CallableStatement cs) -> populateUniqueUserResults(cs.executeQuery()));
    }

    private UniqueUserResultsTO populateUniqueUserResults(ResultSet rs) throws SQLException {
        UniqueUserResultsTO uniqueUserResults = new UniqueUserResultsTO();
        while (rs.next()) {
            uniqueUserResults.setUniqueEmailId((rs.getInt(ColumnLabelConstants.IS_UNIQUE_EMAIL_ID) > 0) ? false : true);
            uniqueUserResults.setUniqueUserName((rs.getInt(ColumnLabelConstants.IS_UNIQUE_USER_NAME) > 0) ? false : true);
        }
        return uniqueUserResults;
    }

    @Override
    public UserLoginDetailsTO getLoginDetails(String emailId) throws IOException {
        InetAddress addr = null;
        String publicIp = "";
        addr = InetAddress.getLocalHost();
        URL whatismyip;
        whatismyip = new URL(GEO_IP_AMAZON_AWS_URL);
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        publicIp = bufferedReader.readLine();

        //Hostname

        String ip = addr.getHostAddress();
        String myIp = publicIp;
//        lookUp = new LookupService(
//
//                new File(GEO_LOCATION_FILE_NAME),
//                LookupService.GEOIP_MEMORY_CACHE);

//        String hostAddress = this.getLocation(publicIp).getCity().concat(", ").concat(this.getLocation(publicIp).getCountryName());

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_LOGIN_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, emailId);
            cs.setString(2, LOGIN_SUCCESS);
            cs.setString(3, NO_ERROR);
            cs.setString(4, ip);
            cs.setString(5, myIp);
            cs.setString(6, "test");
            return cs;
        }, (CallableStatement cs) -> this.extractUserDetails(cs.executeQuery()));

    }

    private GeoLocation getLocation(String ipAddress) {
        return GeoLocation.map(lookUp.getLocation(ipAddress));
    }

    @Override
    public UserLoginDetailsTO insertLoginToken(String emailId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GENERATING_LOGIN_TOKEN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, emailId);
            cs.setString(2, UUID.randomUUID().toString());

            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            UserLoginDetailsTO userLoginDetailsTO = new UserLoginDetailsTO();
            while (rs.next()) {
                userLoginDetailsTO.setLoginToken(rs.getString(ColumnLabelConstants.GENERATED_TOKEN));
                userLoginDetailsTO.setUserBalance(rs.getDouble(ColumnLabelConstants.USER_BALANCE));
                userLoginDetailsTO.setExposure(rs.getDouble(ColumnLabelConstants.EXPOSURE));

            }
            return userLoginDetailsTO;
        });

    }

    @Override
    public FooterControlsWrapperTO getFooterControls() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_FOOTER_CONTROLS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> this.extractFooterControlsFromResultSet(cs.executeQuery()));
    }

    @Override
    public FooterControlsWrapperTO getLogoAndLicencingInfoInFCs(FooterControlsWrapperTO footerControlsWrapper) {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_LOGO_AND_LICENCING_INFO,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> this.extractLogoAndLicencingInfoForFooterControls(cs.executeQuery(), footerControlsWrapper));
    }

    @Override
    public BalanceDetailsTO extractUserBalance(String loginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_USER_BALANCE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            cs.setString(1, loginToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            BalanceDetailsTO balanceDetailsTO = new BalanceDetailsTO();
            while (rs.next()) {
                balanceDetailsTO.setUserBalance(rs.getDouble(ColumnLabelConstants.USER_BALANCE));
                balanceDetailsTO.setExposure(rs.getDouble(ColumnLabelConstants.EXPOSURE));
                balanceDetailsTO.setCurrencyId(rs.getInt(ColumnLabelConstants.CURRENCY_ID));
                balanceDetailsTO.setCurrencyShortName(rs.getString(ColumnLabelConstants.CURRENCY_CODE));
                balanceDetailsTO.setTimeZone("");
            }
            return balanceDetailsTO;
        });
    }

    private FooterControlsWrapperTO extractFooterControlsFromResultSet(ResultSet rs) throws SQLException {
        List<FooterControlsTO> commDetailsList = new ArrayList<>();
        List<FooterControlsTO> pointsList = new ArrayList<>();

        FooterControlsWrapperTO footerControlsWrapper = new FooterControlsWrapperTO();
        while (rs.next()) {
            if (rs.getString(ColumnLabelConstants.PARENT_FOOTER_NAME)
                    .equalsIgnoreCase(SportsBookConstants.FOOTER_CONTROLS_COMMUNICATION_DETAILS)) {
                commDetailsList.add(this.setFooterControlDetails(rs));
            } else if (rs.getString(ColumnLabelConstants.PARENT_FOOTER_NAME)
                    .equalsIgnoreCase(SportsBookConstants.FOOTER_CONTROLS_POINTS)) {
                pointsList.add(this.setFooterControlDetails(rs));
            }
        }
        footerControlsWrapper.setCommDetailsList(commDetailsList);
        footerControlsWrapper.setPointsList(pointsList);

        return footerControlsWrapper;
    }

    private FooterControlsWrapperTO extractLogoAndLicencingInfoForFooterControls(
            ResultSet rs, FooterControlsWrapperTO footerControlsWrapper) throws SQLException {
        List<FooterControlsTO> logoInfoList = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString(ColumnLabelConstants.PARENT_FOOTER_NAME)
                    .equalsIgnoreCase(SportsBookConstants.FOOTER_CONTROLS_LOGO_AND_LICENCING_INFORMATION)) {
                logoInfoList.add(this.setFooterControlDetails(rs));
            }
            footerControlsWrapper.setCompanyInfoList(logoInfoList);
        }
        return footerControlsWrapper;
    }

    private FooterControlsTO setFooterControlDetails(ResultSet rs) throws SQLException {
        FooterControlsTO footerControls = new FooterControlsTO();
        footerControls.setFooterId(rs.getInt(ColumnLabelConstants.FOOTER_ID));
        footerControls.setFooterName(rs.getString(ColumnLabelConstants.FOOTER_NAME));
        if (this.isColumnExists(rs, ColumnLabelConstants.FOOTER_URL)) {
            footerControls.setFooterUrl(rs.getString(ColumnLabelConstants.FOOTER_URL));
        }
        if (this.isColumnExists(rs, ColumnLabelConstants.FOOTER_LOGO_URL)) {
            footerControls.setLogoUrl(rs.getString(ColumnLabelConstants.FOOTER_LOGO_URL));
        }
        if (this.isColumnExists(rs, ColumnLabelConstants.FOOTER_EXCHANGE_URL)) {
            footerControls.setSkyExchangeUrl(rs.getString(ColumnLabelConstants.FOOTER_EXCHANGE_URL));
        }
        footerControls.setFooterText(rs.getString(ColumnLabelConstants.FOOTER_TEXT));
        return footerControls;
    }

    private boolean isColumnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private UserLoginDetailsTO extractUserDetails(ResultSet rs) throws SQLException {
        UserLoginDetailsTO userLoginDetails = new UserLoginDetailsTO();
        while (rs.next()) {
            userLoginDetails.setEmailId(rs.getString(ColumnLabelConstants.USER_EMAIL_ID));
            userLoginDetails.setPassword(rs.getString(ColumnLabelConstants.USER_PWD));
            userLoginDetails.setUserName(rs.getString(ColumnLabelConstants.USER_NAME));
            userLoginDetails.setUserId(rs.getInt(ColumnLabelConstants.USER_ID));
            userLoginDetails.setRoleId(rs.getInt(ColumnLabelConstants.ROLE_ID));
            userLoginDetails.setStatusId(rs.getInt(ColumnLabelConstants.STATUS_ID));
            userLoginDetails.setCurrencyId(rs.getInt(ColumnLabelConstants.CURRENCY_ID));
            userLoginDetails.setCurrencyCode(rs.getString(ColumnLabelConstants.CURRENCY_CODE));
            userLoginDetails.setTimeZone(rs.getString(ColumnLabelConstants.USER_TIME_ZONE));
            userLoginDetails.setSessionToken(rs.getString(ColumnLabelConstants.SESSION_TOKEN));

        }

        return userLoginDetails;
    }

    private Integer registerAndGetId(ResultSet rs, String idType) throws SQLException {
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(idType);
        }
        return id;
    }

    @Override
    public SportHierarchyDetailsTO getCommonEventDetails(Integer sportId) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_COMMON_EVENT_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, sportId);
            return cs;
        }, (CallableStatement cs) -> this.extractCommonEventDetails(cs.executeQuery()));
    }

    @Override
    public String getEmailVerifiedStatus(String token) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement callableStatement = connection.prepareCall(GET_EMAIL_VERIFICATION_STATUS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            callableStatement.setString(1, token);
            return callableStatement;
        }, (CallableStatement cs) -> this.extractEmailVerificationStatus(cs.executeQuery()));
    }

    @Override
    public Integer getEmailVerificationStatus(String emailId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement callableStatement = connection.prepareCall(GET_EMAIL_VERIFIED_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            callableStatement.setString(1, emailId);
            return callableStatement;
        }, (CallableStatement cs) -> this.extractEmailVerificationDetails(cs.executeQuery()));
    }

    @Override
    public int getEmailIdCount(String emailId) {
            return jdbcTemplate.execute(connection -> {
            CallableStatement callableStatement = connection.prepareCall(GET_EMAIL_ID_COUNT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            callableStatement.setString(1, emailId.toLowerCase());
            callableStatement.setString(2, emailId.toLowerCase());
            return callableStatement;
        }, (CallableStatement cs) -> this.getEmailcount(cs.executeQuery()));

    }

    @Override
    public BetfairSessionTO getBetfairSessionKey() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_SESSION_KEY,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> sessionKey(cs.executeQuery()));
    }

    @Override
    public void updateGeneratedTokenConfirmation(String emailId, String emailConfirmationToken) {
        jdbcTemplate.update(UPDATE_GENERATED_TOKEN, emailConfirmationToken, emailId);
    }

    @Override
    public String validateEmail(String emailConfirmationToken) {

        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(GET_EMAIL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, emailConfirmationToken);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            String emailId = null;
            while (rs.next()) {
                emailId = rs.getString(ColumnLabelConstants.BM_USER_ID);
            }
            if(emailId!=null){
                try {
                    jdbcTemplate.update("update bmeusers set forget_pwd_confirm_token=? where userid=?",null,emailId);
                }catch (Exception e){
                    System.out.println("forget_pwd_confirm_token not updated in bmeusers table");
                }
            }
            return emailId;
        });
    }
    private Integer getEmailcount(ResultSet rs) throws SQLException {
        Integer emailVerified = null;
        while (rs.next()) {
            emailVerified = rs.getInt(ColumnLabelConstants.EMAIL_COUNT);
        }
         return emailVerified;
    }

    private Integer extractEmailVerificationDetails(ResultSet rs) throws SQLException {
        Integer emailVerified = null;
        while (rs.next()) {
            emailVerified = rs.getInt(EMAIL_VERIFIED);
        }
        return emailVerified;
    }

    private String extractEmailVerificationStatus(ResultSet rs) throws SQLException {
        String emailStatus = null;
        while (rs.next()) {
            emailStatus = rs.getString(EMAIL_CONFIRMATION_STATUS);
        }
        return emailStatus;

    }

    private SportHierarchyDetailsTO extractCommonEventDetails(ResultSet rs) throws SQLException {
        SportHierarchyDetailsTO sportDetails = new SportHierarchyDetailsTO();
        LeagueDetailsTO leagueDetails = null;
        EventDetailsTO eventDetails = null;
        int lastLeagueId = 0;
        int lastEventId = 0;
        while (rs.next()) {
            sportDetails.setSportId(rs.getInt(ColumnLabelConstants.SPORT_ID));
            sportDetails.setSportName(rs.getString(ColumnLabelConstants.SPORT_NAME));
            int leagueId = rs.getInt(ColumnLabelConstants.LEAGUE_ID);
            if (leagueId == lastLeagueId) {
                assert leagueDetails != null;
                leagueDetails.setLeagueId(rs.getInt(ColumnLabelConstants.LEAGUE_ID));
                leagueDetails.setLeagueName(rs.getString(ColumnLabelConstants.LEAGUE_NAME));
                int eventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                if (eventId == lastEventId) {
                    assert eventDetails != null;
                    eventDetails.setEventId(rs.getInt(ColumnLabelConstants.EVENT_ID));
                    eventDetails.setEventName(rs.getString(ColumnLabelConstants.EVENT_NAME));
                    eventDetails.setEventKickOff(rs.getString(ColumnLabelConstants.EVENT_KICKOFF));
                    FancyMarketDetailsWrapperTO fancyDetailsWrapper = new FancyMarketDetailsWrapperTO();
                    fancyDetailsWrapper.setFancyBetDetailsList(this.extractCommonEventDetailsFromResultSet(rs));
                    eventDetails.setFancyMarketDetailsWrapper(fancyDetailsWrapper);
                } else {
                    lastEventId = rs.getInt(ColumnLabelConstants.EVENT_ID);
                    rs.previous();
                    eventDetails = new EventDetailsTO();
                    leagueDetails.getEventsList().add(eventDetails);
                }
            } else {
                lastLeagueId = rs.getInt(ColumnLabelConstants.LEAGUE_ID);
                rs.previous();
                leagueDetails = new LeagueDetailsTO();
                sportDetails.getLeagueDetailsList().add(leagueDetails);
            }
        }
        return sportDetails;
    }
}