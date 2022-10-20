package com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.AdminTokenValidateTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dao.AdminDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.dto.AgentMarketsInput;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.adminagent.utils.AdminAgentConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.dto.AgentTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

import static com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants.DATE_PATTERN;

@Service
public class AdminDaoImpl  implements AdminDao {

    private static final String PROC_ADD_AGENT_MARKETS="select * from add_agent_markets(?,?,?,?,?,?,?,?)";
    private static final String PROC_ADD_AGENT_MARKET_TEAMS="select * from add_agent_market_teams(?)";
    private static final String PROC_GET_AGENTS_DOWNLINE_LIST="select distinct agentid,agent_name,available,status from get_agent_admin_downline_list(?) order by agent_name";
    private static final String PROC_GET_MARKET_GROUP_SPORT_WISE_EVENT_LIST="select * from get_market_selection_wise_events(?,?)";
    private static final String PROC_GET_SPECIAL_MARKET_TEAM_LIST="select id as team_id,name as team_name,box_count from special_market_teams";
    private static final String PROC_GET_AGENTS_MARKET_ODDS="select * from get_agent_market_odds(?,?,?)";
    private static final String PROC_GET_AGENTS_MARKET_LIST="select * from get_agent_market_list(?,?)";
    private static final String PROC_ADD_AGENT_MARKET_ODDS="select * from save_agent_market_odds(?,?,?)";
    private static final String PROC_UPDATE_AGENT_MARKET_SUSPEND="select * from save_agent_market_suspend(?,?,?,?)";
    private static final String PROC_GET_ALL_AGENT_MARKETS = "select * from get_agent_market_all_new() order by event_id,provideroutcome";
    private static final String PROC_GET_ALL_AGENT_FANCY_MARKETS = "select * from get_agent_fancy_market_all_new() order by event_id";
    private static final String GET_AGENT_PASSWORD = "select userpwd from  bmeusers where userid='";
    private static final String GET_AGENT_EMAIL = "select up.profileemail from userprofile up join bmeusers bm on bm.profileid = up.profileid where bm.userid='";
    private static final String QUERY_GET_MIN_STAKE = "select minstake from min_max_stake order by minstake";
    private static final String QUERY_GET_MAX_STAKE = "select maxstake from min_max_stake order by maxstake";
    private static final String PROC_GET_AGENTS_FANCY_MARKET_ODDS = "select distinct * from get_agent_fancy_market_odds(?,?,?)";
    private static final String GET_AGENT_BET_DELAY_MARKETS = "select distinct am.market_name,am.sport_id,am.sport_name,am.event_id,am.event_name,am.market_selection,am.is_market_suspend,\n" +
            "fm.fancyoutcome,fm.backodds fancyodds,am.sub_market_name,fm.betdelay,\n" +
            "            am.is_fancy,fm.minstake,fm.maxstake  from admin_agent ag\n" +
            "            join agent_markets am on ag.agent_id = am.agent_id \n" +
            "            join fancymarkets fm on fm.fancymarketname = am.market_name\n" +
            "            where ag.agent_name=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Integer addAgent(AgentTO agent) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.ADDAGENT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String encodedPassword = passwordEncoder.encode(agent.getPassword());
            cs.setString(1, agent.getAgentName());
            cs.setString(2, agent.getPassword());
            cs.setString(3, agent.getRemarks());
            cs.setInt(4, agent.getInactiveSessionSuspension());
            cs.setString(5, agent.getBetlistLive());
            cs.setString(6, agent.getBookPosition());
            cs.setString(7, agent.getResultDeclation());
            cs.setString(8,agent.getToken());
            cs.setString(9,encodedPassword);
            return cs;
        }, (CallableStatement cs) -> addAgentAndgetId(cs.executeQuery()));
    }

    @Override
    public Integer getAgentNamecount(String aganetName) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.GET_AGENTNAME_COUNT,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, aganetName);

            return cs;
        }, (CallableStatement cs) -> getAgentCount(cs.executeQuery()));
    }

    private Integer addAgentAndgetId(ResultSet rs) throws SQLException {
        int agentId = 0;
        while (rs.next()) {
            agentId = rs.getInt(AdminAgentConstants.AGENTID);
        }
        return agentId;
    }

    @Override
    public Integer addAgentMarket(AgentMarketsInput agentMarketDetails) {
       return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_ADD_AGENT_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentMarketDetails.getAgentId());
            cs.setInt(2, agentMarketDetails.getSportId()==null?0:agentMarketDetails.getSportId());
            cs.setInt(3, agentMarketDetails.getEventId()==null?0:agentMarketDetails.getEventId());
            cs.setString(4, agentMarketDetails.getMarketSelection());
            cs.setString(5, agentMarketDetails.getSportName());
            cs.setString(6, agentMarketDetails.getEventName());
            cs.setString(7, agentMarketDetails.getMarketName());
            cs.setString(8, agentMarketDetails.getSubMarketName());
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            int agentMarketId = 0;
            while (rs.next()) {
                agentMarketId = rs.getInt(AdminAgentConstants.ADD_AGENT_MARKET);
            }
            return agentMarketId;
        });
    }

    @Override
    public void addAgentMarketTeams(String bulkdataInsert) {
        jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(PROC_ADD_AGENT_MARKET_TEAMS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, bulkdataInsert);
            return cs;
        }, (CallableStatement cs) -> (cs.executeQuery()));
    }

    @Override
    public AdminTokenValidateTO validateToke(String token) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.VALIDATE_TOKEN,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, token);

            return cs;
        }, (CallableStatement cs) -> validateAdminToken(cs.executeQuery()));

    }

    @Override
    public List<Integer> getMinStakeList() {
        return jdbcTemplate.queryForList(QUERY_GET_MIN_STAKE,Integer.class);
    }

    @Override
    public List<Integer> getMaxStakeList() {
        return jdbcTemplate.queryForList(QUERY_GET_MAX_STAKE,Integer.class);
    }

    @Override
    public List<AgentFancyMarketTO> getAgentFancyMarkets(Integer agentMarketId, Integer sportId, Integer eventId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_AGENTS_FANCY_MARKET_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentMarketId);
            cs.setInt(2, sportId);
            cs.setInt(3, eventId);
            return cs;
        }, (CallableStatement cs) -> this.extractAgentFancyMarketOdds(cs.executeQuery()));
    }

    @Override
    public String updateMarket(UpdateFancyOddsTO updateFancyOddsTO) {
        String SQL = "update fancymarkets set backodds=? where fancyoutcome=? and fancymarketname=?";
        String SQL1 = "update odds set backodds=? where fancyoutcome like '%"+updateFancyOddsTO.getOutcome()+"%' and fancymarketname=?";
        try {
            jdbcTemplate.update(SQL, updateFancyOddsTO.getOdds(), updateFancyOddsTO.getOutcome(), updateFancyOddsTO.getFancyMarketName());
            jdbcTemplate.update(SQL1, updateFancyOddsTO.getOdds(), updateFancyOddsTO.getFancyMarketName());
            return "Success";
        }catch (Exception e){
            return "Fail";
        }
    }

    @Override
    public String updateAgentMarkets(AgentUpdateOddsTO updateOddsTO) {
        updateOddsTO.getOutcomes().forEach(bookmakerOddsTO ->{
            if(bookmakerOddsTO.getId().equalsIgnoreCase("1")){
                String SQL = "update agent_market_bookmaker_odds set backodds=? , layodds=? where agent_market_id=? and provideroutcome='1'";
                jdbcTemplate.update(SQL, bookmakerOddsTO.getBackOdds(), bookmakerOddsTO.getLayOdds(), updateOddsTO.getAgentId());
            }else if(bookmakerOddsTO.getId().equalsIgnoreCase("x")){
                String SQL = "update agent_market_bookmaker_odds set backodds=? , layodds=? where agent_market_id=? and provideroutcome='X'";
                jdbcTemplate.update(SQL, bookmakerOddsTO.getBackOdds(), bookmakerOddsTO.getLayOdds(), updateOddsTO.getAgentId());
            }else if(bookmakerOddsTO.getId().equalsIgnoreCase("2")){
                String SQL = "update agent_market_bookmaker_odds set backodds=? , layodds=? where agent_market_id=? and provideroutcome='2'";
                jdbcTemplate.update(SQL, bookmakerOddsTO.getBackOdds(), bookmakerOddsTO.getLayOdds(), updateOddsTO.getAgentId());
            }
        });
        return "Success";
    }

    @Override
    public List<AgentMarketsOutputTO> getAgentBetdelayMarkets(String agentName) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_AGENT_BET_DELAY_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateAgentBetDelayMarkets(cs.executeQuery()));
    }

    @Override
    public List<AgentMarketsOutputTO> markets(String agentName) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(GET_AGENT_BET_DELAY_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateMarkets(cs.executeQuery()));
    }

    private List<AgentMarketsOutputTO> populateMarkets(ResultSet rs) throws SQLException {
        AgentMarketsOutputTO agentMarketsOutputTO = new AgentMarketsOutputTO();
        List<AgentMarketsOutputTO> agentMarketsOutputTOS = new ArrayList<>();
        String lastMarketName = "";
        String marketName = "";
        while (rs.next()) {
            marketName = rs.getString(AdminAgentConstants.MARKET_NAME);
            if(!lastMarketName.equalsIgnoreCase(marketName)) {
                agentMarketsOutputTO = new AgentMarketsOutputTO();
                agentMarketsOutputTO.setEventId(rs.getInt(AdminAgentConstants.EVENT_ID));
                agentMarketsOutputTO.setEventName(rs.getString(AdminAgentConstants.EVENT_NAME));
                agentMarketsOutputTO.setMarketName(rs.getString(AdminAgentConstants.MARKET_NAME));
                agentMarketsOutputTO.setMinStake((int) rs.getDouble(AdminAgentConstants.MINSTAKE));
                agentMarketsOutputTO.setMaxStake((int) rs.getDouble(AdminAgentConstants.MAXSTAKE));
                agentMarketsOutputTOS.add(agentMarketsOutputTO);
            }
            lastMarketName = rs.getString(AdminAgentConstants.MARKET_NAME);
        }
        return agentMarketsOutputTOS;
    }

    @Override
    public String saveBetDelay(AgentTO agentDetails) {
        try {
            String SQL = "update fancymarkets set betdelay=? where fancymarketname=?";
            String SQL1 = "update bmevents set betdelay=? where eventid=?";
            if(agentDetails.getEventId() != 0){
                jdbcTemplate.update(SQL1, agentDetails.getBetDelay(), agentDetails.getEventId());
                return "Successfully updated";
            }
            jdbcTemplate.update(SQL, agentDetails.getBetDelay(), agentDetails.getMarketName());
        }catch (Exception e){
            return e.getMessage();
        }
        return "Successfully updated";
    }


    @Override
    public String saveMarketStake(MinMaxStakeTO minMaxStakeTO) {
        try {
            if(minMaxStakeTO.getIsFancy() != null) {
                if (minMaxStakeTO.getIsFancy().equalsIgnoreCase("true")) {
                    String SQL2 = "update odds set minstake=?, maxstake=? where fancymarketname=? and providerid=3";
                    jdbcTemplate.update(SQL2, minMaxStakeTO.getMinStake(), minMaxStakeTO.getMaxStake(), minMaxStakeTO.getMarketName());
                    return "Successfully updated";
                }
            }

            String SQL = "update fancymarkets set minstake=?, maxstake=? where fancymarketname=?";

            String SQL1 = "update odds set minstake=?, maxstake=? where eventid=? and providerid=1";

            jdbcTemplate.update(SQL1, minMaxStakeTO.getMinStake(), minMaxStakeTO.getMaxStake(), minMaxStakeTO.getEventId());
            jdbcTemplate.update(SQL, minMaxStakeTO.getMinStake(), minMaxStakeTO.getMaxStake(), minMaxStakeTO.getMarketName());
        }catch (Exception e){
            return e.getMessage();
        }
        return "Successfully updated";
    }

    @Override
    public void addSpecialEvent(AgentMarketsInput agentMarketDetails) {
        //String SQL = "insert into bmevents where "
    }


    @Override
    public void updateAgentBookmakerOdds(AgentBookmakerMarketOddsInputTO agentBookmakerMarketOddsDetails) {
        for(BookmakerMarketOddsInput bookmakerMarketOddsInput : agentBookmakerMarketOddsDetails.getBookmakerMarketOddsInputList()){
            if(bookmakerMarketOddsInput.getOldBackOdds() == null){
                bookmakerMarketOddsInput.setOldBackOdds(0.0);
            }if(bookmakerMarketOddsInput.getOldLayOdds() == null){
                bookmakerMarketOddsInput.setOldLayOdds(0.0);
            }

            if(bookmakerMarketOddsInput.getSportId() == 1){
                if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("1")) {
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=27";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=27";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }else if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("2")){
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=29";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=29";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }else if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("X")){
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=28";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=28";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }
            }else if(bookmakerMarketOddsInput.getSportId() == 2){
                if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("1")) {
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=32";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=32";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }else if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("2")){
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=33";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=33";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }
            }else if(bookmakerMarketOddsInput.getSportId() == 4){
                if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("1")) {
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=30";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=30";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }else if(bookmakerMarketOddsInput.getSelectionName().equalsIgnoreCase("2")){
                    String SQL_BACK_ODDS = "update odds set backodds=? where eventid=? and providerid=1 and odd_dictionary_id=31";
                    String SQL_LAY_ODDS = "update odds set layodds=? where eventid=? and providerid=1 and odd_dictionary_id=31";
                    jdbcTemplate.update(SQL_BACK_ODDS, bookmakerMarketOddsInput.getBookMakerBackOdds(), bookmakerMarketOddsInput.getEventid());
                    jdbcTemplate.update(SQL_LAY_ODDS, bookmakerMarketOddsInput.getBookMakerLayOdds(), bookmakerMarketOddsInput.getEventid());
                }
            }
        }
    }

    @Override
    public Boolean updateMinMaxStake(MinMaxStakeTO minMaxStakeTO) {
        try {
            String SQL_UPDATE_MIN_STAKE = "update min_max_stake set minstake=? where minstake=?";
            String SQL_UPDATE_MAX_STAKE = "update min_max_stake set maxstake=? where maxstake=?";
            jdbcTemplate.update(SQL_UPDATE_MIN_STAKE, minMaxStakeTO.getMinStake(), minMaxStakeTO.getOldMinStake());
            jdbcTemplate.update(SQL_UPDATE_MAX_STAKE, minMaxStakeTO.getMaxStake(), minMaxStakeTO.getOldMaxStake());
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public void addAgentBookMakerMatchOdds(List<AgentBookMakerTO> agentBookMakerTOS) {

        jdbcTemplate.batchUpdate("insert into odds(eventid,odd_dictionary_id,backodds,marketgroupid,providerid,minstake,maxstake,status) values(?,?,?,?,?,1000,100000,'SUSPENDED')", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                AgentBookMakerTO agentBookMakerTO = agentBookMakerTOS.get(i);
                preparedStatement.setInt(1, agentBookMakerTO.getEventId());
                preparedStatement.setInt(2, agentBookMakerTO.getOddDictionaryId());
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(agentBookMakerTO.getBackOdds()));
                preparedStatement.setInt(4, 1);
                preparedStatement.setInt(5, 1);
            }
            @Override
            public int getBatchSize() {
                return agentBookMakerTOS.size();
            }
        });

        jdbcTemplate.batchUpdate("insert into odds(eventid,odd_dictionary_id,layodds,marketgroupid,providerid,minstake,maxstake,status) values(?,?,?,?,?,1000,100000,'SUSPENDED')", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                AgentBookMakerTO agentBookMakerTO = agentBookMakerTOS.get(i);
                preparedStatement.setInt(1, agentBookMakerTO.getEventId());
                preparedStatement.setInt(2, agentBookMakerTO.getOddDictionaryId());
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(agentBookMakerTO.getLayOdds()));
                preparedStatement.setInt(4, 1);
                preparedStatement.setInt(5, 1);
            }
            @Override
            public int getBatchSize() {
                return agentBookMakerTOS.size();
            }
        });
    }

    @Override
    public void addFancyMarket(AgentMarketsInput agentMarketDetails) {
        String sql = "insert into fancymarkets(fancymarketname, fancyoutcome, eventid, backodds, layodds, minstake,maxstake,betdelay) values(?,?,?,?,?,?,?,1)";
        jdbcTemplate.update(sql, new Object[] { agentMarketDetails.getMarketName(), "YES", agentMarketDetails.getEventId(), 0.0, 0.0,  1000, 100000});
        String sql1 = "insert into fancymarkets(fancymarketname, fancyoutcome, eventid, backodds, layodds, minstake,maxstake,betdelay) values(?,?,?,?,?,?,?,1)";
        jdbcTemplate.update(sql1, new Object[] { agentMarketDetails.getMarketName(), "NO", agentMarketDetails.getEventId(), 0.0, 100,1000, 100000});

        String SQLFANCY = "insert into odds(eventid, fancymarketname,fancyoutcome,backodds, minstake,maxstake,marketgroupid,providerid,status) values(?,?,?,?,?,?,?,?,'SUSPENDED')";
        jdbcTemplate.update(SQLFANCY,  new Object[] { agentMarketDetails.getEventId(), agentMarketDetails.getMarketName(), "YES", 1000, 10000, 100000, 25, 3});

        String SQLFANCY1 = "insert into odds(eventid, fancymarketname,fancyoutcome,backodds, minstake,maxstake,marketgroupid,providerid,status) values(?,?,?,?,?,?,?,?,'SUSPENDED')";
        jdbcTemplate.update(SQLFANCY1,  new Object[] { agentMarketDetails.getEventId(), agentMarketDetails.getMarketName(), "NO", 1000, 10000, 100000, 25, 3});

    }

    @Override
    public List<AgentMarketsOutputTO> getAgentMarketsAll() {
        List<AgentMarketsOutputTO> agentMarketsOutputTOS = new ArrayList<>();

        agentMarketsOutputTOS =  jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_ALL_AGENT_MARKETS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> populateAgentMarkets(cs.executeQuery()));
        List<AgentMarketsOutputTO> agentMarketsOutputTOS1 = new ArrayList<>();

        agentMarketsOutputTOS1 = jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_ALL_AGENT_FANCY_MARKETS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> populateAgentFancyMarkets(cs.executeQuery()));

        for( int i=0 ; i<= agentMarketsOutputTOS1.size()-1; i++){
            agentMarketsOutputTOS.add(agentMarketsOutputTOS1.get(i));
        }
        return agentMarketsOutputTOS;

    }

    private List<AgentMarketsOutputTO> populateAgentFancyMarkets(ResultSet executeQuery) throws SQLException {
        List<AgentMarketsOutputTO> marketsOutputList = new ArrayList<>();
        Integer eventId = 0;String marketName = "";
        AgentMarketsOutputTO agentMarketsOutputTO = new AgentMarketsOutputTO();

        AgentFancyOutcomeTO fancyOutcomeTO = new AgentFancyOutcomeTO();

        List<AgentFancyOutcomeTO> agentFancyOutcomeTOS = new ArrayList<>();;
        List<String> outcome = new ArrayList<>();
        String out = "";
        BigDecimal b = new BigDecimal(0.0);
        while (executeQuery.next()){


            if(eventId == executeQuery.getInt("event_id") && !marketName.equalsIgnoreCase(executeQuery.getString("market_name"))){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
                marketsOutputList.add(agentMarketsOutputTO);
                agentFancyOutcomeTOS = new ArrayList<>();
                outcome = new ArrayList<>();
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }


            if(eventId != executeQuery.getInt("event_id") && !marketName.equalsIgnoreCase(executeQuery.getString("market_name")) && !marketName.equalsIgnoreCase("")){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
                marketsOutputList.add(agentMarketsOutputTO);
                agentFancyOutcomeTOS = new ArrayList<>();
                outcome = new ArrayList<>();
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }else if(eventId != executeQuery.getInt("event_id") && eventId != 0){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
                marketsOutputList.add(agentMarketsOutputTO);
                agentFancyOutcomeTOS = new ArrayList<>();
                outcome = new ArrayList<>();
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }
            marketName = executeQuery.getString("market_name");
            agentMarketsOutputTO = new AgentMarketsOutputTO();
            agentMarketsOutputTO.setSportId(executeQuery.getInt("sport_id"));
            agentMarketsOutputTO.setSportName(executeQuery.getString("sport_name"));
            agentMarketsOutputTO.setAgentName(executeQuery.getString("agentname"));
            agentMarketsOutputTO.setMarketResult(executeQuery.getString("marketresult") == null ? "OPEN" : executeQuery.getString("marketresult"));
            agentMarketsOutputTO.setMarketStatus(executeQuery.getBoolean("marketstatus"));
            agentMarketsOutputTO.setEventId(executeQuery.getInt("event_id"));
            agentMarketsOutputTO.setEventName(executeQuery.getString("event_name"));
            agentMarketsOutputTO.setMarketName(executeQuery.getString("market_name"));
            agentMarketsOutputTO.setMarketSelection(executeQuery.getString("market_selection"));
            agentMarketsOutputTO.setMinStake((int) executeQuery.getDouble("minstake"));
            agentMarketsOutputTO.setMaxStake((int) executeQuery.getDouble("maxstake"));
            agentMarketsOutputTO.setBetDelay(executeQuery.getInt("bet_delay"));

            String suspend = executeQuery.getString("is_market_suspend");

            if(suspend != null) {
                if (suspend.equalsIgnoreCase("SUSPENDED")) {
                    agentMarketsOutputTO.setMarketSuspended(true);
                } else {
                    agentMarketsOutputTO.setMarketSuspended(false);
                }
            }else {
                agentMarketsOutputTO.setMarketSuspended(false);
            }

            //agentMarketsOutputTO.setMarketSuspended(executeQuery.getBoolean("is_market_suspend"));
            agentMarketsOutputTO.setMarketType((executeQuery.getString("sub_market_name")));
            agentMarketsOutputTO.setAgentMarketId(executeQuery.getInt("id"));
            agentMarketsOutputTO.setSelectionName(executeQuery.getString("market_selection"));


            String providerOutcome = executeQuery.getString("provideroutcome");

            if(!out.equalsIgnoreCase("") && !providerOutcome.equalsIgnoreCase(out) && eventId == executeQuery.getInt("event_id")){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }
            fancyOutcomeTO.setOutcome(executeQuery.getString("provideroutcome"));
            fancyOutcomeTO.setBookMakerBackOdds(executeQuery.getDouble("backodds"));
            fancyOutcomeTO.setOldBackOdds(executeQuery.getDouble("backodds"));
            fancyOutcomeTO.setBackOdds(executeQuery.getDouble("backodds"));
            fancyOutcomeTO.setOldLayOdds(executeQuery.getDouble("layodds"));
            fancyOutcomeTO.setLayOdds(executeQuery.getDouble("layodds"));
            out = executeQuery.getString("provideroutcome");
            eventId = executeQuery.getInt("event_id");

        }
        if(agentFancyOutcomeTOS.size() >0) {
            agentFancyOutcomeTOS.add(fancyOutcomeTO);
            agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
            try {
                if (!agentMarketsOutputTO.getEventName().equalsIgnoreCase("null")) {
                    marketsOutputList.add(agentMarketsOutputTO);
                }
            }catch (Exception e){
                return marketsOutputList;
            }
        }
        return marketsOutputList;
    }


    private List<AgentMarketsOutputTO> populateAgentBetDelayMarkets(ResultSet rs) throws SQLException {
        AgentMarketsOutputTO agentMarketsOutputTO = new AgentMarketsOutputTO();
        List<AgentMarketsOutputTO> agentMarketsOutputTOS = new ArrayList<>();
        String lastMarketName = "";
        String marketName = "";
        while (rs.next()) {
            marketName = rs.getString(AdminAgentConstants.MARKET_NAME);
            if(!lastMarketName.equalsIgnoreCase(marketName)) {
                agentMarketsOutputTO = new AgentMarketsOutputTO();
                agentMarketsOutputTO.setEventId(rs.getInt(AdminAgentConstants.EVENT_ID));
                agentMarketsOutputTO.setEventName(rs.getString(AdminAgentConstants.EVENT_NAME));
                agentMarketsOutputTO.setMarketName(rs.getString(AdminAgentConstants.MARKET_NAME));
                agentMarketsOutputTO.setBetDelay(rs.getInt(AdminAgentConstants.BETDELAY));
                agentMarketsOutputTOS.add(agentMarketsOutputTO);
            }
            lastMarketName = rs.getString(AdminAgentConstants.MARKET_NAME);
        }
        return agentMarketsOutputTOS;
    }

    private List<AgentFancyMarketTO> extractAgentFancyMarketOdds(ResultSet rs) throws SQLException {
        String marketName = "";
        List<AgentFancyMarketTO> agentsMarketOddsList = new ArrayList<>();
        Set<AgentFancyOutcomeTO> agentFancyOutcomeTOS = new HashSet<>();
        AgentFancyMarketTO agentFancyMarketTO = new AgentFancyMarketTO();
        AgentFancyOutcomeTO fancyOutcomeTO = new AgentFancyOutcomeTO();
        while (rs.next()) {
            agentFancyMarketTO = new AgentFancyMarketTO();
            fancyOutcomeTO = new AgentFancyOutcomeTO();
            marketName = rs.getString("market_name");
            if(!rs.getString("market_name").equalsIgnoreCase(marketName) || marketName.equalsIgnoreCase("")){
                agentsMarketOddsList.add(agentFancyMarketTO);
            }
            agentFancyMarketTO.setFancyMarketName(rs.getString("market_name"));
            agentFancyMarketTO.setEventId(rs.getInt("event_id"));
            agentFancyMarketTO.setEventName(rs.getString("event_name"));
            fancyOutcomeTO.setOutcome(rs.getString("provider_outcome"));
            fancyOutcomeTO.setBookMakerBackOdds(rs.getDouble("back_odds"));
            fancyOutcomeTO.setMinStake(rs.getDouble("min_stake"));
            fancyOutcomeTO.setMaxStake(rs.getDouble("max_stake"));
            agentFancyOutcomeTOS.add(fancyOutcomeTO);
            agentFancyMarketTO.setOutcomeList(agentFancyOutcomeTOS);
        }
        agentsMarketOddsList.add(agentFancyMarketTO);
        return agentsMarketOddsList;
    }

    private AdminTokenValidateTO validateAdminToken(ResultSet rs) throws SQLException {
        AdminTokenValidateTO adminTokenValidateTO=new AdminTokenValidateTO();
        while (rs.next()) {
            adminTokenValidateTO.setLoginToken(rs.getString(AdminAgentConstants.USER_LOGIN_TOKEN));
            adminTokenValidateTO.setRoleId(rs.getInt(AdminAgentConstants.ROLE_ID));
        }
        return adminTokenValidateTO;
    }

    private Integer getAgentCount(ResultSet rs) throws SQLException {
        int agentCount = 0;
        while (rs.next()) {
            agentCount = rs.getInt(AdminAgentConstants.AGENT_NAME_COUNT);
        }
        return agentCount;
    }

    @Override
    public List<AgentsMarketsOutputTO> getAgentsDownLineList(String userLoginToken) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_AGENTS_DOWNLINE_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            return cs;
        }, (CallableStatement cs) -> this.extractAgentsDownLineList(cs.executeQuery()));
    }

    private List<AgentsMarketsOutputTO> extractAgentsDownLineList(ResultSet rs) throws SQLException {
        List<AgentsMarketsOutputTO> agentsMarketList = new ArrayList<>();
        AgentsMarketsOutputTO agentMarketDetails;
        while (rs.next()) {
            agentMarketDetails = new AgentsMarketsOutputTO();
            //agentMarketDetails.setMarketResult(rs.getString("marketresult"));
            agentMarketDetails.setAgentId(rs.getInt(AdminAgentConstants.AGENTID));
            agentMarketDetails.setAgentName(rs.getString(AdminAgentConstants.AGENT_NAME));
            //agentMarketDetails.setSportName(rs.getString(AdminAgentConstants.SPORT_NAME));
            //agentMarketDetails.setEventName(rs.getString(AdminAgentConstants.EVENT_NAME));
            //agentMarketDetails.setMarketName(rs.getString(AdminAgentConstants.MARKET_NAME));
            //agentMarketDetails.setMarketSelection(rs.getString(AdminAgentConstants.MARKET_SELECTION_NAME));

            Integer available = rs.getInt(AdminAgentConstants.AVAILABLE);

            if(available != null){
                if(available == 1){
                    agentMarketDetails.setAvailable("Online");
                }else{
                    agentMarketDetails.setAvailable("Offline");
                }
            }else{
                agentMarketDetails.setAvailable("Offline");
            }

            //agentMarketDetails.setAvailable(rs.getString(AdminAgentConstants.AVAILABLE));
            agentMarketDetails.setStatus(rs.getString(AdminAgentConstants.STATUS));
            agentsMarketList.add(agentMarketDetails);
        }
        return agentsMarketList;
    }

    @Override
    public List<MarketGroupEventsOutputTO> getMarketGroupEvents(String marketGroupName,Integer sportId){
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_MARKET_GROUP_SPORT_WISE_EVENT_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, marketGroupName);
            cs.setInt(2, sportId);
            return cs;
        }, (CallableStatement cs) -> this.extractMarketGroupEventList(cs.executeQuery()));
    }

    private List<MarketGroupEventsOutputTO> extractMarketGroupEventList(ResultSet rs) throws SQLException {
        List<MarketGroupEventsOutputTO> eventList = new ArrayList<>();
        MarketGroupEventsOutputTO eventDetails;
        while (rs.next()) {
            eventDetails = new MarketGroupEventsOutputTO();
            eventDetails.setEventId(rs.getInt(AdminAgentConstants.EVENT_ID));
            eventDetails.setEventName(rs.getString(AdminAgentConstants.EVENT_NAME));
            eventList.add(eventDetails);
        }
        return eventList;
    }

    @Override
    public List<SpecialMarketTeamsOutputTO> getSpecialMarketTeams() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_SPECIAL_MARKET_TEAM_LIST,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getTeamList(cs.executeQuery()));
    }

    private List<SpecialMarketTeamsOutputTO> getTeamList(ResultSet executeQuery) throws SQLException {
        List<SpecialMarketTeamsOutputTO> specialMarketTeamsList = new ArrayList<>();
        while (executeQuery.next()) {
            SpecialMarketTeamsOutputTO teamDetails = new SpecialMarketTeamsOutputTO();
            teamDetails.setTeamId(executeQuery.getInt(AdminAgentConstants.TEAM_ID));
            teamDetails.setTeamName(executeQuery.getString(AdminAgentConstants.TEAM_NAME));
            teamDetails.setBoxCount(executeQuery.getInt(AdminAgentConstants.BOX_COUNT));
            specialMarketTeamsList.add(teamDetails);
        }
        return specialMarketTeamsList;
    }

    @Override
    public List<AgentsMarketOddsOutputTO> getMarketOdds(Integer agentMarketId, Integer sportId, Integer eventId){
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_AGENTS_MARKET_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, agentMarketId);
            cs.setInt(2, sportId);
            cs.setInt(3, eventId);
            return cs;
        }, (CallableStatement cs) -> this.extractAgentMarketOdds(cs.executeQuery()));
    }
    private List<AgentsMarketOddsOutputTO> extractAgentMarketOdds(ResultSet rs) throws SQLException {
        List<AgentsMarketOddsOutputTO> agentsMarketOddsList = new ArrayList<>();
        AgentsMarketOddsOutputTO agentsMarketOddsDetails;
        while (rs.next()) {
            agentsMarketOddsDetails = new AgentsMarketOddsOutputTO();
            AgentOddsDetailsTO agentOddsDetailsTO=new AgentOddsDetailsTO();
            agentOddsDetailsTO.setBetFairBackOdds(rs.getDouble(AdminAgentConstants.BETFAIR_BACK_ODDS));
            agentOddsDetailsTO.setBetFairLayOdds(rs.getDouble(AdminAgentConstants.BETFAIR_LAY_ODDS));
            agentOddsDetailsTO.setBookMakerBackOdds(rs.getDouble(AdminAgentConstants.BOOKMAKER_BACK_ODDS));
            agentOddsDetailsTO.setBookMakerLayOdds(rs.getDouble(AdminAgentConstants.BOOKMAKER_LAY_ODDS));
            agentsMarketOddsDetails.setSelectionName(rs.getString(AdminAgentConstants.SELECTION_NAME));
            agentsMarketOddsDetails.setAgentOddsDetails(agentOddsDetailsTO);
            agentsMarketOddsList.add(agentsMarketOddsDetails);
        }
        return agentsMarketOddsList;
    }


    @Override
    public List<AgentMarketListOutputTO> getAgentMarketsList(String userLoginToken,Integer agentId) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_GET_AGENTS_MARKET_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            cs.setInt(2, agentId==null?0:agentId);
            return cs;
        }, (CallableStatement cs) -> this.extractAgentMarketsList(cs.executeQuery()));
    }

    private List<AgentMarketListOutputTO> extractAgentMarketsList(ResultSet rs) throws SQLException {
        List<AgentMarketListOutputTO> agentsMarketList = new ArrayList<>();
        AgentMarketListOutputTO agentMarketDetails;
        while (rs.next()) {
            agentMarketDetails = new AgentMarketListOutputTO();
            agentMarketDetails.setAgentName(rs.getString("agentname"));
            agentMarketDetails.setMarketResult(rs.getString("marketresult"));
            agentMarketDetails.setAgentMarketId(rs.getInt(AdminAgentConstants.AGENT_MARKET_ID));
            agentMarketDetails.setSportId(rs.getInt(AdminAgentConstants.SPORT_ID));
            agentMarketDetails.setEventId(rs.getInt(AdminAgentConstants.EVENT_ID));
            agentMarketDetails.setSportName(rs.getString(AdminAgentConstants.SPORT_NAME));
            agentMarketDetails.setEventName(rs.getString(AdminAgentConstants.EVENT_NAME));
            agentMarketDetails.setMarketName(rs.getString(AdminAgentConstants.MARKET_NAME));
            agentMarketDetails.setSubMarketName(rs.getString(AdminAgentConstants.SUBMARKET_NAME));
            agentMarketDetails.setMarketSelection(rs.getString(AdminAgentConstants.MARKET_SELECTION_NAME));
            agentMarketDetails.setAvailable(rs.getString(AdminAgentConstants.AVAILABLE));
            agentMarketDetails.setStatus(rs.getString(AdminAgentConstants.STATUS));
            agentMarketDetails.setSuspend(rs.getBoolean(AdminAgentConstants.IS_SUSPEND));
            agentsMarketList.add(agentMarketDetails);
        }
        return agentsMarketList;
    }

    @Override
    public Boolean addAgentMarketOdds(String userLoginToken,Integer agentMarketId,String bulkDataInsert){
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_ADD_AGENT_MARKET_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, userLoginToken);
            cs.setInt(2, agentMarketId);
            cs.setString(3, bulkDataInsert);
            return cs;
        }, (CallableStatement cs) -> {
            ResultSet rs = cs.executeQuery();
            Boolean isCreated = false;
            while (rs.next()) {
                isCreated = rs.getBoolean(AdminAgentConstants.IS_CREATED);
            }
            return isCreated;
        });
    }

    @Override
    public Boolean updateAgentMarketSuspended(AgentMarketSuspendInput agentMarketSuspendDetails){
        try {

            if(agentMarketSuspendDetails.getOddDictionaryId() !=0 && agentMarketSuspendDetails.getSuspend() == true){
                String suspendBookakerOdds = "update odds set status = 'SUSPENDED' where odd_dictionary_id=? and eventid=?";
                jdbcTemplate.update(suspendBookakerOdds, agentMarketSuspendDetails.getOddDictionaryId(), agentMarketSuspendDetails.getEventId());
                return true;
            }else if(agentMarketSuspendDetails.getOddDictionaryId() !=0 && agentMarketSuspendDetails.getSuspend() == false){
                String suspendBookakerOdds = "update odds set status = 'UNSUSPENDED' where odd_dictionary_id=? and eventid=?";
                jdbcTemplate.update(suspendBookakerOdds, agentMarketSuspendDetails.getOddDictionaryId(), agentMarketSuspendDetails.getEventId());
                return true;
            }

            String SQL = "update agent_markets set is_market_suspend = ? where agent_id=? and market_name=?";
            jdbcTemplate.update(SQL, agentMarketSuspendDetails.getSuspend(), agentMarketSuspendDetails.getAgentMarketId(), agentMarketSuspendDetails.getMarketName());

            if(agentMarketSuspendDetails.getSuspend() == true){
                String SQL1 = "update odds set status = 'SUSPENDED' where fancymarketname=? and eventid=?";
                jdbcTemplate.update(SQL1, agentMarketSuspendDetails.getMarketName(), agentMarketSuspendDetails.getEventId());
            }else{
                String SQL1 = "update odds set status = 'UNSUSPENDED' where fancymarketname=? and eventid=?";
                jdbcTemplate.update(SQL1, agentMarketSuspendDetails.getMarketName(), agentMarketSuspendDetails.getEventId());
            }


            return true;
        }catch (Exception e){
            return false;
        }

    }


    @Override
    public void suspendAccount(AgentTO agentDetails) {
        if(agentDetails.getStatus().equalsIgnoreCase("Suspend")) {
            String suspendMarkets = "update odds set status='SUSPENDED' where eventid in (select event_id from agent_markets where agent_id in (select agent_id from agent_markets where agent_id in (select agent_id from admin_agent where agent_name= ? ))) and providerid in (1,3)";
            jdbcTemplate.update(suspendMarkets, agentDetails.getAgentName());
            String SQL = "update bmeusers set statusid = 2 where userid = ?";
            jdbcTemplate.update(SQL, agentDetails.getAgentName());
        }else{
            String SQL = "update bmeusers set statusid = 1 where userid = ?";
            jdbcTemplate.update(SQL, agentDetails.getAgentName());
        }
    }

    @Override
    public String getCurrentPassword(String agentName) {
        return jdbcTemplate.queryForObject(GET_AGENT_PASSWORD +  agentName + "'", String.class);
    }

    @Override
    public void changePwd(AgentTO agentDetails, String newPassword) {
        String SQL = "update bmeusers set userpwd = ? where userid = ?";
        jdbcTemplate.update(SQL, newPassword, agentDetails.getAgentName());

        String SQL1 = "update admin_agent set password = ? where agent_name = ?";
        jdbcTemplate.update(SQL1, agentDetails.getAgentNewPassword(), agentDetails.getAgentName());
    }

    @Override
    public String getProfileEmail(String agentName) {
        return jdbcTemplate.queryForObject(GET_AGENT_EMAIL +  agentName + "'" , String.class);
    }

    @Override
    public List<AgentSportsOutputTO> getAgentSports(String agentName) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.GET_AGENT_SPORTS_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateAgentSports(cs.executeQuery()));
    }

    @Override
    public List<AgentEventsOutputTO> getAgentEvents(String agentName) {
        return jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.GET_AGENT_EVENTS_LIST,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateAgentEvents(cs.executeQuery()));
    }

    @Override
    public List<AgentMarketsOutputTO> getAgentMarkets(String agentName) {


        List<AgentMarketsOutputTO> agentMarketsOutputTOS = new ArrayList<>();

        agentMarketsOutputTOS =  jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.GET_AGENT_MARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateAgentMarkets(cs.executeQuery()));


        List<AgentMarketsOutputTO> agentMarketsOutputTOS1 = new ArrayList<>();

        agentMarketsOutputTOS1 = jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall(AdminAgentConstants.GET_AGENT_FANCYMARKETS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, agentName);
            return cs;
        }, (CallableStatement cs) -> populateAgentFancyMarkets(cs.executeQuery()));

        for( int i=0 ; i<= agentMarketsOutputTOS1.size()-1; i++){
            agentMarketsOutputTOS.add(agentMarketsOutputTOS1.get(i));
        }


        return agentMarketsOutputTOS;
    }

    private List<AgentMarketsOutputTO> populateAgentMarkets(ResultSet executeQuery) throws SQLException {
        List<AgentMarketsOutputTO> marketsOutputList = new ArrayList<>();
        Integer eventId = 0;String marketName = "";
        AgentMarketsOutputTO agentMarketsOutputTO = new AgentMarketsOutputTO();

        AgentFancyOutcomeTO fancyOutcomeTO = new AgentFancyOutcomeTO();

        List<AgentFancyOutcomeTO> agentFancyOutcomeTOS = new ArrayList<>();;
        List<String> outcome = new ArrayList<>();
        String out = "";
        BigDecimal b = new BigDecimal(0.0);
        while (executeQuery.next()){


            if(eventId != executeQuery.getInt("event_id") && !marketName.equalsIgnoreCase(executeQuery.getString("market_name")) && !marketName.equalsIgnoreCase("")){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
                marketsOutputList.add(agentMarketsOutputTO);
                agentFancyOutcomeTOS = new ArrayList<>();
                outcome = new ArrayList<>();
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }
            marketName = executeQuery.getString("market_name");
            agentMarketsOutputTO = new AgentMarketsOutputTO();
            agentMarketsOutputTO.setSportId(executeQuery.getInt("sport_id"));
            agentMarketsOutputTO.setSportName(executeQuery.getString("sport_name"));
            agentMarketsOutputTO.setAgentName(executeQuery.getString("agentname"));
            agentMarketsOutputTO.setMarketResult(executeQuery.getString("marketresult") == null ? "OPEN" : executeQuery.getString("marketresult"));
            agentMarketsOutputTO.setMarketStatus(executeQuery.getBoolean("marketstatus"));
            agentMarketsOutputTO.setEventId(executeQuery.getInt("event_id"));
            agentMarketsOutputTO.setEventName(executeQuery.getString("event_name"));
            agentMarketsOutputTO.setMarketName(executeQuery.getString("market_name"));
            agentMarketsOutputTO.setMarketSelection(executeQuery.getString("market_selection"));
            agentMarketsOutputTO.setMarketSuspended(executeQuery.getBoolean("is_market_suspend"));
            agentMarketsOutputTO.setMarketType((executeQuery.getString("sub_market_name")));
            agentMarketsOutputTO.setAgentMarketId(executeQuery.getInt("id"));
            agentMarketsOutputTO.setSelectionName(executeQuery.getString("market_selection"));
            agentMarketsOutputTO.setMinStake((int) executeQuery.getDouble("min_stake"));
            agentMarketsOutputTO.setMaxStake((int) executeQuery.getDouble("max_stake"));
            agentMarketsOutputTO.setBetDelay(executeQuery.getInt("bet_delay"));


            String providerOutcome = executeQuery.getString("provideroutcome");

            if(!out.equalsIgnoreCase("") && !providerOutcome.equalsIgnoreCase(out) && eventId == executeQuery.getInt("event_id")){
                agentFancyOutcomeTOS.add(fancyOutcomeTO);
                fancyOutcomeTO = new AgentFancyOutcomeTO();
            }
            fancyOutcomeTO.setOutcome(executeQuery.getString("provideroutcome"));
            fancyOutcomeTO.setOddDictionaryId(executeQuery.getInt("odd_dictionary"));
            String status = executeQuery.getString("outcome_suspend");

            if(status!= null ) {
                if (status.equalsIgnoreCase("SUSPENDED")) {
                    fancyOutcomeTO.setSuspend(true);
                } else {
                    fancyOutcomeTO.setSuspend(false);
                }
            }else {
                fancyOutcomeTO.setSuspend(false);
            }

            String SQLPL = "select sum(round(b.hometeamprofitloss, 2)) from bet b  join betslip bs on b.betslipid=bs.id where  b.eventid=? and  b.betstatusid=1";
            BigDecimal decimal = new BigDecimal(0.0);

            decimal = jdbcTemplate.queryForObject(SQLPL, new Object[]{executeQuery.getInt("event_id")}, BigDecimal.class);
            fancyOutcomeTO.setHommeTeamProfitLoss(decimal == null ? b : decimal);

            decimal = jdbcTemplate.queryForObject("select sum(round(b.awayteamprofitloss, 2)) from bet b  join betslip bs on b.betslipid=bs.id where  b.eventid=? and  b.betstatusid=1", new Object[]{executeQuery.getInt("event_id")}, BigDecimal.class);
            fancyOutcomeTO.setAwayTeamProfitLoss(decimal == null ? b : decimal);

            decimal = jdbcTemplate.queryForObject("select sum(round(b.drawprofitloss, 2)) from bet b  join betslip bs on b.betslipid=bs.id where  b.eventid=? and  b.betstatusid=1", new Object[]{executeQuery.getInt("event_id")}, BigDecimal.class);
            fancyOutcomeTO.setDrawProfitLoss(decimal == null ? b : decimal);

            if(executeQuery.getDouble("backodds") != 0.0) {
                fancyOutcomeTO.setOldBackOdds(executeQuery.getDouble("backodds"));
                fancyOutcomeTO.setBackOdds(executeQuery.getDouble("backodds"));
            }
            String SQL = "select max(o.backodds) from odds o join odd_dictionary od on o.odd_dictionary_id = od.oddsid where o.eventid=? and o.providerid=2 and od.bloutcome = ? ";
            fancyOutcomeTO.setBetfairbackOdds(jdbcTemplate.queryForObject(SQL, new Object[]{executeQuery.getInt("event_id"), providerOutcome}, Double.class));
            if(executeQuery.getDouble("layodds") != 0.0) {
                fancyOutcomeTO.setOldLayOdds(executeQuery.getDouble("layodds"));
                fancyOutcomeTO.setLayOdds(executeQuery.getDouble("layodds"));
            }
            String SQL1 = "select max(o.layodds) from odds o join odd_dictionary od on o.odd_dictionary_id = od.oddsid where o.eventid=? and o.providerid=2 and od.bloutcome = ? ";
            fancyOutcomeTO.setBetfairlayOdds(jdbcTemplate.queryForObject(SQL1, new Object[]{executeQuery.getInt("event_id"), providerOutcome}, Double.class));

            out = executeQuery.getString("provideroutcome");
            eventId = executeQuery.getInt("event_id");

            /*if(executeQuery.getBoolean("is_fancy") == true) {
                if(!outcome.contains(executeQuery.getString("fancyoutcome"))) {
                    fancyOutcomeTO.setOutcome(executeQuery.getString("fancyoutcome"));
                    fancyOutcomeTO.setBookMakerBackOdds(executeQuery.getDouble("fancyodds"));
                    fancyOutcomeTO.setMinStake(executeQuery.getDouble("minstake"));
                    fancyOutcomeTO.setMaxStake(executeQuery.getDouble("maxstake"));
                    agentFancyOutcomeTOS.add(fancyOutcomeTO);
                }
                outcome.add(executeQuery.getString("fancyoutcome"));
            }else{*/


                /*fancyOutcomeTO.setBetfairlayOdds(jdbcTemplate.queryForObject(
                        sql, new Object[]{id}, String.class));*/

            //fancyOutcomeTO.setBetfairbackOdds(executeQuery.getDouble("betfair_back"));
            //fancyOutcomeTO.setBetfairlayOdds(executeQuery.getDouble("betfair_lay"));
            //agentFancyOutcomeTOS.add(fancyOutcomeTO);
            // }
        }
        if(agentFancyOutcomeTOS.size() >0) {
            agentFancyOutcomeTOS.add(fancyOutcomeTO);
            agentMarketsOutputTO.setOutcomes(agentFancyOutcomeTOS);
            try {
                if (!agentMarketsOutputTO.getEventName().equalsIgnoreCase("null")) {
                    marketsOutputList.add(agentMarketsOutputTO);
                }
            }catch (Exception e){
                return marketsOutputList;
            }
            //marketsOutputList.add(agentMarketsOutputTO);
        }

        return marketsOutputList;
    }

    private List<AgentEventsOutputTO> populateAgentEvents(ResultSet executeQuery) throws SQLException {
        List<AgentEventsOutputTO> sportsOutputTOList = new ArrayList<>();
        while (executeQuery.next()){
            AgentEventsOutputTO agentEventsOutputTO = new AgentEventsOutputTO();
            agentEventsOutputTO.setEventId(executeQuery.getInt("event_id"));
            agentEventsOutputTO.setEventName(executeQuery.getString("event_name"));
            sportsOutputTOList.add(agentEventsOutputTO);
        }
        return sportsOutputTOList;

    }

    private List<AgentSportsOutputTO> populateAgentSports(ResultSet executeQuery) throws SQLException {
        List<AgentSportsOutputTO> sportsOutputTOList = new ArrayList<>();
        while (executeQuery.next()){
            AgentSportsOutputTO agentSportsOutputTO = new AgentSportsOutputTO();
            agentSportsOutputTO.setSportId(executeQuery.getInt("sport_id"));
            agentSportsOutputTO.setSportName(executeQuery.getString("sport_name"));
            sportsOutputTOList.add(agentSportsOutputTO);
        }
        return sportsOutputTOList;
    }

    @Override
    public String adminDeclareResult(AdminDeclareResultTO declareResultTO, Integer roleId) {
        try {
            Instant instant = Instant.now();
            OffsetDateTime odt = instant.atOffset(ZoneOffset.UTC);
            String currentUtc = odt.format((DateTimeFormatter.ofPattern(DATE_PATTERN)));


            if(declareResultTO.getOutcome().equalsIgnoreCase("DRAW")){
                declareResultTO.setOutcome("X");
            }
            if(declareResultTO.getOutcome().equalsIgnoreCase("NO RESULT") || declareResultTO.getOutcome().equalsIgnoreCase("TIE")){
                declareResultTO.setOutcome("VOID");
                String VOID_BET_SQL = "update bet set betstatus='VOID', betstatusid=4, betsettled=? where clmarketname='Match Odds - Bookmaker' and eventname=? ";
                jdbcTemplate.update(VOID_BET_SQL, Timestamp.valueOf(currentUtc), declareResultTO.getMarketName());
            }else {
                String WIN_BET_SQL = "update bet set betstatus='WIN', betstatusid=2, betsettled=? where clmarketname='Match Odds - Bookmaker' and cloutcomename=? and eventname=? ";
                String LOSS_BET_SQL = "update bet set betstatus='LOSS', betstatusid=3, betsettled=? where clmarketname='Match Odds - Bookmaker' and cloutcomename <> ? and eventname=? ";
                jdbcTemplate.update(WIN_BET_SQL, Timestamp.valueOf(currentUtc), declareResultTO.getOutcome(), declareResultTO.getMarketName());
                jdbcTemplate.update(LOSS_BET_SQL, Timestamp.valueOf(currentUtc), declareResultTO.getOutcome(), declareResultTO.getMarketName());
            }
            String  s = jdbcTemplate.queryForObject("select * from update_betfair_bet_settlement_userbal_transactions(?)",new Object[]{Timestamp.valueOf(currentUtc)}, String.class);
            String Result_Declare_SQL = "";
            if(roleId == 1) {
                Result_Declare_SQL = "update agent_markets set market_result=?,result_declared_by='Admin' where market_name=?";
            }else{
                Result_Declare_SQL = "update agent_markets set market_result=?,result_declared_by='Agent' where market_name=?";
            }
            jdbcTemplate.update(Result_Declare_SQL, declareResultTO.getBetStatus().toUpperCase(), declareResultTO.getMarketName());
            return "Success";
        }catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String suspend(MarketSuspensionTO marketSuspensionTO) {
        try {
            for(AgentMarketsInput suspensionTO : marketSuspensionTO.getMarkets()) {
                if (marketSuspensionTO.getStatus().equalsIgnoreCase("suspend")) {
                    String SQL = "update fancymarkets set isfancymarketsuspended = true where fancymarketname=? and eventid=?";
                    jdbcTemplate.update(SQL, suspensionTO.getMarketName(), suspensionTO.getEventId());
                } else {
                    String SQL = "update fancymarkets set isfancymarketsuspended = false where fancymarketname=? and eventid=?";
                    jdbcTemplate.update(SQL, suspensionTO.getMarketName(), suspensionTO.getEventId());
                }
            }
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }
}
